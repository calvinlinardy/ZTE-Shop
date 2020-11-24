package com.zte.zshop.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2020-10-29 15:38
 * Description:<描述>
 */
public class StringUtils
{

    //随机生成文件名
    //aaaa.jpg
    ///yyyyMMdd.jpg
    public static String renameFileName(String fileName){


        int dotIndex = fileName.lastIndexOf(".");
        //获取后缀名，如.jpg
        String suffix = fileName.substring(dotIndex);
        return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+new Random().nextInt(100) +suffix;

    }


    /**
     * 获取hashCode生成的二级目录
     * @param fileName
     * @return
     */
    public static String generateRandomDir(String fileName)
    {
        //获取该文件名的哈希值
        int hashCode = fileName.hashCode();
        //目录1，获取到1-16的一级文件夹
        int dir1=hashCode & 0xf;
        //目录2，获取到1-16的二级文件夹
        int dir2=(hashCode & 0xf0)>>4;
        return "/"+dir1+"/"+dir2;




    }

    public static void main(String[] args)
    {
        String path=generateRandomDir("rose");
        System.out.println(path);

    }
}
