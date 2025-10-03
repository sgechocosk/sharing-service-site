package com.example.sharing_service_site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.Settings;

/**
 * 設定情報のDB操作を行う
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {

  /**
   * ユーザーIDを基に設定情報を検索する
   * 
   * @param userId 検索対象のユーザーID
   * @return 該当する設定情報一覧
   */
  Optional<Settings> findByUserUserId(Long userId);
}
