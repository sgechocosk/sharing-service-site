package com.example.sharing_service_site.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sharing_service_site.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String employeeNumber) throws UsernameNotFoundException {
    com.example.sharing_service_site.entity.User user = userRepository.findByEmployeeNumber(employeeNumber)
        .orElseThrow(() -> new UsernameNotFoundException("社員番号が存在しません: " + employeeNumber));

    return new User(
        user.getEmployeeNumber(),
        user.getPassword(),
        new ArrayList<>());
  }
}
