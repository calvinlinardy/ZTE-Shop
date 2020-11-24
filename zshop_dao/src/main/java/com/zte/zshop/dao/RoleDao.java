package com.zte.zshop.dao;

import com.zte.zshop.entity.Role;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-11-05 10:15
 * Description:<描述>
 */
public interface RoleDao
{
    //查询所有角色
    public List<Role> selectAll();
}
