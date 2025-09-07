package com.example.sharing_service_site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sharing_service_site.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmployeeNumber(String employeeNumber);
}