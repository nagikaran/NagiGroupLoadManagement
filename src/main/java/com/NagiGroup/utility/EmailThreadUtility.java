package com.NagiGroup.utility;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.NagiGroup.config.GmailConfig;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Thread;
import java.util.Base64;


public class EmailThreadUtility {
	
	public static Set<String> getThreadsForLoad(String load_number,String load_recieving_date) {
       // List<String> threadIds = new ArrayList<>();
        Set<String> threadIds = new HashSet<>();
        try {
            Gmail service = GmailConfig.getService();
            LocalDate startDate = LocalDate.parse(load_recieving_date);
            LocalDate endDate = startDate.plusDays(30);
         // Convert LocalDate → LocalDateTime at start of day
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();

            // Convert to Instant (UTC) → Epoch seconds
            long afterEpoch = startDateTime.toEpochSecond(ZoneOffset.UTC);
            long beforeEpoch = endDateTime.toEpochSecond(ZoneOffset.UTC);
            
            

            // search query -> e.g. subject or body contains load_number
          //  String query = "subject:" + load_number + " OR \"" + load_number + "\"";
            String query = "subject:\"" + load_number + "\" after:" + afterEpoch + " before:" + beforeEpoch;


            ListMessagesResponse response = service.users().messages().list("me")
                    .setQ(query) // Gmail search query
                    .setMaxResults(50L) 
                    .execute();

            if (response.getMessages() != null) {
                for (Message message : response.getMessages()) {
                    // fetch message to get threadId
                    Message fullMessage = service.users().messages().get("me", message.getId()).execute();
                    threadIds.add(fullMessage.getThreadId());
                }
            }
          
            

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return threadIds;
    }
	
	
	public static Map<String, Object> getThreadDetails(Gmail gmail, String threadId) throws IOException {
        Map<String, Object> threadData = new HashMap<>();

        // Fetch thread
        Thread thread = gmail.users().threads().get("me", threadId).execute();

        if (thread.getMessages() == null || thread.getMessages().isEmpty()) {
            return threadData; // no messages
        }

        // Extract subject from the first message
        Message firstMessage = thread.getMessages().get(0);
        String subject = firstMessage.getPayload().getHeaders().stream()
                .filter(h -> h.getName().equalsIgnoreCase("Subject"))
                .findFirst()
                .map(h -> h.getValue())
                .orElse("(No Subject)");

        threadData.put("threadId", threadId);
        threadData.put("subject", subject);

        // List to hold messages
        List<Map<String, String>> messageList = new ArrayList<>();

        for (Message msg : thread.getMessages()) {
            Map<String, String> msgData = new HashMap<>();

            String from = msg.getPayload().getHeaders().stream()
                    .filter(h -> h.getName().equalsIgnoreCase("From"))
                    .map(h -> h.getValue())
                    .findFirst()
                    .orElse("Unknown Sender");

            String snippet = msg.getSnippet();
            String messageId = msg.getId();

            // Extract Date
            String date = msg.getPayload().getHeaders().stream()
                    .filter(h -> h.getName().equalsIgnoreCase("Date"))
                    .map(h -> h.getValue())
                    .findFirst()
                    .orElse(null);

            // Extract Body (simplified: just first text/plain part)
            String body = EmailThreadUtility.getMessageBody(msg);

            msgData.put("messageId", messageId);
            msgData.put("from", from);
            msgData.put("snippet", snippet);
            msgData.put("date", date);
            msgData.put("body", body);

            messageList.add(msgData);
        }

        threadData.put("messages", messageList);
        return threadData;
    }

    // Helper to extract plain text body
    private static String getMessageBody(Message message) {
        try {
            if (message.getPayload().getParts() != null) {
                return new String(
                        Base64.getUrlDecoder().decode(
                                message.getPayload().getParts().get(0).getBody().getData()
                        )
                );
            } else if (message.getPayload().getBody() != null && message.getPayload().getBody().getData() != null) {
                return new String(Base64.getUrlDecoder().decode(message.getPayload().getBody().getData()));
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
	
	

}
