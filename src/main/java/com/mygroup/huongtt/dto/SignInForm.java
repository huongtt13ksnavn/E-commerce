package com.mygroup.huongtt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInForm {
  @NotBlank(message = "Username is can not be blank.")
  private String username;
  @NotBlank(message = "Password is can not be blank.")
  private String password;
}
