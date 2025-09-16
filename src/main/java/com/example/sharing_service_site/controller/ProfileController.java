package com.example.sharing_service_site.controller;

import com.example.sharing_service_site.service.CustomUserDetails;
import com.example.sharing_service_site.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @PostMapping("/profile/edit")
  public String changePassword(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam String oldPassword,
        @RequestParam String newPassword,
        @RequestParam String confirmPassword,
        Model model) {

    boolean hasError = false;

    if (newPassword.length() < 8) {
      model.addAttribute("newPasswordError", "パスワードは8文字以上にしてください。");
      model.addAttribute("oldPassword", oldPassword);
      hasError = true;
    }

    if (!newPassword.equals(confirmPassword)) {
      model.addAttribute("confirmPasswordError", "新しいパスワードが一致しません。");
      model.addAttribute("oldPassword", oldPassword);
      hasError = true;
    }

    if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
      model.addAttribute("oldPasswordError", "旧パスワードが間違っています。");
      model.asMap().remove("oldPassword");
      hasError = true;
    }

    if (hasError) {
      model.addAttribute("fullName", userDetails.getFullName());
      return "/profile-edit";
    }

    String encodedPassword = passwordEncoder.encode(newPassword);
    userDetailsService.updatePassword(userDetails.getUsername(), encodedPassword);

    return "redirect:/profile/edit/done";
  }
}
