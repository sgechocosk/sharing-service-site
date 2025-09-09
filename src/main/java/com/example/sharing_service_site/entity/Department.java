package com.example.sharing_service_site.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 部署情報
 */
@Entity
@Table(name = "departments")
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long departmentId;

  private String departmentName;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Department parent;

  @OneToMany(mappedBy = "parent")
  private List<Department> children;

  @OneToMany(mappedBy = "department")
  private List<User> users;

  public String getDepartmentName() { return departmentName; }
}
