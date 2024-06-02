package com.ecajas.banking_service.repository;

import com.ecajas.banking_service.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    /**
     * Muestra un listado de transacciones que se pueda filtrar a partir de un nro de
     * cuenta origen o nro cuenta destino
     */
    @Query("SELECT t FROM Transaction t WHERE t.sourceAcount.accountNumber=:accountNumber OR  t.targetAcount.accountNumber=:accountNumber")
    List<Transaction> findBySourceOrTragetAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT t.transactionDate, COUNT (t) FROM Transaction t WHERE T.transactionDate BETWEEN :startDate AND :endDate AND (t.sourceAcount.accountNumber=:accountNumber OR t.targetAcount.accountNumber=:accountNumber) GROUP BY T.transactionDate")
    List<Object[]> getTransactionCountByDateRangeAndAccountNumber(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate,
                                                   @Param("accountNumber") String accountNumber);

}
