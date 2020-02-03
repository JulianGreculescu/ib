package au.com.gritmed.ib.controler;

import au.com.gritmed.ib.domain.UiAccount;
import au.com.gritmed.ib.domain.UiTransaction;
import au.com.gritmed.ib.exception.ExceptionResponse;
import au.com.gritmed.ib.utils.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import static au.com.gritmed.ib.domain.AccountType.CURRENT;
import static au.com.gritmed.ib.domain.AccountType.SAVINGS;
import static au.com.gritmed.ib.domain.Currency.AUD;
import static au.com.gritmed.ib.domain.Currency.SGD;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AccountsControllerTest {
    @Value("classpath:101_all_accounts.json")
    private Resource _101AllAccounts;
    @Value("classpath:101_unlocked_accounts.json")
    private Resource _101UnlockedAccounts;
    @Value("classpath:901_transactions.json")
    private Resource __901Transactions;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcOperations jdbcOperations;
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private TestHelper helper;

    @BeforeEach
    void setUp() {
        webClient.mutate()
                .responseTimeout(Duration.ofMillis(1000000L))
                .build();
        helper.accountInsert(901, "123-456-001", "Savings 001", SAVINGS, AUD, 100001);
        helper.accountInsert(902, "123-456-002", "Current 002", CURRENT, SGD, 200002);
        helper.accountInsert(903, "123-456-003", "Current 003", CURRENT, AUD, 300003);

        helper.userAccountInsert(1,101, 901, false);
        helper.userAccountInsert(2,101, 902, false);
        helper.userAccountInsert(3,101, 903, true);
        helper.userAccountInsert(4,102, 903, false);

        helper.transactionInsert(1001, 901, "2020-02-01 09:15:23", 114632, "Barry Plant rent");
        helper.transactionInsert(1002, 901, "2020-02-01 12:44:18", -26911, "WOOLWORTHS 3055CASH OUT $200.00");
        helper.transactionInsert(1003, 901, "2020-02-01 15:28:51", -3774, "Miscellaneous debit");
    }

    @AfterEach
    void tearDown() {
        helper.userAccountsTruncate();
        helper.transactionsTruncate();
        helper.accountsTruncate();
    }

    @Test
    void shouldBeAbleToRetrieveAllUserAccounts() throws Exception {
        EntityExchangeResult<String> result = webClient.get()
                .uri("/accounts?userId=101")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult();

         String body = result.getResponseBody();
         assertNotNull(body);
         List<UiAccount> response = objectMapper.readValue(body, List.class);
         String indentedJsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
         assertEquals(StreamUtils.copyToString(_101AllAccounts.getInputStream(), StandardCharsets.UTF_8),
                            indentedJsonResponse);
    }

    @Test
    void shouldBeAbleToRetrieveAllUserUnlockedAccounts() throws Exception {
        EntityExchangeResult<String> result = webClient.get()
                .uri("/accounts?userId=101&excludeLocked=true")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult();

         String body = result.getResponseBody();
         assertNotNull(body);
         List<UiAccount> response = objectMapper.readValue(body, List.class);
         String indentedJsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
         assertEquals(StreamUtils.copyToString(_101UnlockedAccounts.getInputStream(), StandardCharsets.UTF_8),
                            indentedJsonResponse);
    }

    @Test
    void shouldReturnNotFoundIfNoAccounts() throws Exception {
        EntityExchangeResult<String> result = webClient.get()
                .uri("/accounts?userId=999")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class)
                .returnResult();

         String body = result.getResponseBody();
         assertNotNull(body);
         ExceptionResponse response = objectMapper.readValue(body, ExceptionResponse.class);
         assertEquals("No accounts found for userId = 999", response.getMessage());
         assertEquals("uri=/accounts", response.getDetails());
    }

    @Test
    void shouldBeAbleToRetrieveAccountTransactions() throws Exception {
        EntityExchangeResult<String> result = webClient.get()
                .uri("/accounts/901?userId=101")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult();

        String body = result.getResponseBody();
        assertNotNull(body);
        List<UiTransaction> response = objectMapper.readValue(body, List.class);
        String indentedJsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        assertEquals(StreamUtils.copyToString(__901Transactions.getInputStream(), StandardCharsets.UTF_8),
                indentedJsonResponse);
    }

    @Test
    void shouldReturnAnEmptyListIfNoTransactions() throws Exception {
        EntityExchangeResult<String> result = webClient.get()
                .uri("/accounts/902?userId=101")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult();

        String body = result.getResponseBody();
        assertNotNull(body);
        assertTrue(objectMapper.readValue(body, List.class).isEmpty());
    }

    @Test
    void shouldReturnNotFoundIfNoAccount() throws Exception {
        EntityExchangeResult<String> result = webClient.get()
                .uri("/accounts/999?userId=101")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class)
                .returnResult();

        String body = result.getResponseBody();
        assertNotNull(body);
        ExceptionResponse response = objectMapper.readValue(body, ExceptionResponse.class);
        assertEquals("No account found for userId = 101 and accountId = 999", response.getMessage());
        assertEquals("uri=/accounts/999", response.getDetails());
    }

}
