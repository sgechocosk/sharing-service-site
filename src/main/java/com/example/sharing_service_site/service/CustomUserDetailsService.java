package com.example.sharing_service_site.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sharing_service_site.entity.Role;
import com.example.sharing_service_site.entity.User;
import com.example.sharing_service_site.repository.RoleRepository;
import com.example.sharing_service_site.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String employeeNumber) throws UsernameNotFoundException {
    com.example.sharing_service_site.entity.User user = userRepository.findByEmployeeNumber(employeeNumber)
        .orElseThrow(() -> new UsernameNotFoundException("従業員番号が存在しません: " + employeeNumber));

    // 利用することができるログイン中のユーザー情報
    return new CustomUserDetails(
        user.getEmployeeNumber(),
        user.getPassword(),
        user.getRoles(),
        user.getFullName(),
        user.getCompany(),
        user.getDepartment());
  }

  public void updatePassword(String employeeNumber, String encodedPassword) {
    com.example.sharing_service_site.entity.User user = userRepository.findByEmployeeNumber(employeeNumber)
        .orElseThrow(() -> new UsernameNotFoundException("従業員番号が存在しません: " + employeeNumber));

    user.setPassword(encodedPassword);
    userRepository.save(user);
  }

  public User getUser(String employeeNumber) {
    return userRepository.findByEmployeeNumber(employeeNumber)
        .orElseThrow(() -> new UsernameNotFoundException("従業員番号が存在しません: " + employeeNumber));
  }

  public List<User> getUsersByCompanyId(Long companyId) {
    return userRepository.findByCompanyCompanyId(companyId);
  }

  public void updateUserRole(Long userId, String roleName) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("ユーザーIDが存在しません: " + userId));
    user.getRoles().clear();

    Set<Role> newRoles = new HashSet<>();

    if ("ADMIN".equals(roleName)) {
      Role userRole = roleRepository.findById(1L)
              .orElseThrow(() -> new IllegalArgumentException("USERロールが見つかりません"));
      Role adminRole = roleRepository.findById(2L)
              .orElseThrow(() -> new IllegalArgumentException("ADMINロールが見つかりません"));
      newRoles.add(userRole);
      newRoles.add(adminRole);
    } else if ("USER".equals(roleName)) {
      Role userRole = roleRepository.findById(1L)
              .orElseThrow(() -> new IllegalArgumentException("USERロールが見つかりません"));
      newRoles.add(userRole);
    }

    user.setRoles(newRoles);
    userRepository.save(user);
  }
}
