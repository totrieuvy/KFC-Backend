package com.example.Order_Project.repository;

import com.example.Order_Project.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Account findAccountByEmail(String email);
    Optional<Account> findByPhone(String phone);
}
