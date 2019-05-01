package com.CNFloWopen.niugou.web.shopadmin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CNFloWopen.niugou.dto.ShopAuthMapExecution;
import com.CNFloWopen.niugou.dto.UserAccessToken;
import com.CNFloWopen.niugou.entity.*;
import com.CNFloWopen.niugou.enums.ShopAuthMapStateEnum;
import com.CNFloWopen.niugou.service.PersonInfoService;
import com.CNFloWopen.niugou.service.ShopAuthMapService;
import com.CNFloWopen.niugou.service.WechatAuthService;
import com.CNFloWopen.niugou.util.BaiduDwz;
import com.CNFloWopen.niugou.util.CodeUtil;
import com.CNFloWopen.niugou.util.HttpServletRequestUtil;
import com.CNFloWopen.niugou.util.wechat.WechatUtil;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
	@Autowired
	private ShopAuthMapService shopAuthMapService;
	@Autowired
	private PersonInfoService personInfoService;
	@Autowired
	private WechatAuthService wechatAuthService;

	@RequestMapping(value = "/listshopauthmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopAuthMapsByShop(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		取出分页信息
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
//		从session中获取店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
//		空值判断
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
				&& (currentShop.getShopId() != null)) {
//			分页取出该店铺下的授权信息列表
			ShopAuthMapExecution se = shopAuthMapService
					.listShopAuthMapByShopId(currentShop.getShopId(),
							pageIndex, pageSize);
			modelMap.put("shopAuthMapList", se.getShopAuthMapList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopauthmapbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		非空判断
		if (shopAuthId != null && shopAuthId > -1) {
//			根据前台传入的shopAuthId查找相对应的授权信息
			ShopAuthMap shopAuthMap = shopAuthMapService
					.getShopAuthMapById(shopAuthId);
			modelMap.put("shopAuthMap", shopAuthMap);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopAuthId");
		}
		return modelMap;
	}

	/**
	 * 根据微信回传回来的参数添加店铺的授权信息
	 * @param response
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/addshopauthmap", method = RequestMethod.GET)
	private String addShopAuthMap( HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
//			从request里面得到微信用户的信息
		WechatAuth auth = getEmployeeInfo(request);
		if (auth!=null)
		{
//			根据userId获取用户信息
			PersonInfo user = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
//			将用户信息添加到user里
			request.getSession().setAttribute("user",user);
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
//			效验二维码是否过期
			if (!checkQRCodeInfo(wechatInfo))
			{
				return "shop/operationfail";
			}

//			去重效验,由于出现扫码一个员工出现多次插入到数据库的情况
//			获取店铺下的所有授权信息
			ShopAuthMapExecution allMapList = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(),1,999);
			List<ShopAuthMap> shopAuthMapList = allMapList.getShopAuthMapList();
			for (ShopAuthMap sm:shopAuthMapList)
			{
				if (sm.getEmployee().getUserId() == user.getUserId())
				{
					return "shop/operationfail";
				}
			}

			try {
//				根据获取到的内容，添加微信授权信息
				ShopAuthMap shopAuthMap = new ShopAuthMap();
				Shop shop = new Shop();
				shop.setShopId(wechatInfo.getShopId());
				shopAuthMap.setShop(shop);
				shopAuthMap.setEmployee(user);
				shopAuthMap.setTitle("员工");
				shopAuthMap.setTitleFlag(1);
				ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState())
				{
					return "shop/operationsuccess";
				}else {
					return "shop/operationfail";
				}
			}catch (RuntimeException e)
			{
				return "shop/operationfail";
			}
		}
		return "shop/operationfail";
	}

	@RequestMapping(value = "/modifyshopauthmap", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShopAuthMap(String shopAuthMapStr,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		授权编辑的时候调用还是删除/恢复操作的时候使用
//		若为前者需要验证码的判断，若为后者则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");
//		验证码效验
		if (!statusChange&& !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		ShopAuthMap shopAuthMap = null;
		try {
//			将前台传入的字符川json转化为shopAuthMap实例
			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
//		空值判断
		if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
			try {
//				看看操作的对象是否是店家本身，店家本身不支持更改
				if (!checkPermission(shopAuthMap.getShopAuthId()))
				{
					modelMap.put("success",false);
					modelMap.put("errMsg","无法对店家本身进行操作(店家已经具有最高权限)");
					return modelMap;
				}
				ShopAuthMapExecution se = shopAuthMapService
						.modifyShopAuthMap(shopAuthMap);
				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入要修改的授权信息");
		}
		return modelMap;
	}
//
//	@RequestMapping(value = "/removeshopauthmap", method = RequestMethod.GET)
//	@ResponseBody
//	private Map<String, Object> removeShopAuthMap(Long shopAuthId) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		if (shopAuthId != null && shopAuthId > 0) {
//			try {
//				ShopAuthMapExecution se = shopAuthMapService
//						.removeShopAuthMap(shopAuthId);
//				if (se.getState() == ShopAuthMapStateEnum.SUCCESS.getState()) {
//					modelMap.put("success", true);
//				} else {
//					modelMap.put("success", false);
//					modelMap.put("errMsg", se.getStateInfo());
//				}
//			} catch (RuntimeException e) {
//				modelMap.put("success", false);
//				modelMap.put("errMsg", e.toString());
//				return modelMap;
//			}
//
//		} else {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "请至少选择一个授权进行删除");
//		}
//		return modelMap;
//	}

	/**
	 * 效验角色
	 * @param shopAuthId
	 * @return
	 */
	private boolean checkPermission(Long shopAuthId)
	{
		ShopAuthMap grantedPerson = shopAuthMapService.getShopAuthMapById(shopAuthId);
		if (grantedPerson.getTitleFlag()==0)
		{
//			若是店家本身，则不能进行操作
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 根据微信回传的code获取用户的信息
	 * @param request
	 * @return
	 */
	private WechatAuth getEmployeeInfo(HttpServletRequest request)
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
	 * 检验二维码是否过期，通过二维码携带的createTime判断其是否超过10分钟，超过10分钟则认为过期
	 * @param wechatInfo
	 * @return
	 */
	private boolean checkQRCodeInfo(WechatInfo wechatInfo)
	{
		if (wechatInfo!=null && wechatInfo.getShopId()!=null && wechatInfo.getCreateTime()!=null)
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



	//	获取微信用户信息的api前缀
	private static String urlPrefix;
	//	微信获取用户信息的api中间部分
	private static String urlMiddle;
	//	微信获取用户信息的api后缀
	private static String urlSuffix;
	//	微信回传给的响应添加授权信息的url
	private static String authUrl;
	@Value("${wechat.prefix}")
	public  void setUrlPrefix(String urlPrefix) {
		ShopAuthManagementController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public  void setUrlMiddle(String urlMiddle) {
		ShopAuthManagementController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public  void setUrlSuffix(String urlSuffix) {
		ShopAuthManagementController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.auth.url}")
	public  void setAuthUrl(String authUrl) {
		ShopAuthManagementController.authUrl = authUrl;
	}

	/**
	 * 生成带有url的二维码，微信扫一扫就能链接到对应的url里面
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "generateqrcode4shopauth",method = RequestMethod.GET)
	@ResponseBody
	public void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response)
	{
//		从session里面获取当前shop的信息
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		if (shop!=null && shop.getShopId()!=null)
		{
//		获取当前的时间轴，以保证二维码的有效性，精确到毫秒
			long timeStamp = System.currentTimeMillis();
//		将店铺id的timestamp传入content，赋值到state中，这样微信获取到这些信息后回传到授权信息的添加
//		加上aaa是为了一会的添加信息的方法里面替换这些信息使用
			String content = "{aaashopIdaaa:"+shop.getShopId()+",aaacreateTimeaaa:"+timeStamp+"}";
			try {
//		将content的信息先进行base64编码以避免特殊字符进行干扰，之后拼接的url
				String longUrl = urlPrefix+authUrl+urlMiddle+ URLEncoder.encode(content,"UTF-8")+urlSuffix;
//		将目标url转化为短的url
				String shortUrl = BaiduDwz.createShortUrl(longUrl);
//		调用二维码生成的工具类方法，传入短的二维码，生成二维码
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl,response);
//		将二维码以图片流的形式输出到前端
				MatrixToImageWriter.writeToStream(qRcodeImg,"png",response.getOutputStream());
			}catch (IOException e)
			{
				e.printStackTrace();
			}
		}


	}

}
