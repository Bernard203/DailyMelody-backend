package com.DailyMelody.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DailyMelody.po.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Store findByName(String name);

}
