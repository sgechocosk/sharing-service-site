package com.example.sharing_service_site.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sharing_service_site.entity.Company;
import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.repository.DepartmentRepository;

@Service
public class DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  /**
   * 会社名を指定して、ルート部署（親がnullの部署）を取得する
   */
  public List<Department> getRootDepartmentsByCompany(Company company) {
    List<Department> allDepartments = departmentRepository.findByCompany(company);
    return allDepartments.stream()
        .filter(d -> d.getParent() == null)
        .toList();
  }

  /**
   * 部署IDを指定して、その子部署一覧を取得する
   */
  public List<Department> getChildDepartments(Long parentDepartmentId) {
    Optional<Department> parentOpt = departmentRepository.findById(parentDepartmentId);
    if (parentOpt.isEmpty()) {
      throw new IllegalArgumentException("部署が見つかりません: id=" + parentDepartmentId);
    }
    return departmentRepository.findByParent(parentOpt.get());
  }

  /**
   * 部署IDを指定して、その部署名を取得する
   */
  public String getDepartmentNameById(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .map(Department::getDepartmentName)
        .orElse(null);
  }
}
