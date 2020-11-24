package com.zte.zshop.cart;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2020-11-13 14:14
 * Description:<描述>
 * 购物车工具类：
 * 逻辑：
 * 从session作用域中获取购物车对象，如果session中没有该对象，创建一个新的对象，放入到该session中，若有，直接返回(懒汉式单例)
 */
public class ShoppingCartUtils
{
    public static ShoppingCart getShoppingCart(HttpSession session){
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingCart");
        if(sc==null){
            sc = new ShoppingCart();
            session.setAttribute("shoppingCart",sc);
        }
        return sc;
    }

    //根据时间生成订单号
    public static String getOrderIdByTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result="";
        Random random = new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
}
