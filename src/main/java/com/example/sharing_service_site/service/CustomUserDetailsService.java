package com.example.sharing_service_site.service;

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

    // 利用することができるログイン中のユーザー情報
    return new CustomUserDetails(
        user.getEmployeeNumber(),
        user.getPassword(),
        user.getRoles(),
        user.getFullName(),
        user.getCompany(),
        user.getDepartment());
  }
}
