package com.zte.zshop.service;

import com.zte.zshop.entity.Role;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-11-05 10:21
 * Description:<描述>
 */
public interface RoleService
{
    //查询所有角色
    public List<Role> findAll();
}
