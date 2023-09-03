package com.mygroup.huongtt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mygroup.huongtt.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByUsernameAndPassword(String userName, String password);
}
