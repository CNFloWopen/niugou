package com.CNFloWopen.niugou.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/local")
public class LoginController {
    @PostMapping(value = "/register")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpServletRequest request) {

        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            request.getSession().setAttribute("loginUser", username);
            //登录成功
            return "redirect:/frontend/index";
        }
        //登录失败
        map.put("msg", "用户名密码错误");
        return "register";
    }
}
