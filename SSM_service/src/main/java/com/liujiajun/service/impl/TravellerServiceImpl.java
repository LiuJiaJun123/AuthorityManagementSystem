package com.liujiajun.service.impl;

import com.liujiajun.dao.ITravellerDao;
import com.liujiajun.domain.Traveller;
import com.liujiajun.service.ITravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("travellerService")
public class TravellerServiceImpl implements ITravellerService {

    @Autowired
    private ITravellerDao travellerDao;

    @Override
    public List<Traveller> findAll() throws Exception {
        return travellerDao.findAll();
    }
}
