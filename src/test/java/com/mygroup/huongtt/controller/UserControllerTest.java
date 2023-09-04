package com.mygroup.huongtt.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.huongtt.dto.SignUpForm;
import com.mygroup.huongtt.exception.ResourceNotFoundException;
import com.mygroup.huongtt.model.User;
import com.mygroup.huongtt.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
  /**
   * API USER URL.
   */
  private static final String API_USER = "/api/users";

  /**
   * Object mapper.
   */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Mock MVC.
   */
  @Autowired
  private MockMvc mockMvc;

  /**
   * User service.
   */
  @MockBean
  private UserService userService;

  /**
   * List of user for testing.
   */
  private Page<User> pageUser;

  /**
   * Initial data.
   */
  @BeforeEach
  public void init() {
    pageUser = new PageImpl<>(Arrays.asList(new User(1, "huongtt@gmail.com",
        "huongtt", "123123", "Uchiha", "Itachi", "0123123123", false)));
  }

  /**
   * Remove all of data.
   */
  @AfterEach
  public void teardown() {
  }

  /**
   * Test case get all of user is successfully.
   * @throws Exception
   */
  @Test
  public void whenReadAllThenStatusIsOkAndSizeEquality() throws Exception {
    when(userService.findAll(any())).thenReturn(pageUser);
    this.mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(1)));
  }

  /**
   * Test case create user is successfully.
   *
   * @throws Exception
   */
  @Test
  public void givenUserObjectWhenCreateUserThenReturnSavedUser()
      throws Exception {
    SignUpForm sigUpForm = new SignUpForm("huongtt", "huongtt@gmail.com",
        "123123", "Uchiha", "Itachi", "0123123123");

    String requestBody = objectMapper.writeValueAsString(sigUpForm);

    when(userService.create(sigUpForm)).thenReturn(sigUpForm.transformToUser());

    this.mockMvc
        .perform(post(API_USER).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username", Matchers.is("huongtt")))
        .andExpect(jsonPath("$.password", Matchers.is("123123")))
        .andExpect(jsonPath("$.email", Matchers.is("huongtt@gmail.com")))
        .andExpect(jsonPath("$.firstName", Matchers.is("Uchiha")))
        .andExpect(jsonPath("$.lastName", Matchers.is("Itachi")))
        .andExpect(jsonPath("$.phoneNumber", Matchers.is("0123123123")));
  }

  /**
   * Test case create user invalid input.
   *
   * @throws Exception
   */
  @Test
  public void givenInvalidUserObjectWhenCreateUserThenReturnStatusBadRequest()
      throws Exception {
    SignUpForm sigUpForm = new SignUpForm(null, "huongtt@gmail.com", "123123",
        "Uchiha", "Itachi", "0123123123");

    String requestBody = objectMapper.writeValueAsString(sigUpForm);

    this.mockMvc
        .perform(post(API_USER).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  /**
   * Test case update user is successfully.
   *
   * @throws Exception
   */
  @Test
  public void givenUserObjectWhenUpdateUserThenReturnSavedUser()
      throws Exception {
    final int userId = 1;

    SignUpForm sigUpForm = new SignUpForm("huongttUpdate", "huongtt@gmail.com",
        "123123", "Uchiha", "Itachi", "0123123123");

    String requestBody = objectMapper.writeValueAsString(sigUpForm);

    when(userService.update(sigUpForm, userId))
        .thenReturn(sigUpForm.transformToUser());

    this.mockMvc
        .perform(put(API_USER + "/" + userId).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is("huongttUpdate")))
        .andExpect(jsonPath("$.password", Matchers.is("123123")))
        .andExpect(jsonPath("$.email", Matchers.is("huongtt@gmail.com")))
        .andExpect(jsonPath("$.firstName", Matchers.is("Uchiha")))
        .andExpect(jsonPath("$.lastName", Matchers.is("Itachi")))
        .andExpect(jsonPath("$.phoneNumber", Matchers.is("0123123123")));
  }

  /**
   * Test case update user with user id not found.
   *
   * @throws Exception
   */
  @Test
  public void givenUserObjectWhenUpdateUserThenReturnStatusNotFound()
      throws Exception {
    final int userId = 2;

    SignUpForm sigUpForm = new SignUpForm("huongttUpdate", "huongtt@gmail.com",
        "123123", "Uchiha", "Itachi", "0123123123");

    String requestBody = objectMapper.writeValueAsString(sigUpForm);

    when(userService.update(sigUpForm, userId))
        .thenThrow(ResourceNotFoundException.class);

    this.mockMvc
        .perform(put(API_USER + "/" + userId).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isNotFound());
  }

  /**
   * Test case update user that has invalid input.
   *
   * @throws Exception
   */
  @Test
  public void givenInvalidUserObjectWhenUpdateUserThenReturnStatusBadRequest()
      throws Exception {
    final int userId = 1;
    SignUpForm sigUpForm = new SignUpForm(null, "huongtt@gmail.com", "123123",
        "Uchiha", "Itachi", "0123123123");

    String requestBody = objectMapper.writeValueAsString(sigUpForm);

    this.mockMvc
        .perform(put(API_USER + "/" + userId).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }
}
