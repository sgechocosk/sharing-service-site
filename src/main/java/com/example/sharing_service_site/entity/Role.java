package com.example.sharing_service_site.entity;

import java.util.List;

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
  private Long roleId;

  private String roleName;

  @OneToMany(mappedBy = "roles")
  private List<User> users;

  public String getRoleName() { return roleName; }
}
