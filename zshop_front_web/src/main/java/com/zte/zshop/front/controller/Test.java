package com.zte.zshop.front.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2020-11-20 9:53
 * Description:<描述>
 */
public class Test
{
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


    public static void main(String[] args)
    {
        //随机生成6位数字
        //思路：math.random(),范围：[0.0,1.0),那么math.random()*9+1一定是小于10的数
        //System.out.println((int)((Math.random()*9+1)*100000));
        String orderIdByTime = getOrderIdByTime();
        System.out.println(orderIdByTime);

    }
}
