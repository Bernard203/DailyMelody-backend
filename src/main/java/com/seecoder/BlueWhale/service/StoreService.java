package com.seecoder.BlueWhale.service;

import java.util.List;

import com.seecoder.BlueWhale.vo.StoreVO;

public interface StoreService {
    Boolean create(StoreVO storeVO);

    StoreVO getStore(Integer id);

    List<StoreVO> getAllStores();

}
