package com.CNFloWopen.niugou.web.shopadmin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CNFloWopen.niugou.dto.ShopAuthMapExecution;
import com.CNFloWopen.niugou.dto.UserAccessToken;
import com.CNFloWopen.niugou.dto.UserProductMapExecution;
import com.CNFloWopen.niugou.entity.*;
import com.CNFloWopen.niugou.enums.UserProductMapStateEnum;
import com.CNFloWopen.niugou.service.ProductService;
import com.CNFloWopen.niugou.service.ShopAuthMapService;
import com.CNFloWopen.niugou.service.UserProductMapService;
import com.CNFloWopen.niugou.service.WechatAuthService;
import com.CNFloWopen.niugou.util.BaiduDwz;
import com.CNFloWopen.niugou.util.CodeUtil;
import com.CNFloWopen.niugou.util.HttpServletRequestUtil;
import com.CNFloWopen.niugou.util.wechat.WechatUtil;
import com.CNFloWopen.niugou.web.frontend.ProductDetailController;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
	@Autowired
	private UserProductMapService userProductMapService;
	@Autowired
	private WechatAuthService wechatAuthService;
	@Autowired
	private ShopAuthMapService shopAuthMapService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/listuserproductmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserProductMapsByShop(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		获取分页信息
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
//		获取当前店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
//		空值效验，确保shopId不为空
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
//			添加查询条件
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setShop(currentShop);
			String productName = HttpServletRequestUtil.getString(request,
					"productName");
			if (productName != null) {
//				若前端想按照商品名进行模糊查询，则传入productName
				Product product = new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
			}
//			根据传入的查询条件获取该店铺的商品销售情况
			UserProductMapExecution ue = userProductMapService
					.listUserProductMap(userProductMapCondition, pageIndex,
							pageSize);
			modelMap.put("userProductMapList", ue.getUserProductMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	/**
	 * 微信扫描添加消费记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adduserproductmap", method = RequestMethod.GET)
	@ResponseBody
	private String addUserProductMap(HttpServletRequest request) throws UnsupportedEncodingException {
//		获取微信授权信息
		WechatAuth auth = getOperatorInfo(request);
		if (auth!=null)
		{
			PersonInfo operator = auth.getPersonInfo();
//			传入操作员的信息
			request.getSession().setAttribute("user",request);
//			获取二维码中state携带的content信息并解码
//			解析微信回传过来的自定义参数state，由于之前进行了编码，这里需要解码一下
			String qrCodeinfo = new String(URLDecoder.decode(HttpServletRequestUtil.getString(request,"state"),"UTF-8"));
//			因为要做json转化所以要有ObjectMapper
			ObjectMapper mapper = new ObjectMapper();
			WechatInfo wechatInfo = null;
			try {
//				将解码后的内容用aaa去替换掉之前生成的二维码的时候加入aaa前缀，转化为WechatInfo
				wechatInfo = mapper.readValue(qrCodeinfo.replace("aaa","\""),WechatInfo.class);
			}catch (Exception e)
			{
				return "shop/operationfail";
			}

			//效验二维码是否过期
			if (!checkQRCodeInfo(wechatInfo))
			{
				return "shop/operationfail";
			}

//			获取添加消费记录所需要的参数并组建成userproductmap实例
			Long productId = wechatInfo.getProductId();
			Long customerId = wechatInfo.getCustomerId();
			UserProductMap userProductMap = compactUserProdcutMap4Add(customerId,productId,auth.getPersonInfo());
//			空值效验
			if (userProductMap != null && customerId != -1)
			{
				try {
					if (!checkShopAuth(operator.getUserId(),userProductMap))
					{
						return "shop/operationfail";
					}
//					添加消费记录
					UserProductMapExecution se = userProductMapService.addUserProductMap(userProductMap);
					if (se.getState() == UserProductMapStateEnum.SUCCESS.getState())
					{
						return "shop/operationsuccess";
					}
				}catch (Exception e)
				{
						return "shop/operationfail";
				}
			}
		}
		return "shop/operationfail";

	}



	/**
	 * 获取操作员的信息
	 * @param request
	 * @return
	 */
	private WechatAuth getOperatorInfo(HttpServletRequest request)
	{
		String code = request.getParameter("code");
		WechatAuth auth = null;
		if (null != code)
		{
			UserAccessToken token;
			try {
				token = WechatUtil.getUserAccessToken(code);
				String openId = token.getOpenId();
				request.getSession().setAttribute("openId",openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return auth;
	}

	/**
	 * 根据传入的customerId,productId以及操作员信息组建用户消费记录
	 * @param customerId
	 * @param productId
	 * @param operator
	 * @return
	 */
	private UserProductMap compactUserProdcutMap4Add(Long customerId,Long productId,PersonInfo operator)
	{
		UserProductMap userProductMap = null;
		if (customerId!=null && productId!=null)
		{
			userProductMap = new UserProductMap();
			PersonInfo customer = new PersonInfo();
			customer.setUserId(customerId);
//			设置用户消费记录，在那个店铺消费了什么商品，在什么时候
			Product product = productService.getProductById(productId);
			userProductMap.setProduct(product);
			userProductMap.setShop(product.getShop());
			userProductMap.setUser(customer);
			userProductMap.setCreateTime(new Date());
			userProductMap.setOperator(operator);
		}
		return userProductMap;
	}

	/**
	 * 检验二维码是否过期，通过二维码携带的createTime判断其是否超过10分钟，超过10分钟则认为过期
	 * @param wechatInfo
	 * @return
	 */
	private boolean checkQRCodeInfo(WechatInfo wechatInfo)
	{
		if (wechatInfo!=null  && wechatInfo.getCreateTime()!=null)
		{
			long nowTime = System.currentTimeMillis();
//			10分钟
			if ((nowTime-wechatInfo.getCreateTime())<=600000)
			{
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	/**
	 * 检查扫码的人是否具有权限
	 * @param userId
	 * @param userProductMap
	 * @return
	 */
	private boolean checkShopAuth(long userId,UserProductMap userProductMap)
	{
//		获取该店铺的所有授权信息
		ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.listShopAuthMapByShopId(userProductMap.getShop().getShopId(),1,1000);
		for (ShopAuthMap shopAuthMap:shopAuthMapExecution.getShopAuthMapList())
		{
//			查看是否给过该人员进行过授权
			if (shopAuthMap.getEmployee().getUserId() == userId)
			{
				return true;
			}
		}
		return false;
	}





}
