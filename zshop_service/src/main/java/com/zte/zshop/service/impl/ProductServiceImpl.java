package com.zte.zshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.common.utils.StringUtils;
import com.zte.zshop.dao.ProductDao;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.ftp.FTPConfig;
import com.zte.zshop.ftp.FtpUtils;
import com.zte.zshop.params.ProductParams;
import com.zte.zshop.service.ProductService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Author:helloboy
 * Date:2020-10-29 10:12
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService
{

    @Autowired
    private ProductDao productDao;

    @Autowired
    private FTPConfig ftpConfig;

    @Override
    public void add(ProductDto productDto) throws FileUploadException
    {
        //将文件名转成一个随机字符串
        String fileName = StringUtils.renameFileName(productDto.getFileName());
        //上传路径
        //String filePath = productDto.getUploadPath() + "\\" + fileName;
        //生成一个存放该图片的二级目录
        String picSavePath =  StringUtils.generateRandomDir(fileName);
        String filePath="";
        //文件上传

        try
        {
            //StreamUtils.copy(productDto.getInputStream(),new FileOutputStream(filePath));
            //将图片上传到ftp服务器
            filePath=FtpUtils.pictureUploadByCaonfig(ftpConfig,fileName,picSavePath,productDto.getInputStream());
        } catch (IOException e)
        {
            //e.printStackTrace();
            throw  new FileUploadException("文件上传失败："+e.getMessage());
        }

        //将数据保存到数据库，dto--->pojo
        Product product = new Product();
        try
        {
            PropertyUtils.copyProperties(product,productDto);
            product.setImage(filePath);
            ProductType productType = new ProductType();
            productType.setId(productDto.getProductTypeId());
            product.setProductType(productType);
            productDao.insertProduct(product);

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public boolean checkName(String name)
    {
        Product product= productDao.selectByName(name);
        if(product!=null){
            return false;
        }
        return true;
    }

    @Override
    public PageInfo<Product> findAll(Integer pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products= productDao.selectByEnable();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Product findById(Integer id)
    {
        return productDao.selectById(id);
    }

    @Override
    public void modifyProduct(ProductDto productDto) throws FileUploadException
    {
        String filePath="";
        //当你需要上上传附件时
        if(productDto.getFileName()!=null){

            //将文件名转成一个随机字符串
            String fileName = StringUtils.renameFileName(productDto.getFileName());
            //上传路径
            //filePath = productDto.getUploadPath() + "\\" + fileName;
            String picSavePath = StringUtils.generateRandomDir(fileName);

            //文件上传
            try
            {
                //StreamUtils.copy(productDto.getInputStream(),new FileOutputStream(filePath));
                filePath = FtpUtils.pictureUploadByCaonfig(ftpConfig, fileName, picSavePath, productDto.getInputStream());
            } catch (IOException e)
            {
                //e.printStackTrace();
                throw  new FileUploadException("文件上传失败："+e.getMessage());
            }
        }


        //将数据保存到数据库，dto--->pojo
        Product product = new Product();
        try
        {
            PropertyUtils.copyProperties(product,productDto);
            if(productDto.getFileName()!=null)
            {
                product.setImage(filePath);
            }
            ProductType productType = new ProductType();
            productType.setId(productDto.getProductTypeId());
            product.setProductType(productType);
            productDao.updateProduct(product);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void removeProduct(Integer id)
    {
        productDao.deleteById(id);

    }


    //获取该路径上的图片，写到输出流
    //path:图片所在的物理路径
    //out:将图片写入输出流
    @Override
    public void getImage(String path, OutputStream out)
    {
        try
        {
            StreamUtils.copy(new FileInputStream(path),out);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> findByParams(ProductParams productParams)
    {
        return productDao.selectByParams(productParams);
    }

    @Override
    public boolean addToCart(int id, ShoppingCart sc)
    {
        Product product = productDao.selectById(id);
        if(product!=null){
            sc.addProduct(product);
            return true;
        }
        return false;
    }
    //
    //@Override
    //public void removeItemFromShoppingCart(ShoppingCart sc, Integer id)
    //{
    //    sc.removeItem(id);
    //}

    @Override
    public void modifyItemQuantity(ShoppingCart sc, int id, int quantity)
    {
        sc.updateItemQuantity(id,quantity);
    }

    @Override
    public void clearShoppingCart(ShoppingCart sc)
    {
        sc.clear();
    }

    @Override
    public void removeItemsFromShoppingCart(ShoppingCart sc, int[] ids)
    {
        for(int id :ids){
            sc.removeItem(id);
        }
    }
}
