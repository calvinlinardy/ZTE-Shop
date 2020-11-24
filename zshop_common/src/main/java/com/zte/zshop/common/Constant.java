package com.zte.zshop.common;

/**
 * Author:helloboy
 * Date:2020-10-23 13:54
 * Description:<描述>
 * 常量类，用于定义常量信息
 */
public class Constant
{

    //当前页
    public static final int PAGE_NUM=1;

    //页的大小
    public static final int PAGE_SIZE=3;

    //响应状态码：1---->成功
    public static final int RESPONSE_STATUS_SUCCESS=1;
    //响应状态码：2---->失败
    public static final int RESPONSE_STATUS_FAILURE=2;
    //响应状态码：3---->未授权
    public static final int RESPONSE_NO_PERMISSION=3;

    //商品类型的启用状态：1--->启用   0---->禁用   默认启用
    public static  final int PRODUCT_TYPE_ENABLE=1;

    public static  final int PRODUCT_TYPE_DISABLE=0;

    //系统用户的有效状态：1--->有效，0---->无效
    public static final Integer SYSUSER_VALID=1;

    public static final Integer SYSUSER_INVALID=0;

    //前台产品列表显示页的大小
    public static final int PAGE_SIZE_FRONT =8 ;
}
