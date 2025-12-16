package com.NagiGroup.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NagiGroup.dto.loadRevenueSunmmary.LoadRevenueSummaryDto;
import com.NagiGroup.service.LoadRevenuSummaryService;
import com.NagiGroup.utility.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value="/api/revenue_summary")
public class LoadRevenueSummaryController {

	@Autowired
	public LoadRevenuSummaryService loadRevenuSummaryService ;
	
	@GetMapping("/get")
	@Operation(summary = "function = get_load_by_id")
	public ApiResponse<LoadRevenueSummaryDto> getloadRevenuSummary() {
		
		return loadRevenuSummaryService.getloadRevenuSummary();
	}
	
	
}
