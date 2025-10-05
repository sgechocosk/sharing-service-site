package com.example.sharing_service_site.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
  private Long userId;

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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  public Long getUserId() { return userId; }
  public String getEmployeeNumber() { return employeeNumber; }
  public String getPassword() { return password; }
  public String getFullName() { return fullName; }
  public Set<Role> getRoles() { return roles; }
  public Company getCompany() { return company; }
  public Department getDepartment() { return department; }
  public String getRoleName() {
    if (roles == null || roles.isEmpty()) {
        return "閲覧のみ";
    }
    return roles.stream()
            .map(Role::getRoleName)
            .anyMatch("ADMIN"::equals) ? "管理者" :
          roles.stream()
            .map(Role::getRoleName)
            .anyMatch("USER"::equals) ? "ユーザー" :
          "閲覧のみ";
  }

  public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
  public void setPassword(String password) { this.password = password; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public void setRoles(Set<Role> roles) { this.roles = roles; }
  public void setCompany(Company company) { this.company = company; }
  public void setDepartment(Department department) { this.department = department; }
}
