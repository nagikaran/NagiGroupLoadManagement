package com.NagiGroup.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NagiGroup.service.LoadPaymentCheckerService;

@RestController
@RequestMapping(value="/api/load_payment_check")
public class LoadPaymentCheckerController {
	@Autowired
	public LoadPaymentCheckerService loadPaymentCheckerService;
	
	  @GetMapping("/check-payments")
	    public String checkPayments() {
	        try {
	        	loadPaymentCheckerService.checkAndHighlightPayments();
	            return "✅ Payment check completed!";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "❌ Error: " + e.getMessage();
	        }
	    }

}
