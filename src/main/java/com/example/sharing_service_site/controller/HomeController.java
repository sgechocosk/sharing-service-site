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
import com.example.sharing_service_site.service.SettingsService;

@Controller
public class HomeController {

  private final DepartmentService departmentService;
  private final SettingsService settingsService;

  public HomeController(DepartmentService departmentService,
                        SettingsService settingsService) {
    this.departmentService = departmentService;
    this.settingsService = settingsService;
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

    String themeColor = settingsService.getThemeColorByUserId(userDetails.getUserId());
    model.addAttribute("themeColor", themeColor);
    return "home";
  }

  @GetMapping("/departments")
  @ResponseBody
  public List<Department> getRootDepartments(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return departmentService.getRootDepartmentsByCompany(userDetails.getCompany());
  }

  @GetMapping("/departments/{id}")
  @ResponseBody
  public List<Department> getChildDepartments(@PathVariable Long id) {
    return departmentService.getChildDepartments(id);
  }
}
