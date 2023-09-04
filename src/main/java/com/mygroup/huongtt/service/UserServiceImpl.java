package com.mygroup.huongtt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mygroup.huongtt.dto.ChangePasswordForm;
import com.mygroup.huongtt.dto.PageRequestDto;
import com.mygroup.huongtt.dto.SignUpForm;
import com.mygroup.huongtt.exception.ResourceNotFoundException;
import com.mygroup.huongtt.model.User;
import com.mygroup.huongtt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User create(final SignUpForm signUpForm) {
    return userRepository.save(signUpForm.transformToUser());
  }

  @Override
  public Page<User> findAll(PageRequestDto pageRequestDto) {
    return userRepository.findAll(pageRequestDto.transformToPageable());
  }

  @Override
  public User update(final SignUpForm signUpForm, final Integer userId) {
    boolean existsById = userRepository.existsById(userId);
    if (!existsById) {
      throw new ResourceNotFoundException("User id is not exist.");
    }
    User user = signUpForm.transformToUser();
    user.setUserId(userId);
    return userRepository.save(user);
  }

  @Override
  public void delete(final Integer userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new ResourceNotFoundException("User id is not exist.");
    }
    userRepository.delete(optionalUser.get());
  }

  @Override
  public User changePassword(final ChangePasswordForm changePasswordForm,
      final Integer userId) {
    // Check valid user id
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new ResourceNotFoundException("User id is not exist.");
    }

    // Check valid username and password
    existsUserNameAndPassword(changePasswordForm.getUsername(),
        changePasswordForm.getOldPassword());

    User user = optionalUser.get();
    user.setPassword(changePasswordForm.getNewPassword());

    User userSaved = userRepository.save(user);
    return userSaved;
  }

  @Override
  public boolean existsUserNameAndPassword(final String username,
      final String password) {
    boolean isValidUser = userRepository.existsByUsernameAndPassword(username,
        password);
    if (!isValidUser) {
      throw new ResourceNotFoundException("Invalid user name or password.");
    }
    return true;
  }

}
