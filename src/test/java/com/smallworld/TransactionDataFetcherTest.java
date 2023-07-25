package com.smallworld;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.smallworld.data.Transaction;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransactionDataFetcherTest {

    private TransactionDataFetcher dataFetcher;

    @BeforeEach
    public void setUp() {
        dataFetcher = new TransactionDataFetcher();
    }

    @Test
    public void testGetTotalTransactionAmount() {
        double expectedTotalAmount = 4371.37;
        assertEquals(expectedTotalAmount, dataFetcher.getTotalTransactionAmount(), 0.001);
    }

    @Test
    public void testGetTotalTransactionAmountSentBy() {
        String senderFullName = "Tom Shelby";
        double expectedAmountSentBySender = 828.26;
        assertEquals(expectedAmountSentBySender, dataFetcher.getTotalTransactionAmountSentBy(senderFullName), 0.001);
    }

    @Test
    public void testGetMaxTransactionAmount() {
        double expectedMaxAmount = 985.0;
        assertEquals(expectedMaxAmount, dataFetcher.getMaxTransactionAmount(), 0.001);
    }

    @Test
    public void testCountUniqueClients() {
        long expectedUniqueClients = 14;
        assertEquals(expectedUniqueClients, dataFetcher.countUniqueClients());
    }

    @Test
    public void testHasOpenComplianceIssues() {
        String clientFullName = "Tom Shelby";
        assertTrue(dataFetcher.hasOpenComplianceIssues(clientFullName));
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        Map<String, List<Transaction>> transactionsByBeneficiaryName = dataFetcher.getTransactionsByBeneficiaryName();
        assertNotNull(transactionsByBeneficiaryName);
        assertEquals(10, transactionsByBeneficiaryName.size());
    }

    @Test
    public void testGetUnsolvedIssueIds() {
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
        assertNotNull(unsolvedIssueIds);
        assertEquals(5, unsolvedIssueIds.size());
        assertTrue(unsolvedIssueIds.contains(1));
        assertTrue(unsolvedIssueIds.contains(3));
        assertTrue(unsolvedIssueIds.contains(99));
        assertTrue(unsolvedIssueIds.contains(54));
        assertTrue(unsolvedIssueIds.contains(15));
    }

    @Test
    public void testGetAllSolvedIssueMessages() {
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
        assertNotNull(solvedIssueMessages);
        assertEquals(3, solvedIssueMessages.size());
        assertTrue(solvedIssueMessages.contains("Never gonna give you up"));
        assertTrue(solvedIssueMessages.contains("Never gonna let you down"));
        assertTrue(solvedIssueMessages.contains("Never gonna run around and desert you"));
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
        assertNotNull(top3Transactions);
        assertEquals(3, top3Transactions.size());
        assertEquals(985.0, top3Transactions.get(0).getAmount(), 0.001);
        assertEquals(666.0, top3Transactions.get(1).getAmount(), 0.001);
        assertEquals(666.0, top3Transactions.get(2).getAmount(), 0.001);
    }

    @Test
    public void testGetTopSender() {
        String expectedTopSender = "Grace Burgess";
        assertEquals(expectedTopSender, dataFetcher.getTopSender().orElse(null));
    }
}
