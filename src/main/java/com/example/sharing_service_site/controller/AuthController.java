package com.example.sharing_service_site.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.entity.Message;
import com.example.sharing_service_site.entity.User;
import com.example.sharing_service_site.service.CustomUserDetails;
import com.example.sharing_service_site.service.CustomUserDetailsService;
import com.example.sharing_service_site.service.DepartmentService;
import com.example.sharing_service_site.service.MessageService;

@Controller
public class AuthController {

  private final CustomUserDetailsService userDetailsService;
  private final DepartmentService departmentService;
  private final MessageService messageService;

  public AuthController(DepartmentService departmentService, MessageService messageService, CustomUserDetailsService userDetailsService) {
    this.departmentService = departmentService;
    this.messageService = messageService;
    this.userDetailsService = userDetailsService;
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

  @PostMapping("/message")
  public String message(Model model,
                        @AuthenticationPrincipal CustomUserDetails userDetails,
                        @RequestParam Long departmentId) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("roleName", userDetails.getRoleName());
    model.addAttribute("departmentId", departmentId);

    User user = userDetailsService.getUser(userDetails.getEmployeeNumber());
    model.addAttribute("user", user);
    
    String departmentName = departmentService.getDepartmentNameById(departmentId);
    List<Message> messages = messageService.getMessagesByDepartmentId(departmentId);
    model.addAttribute("selectedDepartmentName", departmentName);
    model.addAttribute("messages", messages);
    return "message";
  }

  @PostMapping("/message/send")
  public String sendMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @RequestParam Long departmentId,
                            @RequestParam String content,
                            RedirectAttributes redirectAttributes) {
    User author = userDetailsService.getUser(userDetails.getEmployeeNumber());
    try {
      messageService.saveMessage(departmentId, author, content.trim());
      redirectAttributes.addFlashAttribute("success", "メッセージを投稿しました。");
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("error", ex.getMessage());
    }

    redirectAttributes.addFlashAttribute("departmentId", departmentId);
    return "redirect:/message";
  }

  @GetMapping("/message")
  public String getMessage(Model model,
                          @AuthenticationPrincipal CustomUserDetails userDetails,
                          @ModelAttribute("departmentId") Long departmentId) {
    if (departmentId == null) {
      return "redirect:/home";
    }
    populateMessageModel(model, userDetails, departmentId);
    return "message";
  }

  private void populateMessageModel(Model model, CustomUserDetails userDetails, Long departmentId) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("departmentId", departmentId);

    User user = userDetailsService.getUser(userDetails.getEmployeeNumber());
    model.addAttribute("user", user);

    String departmentName = departmentService.getDepartmentNameById(departmentId);
    List<Message> messages = messageService.getMessagesByDepartmentId(departmentId);
    model.addAttribute("selectedDepartmentName", departmentName);
    model.addAttribute("messages", messages);
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
  public String profileEdit(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("password", userDetails.getPassword());
    return "/profile-edit";
  }

  @GetMapping("/profile/edit/done")
  public String profileEditDone(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    return "/profile-edit-done";
  }

  @GetMapping("/settings")
  public String settings(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    return "/settings";
  }

  @GetMapping("/settings/user")
  public String settingsUser(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
    model.addAttribute("fullName", userDetails.getFullName());
    List<User> users = userDetailsService.getUsersByCompanyId(userDetails.getCompany().getCompanyId());
    model.addAttribute("users", users);
    return "/settings-user";
  }

  @PostMapping("/settings/user/update")
  public String updateUserRole(@RequestParam Long userId,
                              @RequestParam String role,
                              RedirectAttributes redirectAttributes) {
    try {
      userDetailsService.updateUserRole(userId, role);
      redirectAttributes.addFlashAttribute("success", "ユーザーのロールを更新しました。");
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("error", ex.getMessage());
    }
    return "redirect:/settings/user";
  }
}
