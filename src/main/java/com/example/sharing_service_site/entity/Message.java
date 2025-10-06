package com.example.sharing_service_site.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * メッセージ情報
 */
@Entity
@Table(name = "messages")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "message_id")
  private Long messageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User author;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "edited", nullable = false)
  private boolean edited = false;

  public Message() {}

  public Message(Department department, User author, String content) {
    this.department = department;
    this.author = author;
    this.content = content;
    this.createdAt = LocalDateTime.now();
    this.edited = false;
  }

  public Long getMessageId() { return messageId; }
  public Department getDepartment() { return department; }
  public User getAuthor() { return author; }
  public String getContent() { return content; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public String getCreatedAtText() {return createdAt.toString()
                                                    .replace('T', ' ')
                                                    .substring(0, 16); }

  public void setDepartment(Department department) { this.department = department; }
  public void setAuthor(User author) { this.author = author; }
  public void setContent(String content) { this.content = content; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
  public void setEdited(boolean edited) { this.edited = edited; }

  public boolean isEdited() { return edited; }

  public void updateContent(String newContent) {
    this.content = newContent;
    this.updatedAt = LocalDateTime.now();
    this.edited = true;
  }
}
