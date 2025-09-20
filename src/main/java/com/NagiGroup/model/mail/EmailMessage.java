package com.NagiGroup.model.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailMessage {
	private Long id;
    private Long threadId; // foreign key to EmailThread
    private String senderEmail;
    private String subject; // optional if you want per-message subject
    private String body;
    private String direction; // inbound/outbound
    private String messageId;
    private String inReplyTo;
}
