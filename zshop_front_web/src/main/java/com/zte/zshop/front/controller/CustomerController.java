package com.zte.zshop.front.controller;

import com.zte.zshop.common.exception.LoginErrorException;
import com.zte.zshop.common.utils.ResponseResult;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Author:helloboy
 * Date:2020-11-12 14:50
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/customer")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;
    //登录
    @RequestMapping("/loginByAccount")
    @ResponseBody
    public ResponseResult login(String loginName, String password, HttpSession session)
    {
        try
        {
            Customer customer=customerService.login(loginName,password);
            session.setAttribute("customer",customer);
            return ResponseResult.success(customer);
        } catch (LoginErrorException e)
        {
            //e.printStackTrace();
            return ResponseResult.fail("登录失败");
        }

    }

    @RequestMapping("/loginOut")
    @ResponseBody
    public ResponseResult loginOut(HttpSession session){
        //将session注销
        //session.invalidate();
        //将session中的customer移除
        session.removeAttribute("customer");
        return ResponseResult.success("退出成功");
    }




}
