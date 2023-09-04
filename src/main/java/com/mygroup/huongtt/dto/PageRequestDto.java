package com.mygroup.huongtt.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {
  @Min(value = 0, message = "Page index must not be less than zero.")
  private int pageNumber;
  @Min(value = 0, message = "Page size must not be less than one.")
  private int size;
  private String sortDir;
  private String sort;

  public Pageable transformToPageable() {
    List<String> listStringSortDir = Arrays.asList("asc", "desc", "ASC",
        "DESC");
    if (listStringSortDir.contains(sortDir)) {
      return PageRequest.of(pageNumber, size,
          Sort.Direction.fromString(sortDir), sort);
    }
    return PageRequest.of(pageNumber, size);
  }
}
