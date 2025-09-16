package com.example.sharing_service_site.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.repository.DepartmentRepository;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

  private DepartmentRepository departmentRepository;

  @GetMapping("/departments/{parentId}/children")
  @ResponseBody
  public List<Department> getChildDepartments(@PathVariable Long parentId) {
    return departmentRepository.findByParentDepartmentId(parentId);
  }
}
