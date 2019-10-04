package com.liujiajun.service.impl;

import com.liujiajun.dao.IMemberDao;
import com.liujiajun.domain.Member;
import com.liujiajun.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberDao memberDao;

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }



}
