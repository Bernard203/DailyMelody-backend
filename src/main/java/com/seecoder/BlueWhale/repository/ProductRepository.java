package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByStoreId(Integer storeId);

    Product findByStoreIdAndName(Integer storeId,String name);
}
