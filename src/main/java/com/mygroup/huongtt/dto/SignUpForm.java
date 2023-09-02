package com.mygroup.huongtt.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.mygroup.huongtt.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Validated
public class SignUpForm {
  @NotBlank(message = "Username is can not be blank.")
  private String username;
  @NotBlank(message = "Email is can not be blank.")
  @Email
  private String email;
  @NotBlank(message = "Password is can not be blank.")
  @Length(min = 6, max = 12)
  private String password;
  @NotBlank(message = "FirstName is can not be blank.")
  private String firstName;
  @NotBlank(message = "LastName is can not be blank.")
  private String lastName;
  @NotBlank(message = "PhoneNumber is can not be blank.")
  @Pattern(regexp = "^\\d{10}$")
  private String phoneNumber;

  public User transformToUser() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPhoneNumber(phoneNumber);
    return user;
  }
}
