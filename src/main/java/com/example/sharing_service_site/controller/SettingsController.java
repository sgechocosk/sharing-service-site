package com.example.sharing_service_site.controller;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sharing_service_site.entity.Settings;
import com.example.sharing_service_site.entity.User;
import com.example.sharing_service_site.service.CustomUserDetails;
import com.example.sharing_service_site.service.CustomUserDetailsService;
import com.example.sharing_service_site.service.SettingsService;

@Controller
public class SettingsController {

  private final SettingsService settingsService;
  private final CustomUserDetailsService userDetailsService;

  public SettingsController(SettingsService settingsService,
                            CustomUserDetailsService userDetailsService) {
    this.settingsService = settingsService;
    this.userDetailsService = userDetailsService;
  }

  @GetMapping("/settings")
  public String settings(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    Settings settings = settingsService.getSettingsByUserId(userDetails.getUserId());
    model.addAttribute("settings", settings);

    String themeColor = settingsService.getThemeColorByUserId(userDetails.getUserId());
    model.addAttribute("themeColor", themeColor);
    return "/settings";
  }

  @PostMapping("/settings/updateThemeColor")
  public String updateSettings(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam String themeColor,
                               RedirectAttributes redirectAttributes) {
    settingsService.updateThemeColor(userDetails.getUserId(), themeColor);
    redirectAttributes.addFlashAttribute("success", "設定を更新しました。");
    return "redirect:/settings";
  }

  @GetMapping("/settings/user")
  public String settingsUser(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    List<User> users = userDetailsService.getUsersByCompanyId(userDetails.getCompany().getCompanyId());
    model.addAttribute("users", users);

    String themeColor = settingsService.getThemeColorByUserId(userDetails.getUserId());
    model.addAttribute("themeColor", themeColor);
    return "/settings-user";
  }

  @PostMapping("/settings/user/update")
  public String updateUserRole(@RequestParam Long userId,
                              @RequestParam String role,
                              RedirectAttributes redirectAttributes) {
    try {
      userDetailsService.updateUserRole(userId, role);
      redirectAttributes.addFlashAttribute("success", "ロールを更新しました。");
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("error", ex.getMessage());
    }
    return "redirect:/settings/user";
  }
}
