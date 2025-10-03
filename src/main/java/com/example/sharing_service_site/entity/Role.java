package com.example.sharing_service_site.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * ロール情報
 */
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long roleId;

  // rolesテーブルのrole_nameカラムをDEFAULT NULLでないように変更する
  // DBにはユニーク制約なし
  @Column(name = "role_name", nullable = false, unique = true)
  private String roleName;

  @OneToMany(mappedBy = "roles")
  private List<User> users;

  public Long getRoleId() { return roleId; }
  public String getRoleName() { return roleName; }
  public List<User> getUsers() { return users; }
}
