package by.test.controller;

import by.test.entity.Wallet;
import by.test.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<String> processTransaction(@Valid @RequestBody Wallet request) {
        try {
            walletService.modifyWallet(request.getId(), request.getType(), request.getAmount());
            return ResponseEntity.ok("Transaction processed successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Error: Wallet not found. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: Invalid request. " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Error: Invalid data type provided in request. Please check your input.");
    }


    @GetMapping("/{walletId}")
    public ResponseEntity<String> getWalletBalance(@PathVariable String walletId) {
        try {
            if (!walletId.matches("\\d+")) {
                return ResponseEntity.badRequest().body("Error: Wallet ID must be a valid integer.");
            }
            Integer id = Integer.valueOf(walletId);
            BigDecimal balance = walletService.getBalance(id);
            return ResponseEntity.ok("Your wallet balance: " + balance);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Wallet with ID " + walletId + " does not exist.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Please check the data you entered");
        }
    }
}
