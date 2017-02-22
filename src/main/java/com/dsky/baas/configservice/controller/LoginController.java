package com.dsky.baas.configservice.controller;

import java.security.Principal;
/**
 * @ClassName: DateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class LoginController {
	
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model) {
        return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model ) {
        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("User Name: "+ userName);
        return "index";//"userInfoPage";
    }
    
    @RequestMapping(value = "/changepassword2db", method = RequestMethod.POST)
    public @ResponseBody String changePassword(String username,String oldpassword,String newpassword,Model model, Principal principal,HttpServletRequest request) {
        String userName = principal.getName();
        System.out.println("User Name: "+ userName);
        return "welcome "+userName +"修改密码功能尚未提供，敬请期待 ...";
    }
    
    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        return "changePasswordPage";
    }
    
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("message", "您好！ " + principal.getName()
                    + "<br> 您没有访问该页面的权限!");
        } else {
            model.addAttribute("msg",
                    "您没有访问该页面的权限!");
        }
        return "403Page";
    }
}
