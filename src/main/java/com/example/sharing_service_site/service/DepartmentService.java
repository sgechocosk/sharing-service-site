package com.example.sharing_service_site.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sharing_service_site.entity.Company;
import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.repository.DepartmentRepository;

@Service
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  public DepartmentService(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  /**
   * 会社名を指定して、ルート部署（親がnullの部署）を取得する
   * 
   * @param company 会社エンティティ
   * @return ルート部署一覧
   */
  public List<Department> getRootDepartmentsByCompany(Company company) {
    List<Department> allDepartments = departmentRepository.findByCompany(company);
    return allDepartments.stream()
        .filter(d -> d.getParent() == null)
        .toList();
  }

  /**
   * 部署IDを指定して、その子部署一覧を取得する
   * 
   * @param parentDepartmentId 親部署ID
   * @return 子部署一覧
   * @throws IllegalArgumentException 親部署が見つからない場合
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
   * 
   * @param departmentId 部署ID
   * @return 部署名（部署が見つからない場合はnullを返す）
   */
  public String getDepartmentNameById(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .map(Department::getDepartmentName)
        .orElse(null);
  }

  /**
   * 部署IDを指定して、その部署の会社IDを取得する
   * 
   * @param departmentId 部署ID
   * @return 会社ID（部署が見つからない場合はnullを返す）
   */
  public Long getCompanyIdById(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .map(Department::getCompanyId) // 以下のように取得したいがDepartmentエンティティにgetCompany()を追加すると部署が適切に表示されないためgetCompanyId()で対応
        // .map(dept -> dept.getCompany().getCompanyId())
        .orElse(null);
  }
}
