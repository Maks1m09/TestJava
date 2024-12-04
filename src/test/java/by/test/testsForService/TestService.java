package by.test.testsForService;


import by.test.controller.WalletController;
import by.test.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WalletController.class)
class TestWalletController {

    @Autowired
    MockMvc mvc;

    @MockBean
    WalletService walletService;


    @Test
    public void testGetWalletBalanceNotFound() throws Exception {
        when(walletService.getBalance(1)).thenThrow(new NoSuchElementException("wallet not found"));

        mvc.perform(get("/api/v1/wallet/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: Wallet with ID 1 does not exist."));
    }

    @Test
    public void testGetWalletBalanceSuccess() throws Exception {
        when(walletService.getBalance(1)).thenReturn(BigDecimal.valueOf(150));

        mvc.perform(get("/api/v1/wallet/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Your wallet balance: 150"));

        verify(walletService, times(1)).getBalance(1);
    }

}



