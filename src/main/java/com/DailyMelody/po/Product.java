package com.DailyMelody.po;

import com.DailyMelody.enums.CategoryEnum;
import com.DailyMelody.vo.ProductVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "store_id")
    private Integer storeId;

    @Basic
    @Column(name = "rating")
    private Double rating;

    @Basic
    @Column(name = "number")
    private Integer number;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photoUrlList;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "sales_amount")
    private Integer salesAmount;

    @Basic
    @Column(name = "stock")
    private Integer stock;

    @Basic
    @Column(name = "price")
    private Double price;

    @Basic
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    public ProductVO toVO(){
        ProductVO productVO=new ProductVO();
        productVO.setCategory(this.category);
        productVO.setName(this.name);
        productVO.setId(this.id);
        productVO.setPrice(this.price);
        productVO.setNumber(this.number);
        productVO.setRating(this.rating);
        productVO.setStock(this.stock);
        productVO.setStoreId(this.storeId);
        productVO.setSalesAmount(this.salesAmount);
        productVO.setPhotoUrlList(this.photoUrlList);
        return productVO;
    }
}
