package me.sungbin.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import me.sungbin.spring.api.service.product.request.ProductCreateServiceRequest;
import me.sungbin.spring.api.service.product.response.ProductResponse;
import me.sungbin.spring.domain.product.Product;
import me.sungbin.spring.domain.product.ProductRepository;
import me.sungbin.spring.domain.product.ProductSellingStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readOnly = true: 읽기전용
 * CRUD에서 CUD 동작 X / only Read
 * JPA: CUD 스냅샷 저장, 변경감지 X (성능향상)
 * <p>
 * CQRS - Command / Read
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductNumberFactory productNumberFactory;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toProduct(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
