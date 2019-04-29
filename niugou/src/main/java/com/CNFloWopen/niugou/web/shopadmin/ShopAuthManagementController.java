package com.CNFloWopen.niugou.web.shopadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.CNFloWopen.niugou.dto.ShopAuthMapExecution;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.entity.ShopAuthMap;
import com.CNFloWopen.niugou.enums.ShopAuthMapStateEnum;
import com.CNFloWopen.niugou.service.ShopAuthMapService;
import com.CNFloWopen.niugou.util.CodeUtil;
import com.CNFloWopen.niugou.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/shopadmin")
public class ShopAuthManagementController {
	@Autowired
	private ShopAuthMapService shopAuthMapService;


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

//	@RequestMapping(value = "/addshopauthmap", method = RequestMethod.POST)
//	@ResponseBody
//	private Map<String, Object> addShopAuthMap(String shopAuthMapStr,
//			HttpServletRequest request) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		ObjectMapper mapper = new ObjectMapper();
//		ShopAuthMap shopAuthMap = null;
//		try {
//			shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
//		} catch (Exception e) {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", e.toString());
//			return modelMap;
//		}
//		if (shopAuthMap != null) {
//			try {
//				Shop currentShop = (Shop) request.getSession().getAttribute(
//						"currentShop");
//				PersonInfo user = (PersonInfo) request.getSession()
//						.getAttribute("user");
//				if (currentShop.getOwnerId() != user.getUserId()) {
//					modelMap.put("success", false);
//					modelMap.put("errMsg", "无操作权限");
//					return modelMap;
//				}
//				shopAuthMap.setShopId(currentShop.getShopId());
//				shopAuthMap.setEmployeeId(user.getUserId());
//				ShopAuthMapExecution se = shopAuthMapService
//						.addShopAuthMap(shopAuthMap);
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
//			modelMap.put("errMsg", "请输入授权信息");
//		}
//		return modelMap;
//	}

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




	//	获取微信用户信息的api前缀
	private static String urlPrefix;
	//	微信获取用户信息的api中间部分
	private static String urlMiddle;
	//	微信获取用户信息的api后缀
	private static String urlSuffix;
	//	微信回传给的响应添加授权信息的url
	private static String authUrl;
	@Value("${wechat.prefix}")
	public static void setUrlPrefix(String urlPrefix) {
		ShopAuthManagementController.urlPrefix = urlPrefix;
	}
	@Value("${wechat.middle}")
	public static void setUrlMiddle(String urlMiddle) {
		ShopAuthManagementController.urlMiddle = urlMiddle;
	}
	@Value("${wechat.suffix}")
	public static void setUrlSuffix(String urlSuffix) {
		ShopAuthManagementController.urlSuffix = urlSuffix;
	}
	@Value("${wechat.auth.url}")
	public static void setAuthUrl(String authUrl) {
		ShopAuthManagementController.authUrl = authUrl;
	}

	
}
