package com.liujiajun.dao;

import com.liujiajun.domain.Member;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("memberDao")
public interface IMemberDao {

    @Select("select * from member where id=#{id}")
    public List<Member> findById(String id);

    @Select("select * from member")
    List<Member> findAll();
}
