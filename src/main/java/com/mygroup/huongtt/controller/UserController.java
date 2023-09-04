package com.mygroup.huongtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mygroup.huongtt.dto.ChangePasswordForm;
import com.mygroup.huongtt.dto.PageRequestDto;
import com.mygroup.huongtt.dto.SignUpForm;
import com.mygroup.huongtt.model.User;
import com.mygroup.huongtt.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping
  public Page<User> findAll(
      @ModelAttribute @Valid PageRequestDto pageRequestDto) {
    return userService.findAll(pageRequestDto);
  }

  @PostMapping
  public ResponseEntity<Object> add(
      @RequestBody @Valid final SignUpForm signUpForm) {
    User savedUser = userService.create(signUpForm);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<Object> update(
      @RequestBody @Valid final SignUpForm signUpForm,
      @PathVariable @Min(value = 1, message = "Min value of user id is one.") final Integer userId) {
    User updatedUser = userService.update(signUpForm, userId);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Object> update(
      @PathVariable @Min(value = 1, message = "Min value of user id is one.") final Integer userId) {
    userService.delete(userId);
    return new ResponseEntity<>("Delete user is successfully", HttpStatus.OK);
  }

  @PostMapping("/{userId}/change-password")
  public ResponseEntity<Object> changePassword(
      @RequestBody(required = true) @Valid final ChangePasswordForm changePasswordForm,
      @PathVariable(required = true) @Min(value = 1, message = "Min value of user id is one.") final Integer userId) {
    User changedUser = userService.changePassword(changePasswordForm, userId);
    return new ResponseEntity<>(changedUser, HttpStatus.OK);
  }
}
