package com.NagiGroup.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.NagiGroup.model.mail.EmailMessage;
import com.NagiGroup.repository.EmailThreadRepository;
import com.NagiGroup.service.EmailThreadService;
import com.NagiGroup.utility.ApiResponse;

@Service
public class EmailThreadServiceIMP implements EmailThreadService {

	private EmailThreadRepository emailThreadRepository;
	public EmailThreadServiceIMP(EmailThreadRepository emailThreadRepository) {
		this.emailThreadRepository=emailThreadRepository;
		
	}
	
	@Override
	public ApiResponse<List<EmailMessage>> getEmailMessages(Long id) {
		// TODO Auto-generated method stub
		return null	;
	}

	@Override
	public ApiResponse<List<String>> geAllThreadIdsForLoadNumber(String loadNumber,String load_recieving_date) {
		// TODO Auto-generated method stub
		return emailThreadRepository.geAllThreadIdsForLoadNumber(loadNumber,load_recieving_date);
	}
	

}
