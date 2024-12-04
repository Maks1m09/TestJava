package by.test.service;

import by.test.entity.OperationType;
import by.test.entity.Wallet;
import by.test.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public void modifyWallet(UUID id, OperationType type, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new NoSuchElementException("wallet not found"));
        if (type == OperationType.DEPOSIT) {
            wallet.setAmount(wallet.getAmount().add(amount));
        } else if (type == OperationType.WITHDRAW) {
            if (wallet.getAmount().compareTo(amount) < 0) {
                throw new IllegalArgumentException("insufficient funds");
            }
            wallet.setAmount(wallet.getAmount().subtract(amount));
        } else {
            throw new IllegalArgumentException("Invalid operation type");
        }
        walletRepository.save(wallet);
    }

    public BigDecimal getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(Wallet::getAmount)
                .orElseThrow(() -> new ExpressionException("wallet not found"));
    }
}
