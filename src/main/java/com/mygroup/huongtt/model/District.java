package com.mygroup.huongtt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Districts")
public class District {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer districtId;
  @Column(name = "district_name")
  private String districtName;
  @Column(name = "province_id")
  private Integer provinceId;
}
