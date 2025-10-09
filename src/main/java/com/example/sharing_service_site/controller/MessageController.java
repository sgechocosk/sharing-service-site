package com.example.sharing_service_site.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sharing_service_site.entity.Message;
import com.example.sharing_service_site.entity.User;
import com.example.sharing_service_site.service.CustomUserDetails;
import com.example.sharing_service_site.service.CustomUserDetailsService;
import com.example.sharing_service_site.service.DepartmentService;
import com.example.sharing_service_site.service.MessageService;

@Controller
public class MessageController {

  private final MessageService messageService;
  private final DepartmentService departmentService;
  private final CustomUserDetailsService userDetailsService;

  public MessageController(MessageService messageService,
                           DepartmentService departmentService,
                           CustomUserDetailsService userDetailsService) {
    this.messageService = messageService;
    this.departmentService = departmentService;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping("/message")
  public String message(Model model,
                        @AuthenticationPrincipal CustomUserDetails userDetails,
                        @RequestParam Long departmentId) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("userId", userDetails.getUserId());
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

  @GetMapping("/message")
  public String getMessage(Model model,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           @ModelAttribute("departmentId") Long departmentId) {
    model.addAttribute("fullName", userDetails.getFullName());
    model.addAttribute("departmentId", departmentId);
    model.addAttribute("userId", userDetails.getUserId());
    model.addAttribute("roleName", userDetails.getRoleName());

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

  @PostMapping("/message/delete")
  public String deleteMessage(@RequestParam Long messageId,
                              @RequestParam Long departmentId,
                              RedirectAttributes redirectAttributes) {
    try {
      messageService.deleteMessage(messageId);
      redirectAttributes.addFlashAttribute("success", "メッセージを削除しました。");
    } catch (IllegalArgumentException ex) {
      redirectAttributes.addFlashAttribute("error", ex.getMessage());
    }
    redirectAttributes.addFlashAttribute("departmentId", departmentId);
    return "redirect:/message";
  }
}
