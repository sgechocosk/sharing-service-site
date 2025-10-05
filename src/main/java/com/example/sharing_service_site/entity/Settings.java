package com.example.sharing_service_site.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "settings")
public class Settings {
  @Id
  @Column(name = "user_id")
  private Long userId;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "theme_color", nullable = false)
  private String themeColor = "navy-blue";

  @Column(name = "department_order", nullable = false)
  private String departmentOrder = "created-date";

  public Settings() {}

  public Settings(User user, String themeColor) {
    this.user = user;
    this.themeColor = themeColor;
  }

  public Long getUserId() { return userId; }
  public User getUser() { return user; }

  // 以下、各種設定項目のゲッター・セッターを追加
  public String getThemeColor() { return themeColor; }
  public void setThemeColor(String themeColor) { this.themeColor = themeColor; }

  public String getDepartmentOrder() { return departmentOrder; }
  public void setDepartmentOrder(String departmentOrder) { this.departmentOrder = departmentOrder; }
}
