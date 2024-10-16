package me.sungbin.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.spring.api.controller.order.request.OrderCreateRequest;
import me.sungbin.spring.api.service.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("신규 주문을 등록한다.")
    void createOrder() throws Exception {
       // given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001"))
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/orders/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("status").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("message").value(HttpStatus.OK.name()));
    }

    @Test
    @DisplayName("신규 주문을 등록할땨 상품 번호는 1개 이상이어야 한다.")
    void createOrderWithEmptyProductNumbers() throws Exception {
        // given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of())
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/orders/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("message").value("상품 번호 리스트는 필수입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }
}