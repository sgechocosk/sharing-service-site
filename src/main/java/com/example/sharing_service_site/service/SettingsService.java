package com.example.sharing_service_site.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sharing_service_site.entity.Settings;
import com.example.sharing_service_site.repository.SettingsRepository;

@Service
public class SettingsService {
  @Autowired
  private SettingsRepository settingsRepository;
  
  /**
   * ユーザーIDを基に設定情報を取得する
   * 
   * @param userId ユーザーID
   * @return 該当する設定情報一覧
   */
  public Settings getSettingsByUserId(Long userId) {
    return settingsRepository.findByUserUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("設定情報が存在しません: " + userId));
  }

  // 以下、各種設定項目の更新メソッドを追加
  /**
   * テーマカラーを更新する
   * @param themeColor 更新後のテーマカラー
   */
  public Settings updateThemeColor(Long userId, String themeColor) {
    Settings settings = settingsRepository.findByUserUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("設定情報が存在しません: " + userId));
    settings.setThemeColor(themeColor);
    return settingsRepository.save(settings);
  }
}
