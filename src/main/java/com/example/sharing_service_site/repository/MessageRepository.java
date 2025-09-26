package com.example.sharing_service_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.Message;

/**
 * メッセージ情報のDB操作を行う
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
  
  /**
   * 部署IDを基にメッセージ情報を検索する
   * 作成日時の昇順でソートする
   * 
   * @param departmentId 検索対象の部署ID
   * @return 該当するメッセージ情報
   */
  List<Message> findByDepartmentDepartmentIdOrderByCreatedAtAsc(Long departmentId);
}
