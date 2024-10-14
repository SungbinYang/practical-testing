package me.sungbin.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import me.sungbin.spring.api.service.product.response.ProductResponse;
import me.sungbin.spring.domain.product.Product;
import me.sungbin.spring.domain.product.ProductRepository;
import me.sungbin.spring.domain.product.ProductSellingStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
