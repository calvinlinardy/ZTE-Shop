package com.zte.zshop.service;

import com.zte.zshop.common.exception.SysuserNotExistException;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParam;
import com.zte.zshop.vo.SysuserVo;

import java.util.List;

/**
 * Author:helloboy
 * Date:2020-11-04 15:52
 * Description:<描述>
 */
public interface SysuserService
{
    public List<Sysuser> findAll();

    //根据id查找记录
    public Sysuser findById(Integer id);

    //新增
    public void addSysuser(SysuserVo sysuserVo);

    //修改
    public void modifySysuser(SysuserVo sysuserVo);

    //更新状态
    public void modifyStatus(Integer id);

    public boolean checkName(String loginName);

    public List<Sysuser> findByParams(SysuserParam sysuserParam);

    public Sysuser login(String loginName, String password) throws SysuserNotExistException;
}
