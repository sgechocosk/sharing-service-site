package com.example.sharing_service_site.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 会社情報
 */
@Entity
@Table(name = "companies")
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long companyId;

  private String companyName;

  @OneToMany(mappedBy = "company")
  private List<Department> departments;

  @OneToMany(mappedBy = "company")
  private List<User> users;

  public Long getCompanyId() { return companyId; }
  public String getCompanyName() { return companyName; }
}
