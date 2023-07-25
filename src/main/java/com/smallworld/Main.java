package com.smallworld;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.smallworld.data.Transaction;

public class Main {
    public static void main(String[] args) {
        TransactionDataFetcher dataFetcher = new TransactionDataFetcher();

        double totalTransactionAmount = dataFetcher.getTotalTransactionAmount();
        System.out.println("Total Transaction Amount: " + totalTransactionAmount);

        // Get total transaction amount sent by a specific client 
        double totalAmountSentByClient = dataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        System.out.println("Total Amount Sent by Tom Shelby: " + totalAmountSentByClient);

        // Get the highest transaction amount
        double maxTransactionAmount = dataFetcher.getMaxTransactionAmount();
        System.out.println("Highest Transaction Amount: " + maxTransactionAmount);

        // Count the number of unique clients
        long uniqueClientsCount = dataFetcher.countUniqueClients();
        System.out.println("Number of Unique Clients: " + uniqueClientsCount);

        // Check if a client has open compliance issues 
        boolean hasOpenIssues = dataFetcher.hasOpenComplianceIssues("Tom Shelby");
        System.out.println("Does Tom Shelby have open compliance issues? " + hasOpenIssues);

        // Get all transactions indexed by beneficiary name
        Map<String, List<Transaction>> transactionsByBeneficiaryName = dataFetcher.getTransactionsByBeneficiaryName();
        System.out.println("Transactions indexed by beneficiary name: " + transactionsByBeneficiaryName);

        // Get the identifiers of all open compliance issues
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
        System.out.println("Unsolved Issue IDs: " + unsolvedIssueIds);

        // Get a list of all solved issue messages
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
        System.out.println("Solved Issue Messages: " + solvedIssueMessages);

        // Get the 3 transactions with the highest amount
        List<Transaction> top3TransactionsByAmount = dataFetcher.getTop3TransactionsByAmount();
        System.out.println("Top 3 Transactions by Amount: " + top3TransactionsByAmount);

        // Get the senderFullName of the sender with the most total sent amount
        Optional<String> topSender = dataFetcher.getTopSender();
        System.out.println("Sender with the most total sent amount: " + topSender.orElse("No sender found"));

    }
}
