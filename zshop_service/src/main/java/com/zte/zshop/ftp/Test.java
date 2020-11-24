package com.zte.zshop.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author:helloboy
 * Date:2020-11-11 14:36
 * Description:<描述>
 */
public class Test
{

    public static void main(String[] args)
    {
        testFtp();
    }

    private static void testFtp()
    {
        //创建客户端对象
        FTPClient ftp = new FTPClient();
        InputStream local=null;
        try
        {
            //连接ftp服务器
            ftp.connect("localhost",21);
            //登录
            ftp.login("mike","123");

            //设置上传路径
            String path="/2/3";
            //检查上传路径是否已经存在,不存在，返回false
            boolean flag = ftp.changeWorkingDirectory(path);
            if(!flag){
                //不存在该目录，创建该目录
                boolean b = ftp.makeDirectory(path);
                System.out.println("aaa------>"+b);
            }

            //指定上传路径
            boolean c = ftp.changeWorkingDirectory(path);
            //指定文件上传的类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);


            //读取文件
            File file = new File("d:\\b\\timg (2).jfif");
            local= new FileInputStream(file);

            //设置字符集
            ftp.setControlEncoding("GBK");
            String name = file.getName();
            boolean flag2 = ftp.storeFile(name, local);
            System.out.println(flag2);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //关闭文件流
                local.close();

                //退出
                ftp.logout();

                //退出
                ftp.disconnect();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
