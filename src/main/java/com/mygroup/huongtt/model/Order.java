package com.mygroup.huongtt.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderProducts")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateCreated;

  private String status;

  @OneToMany(mappedBy = "pk.order")
  @Valid
  private List<OrderProduct> orderProducts;

  @Transient
  public Double getTotalOrderPrice() {
    double sum = 0D;
    for (OrderProduct op : orderProducts) {
      sum += op.getTotalPrice();
    }

    return sum;
  }

  @Transient
  public int getNumberOfProducts() {
    return this.orderProducts.size();
  }
}
