package com.example.sharing_service_site.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
  @GetMapping("/login")
  public String login() {
    return "/login";
  }

  @GetMapping("/home")
  public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    model.addAttribute("userName", userDetails.getUsername());
    return "home";
  }
}
