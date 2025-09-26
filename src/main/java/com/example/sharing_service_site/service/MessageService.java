package com.example.sharing_service_site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sharing_service_site.entity.Department;
import com.example.sharing_service_site.entity.Message;
import com.example.sharing_service_site.entity.User;
import com.example.sharing_service_site.repository.DepartmentRepository;
import com.example.sharing_service_site.repository.MessageRepository;

@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  public List<Message> getMessagesByDepartmentId(Long departmentId) {
    return messageRepository.findByDepartmentDepartmentIdOrderByCreatedAtAsc(departmentId);
  }

  public Message saveMessage(Long departmentId, User author, String content) {
    Department department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new IllegalArgumentException("部署が見つかりません: " + departmentId));

    Message message = new Message(department, author, content);
    return messageRepository.save(message);
  }
}
