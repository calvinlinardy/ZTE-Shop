package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.Constant;
import com.zte.zshop.common.exception.SysuserNotExistException;
import com.zte.zshop.common.utils.ResponseResult;
import com.zte.zshop.entity.Role;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParam;
import com.zte.zshop.service.RoleService;
import com.zte.zshop.service.SysuserService;
import com.zte.zshop.vo.SysuserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2020-10-22 16:10
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/sysuser")
public class SysuserController
{
    @Autowired
    private SysuserService sysuserService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("roles")
    public List<Role> loadRoles(){
        return roleService.findAll();
    }


    @RequestMapping("/login")
    public String login(String loginName, String password, HttpSession session,Model model){

        try
        {
            Sysuser sysuser= sysuserService.login(loginName,password);
            //将用户名存入session作用域
            session.setAttribute("sysuser",sysuser);
            //返回主界面
            return "main";
        } catch (SysuserNotExistException e)
        {
            //e.printStackTrace();
            model.addAttribute("errorMsg","登录异常");
            //返回登录页面，继续登录
            return "login";

        }
    }




    @RequestMapping("/findAll")
    public String findAll(Integer pageNum, Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum= Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE);
        List<Sysuser> sysusers = sysuserService.findAll();
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);
        return "sysuserManager";
    }

    //新增用户
    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(SysuserVo sysuserVo){

        try
        {
            sysuserService.addSysuser(sysuserVo);
            return ResponseResult.success("添加成功");
        } catch (Exception e)
        {
            //e.printStackTrace();
            return ResponseResult.fail("添加失败");
        }
    }

    //校验用户名是否已经存在
    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String,Object> checkName(String loginName){
        Map<String,Object> map = new HashMap<>();
        boolean res=sysuserService.checkName(loginName);
        //如果用户名不存在
        if(res){
            map.put("valid",true);
        }
        else{
            map.put("valid",false);
            map.put("message","账号【"+loginName+"】已经存在");
        }
        return map;

    }

    //按条件查询
    @RequestMapping("/findByParams")
    public String findByParams(SysuserParam sysuserParam,Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE);
        List<Sysuser> sysusers= sysuserService.findByParams(sysuserParam);
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);
        //设置数据回显
        model.addAttribute("sysuserParam",sysuserParam);
        //返回用户列表页面
        return "sysuserManager";

    }

    //启用禁用
    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(Integer id){
        try
        {
            sysuserService.modifyStatus(id);
            return ResponseResult.success("更新成功");
        } catch (Exception e)
        {
            //e.printStackTrace();
            return ResponseResult.fail("更新失败");
        }
    }
}
