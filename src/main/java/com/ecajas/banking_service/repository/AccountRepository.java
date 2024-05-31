package com.ecajas.banking_service.repository;

import com.ecajas.banking_service.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Como estamos usando SpringDataJPA nuetras interfaces van a extender de
 * la interfaz JpaRepository
 */
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

}
