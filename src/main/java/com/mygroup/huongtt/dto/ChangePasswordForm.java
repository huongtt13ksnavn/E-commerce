package com.mygroup.huongtt.dto;

import com.mygroup.huongtt.model.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordForm {
  @NotBlank(message = "Username is can not be blank.")
  private String username;
  @NotBlank(message = "Old password is can not be blank.")
  private String oldPassword;
  @NotBlank(message = "New password is can not be blank.")
  private String newPassword;
  @NotBlank(message = "Re new password is can not be blank.")
  private String reNewPassword;

  public User transformToUser() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(newPassword);
    return user;
  }
}
