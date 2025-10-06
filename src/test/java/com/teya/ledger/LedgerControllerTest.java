package com.teya.ledger;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.teya.constants.Constants;
import com.teya.ledger.controller.LedgerController;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.service.LedgerService;


@WebMvcTest(LedgerController.class)
class LedgerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LedgerService ledgerService;

    @Test
    void testDepositEndpoint() throws Exception {
        Transaction t = new Transaction(1, "DEPOSIT", 100, ZonedDateTime.now(), 100);
        when(ledgerService.deposit("111", 100.0)).thenReturn(t);

        mockMvc.perform(post("/api/v1/ledger/111/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(100));
    }

    @Test
    void testWithdrawEndpoint() throws Exception {
        Transaction t = new Transaction(1, "WITHDRAWAL", 50, ZonedDateTime.now(), 50);
        when(ledgerService.withdraw("111", 50.0)).thenReturn(t);

        mockMvc.perform(post("/api/v1/ledger/111/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"))
                .andExpect(jsonPath("$.amount").value(50));
    }
    
    @Test
    void testGetBalance() throws Exception {
        when(ledgerService.getBalance("111")).thenReturn(250.0);

        mockMvc.perform(get("/api/v1/ledger/111/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("250.0"));
    }

    @Test
    void testResetEndpoint() throws Exception {
        doNothing().when(ledgerService).resetAllData();

        mockMvc.perform(post("/api/v1/ledger/admin/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string(Constants.MSG_RESET_MESSAGE));
    }
}
