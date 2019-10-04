package com.liujiajun.service.impl;

import com.github.pagehelper.PageHelper;
import com.liujiajun.dao.ISysLogDao;
import com.liujiajun.domain.SysLog;
import com.liujiajun.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysLogService")
public class SysLogServiceImpl implements ISysLogService {


    @Autowired
    private ISysLogDao sysLogDao;

    //保存日志
    @Override
    public void save(SysLog sysLog)  throws Exception {
        sysLogDao.save(sysLog);
    }

    //查找所有日志
    @Override
    public List<SysLog> findAll(Integer page,Integer pageSize) throws Exception{
        PageHelper.startPage(page,pageSize);
        return sysLogDao.findAll();
    }

    //批量删除日志
    @Override
    public void delete(String[] selectIds) {
        for (String id : selectIds) {
            sysLogDao.delete(id);
        }
    }

    //搜索日志
    @Override
    public List<SysLog> findByUsername(String username,Integer page,Integer pageSize) {
        username="%"+username+"%";
        PageHelper.startPage(page,pageSize);
        List<SysLog> findSysLog = sysLogDao.findByUsername(username);
        return findSysLog;
    }
}
