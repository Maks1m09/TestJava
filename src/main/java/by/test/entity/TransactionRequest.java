package by.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TransactionRequest {

    private UUID walletId;

    private OperationType operationType;

    private BigDecimal amount;
}
