package com.liujiajun.service;

import com.liujiajun.domain.Traveller;

import java.util.List;

public interface ITravellerService {

    List<Traveller> findAll() throws Exception;

}
