package me.sungbin.spring.api.controller.product;

import me.sungbin.spring.ControllerTestSupport;
import me.sungbin.spring.api.controller.product.dto.request.ProductCreateRequest;
import me.sungbin.spring.api.service.product.response.ProductResponse;
import me.sungbin.spring.domain.product.ProductSellingStatus;
import me.sungbin.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("신규 상품을 등록한다.")
    void createProduct() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수 값이다.")
    void createProductWithoutType() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("message").value("상품 타입은 필수입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 판매상태는 필수값이다.")
    void createProductWithoutSellingStatus() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("message").value("상품 판매상태는 필수입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 이름은 필수 값이다.")
    void createProductWithoutName() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .price(4000)
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("message").value("상품 이름은 필수입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 가격은 양수이다.")
    void createProductWithZeroPrice() throws Exception {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(0)
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("message").value("상품 가격은 양수여야 합니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("판매 상품을 조회한다.")
    void getSellingProducts() throws Exception {
        //given
        List<ProductResponse> result = List.of();
        when(productService.getSellingProducts()).thenReturn(result);

        // when & then
        mockMvc.perform(get("/api/v1/products/selling")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("status").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("message").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("data").isArray());
    }
}