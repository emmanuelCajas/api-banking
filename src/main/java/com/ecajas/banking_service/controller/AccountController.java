package com.ecajas.banking_service.controller;


import com.ecajas.banking_service.model.dto.AccountRequestDTO;
import com.ecajas.banking_service.model.dto.AccountResponseDTO;
import com.ecajas.banking_service.services.AccountService;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//hhtp:/localhost/8080/api/v1/accounts -- esto es una url
//y accounts solo es la uri
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllCounts(){

        List<AccountResponseDTO> accounts= accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AccountResponseDTO>> getAccountById(@PathVariable Long id){

        List<AccountResponseDTO> accounts= accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Validated @RequestBody
                                                            AccountRequestDTO accountDTO) {
        AccountResponseDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/accounts/4
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id,
                                                            @Validated @RequestBody AccountRequestDTO accountDTO) {
        AccountResponseDTO updatedAccount = accountService.upDateAccount(id, accountDTO);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/accounts/4
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
