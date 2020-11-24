package com.zte.zshop.dto;

import java.io.InputStream;

/**
 * Author:helloboy
 * Date:2020-10-29 15:28
 * Description:<描述>
 */
//该类是在service层使用的数据传输对象，因为在vo中于部分属性不能注入到数据库，需要重新封装对象
public class ProductDto
{
    private Integer id;

    private String name;

    private Double price;

    //private CommonsMultipartFile file;

    private Integer productTypeId;

    //文件的输入流
    private InputStream inputStream;

    //文件的名称
    private String fileName;

    //文件的上传路径
    private String uploadPath;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Integer getProductTypeId()
    {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId)
    {
        this.productTypeId = productTypeId;
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getUploadPath()
    {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath)
    {
        this.uploadPath = uploadPath;
    }
}
