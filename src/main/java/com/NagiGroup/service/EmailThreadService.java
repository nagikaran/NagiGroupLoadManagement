package com.NagiGroup.service;

import java.util.List;

import com.NagiGroup.model.mail.EmailMessage;
import com.NagiGroup.utility.ApiResponse;

public interface EmailThreadService {

	ApiResponse<List<EmailMessage>> getEmailMessages(Long id);

	ApiResponse<List<String>> geAllThreadIdsForLoadNumber(String loadNumber,String load_recieving_date);

}
