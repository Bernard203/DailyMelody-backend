package com.DailyMelody.vo;

import com.DailyMelody.enums.CategoryEnum;
import com.DailyMelody.po.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductVO {

    private Integer id;

    private Integer storeId;

    private Double rating;

    private Integer number;

    private List<String> photoUrlList;

    private String name;

    private Integer salesAmount;

    private Integer stock;

    private Double price;

    private CategoryEnum category;

    public Product toPO(){
        Product product=new Product();
        product.setCategory(this.category);
        product.setId(this.id);
        product.setPrice(this.price);
        product.setName(this.name);
        product.setNumber(this.number);
        product.setRating(this.rating);
        product.setStock(this.stock);
        product.setStoreId(this.storeId);
        product.setSalesAmount(this.salesAmount);
        product.setPhotoUrlList(this.photoUrlList);
        return product;
    }
}
