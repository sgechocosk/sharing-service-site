package com.example.sharing_service_site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.User;

/*
 * ユーザー情報のDB操作を行う
 */
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * 従業員番号を基にユーザー情報を検索する
   * 従業員番号は一意である
   * @param employeeNumber 検索対象の従業員番号
   * @return 該当するユーザー情報
   */
  Optional<User> findByEmployeeNumber(String employeeNumber);
}
