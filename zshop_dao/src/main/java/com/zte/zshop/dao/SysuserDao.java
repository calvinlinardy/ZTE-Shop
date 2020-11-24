package com.zte.zshop.dao;

import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-11-04 15:31
 * Description:<描述>
 */
public interface SysuserDao
{
    //查询所有
    public List<Sysuser> selectAll();

    //根据id查找记录
    public Sysuser selectById(Integer id);

    //新增
    public void insert(Sysuser sysuser);

    //修改
    public void update(Sysuser sysuser);

    //更新状态
    public void updateStatus(@Param("id") Integer id, @Param("isValid") Integer isValid);

    public Sysuser selectByLoginName(String loginName);

    public List<Sysuser> selectByParam(SysuserParam sysuserParam);

    public Sysuser selectByLoginNameAndPass(@Param("loginName") String loginName, @Param("password") String password, @Param("isValid") Integer sysuserValid);
}
