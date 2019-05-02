package com.CNFloWopen.niugou.web.frontend;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.entity.Product;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.service.ProductService;
import com.CNFloWopen.niugou.util.BaiduDwz;
import com.CNFloWopen.niugou.util.CodeUtil;
import com.CNFloWopen.niugou.util.HttpServletRequestUtil;
import com.CNFloWopen.niugou.web.shopadmin.ShopAuthManagementController;
import com.CNFloWopen.niugou.web.shopadmin.UserProductManagementController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;


	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		获取前台传过来的productId
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
//		空值判断
		if (productId != -1) {
//			根据product获取商品信息，包含商品详情图列表
			product = productService.getProductById(productId);
//			获取用户信息
			PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
			if (user == null)
			{
//				没登录，不显示二维码
				modelMap.put("needQRCode",false);
			}else {
//				登录显示二维码
				modelMap.put("needQRCode",true);
			}

			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}





	//----------------------------------------------------
//----------------------------------------------------
//----------------------------------------------------
//----------------------------------------------------
//----------------------------------------------------
//----------------------------------------------------
//----------------------------------------------------
//	和授权二维码的方法基本一致



	//	获取微信用户信息的api前缀
	private static String urlPrefix;
	//	微信获取用户信息的api中间部分
	private static String urlMiddle;
	//	微信获取用户信息的api后缀
	private static String urlSuffix;
	//	微信回传给的响应添加顾客商品映射信息的url
	private static String productmapUrl;
	@Value("${wechat.prefix}")
	public  void setUrlPrefix(String urlPrefix) {
		ProductDetailController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public  void setUrlMiddle(String urlMiddle) {
		ProductDetailController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public  void setUrlSuffix(String urlSuffix) {

		ProductDetailController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.productmap.url}")
	public void setProductmapUrl(String productmapUrl) {
		ProductDetailController.productmapUrl = productmapUrl;
	}


	/**
	 * 生成带有商品消费凭证url的二维码，仅凭操作员扫描，证明以消费，微信扫一扫就能进入链接
	 * @param response
	 */
	@RequestMapping(value = "/generateqrcode4sproduct",method = RequestMethod.GET)
	@ResponseBody
	public void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response)
	{
//		获取前端传过来的productId
		long productId = HttpServletRequestUtil.getLong(request,"productId");
//		从session中获取到用户的信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
//		空值判断
		if (productId!=-1 && user!=null && user.getUserId()!=null)
		{
//		获取当前的时间轴，以保证二维码的有效性，精确到毫秒
			long timeStamp = System.currentTimeMillis();
//		将商品id，顾客id和timeStamp传入content复制到state中，这样微信获取到这些信息后会回传到用户商户
//		加上aaa是为了一会的添加信息的方法里面替换这些信息使用
			String content = "{aaaproductIdaaa:"+productId+",aaacustomerIdaaa:"+user.getUserId()+",aaacreateTimeaaa:"+timeStamp+"}";
			try {
//				将content的信息先进行base64编码以避免特殊字符进行干扰，之后拼接的url
				String longUrl = urlPrefix+productmapUrl+urlMiddle+ URLEncoder.encode(content,"UTF-8")+urlSuffix;
//				将目标url转化为短的url
				String shortUrl = BaiduDwz.createShortUrl(longUrl);
//				调用二维码生成的工具类方法，传入短的二维码，生成二维码
				BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl,response);
//				将二维码以图片流的形式输出到前端
				MatrixToImageWriter.writeToStream(qRcodeImg,"png",response.getOutputStream());
			}catch (IOException e)
			{
				e.printStackTrace();
			}
		}


	}




}
