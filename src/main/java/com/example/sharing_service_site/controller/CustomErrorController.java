package com.example.sharing_service_site.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sharing_service_site.service.CustomUserDetails;
import com.example.sharing_service_site.service.SettingsService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

  private final SettingsService settingsService;

  public CustomErrorController(SettingsService settingsService) {
    this.settingsService = settingsService;
  }

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request,
                            Model model,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
    Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
    model.addAttribute("status", statusCode);

    if (userDetails != null) {
      model.addAttribute("fullName", userDetails.getFullName());
    }

    String themeColor = settingsService.getThemeColorByUserId(userDetails.getUserId());
    model.addAttribute("themeColor", themeColor);
    return "error";
  }
}
