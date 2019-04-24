package com.CNFloWopen.niugou.web.local;


import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.dto.LocalAuthExecution;
import com.CNFloWopen.niugou.entity.LocalAuth;
import com.CNFloWopen.niugou.entity.PersonInfo;
import com.CNFloWopen.niugou.enums.LocalAuthStateEnum;
import com.CNFloWopen.niugou.service.LocalAuthService;
import com.CNFloWopen.niugou.util.CodeUtil;
import com.CNFloWopen.niugou.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "local", method = { RequestMethod.GET,
		RequestMethod.POST })
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;

	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> LoginCheck(HttpServletRequest request) {
		//获取是否需要进行验证码验证码效验的标识符
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取是否需要进行验证码效验的标识符
		boolean needVerify = HttpServletRequestUtil.getBoolean(request,
				"needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//获取用户名和密码
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		//非空效验
		if (userName != null && password != null) {
			LocalAuth localAuth = localAuthService
					.getLocalAuthByUserNameAndPwd(userName, password);
			if (localAuth != null) {
					//若能取到账号则登录成功
					modelMap.put("success", true);
					//同时在session里面设置用户信息
					request.getSession().setAttribute("user",
							localAuth.getPersonInfo());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}


	/**
	 * 绑定微信账号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码效验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//获取输入的账号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		//获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		//从session中获取当前用户信息（用户一旦通过微信登录后，便能获取到用户的信息）
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		//非空判断，要求密码和当前的session为空
		if (userName != null && password != null && user != null
				&& user.getUserId() != null) {
			//创建localAuth对象并赋值
			LocalAuth localAuth = new LocalAuth();
			localAuth.setUserName(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			LocalAuthExecution le = localAuthService
					.bindLocalAuth(localAuth);
			if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", le.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}

	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码效验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//获取用户名和密码
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		//获取新密码
		String newPassword = HttpServletRequestUtil.getString(request,
				"newPassword");
		//从session中获取当前用户信息（用户一旦通过微信登录后，便能获取到用户的信息）
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");

		if (userName != null && password != null && newPassword != null
				&& user !=null && user.getUserId()!=null && !password.equals(newPassword)) {
			try {
				//查看原先的账号，看看输入的账号是否一致，不一样则认为是非法操作
				LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if (localAuth!=null && !localAuth.getUserName().equals(userName))
				{
					//不一致则直接退出
					modelMap.put("success",false);
					modelMap.put("errMsg","输入的账号非本次登录的账号");
					return modelMap;
				}
				//修改平台账号的用户密码
				LocalAuthExecution le = localAuthService.modifyLocalAuth(
						user.getUserId(), userName, password, newPassword);
				if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	/**
	 * 用户点击退出的时候进行注销session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//将用户的session设置为空
		request.getSession().setAttribute("user", null);
//		request.getSession().setAttribute("shopList", null);
//		request.getSession().setAttribute("currentShop", null);
		modelMap.put("success", true);
		return modelMap;
	}

//
	@RequestMapping(value = "/Loginregister", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> register(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		LocalAuth localAuth = null;

		String localAuthStr = HttpServletRequestUtil.getString(request,
				"localAuthStr");
//		MultipartHttpServletRequest multipartRequest = null;
//		CommonsMultipartFile profileImg = null;
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//				request.getSession().getServletContext());
//		if (multipartResolver.isMultipart(request)) {
//			multipartRequest = (MultipartHttpServletRequest) request;
//			profileImg = (CommonsMultipartFile) multipartRequest
//					.getFile("thumbnail");
//		}
		CommonsMultipartFile profileImg = null;
//        文件上传解析器，解析request中的信息，就是在上下文中找===上传文件的信息
		CommonsMultipartResolver multipartResolver =
				new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断request用户给的请求注册中式不是上传了文件
		if (multipartResolver.isMultipart(request))
		{
//            强制转化
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//            提取出上传的文件并命名为shopImg
			// shopImg是和前端约定好的变量名
			profileImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
		}
		else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		try {
			localAuth = mapper.readValue(localAuthStr, LocalAuth.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (localAuth != null && localAuth.getPassword() != null
				&& localAuth.getUserName() != null) {
			try {
				PersonInfo user = (PersonInfo) request.getSession()
						.getAttribute("user");
				if (user != null && localAuth.getPersonInfo() != null) {
					localAuth.getPersonInfo().setUserId(user.getUserId());
				}
//				localAuth.getPersonInfo().setShopOwnerFlag(1);
//				localAuth.getPersonInfo().setAdminFlag(0);
				localAuth.getPersonInfo().setUserType(1);
				ImageHolder imageHolder = new ImageHolder(profileImg.getOriginalFilename(),profileImg.getInputStream());
				LocalAuthExecution le = localAuthService.register(localAuth,
						imageHolder);
				if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入注册信息");
		}
		return modelMap;
	}

}
