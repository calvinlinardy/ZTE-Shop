package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.Constant;
import com.zte.zshop.common.utils.ResponseResult;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import com.zte.zshop.vo.ProductVo;
import org.apache.commons.beanutils.PropertyUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2020-10-29 10:03
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/product")
public class ProductController
{

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    //在执行该类下任意方法，之前首先执行该方法,相当于init
    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){
        //查询所有启用的商品类型
        List<ProductType> productTypes = productTypeService.findEnable(Constant.PRODUCT_TYPE_ENABLE);
        return productTypes;
    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageInfo<Product> pageInfo= productService.findAll(pageNum,3);
        model.addAttribute("data",pageInfo);
        return "productManager";
    }

    @RequestMapping("/add")
    //表单提交过来的值如果key和vo中属性匹配，能够自动注入到该属性中
    public String add(ProductVo productVo,Integer pageNum, HttpSession session, Model model){
        //System.out.println(productVo);
        //获取上传路径,注意：在物理路径上必须有该目录
        //String uploadPath=session.getServletContext().getRealPath("/WEB-INF/upload");
        //将VO转换成dto
        ProductDto productDto = new ProductDto();
        //productDto.setName(productVo.getName());
        //productDto.setPrice(productVo.getPrice());
        try
        {
            //将vo中属性与dto相同的属性的值进行拷贝
            PropertyUtils.copyProperties(productDto,productVo);
            productDto.setFileName(productVo.getFile().getOriginalFilename());
            productDto.setInputStream(productVo.getFile().getInputStream());
            //productDto.setUploadPath(uploadPath);
            productService.add(productDto);
            model.addAttribute("successMsg","添加成功");
        } catch (Exception e)
        {
            //e.printStackTrace();
            model.addAttribute("errorMsg",e.getMessage());
        }
        //返回列表页面
        return "forward:findAll?pageNum="+pageNum;
    }

    @RequestMapping("/modify")
    //表单提交过来的值如果key和vo中属性匹配，能够自动注入到该属性中
    public String modify(ProductVo productVo, Integer pageNum,HttpSession session, Model model){
        //System.out.println(productVo);
        //获取上传路径,注意：在物理路径上必须有该目录
        //String uploadPath=session.getServletContext().getRealPath("/WEB-INF/upload");
        //将VO转换成dto
        ProductDto productDto = new ProductDto();
        //productDto.setName(productVo.getName());
        //productDto.setPrice(productVo.getPrice());
        try
        {
            //将vo中属性与dto相同的属性的值进行拷贝
            PropertyUtils.copyProperties(productDto,productVo);
            //当换了附件时，需要重新上传附件
            if(!"".equals(productVo.getFile().getOriginalFilename()))
            {
                productDto.setFileName(productVo.getFile().getOriginalFilename());
                productDto.setInputStream(productVo.getFile().getInputStream());
                //productDto.setUploadPath(uploadPath);
            }
            productService.modifyProduct(productDto);
            model.addAttribute("successMsg","修改成功");
        } catch (Exception e)
        {
            //e.printStackTrace();
            model.addAttribute("errorMsg",e.getMessage());
        }
        //返回列表页面
        return "forward:findAll?pageNum="+pageNum;
    }

    @RequestMapping("/checkName")
    @ResponseBody
    //需要返回两个值：1.valid，2.message,当valid值为true时，表示校验通过，为false时表示校验不通过，I显示message所对应的值
    public Map<String,Object> checkName(String name){
        Map<String,Object> map = new HashMap<>();
        boolean res=productService.checkName(name);
        //如果名字不存在，可用
        if(res){
            map.put("valid",true);
        }
        else{
           map.put("valid",false);
           map.put("message","商品("+name+")已经存在");
        }
        return map;

    }

    //根据id查找记录
    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){
       Product product= productService.findById(id);
       return ResponseResult.success(product);
    }

    //图片预览
    @RequestMapping("/getImg")
    public void getImg(String path, OutputStream out){
    productService.getImage(path,out);
}

    //删除产品
    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult removeById(Integer id){
        try
        {
            productService.removeProduct(id);
            return ResponseResult.success("删除成功");
        } catch (Exception e)
        {
            //e.printStackTrace();
            return ResponseResult.fail("删除失败");
        }
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
}
