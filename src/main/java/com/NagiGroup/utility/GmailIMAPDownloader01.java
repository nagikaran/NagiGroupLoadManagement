package com.NagiGroup.utility;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.search.BodyTerm;
import jakarta.mail.search.OrTerm;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SubjectTerm;

public class GmailIMAPDownloader01 {

    public static void downloadPdfAttachmentsByLoadNumbers(String email, String appPassword, List<String> loadNumbers, String downloadDir) throws Exception {
        // Setup Gmail IMAP properties
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");

        // Establish mail session and store connection
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, appPassword);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        for (String loadNumber : loadNumbers) {
            SearchTerm subjectTerm = new SubjectTerm(loadNumber);
            SearchTerm bodyTerm = new BodyTerm(loadNumber);
            SearchTerm searchTerm = new OrTerm(subjectTerm, bodyTerm);

            Message[] messages = inbox.search(searchTerm);
            System.out.println("Found " + messages.length + " messages for load number: " + loadNumber);

            int fileCounter = 1;

            for (Message message : messages) {
                // Skip if subject or sender contains "TriumphPay"
                String subject = message.getSubject() != null ? message.getSubject().toLowerCase() : "";
                Address[] fromAddresses = message.getFrom();
                String fromEmail = (fromAddresses != null && fromAddresses.length > 0)
                        ? fromAddresses[0].toString().toLowerCase()
                        : "";

                if (subject.contains("triumphpay") || fromEmail.contains("triumphpay")) {
                    System.out.println("Skipped TriumphPay email for load number: " + loadNumber);
                    continue;
                }

                if (message.isMimeType("multipart/*")) {
                    Multipart multipart = (Multipart) message.getContent();

                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart part = multipart.getBodyPart(i);

                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())
                                && part.getFileName().toLowerCase().endsWith(".pdf")) {

                            InputStream is = part.getInputStream();

                            // Build file name
                            String fileName = loadNumber + "_roc";
                            if (fileCounter > 1) {
                                fileName += "_" + fileCounter;
                            }
                            fileName += ".pdf";

                            File file = new File(downloadDir + File.separator + fileName);
                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                byte[] buf = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = is.read(buf)) != -1) {
                                    fos.write(buf, 0, bytesRead);
                                }
                            }

                            System.out.println("Saved attachment: " + file.getAbsolutePath());
                            fileCounter++;
                        }
                    }
                }
            }
        }

        inbox.close(false);
        store.close();
    }

    public static void main(String[] args) throws Exception {
//        String email = ""; // <-- Add your email here
//        String appPassword = ""; // <-- Add your app-specific password here
    	String email = "nagigroup0076@gmail.com";
        String appPassword = "ueyn hepa zarp cmjt";
//        List<String> loadNumbers = List.of("3591611", "504308052");
        List<String> loadNumbers = List.of(
        		"515816399",
        		"515581114",
        		"32477690",
        		"0080213",
        		"516702267",
        		"LD7117RV",
        		"515581116",
        		"1002983",
        		"3692400",
        		"1000136",
        		"8577002",
        		"517471149",
        		"LD7179RV",
        		"512 012649",
        		"19195911",
        		"LD7180RV",
        		"LD7192RV",
        		"516483927",
        		"516483494",
        		"516333966",
        		"1007237",
        		"516483570",
        		"1007239",
        		"516333976",
        		"3695440",
        		"32686779",
        		"32652286",
        		"32718563",
        		"1228494",
        		"518539637",
        		"516483851",
        		"516483561",
        		"512 012976",
        		"19305357",
        		"516514255",
        		"518266030",
        		"LD7239RV",
        		"519007148",
        		"19349395",
        		"516514493",
        		"516514426",
        		"516514516",
        		"78816498",
        		"5005556",
        		"1230259",
        		"L250624-00427",
        		"32833159",
        		"519801305"

        	);

        String downloadDir = "D:/roc_docs01";
         downloadDir = "C:/NAGI_GROUP/DOCUMENTS_2025/JUNE/David/DISPATCH RECORD";


        File directory = new File(downloadDir);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
                return;
            }
        }

        downloadPdfAttachmentsByLoadNumbers(email, appPassword, loadNumbers, downloadDir);
    }
}
