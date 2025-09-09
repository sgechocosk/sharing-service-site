package com.example.sharing_service_site.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sharing_service_site.entity.Company;
import com.example.sharing_service_site.entity.Department;

public class CustomUserDetails implements UserDetails {
  private final String employeeNumber;
  private final String password;
  private final List<GrantedAuthority> authorities;
  private final String fullName;
  private final Company company;
  private final Department department;

  /**
   * 利用することができるログイン中のユーザー情報
   * @param employeeNumber 従業員番号
   * @param password パスワード
   * @param authorities 権限(ロール)
   * @param fullName ユーザー名
   * @param company 所属会社
   * @param department 所属部署
   */
  public CustomUserDetails(String employeeNumber, String password, List<GrantedAuthority> authorities, String fullName, Company company, Department department) {
      this.employeeNumber = employeeNumber;
      this.password = password;
      this.authorities = authorities;
      this.fullName = fullName;
      this.company = company;
      this.department = department;
  }

  // 利用することができるログイン中のユーザー情報の取得メソッド
  @Override public String getUsername() { return employeeNumber; }
  @Override public String getPassword() { return password; }
  @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }

  public String getFullName() { return fullName; }
  public Company getCompany() { return company; }
  public String getCompanyName() { return company.getCompanyName(); }
  public Department getDepartment() { return department; }
  public String getDepartmentName() { return department.getDepartmentName(); }
}
