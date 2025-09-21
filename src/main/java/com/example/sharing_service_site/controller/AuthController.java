package com.example.sharing_service_site.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.service.CustomUserDetails;
import com.example.sharing_service_site.service.DepartmentService;

@Controller
public class AuthController {

  private final DepartmentService departmentService;

  public AuthController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping("/login")
  public String login() {
    return "/login";
  }

  @GetMapping("/home")
  public String home(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("companyName", userDetails.getCompanyName());
    model.addAttribute("company", userDetails.getCompany());
    model.addAttribute("department", userDetails.getDepartment());

    List<Department> rootDepartments =
      departmentService.getRootDepartmentsByCompany(userDetails.getCompany());
    model.addAttribute("departments", rootDepartments);
    return "home";
  }

  @GetMapping("/departments/{id}")
  @ResponseBody
  public List<Department> getChildDepartments(@PathVariable Long id) {
    return departmentService.getChildDepartments(id);
  }

  @GetMapping("/departments")
  @ResponseBody
  public List<Department> getRootDepartments(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return departmentService.getRootDepartmentsByCompany(userDetails.getCompany());
  }

  @GetMapping("/profile")
  public String profile(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("username", userDetails.getUsername());
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("role", userDetails.getRoleName());
    model.addAttribute("company", userDetails.getCompanyName());
    model.addAttribute("department", userDetails.getDepartmentName());
    return "/profile";
  }

  @GetMapping("/profile/edit")
  public String profile_edit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("password", userDetails.getPassword());
    return "/profile-edit";
  }

  @GetMapping("/profile/edit/done")
  public String profile_edit_done(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    return "/profile-edit-done";
  }
}
