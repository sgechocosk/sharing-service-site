package com.example.sharing_service_site.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sharing_service_site.entity.Company;
import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.entity.User;
import com.example.sharing_service_site.repository.UserRepository;

@Service
public class UserService {
  private final DepartmentService departmentService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(DepartmentService departmentService,
                     UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.departmentService = departmentService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(String userName,Company company, Department department) {
    User user = null; // 一時的に作成
    return user;
  }
}
