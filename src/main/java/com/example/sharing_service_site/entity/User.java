package com.example.sharing_service_site.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long UserId;

  // ログイン時に必要な情報
  private String employeeNumber;
  private String password;
  // 表示名
  private String userName;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  public Long getUserId() { return UserId; }
  public String getEmployeeNumber() { return employeeNumber; }
  public String getPassword() { return password; }
  public String getUserName() { return userName; }

  public void setUserId(Long UserId) { this.UserId = UserId; }
  public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
  public void setPassword(String password) { this.password = password; }
  public void setUserName(String userName) { this.userName = userName; }
}
