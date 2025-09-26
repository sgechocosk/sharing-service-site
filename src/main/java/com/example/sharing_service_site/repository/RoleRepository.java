package com.example.sharing_service_site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  /**
   * ロールIDを基にロール情報を検索する
   * @param roleId 検索対象のロールID
   * @return 該当するロール情報
   */
  Optional<Role> findByRoleId(Long roleId);
}
