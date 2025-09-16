package com.example.sharing_service_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.Company;
import com.example.sharing_service_site.entity.Department;

/**
 * 部署情報のDB操作を行う
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {

  /**
   * 会社を基に部署情報を検索する
   * 部署名は一意であることを前提とする
   * 
   * @param company 検索対象の会社
   * @return 該当する部署情報
   */
  List<Department> findByCompany(Company company);

  /**
   * 親を基に部署情報を検索する
   * 
   * @param parent 検索対象の親
   * @return 該当する部署情報
   */
  List<Department> findByParent(Department parent);

  /**
   * 親のIDを基に部署情報を検索する
   * @param parentId 検索対象の親ID
   * @return 該当する部署情報
   */
  List<Department> findByParentDepartmentId(Long parentId);
}
