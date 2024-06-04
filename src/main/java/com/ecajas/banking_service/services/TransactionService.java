package com.ecajas.banking_service.services;

import com.ecajas.banking_service.exception.BadRequestException;
import com.ecajas.banking_service.exception.ResourceNotFoundException;
import com.ecajas.banking_service.mapper.TransactionMapper;
import com.ecajas.banking_service.model.dto.TransactionReportDTO;
import com.ecajas.banking_service.model.dto.TransactionRequestDTO;
import com.ecajas.banking_service.model.dto.TransactionResponseDTO;
import com.ecajas.banking_service.model.entity.Account;
import com.ecajas.banking_service.model.entity.Transaction;
import com.ecajas.banking_service.repository.AccountRepository;
import com.ecajas.banking_service.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private TransactionMapper trasactionMapper;


    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionByAccountNumber(String accountNumber){
        List<Transaction> transactions= transactionRepository.findBySourceOrTragetAccountNumber(accountNumber);
        return trasactionMapper.convertToListDTO(transactions);
    }

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionDTO) {
        // Obtener las cuentas involucradas en la transacción y verificar si existen
        Account sourceAccount = accountRepository.findByAccountNumber(transactionDTO.getSourceAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("La cuenta de origen no existe"));

        Account targetAccount = accountRepository.findByAccountNumber(transactionDTO.getTargetAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("La cuenta de destino no existe"));

        // Verificar si el saldo de la cuenta de origen es suficiente para realizar la transacción
        BigDecimal amount = transactionDTO.getAmount();
        BigDecimal sourceAccountBalance = sourceAccount.getBalance();
        if (sourceAccountBalance.compareTo(amount) < 0) {
            throw new BadRequestException("Saldo insuficiente en la cuenta de origen");
        }

        // Realizar la transacción
        Transaction transaction = trasactionMapper.convertToEntity(transactionDTO);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setSourceAcount(sourceAccount);
        transaction.setTargetAcount(targetAccount);
        transaction = transactionRepository.save(transaction);

        // Actualizar los saldos de las cuentas
        BigDecimal newSourceAccountBalance = sourceAccountBalance.subtract(amount);
        BigDecimal newTargetAccountBalance = targetAccount.getBalance().add(amount);

        sourceAccount.setBalance(newSourceAccountBalance);
        targetAccount.setBalance(newTargetAccountBalance);

        // Guardar los cambios en las cuentas
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        return trasactionMapper.convertToDTO(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionReportDTO> generateTransactionReport(String startDateStr,
                                                                String endDateStr,
                                                                String  accountNumber){

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Object[]> transactionCounts = transactionRepository.
                getTransactionCountByDateRangeAndAccountNumber(startDate,endDate,accountNumber);
        return transactionCounts.stream().
                map(trasactionMapper::convertTransactionReportDTO).toList();
    }
}
