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

  /**
   * 部署IDを基にメッセージを取得する
   * 
   * @param departmentId 部署ID
   * @return 該当するメッセージ一覧
   */
  public List<Message> getMessagesByDepartmentId(Long departmentId) {
    return messageRepository.findByDepartmentDepartmentIdOrderByCreatedAtAsc(departmentId);
  }

  /**
   * メッセージを保存する
   * 
   * @param departmentId 部署ID
   * @param author       投稿者ユーザー
   * @param content      メッセージ内容
   * @return 保存されたメッセージ
   * @throws IllegalArgumentException 部署が見つからない場合
   */
  public Message saveMessage(Long departmentId, User author, String content) {
    Department department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new IllegalArgumentException("部署が見つかりません: " + departmentId));

    Message message = new Message(department, author, content);
    return messageRepository.save(message);
  }

  /**
   * メッセージを削除する
   * 
   * @param messageId メッセージID
   */
  public void deleteMessage(Long messageId) {
    messageRepository.deleteById(messageId);
  }
}
