package by.test.controller;

import by.test.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    WalletService walletService;

    @Test
    public void testGetWalletBalanceNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(walletService.getBalance(uuid)).thenThrow(new NoSuchElementException("wallet not found"));

        mvc.perform(get("/api/v1/wallet/{walletId}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Wallet with ID " + uuid + " does not exist."));
    }

    @Test
    public void testGetWalletBalanceSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(walletService.getBalance(uuid)).thenReturn(BigDecimal.valueOf(150));
        mvc.perform(get("/api/v1/wallet/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().string("Your wallet balance: 150"));

        verify(walletService, times(1)).getBalance(uuid);
    }
}



