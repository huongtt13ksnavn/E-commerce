package com.mygroup.huongtt.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mygroup.huongtt.model.Product;
import com.mygroup.huongtt.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService service;

  @Test()
  public void whenReadAll_thenStatusIsOk() throws Exception {

    List<Product> singletonList = Collections
        .singletonList(new Product(1l, "P01", 1000d, "url"));
    when(service.getAllProducts()).thenReturn(singletonList);

    this.mockMvc
        .perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)));
  }

}
