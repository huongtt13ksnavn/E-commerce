package com.mygroup.huongtt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.mygroup.huongtt.controller.OrderController;
import com.mygroup.huongtt.controller.ProductController;

@SpringBootTest
@AutoConfigureMockMvc
class ECommerceApplicationTests {

  @Autowired
  ProductController productController;

  @Autowired
  OrderController orderController;

  @Test
  void contextLoads() {
    assertThat(productController).isNotNull();
    assertThat(orderController).isNotNull();
  }

}
