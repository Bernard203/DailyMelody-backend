package com.DailyMelody.service;

import java.util.List;

import com.DailyMelody.vo.StoreVO;

public interface StoreService {
    Boolean create(StoreVO storeVO);

    StoreVO getStore(Integer id);

    List<StoreVO> getAllStores();

}
