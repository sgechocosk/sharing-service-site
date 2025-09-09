package com.example.sharing_service_site.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sharing_service_site.service.CustomUserDetails;

@Controller
public class AuthController {
  @GetMapping("/login")
  public String login() {
    return "/login";
  }

  @GetMapping("/home")
  public String home(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("company", userDetails.getCompanyName());
    model.addAttribute("department", userDetails.getDepartmentName());
    model.addAttribute("roles", userDetails.getRoleName());
    return "home";
  }
}
