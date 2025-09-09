package com.example.sharing_service_site.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * ユーザー情報
 */
@Entity
@Table(name = "users")
public class User {

  /**
   * ユーザーID。主キーであり自動で作成される。
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long UserId;

  /**
   * 従業員番号。ユーザーを識別するユニークな情報として利用する。
   */
  private String employeeNumber;
  /**
   * パスワード。ハッシュ化された状態で保持され、認証に利用する。
   */
  private String password;
  
  /**
   * 表示名。表示のためだけに扱われる。
   */
  private String fullName;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role; // リストで扱う

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  public String getEmployeeNumber() { return employeeNumber; }
  public String getPassword() { return password; }
  public String getFullName() { return fullName; }
  public Role getRole() { return role; } // リストで扱う
  public Company getCompany() { return company; }
  public Department getDepartment() { return department; }

  public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
  public void setPassword(String password) { this.password = password; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public void setRole(Role role) { this.role = role; } // リストで扱う
  public void setCompany(Company company) { this.company = company; }
  public void setDepartment(Department department) { this.department = department; }
}
