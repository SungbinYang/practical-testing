package me.sungbin.spring.api.controller.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import me.sungbin.spring.domain.product.Product;
import me.sungbin.spring.domain.product.ProductSellingStatus;
import me.sungbin.spring.domain.product.ProductType;

@Getter
public class ProductCreateRequest {

    private ProductType type;

    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;

    @Builder
    private ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toProduct(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
