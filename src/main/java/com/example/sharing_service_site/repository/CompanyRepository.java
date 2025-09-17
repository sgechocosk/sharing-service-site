package com.example.sharing_service_site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.Company;

/**
 * 会社情報のDB操作を行う
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

  /**
   * 会社名を基に会社情報を検索する
   * 会社名は一意であることを前提とする
   * 
   * @param companyName 検索対象の会社名
   * @return 該当する会社情報
   */
  Optional<Company> findByCompanyName(String companyName);
}
