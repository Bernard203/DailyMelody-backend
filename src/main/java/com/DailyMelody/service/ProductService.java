package com.DailyMelody.service;

import java.util.List;

import com.DailyMelody.enums.CategoryEnum;
import com.DailyMelody.vo.CommentVO;
import com.DailyMelody.vo.ProductVO;

public interface ProductService {
    Boolean create(ProductVO productVO);

    Boolean addStock(Integer id, Integer number);

    List<ProductVO> getAllProducts(Integer storeId);

    ProductVO getProduct(Integer id);

    List<ProductVO> getProductsWithCondition(String name, CategoryEnum category, Double price);

    List<CommentVO> getComments(Integer productId);
}
