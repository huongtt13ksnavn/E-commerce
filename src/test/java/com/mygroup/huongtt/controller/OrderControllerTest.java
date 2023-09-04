package com.mygroup.huongtt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.huongtt.controller.OrderController.OrderForm;
import com.mygroup.huongtt.dto.OrderProductDto;
import com.mygroup.huongtt.exception.ApiExceptionHandler.ErrorItem;
import com.mygroup.huongtt.exception.ApiExceptionHandler.ErrorResponse;
import com.mygroup.huongtt.model.Order;
import com.mygroup.huongtt.model.OrderProduct;
import com.mygroup.huongtt.model.OrderStatus;
import com.mygroup.huongtt.model.Product;
import com.mygroup.huongtt.model.User;
import com.mygroup.huongtt.service.OrderProductService;
import com.mygroup.huongtt.service.OrderService;
import com.mygroup.huongtt.service.ProductService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

  @MockBean
  private OrderService orderService;

  @MockBean
  private ProductService productService;

  @MockBean
  private OrderProductService orderProductService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * User for testing.
   */
  private User user;

  /**
   * Initial data.
   */
  @BeforeEach
  public void init() {
    if (Objects.isNull(user)) {
      user = new User(1, "huongtt@gmail.com", "huongtt", "123123", "Uchiha",
          "Itachi", "0123123123", false);
    }
  }

  @Test()
  public void whenReadAll_thenStatusIsOkAndSizeEquality() throws Exception {

    Order order = new Order();
    List<OrderProduct> singletonListOrderProduct = Collections.singletonList(
        new OrderProduct(order, new Product(1l, "Pr01", 100000d, "Url"), 100));
    List<Order> singletonListOrder = Collections.singletonList(new Order(1l,
        null, OrderStatus.PAID.name(), singletonListOrderProduct, user));

    when(orderService.getAllOrders()).thenReturn(singletonListOrder);

    this.mockMvc.perform(get("/api/orders").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)));
  }

  @Test()
  public void whenAddAnOrder_thenStatusIsCreatedAndTypedAssertion()
      throws Exception {

    Order order = new Order(null, null, OrderStatus.PAID.name(), null, user);
    Product product = new Product(1l, "Pr01", 100000d, "Url");
    when(orderService.create(order)).thenReturn(order);

    OrderProduct orderProduct = new OrderProduct(order, product, 100);

    List<OrderProductDto> singletonListInput = Collections
        .singletonList(new OrderProductDto(product, 20));
    OrderForm orderForm = new OrderController.OrderForm();
    orderForm.setProductOrders(singletonListInput);

    when(orderProductService.create(orderProduct)).thenReturn(orderProduct);
    when(productService.getProduct(product.getId())).thenReturn(product);

    String writeValueAsString = objectMapper.writeValueAsString(orderForm);

    MvcResult result = this.mockMvc
        .perform(post("/api/orders").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsString))
        .andExpect(status().isCreated()).andReturn();

    Order orderResponse = objectMapper
        .readValue(result.getResponse().getContentAsString(), Order.class);

    System.out.println(result.getResponse().getContentAsString());

    assertThat(singletonListInput.size())
        .isEqualTo(orderResponse.getOrderProducts().size());
  }

  @Test()
  public void whenInValidInput_thenStatusIsBadRequestAndResponseError()
      throws Exception {

    OrderForm orderForm = new OrderController.OrderForm();
    String orderFormJSONStr = objectMapper.writeValueAsString(orderForm);

    ErrorResponse errors = new ErrorResponse();
    ErrorItem error = new ErrorItem();
    error.setCode("productOrders");
    error.setMessage("productOrders is not null");
    errors.addError(error);

    String expectedError = objectMapper.writeValueAsString(errors);

    MvcResult response = this.mockMvc
        .perform(post("/api/orders").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(orderFormJSONStr))
        .andExpect(status().isBadRequest()).andReturn();

    String responseError = response.getResponse().getContentAsString();

    assertThat(responseError).isEqualToIgnoringCase(expectedError);
  }
}
