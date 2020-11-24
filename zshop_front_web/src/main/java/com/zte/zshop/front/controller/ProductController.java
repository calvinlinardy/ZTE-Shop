package com.zte.zshop.front.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.cart.ShoppingCartUtils;
import com.zte.zshop.common.Constant;
import com.zte.zshop.common.utils.ResponseResult;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.params.ProductParams;
import com.zte.zshop.service.OrderService;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import com.zte.zshop.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2020-11-11 8:33
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/product")
public class ProductController
{

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    //初始化产品类型下拉列表
    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){

        List<ProductType> productTypes = productTypeService.findEnable(Constant.PRODUCT_TYPE_ENABLE);
        return productTypes;
    }

    @RequestMapping("/findByParams")
    public String main(ProductParams productParams, Integer pageNum, Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE_FRONT);
        List<Product> products= productService.findByParams(productParams);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        model.addAttribute("data",pageInfo);
        model.addAttribute("productParams",productParams);
        //返回main.jsp
        return "main";
    }

    @RequestMapping("/showPic")
    public void showPic(String image, OutputStream out) throws IOException
    {
        //把http请求读取为流
        URL url = new URL(image);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        //创建缓存
        byte[] data=  new byte[4096];
        int size=0;
        size=is.read(data);
        while(size!=-1){
            bos.write(data,0,size);
            size=is.read(data);
        }
        is.close();
        bos.flush();
        bos.close();
    }

    @RequestMapping("/toCart")
    public String toCart(){
        return "cart";
    }

    @RequestMapping("/toOrder")
    public String toOrder(){
        return "myOrders";
    }
    @RequestMapping("/toCenter")
    public String toCenter(){
        return "center";
    }

    //将商品放入购物车
    @RequestMapping("/addToCart")
    @ResponseBody
    public ResponseResult addToCart(int id, HttpSession session){
        boolean flag=false;
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        flag=productService.addToCart(id,sc);
        if(flag){
            //成功
            return ResponseResult.success("放入购物车成功");


        }
        else{
            return ResponseResult.fail("放入购物车失败");
        }
    }

   /* //删除购物车明细
    @RequestMapping("/removeItemById")
    @ResponseBody
    public ResponseResult removeItemById(int[] ids,HttpSession session){
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        productService.removeItemsFromShoppingCart(sc,ids);
        //如果购物车为空，到空页面，否则返回cart.jsp
        if(sc.isEmpty()){
            return ResponseResult.fail("购物车为空");
        }
        //重新计算总价
        float totalMoney = sc.getTotalMoney();

        return ResponseResult.success(totalMoney);

    }*/

    //更新商品数量
    @RequestMapping("/updateItemQuantity")
    @ResponseBody
    public Map<String,Object> updateItemQuantity(int id, int quantity,HttpSession session){
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        productService.modifyItemQuantity(sc,id,quantity);
        Map<String,Object> result = new HashMap<>();
        //获取单个商品总价
        result.put("itemMoney",sc.getProducts().get(id).getItemMoney());
        //获取所有商品总价
        result.put("totalMoney",sc.getTotalMoney());
        return result;
    }

    //清空购物车
    @RequestMapping("/clear")
    @ResponseBody
    public ResponseResult clear(HttpSession session){
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        productService.clearShoppingCart(sc);
        return ResponseResult.success("清空购物车成功");
    }

    //批量删除
    @RequestMapping("/removeItemsByIds")
    @ResponseBody
    public ResponseResult removeItemByIds(int[] ids,HttpSession session){
        //System.out.println("ids---->"+ids);
        //获取购物车对象
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        productService.removeItemsFromShoppingCart(sc,ids);
        //ResponseResult result = new ResponseResult();
        if(sc.isEmpty()){
            return ResponseResult.fail("购物车已空");
        }
        //重新计算总价
        float totalMoney = sc.getTotalMoney();
        return ResponseResult.success("删除成功",totalMoney);
    }

    //显示订单页面
    @RequestMapping("/showOrder")
    public String showOrder(){
        return "order";
    }

    //生成订单号
    @RequestMapping("/generateOrder")
    @ResponseBody
    public ResponseResult generateOrder(OrderVo orderVo){
        try
        {
            String no = ShoppingCartUtils.getOrderIdByTime();
            orderVo.setNo(no);
            orderVo.setCreateDate(new Date());
            orderService.addOrder(orderVo);

            return ResponseResult.success(orderVo);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseResult.fail("生成订单失败");
        }

    }

}
