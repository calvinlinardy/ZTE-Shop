package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.Constant;
import com.zte.zshop.common.exception.ProductTypeExistException;
import com.zte.zshop.common.utils.ResponseResult;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:helloboy
 * Date:2020-10-22 16:19
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/productType")
public class ProductTypeController
{

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){
        //如果不传页码，默认第一页
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum= Constant.PAGE_NUM;
        }

        PageInfo<ProductType> pageInfo =
                productTypeService.findAll(pageNum,Constant.PAGE_SIZE);
        model.addAttribute("data",pageInfo);
        //pageInfo.getList();
        //pageInfo.getNextPage();
        //pageInfo.getPageNum();
        //pageInfo.getPages();
        //pageInfo.getPages();

        return "productTypeManager";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(@RequestParam("name") String productTypeName){

        ResponseResult result = new ResponseResult();
        try
        {
            productTypeService.add(productTypeName);
            result.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            result.setMessage("添加成功");
        }
        catch (ProductTypeExistException e){
            result.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            result.setMessage("商品类型已经存在");
        }

        return result;



    }

    //根据id查找记录
    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){
        ProductType productType=productTypeService.findById(id);
        return ResponseResult.success(productType);

    }

    //修改商品名称
    @RequestMapping("/modifyName")
    @ResponseBody
    public ResponseResult modifyName(Integer id,String name){
        try
        {
            productTypeService.modifyName(id,name);
            return ResponseResult.success("修改商品类型成功");
        } catch (ProductTypeExistException e)
        {
            //e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }

    }

    //删除商品类型
    @RequestMapping("/deleteById")
    @ResponseBody
    public ResponseResult deleteById(Integer id){
        productTypeService.removeById(id);
        return ResponseResult.success("删除成功");

    }

    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(Integer id){
        //更新状态
        productTypeService.modifyStatus(id);
        //返回成功
        return ResponseResult.success("删除状态成功");

    }






}
