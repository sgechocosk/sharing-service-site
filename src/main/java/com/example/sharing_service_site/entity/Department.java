package com.example.sharing_service_site.entity;

import java.util.List;

import jakarta.persistence.Column;
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
  @Column(name = "department_id")
  private Long departmentId;

  @Column(name = "department_name", nullable = false)
  private String departmentName;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Department parent = null;

  @OneToMany(mappedBy = "parent")
  private List<Department> children;

  @OneToMany(mappedBy = "department")
  private List<User> users;

  public Long getDepartmentId() { return departmentId; }
  public String getDepartmentName() { return departmentName; }
  public Company getCompany() { return company; }
  public Department getParent() { return parent; }
  public List<Department> getChildren() { return children; }
  public List<User> getUsers() { return users; }
}
