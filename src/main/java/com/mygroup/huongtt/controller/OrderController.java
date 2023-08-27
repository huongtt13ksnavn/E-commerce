package com.mygroup.huongtt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mygroup.huongtt.dto.OrderProductDto;
import com.mygroup.huongtt.exception.ResourceNotFoundException;
import com.mygroup.huongtt.model.Order;
import com.mygroup.huongtt.model.OrderProduct;
import com.mygroup.huongtt.model.OrderStatus;
import com.mygroup.huongtt.service.OrderProductService;
import com.mygroup.huongtt.service.OrderService;
import com.mygroup.huongtt.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  /**
   * Product Service.
   */
  private ProductService productService;
  /**
   * Order Service.
   */
  private OrderService orderService;
  /**
   * Order Product Service.
   */
  private OrderProductService orderProductService;

  /**
   * Order controller constructor.
   *
   * @param pProductService
   * @param pOrderService
   * @param pOrderProductService
   */
  public OrderController(final ProductService pProductService,
      final OrderService pOrderService,
      final OrderProductService pOrderProductService) {
    this.productService = pProductService;
    this.orderService = pOrderService;
    this.orderProductService = pOrderProductService;
  }

  /**
   * Get all of order.
   *
   * @return Iterator of {@link Order}
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public @NotNull Iterable<Order> list() {
    return this.orderService.getAllOrders();
  }

  /**
   * Create an order.
   *
   * @param form
   * @return ResponseEntity {@link Order}
   */
  @PostMapping
  public ResponseEntity<Order> create(final @RequestBody OrderForm form) {
    List<OrderProductDto> formDtos = form.getProductOrders();
    validateProductsExistence(formDtos);
    Order order = new Order();
    order.setStatus(OrderStatus.PAID.name());
    order = this.orderService.create(order);

    List<OrderProduct> orderProducts = new ArrayList<>();
    for (OrderProductDto dto : formDtos) {
      orderProducts.add(orderProductService.create(new OrderProduct(order,
          productService.getProduct(dto.getProduct().getId()),
          dto.getQuantity())));
    }

    order.setOrderProducts(orderProducts);

    this.orderService.update(order);

    String uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
        .path("/orders/{id}").buildAndExpand(order.getId()).toString();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", uri);

    return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
  }

  private void validateProductsExistence(
      final List<OrderProductDto> orderProducts) {
    List<OrderProductDto> list = orderProducts.stream()
        .filter(op -> Objects
            .isNull(productService.getProduct(op.getProduct().getId())))
        .collect(Collectors.toList());

    if (!CollectionUtils.isEmpty(list)) {
      new ResourceNotFoundException("Product not found");
    }
  }

  public static class OrderForm {

    /**
     * List of ordered product.
     */
    private List<OrderProductDto> productOrders;

    /**
     * Get list of ordered product.
     *
     * @return list of {@link OrderProductDto}
     */
    public List<OrderProductDto> getProductOrders() {
      return productOrders;
    }

    /**
     * Set list of ordered product.
     *
     * @param pProductOrders
     */
    public void setProductOrders(final List<OrderProductDto> pProductOrders) {
      this.productOrders = pProductOrders;
    }
  }
}
