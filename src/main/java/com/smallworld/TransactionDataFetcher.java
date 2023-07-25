package com.smallworld;

import com.smallworld.data.Transaction;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;


public class TransactionDataFetcher {

    private List<Transaction> transactions;

    public TransactionDataFetcher() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            transactions = objectMapper.readValue(new File("transactions.json"), new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }
    

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        return transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
    }
    

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
    return transactions.stream()
            .flatMap(transaction -> Stream.of(transaction.getSenderFullName(), transaction.getBeneficiaryFullName()))
            .distinct()
            .count();
}


    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        return transactions.stream()
                .anyMatch(transaction -> 
                    transaction.getSenderFullName().equals(clientFullName) ||
                    transaction.getBeneficiaryFullName().equals(clientFullName)
                && !transaction.isIssueSolved());
    }
    

    /**
     * Returns all transactions indexed by beneficiary name
     */

    // changed the return type from  "Map<String, Transaction>" to "Map<String, List<Transaction>>" as one beneficiary can have multiple transactions 
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }
    


    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        return transactions.stream()
                .filter(transaction -> !transaction.isIssueSolved())
                .map(Transaction::getIssueId)
                .collect(Collectors.toSet());
    }
    

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        return transactions.stream()
                .filter(transaction -> transaction.isIssueSolved() && transaction.getIssueMessage() != null)
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());
    }
    

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
    return transactions.stream()
            .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
            .limit(3)
            .collect(Collectors.toList());
}


    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        Map<String, Double> senderToTotalAmountMap = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)));
    
        return senderToTotalAmountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
    

}
