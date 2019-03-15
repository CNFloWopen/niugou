package com.isagod.web.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
public class LocalController {
    /**
     * 绑定账号路由
     * @return
     */
    @RequestMapping(value = "/accountbind",method = RequestMethod.GET)
    public String accountbind()
    {
        return "local/accountbind";
    }

    /**
     * 修改账户密码
     * @return
     */
    @RequestMapping(value = "/changepsw",method = RequestMethod.GET)
    public String changepsw()
    {
        return "local/changepsw";
    }


}
