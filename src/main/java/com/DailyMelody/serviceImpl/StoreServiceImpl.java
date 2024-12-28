package com.DailyMelody.serviceImpl;

import com.DailyMelody.exception.DailyMelodyException;
import com.DailyMelody.po.Store;
import com.DailyMelody.repository.StoreRepository;
import com.DailyMelody.service.StoreService;
import com.DailyMelody.vo.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Boolean create(StoreVO storeVO) {
        Store store = storeRepository.findByName(storeVO.getName());
        if (store != null) {
            throw DailyMelodyException.nameAlreadyExists();
        }
        Store newStore = storeVO.toPO();
        newStore.setRating(0.0);
        newStore.setNumber(0);
        storeRepository.save(newStore);
        return true;
    }

    @Override
    public StoreVO getStore(Integer id) {
        Store store = storeRepository.findById(id).orElse(null);
        if (store == null) {
            throw DailyMelodyException.storeNotExists();
        }
        return store.toVO();
    }

    @Override
    public List<StoreVO> getAllStores() {
        return storeRepository.findAll().stream().map(Store::toVO).collect(Collectors.toList());
    }

}
