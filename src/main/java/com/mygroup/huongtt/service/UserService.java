package com.mygroup.huongtt.service;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import com.mygroup.huongtt.dto.ChangePasswordForm;
import com.mygroup.huongtt.dto.PageRequestDto;
import com.mygroup.huongtt.dto.SignUpForm;
import com.mygroup.huongtt.model.User;

import jakarta.validation.constraints.NotNull;

@Validated
public interface UserService {
  User create(@NotNull(message = "User can not be null") SignUpForm signUpForm);

  Page<User> findAll(
      @NotNull(message = "PageRequestDto can not be null") PageRequestDto pageRequestDto);

  User update(@NotNull(message = "User can not be null") SignUpForm signUpForm,
      @NotNull(message = "User id can not be null") Integer userId);

  void delete(@NotNull(message = "User id can not be null") Integer userId);

  User changePassword(
      @NotNull(message = "User can not be null") ChangePasswordForm changePasswordForm,
      @NotNull(message = "User id can not be null") Integer userId);

  boolean existsUserNameAndPassword(String username, String password);
}
