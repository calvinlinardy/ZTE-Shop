package com.zte.zshop.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2020-11-06 13:47
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/code")
public class CodeController
{
    @RequestMapping("/image")
    //逻辑：1:随机生成一个字符串（包括字母和数字）,2:将该字符串生成一张图片
    public void image(HttpServletResponse resp, HttpServletRequest req) throws IOException
    {

        //创建一个图片对象缓冲区
        BufferedImage bi = new BufferedImage(68,22,BufferedImage.TYPE_INT_RGB);
        //获取一个刷子
        Graphics gra = bi.getGraphics();
        Color c = new Color(200,150,255);//设置颜色
        gra.setColor(c);
        gra.fillRect(0,0,68,22);



        //创建一个数组，在数组中存放字母和数字
        char[] ch="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int len = ch.length;
        int index;
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        //随机取4个字符
        for(int i=0;i<4;i++){
            index=r.nextInt(len);//随机返回[0,len)范围内的任意整数
            //设置文字的颜色
            gra.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(158)));
            //绘制字符
            gra.drawString(ch[index]+"",(i*15)+3,18);//3   18    33   48
            sb.append(ch[index]);

        }
        //将字符串从内存写入输出流
        ImageIO.write(bi,"jpg",resp.getOutputStream());
        //将该字符存放在session作用域，供用户使用
        req.getSession().setAttribute("picCode",sb.toString());

    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String,Object> checkCode(String code,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        //从session中获得code
        String picCode = (String) session.getAttribute("picCode");
        //将这个code和传递过来的值进行比较
        if(picCode.equalsIgnoreCase(code)){
            map.put("valid",true);
        }
        else{
            map.put("valid",false);
            //map.put("message","验证码错误");
        }
        return map;
    }
}
