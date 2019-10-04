package com.liujiajun.service;

import com.liujiajun.domain.SysLog;

import java.util.List;

public interface ISysLogService {

    //保存日志
    void save(SysLog sysLog) throws Exception;


    //查找所有日志
    List<SysLog> findAll(Integer page,Integer pageSize) throws Exception;

    //批量删除日志
    void delete(String[] selectIds);

    //搜索日志
    List<SysLog> findByUsername(String username,Integer page,Integer pageSize);
}
