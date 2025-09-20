package com.NagiGroup.conroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NagiGroup.service.EmailThreadService;
import com.NagiGroup.utility.ApiResponse;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.google.api.services.gmail.model.Thread;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/threads")
public class EmailThreadController {
	@Autowired
	public EmailThreadService emailThreadService;
	@Autowired
	private final Gmail gmail;
	public EmailThreadController(Gmail gmail) {
		this.gmail=gmail;
		
	}
	
	@GetMapping("/get_thread_ids/{loadNumber}/{load_recieving_date}")
	@Operation(summary = "function = get_all_thread_ids_for_load_number")
	public ApiResponse<List<String>> geAllThreadIdsForLoadNumber(@PathVariable String loadNumber,@PathVariable String load_recieving_date) {
		
		return emailThreadService.geAllThreadIdsForLoadNumber(loadNumber,load_recieving_date);
	}
	
	
	
	
//	@GetMapping("/{id}/messages")
//	@Operation(summary = "function = get_email_messages")
//    public ApiResponse<List<EmailMessage>> getMessages(@PathVariable Long id){
//		return emailThreadService.getEmailMessages(id);
//		
//        Optional<EmailThread> t = threadRepo.findById(id);
//        if (!t.isPresent()) return List.of();
//        return msgRepo.findAll().stream().filter(m->m.getThread()!=null && m.getThread().getId().equals(id)).toList();
//    }
//	@PostMapping("/{id}/messages")
//    public EmailMessage sendMessage(@PathVariable Long id, @RequestBody EmailMessage incoming){
//		EmailThread thread = threadRepo.findById(id).orElseGet(()->{
//            ThreadEntity nt = new ThreadEntity();
//            nt.setSubject(incoming.getThread()!=null?incoming.getThread().getSubject():"(no-subject)");
//            return threadRepo.save(nt);
//        });
//        incoming.setThread(thread);
//        incoming.setDirection("outbound");
//        // send email (simple): pick recipient from senderEmail field for demo
//        emailService.sendEmailReply(incoming.getSenderEmail(), thread.getSubject(), incoming.getBody(), null);
//        EmailMessage saved = msgRepo.save(incoming);
//        template.convertAndSend("/topic/thread."+thread.getId(), saved);
//        return saved;
//    }
	
	
	
	
	@GetMapping("/thread/{threadId}")
    public Map<String, Object> getThread(@PathVariable String threadId) throws IOException {
        Map<String, Object> response = new HashMap<>();

        // Fetch full thread
        Thread thread = gmail.users().threads().get("me", threadId).execute();
        response.put("threadId", thread.getId());
        response.put("messageCount", thread.getMessages().size());

        List<Map<String, Object>> messages = new ArrayList<>();
        for (Message msg : thread.getMessages()) {
            MessagePart payload = msg.getPayload();

            Map<String, Object> messageData = new HashMap<>();
            messageData.put("subject", getHeader(payload.getHeaders(), "Subject"));
            messageData.put("from", getHeader(payload.getHeaders(), "From"));
            messageData.put("to", getHeader(payload.getHeaders(), "To"));
            messageData.put("date", getHeader(payload.getHeaders(), "Date"));
            messageData.put("body", getMessageBody(payload));

            // 👉 Check if attachments exist
            List<String> attachments = new ArrayList<>();
            if (payload.getParts() != null) {
                for (MessagePart part : payload.getParts()) {
                    if (part.getFilename() != null && !part.getFilename().isEmpty()) {
                        attachments.add(part.getFilename());
                    }
                }
            }
            messageData.put("attachments", attachments);

            messages.add(messageData);
        }

        response.put("messages", messages);
        return response;
    }

    private String getHeader(List<MessagePartHeader> headers, String name) {
        return headers.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .map(MessagePartHeader::getValue)
                .findFirst()
                .orElse("");
    }

    private String getMessageBody(MessagePart payload) {
        if (payload == null) return "";
        if (payload.getBody() != null && payload.getBody().getData() != null) {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(payload.getBody().getData());
            return new String(decodedBytes);
        }
        if (payload.getParts() != null) {
            for (MessagePart part : payload.getParts()) {
                if (part.getMimeType().equals("text/plain") || part.getMimeType().equals("text/html")) {
                    byte[] decodedBytes = Base64.getUrlDecoder().decode(part.getBody().getData());
                    return new String(decodedBytes);
                }
            }
        }
        return "";
    }
	
}
