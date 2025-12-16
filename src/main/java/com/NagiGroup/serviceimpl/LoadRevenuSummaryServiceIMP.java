package com.NagiGroup.serviceimpl;

import org.springframework.stereotype.Service;

import com.NagiGroup.dto.loadRevenueSunmmary.LoadRevenueSummaryDto;
import com.NagiGroup.repository.LoadRevenuSummaryRepository;
import com.NagiGroup.service.LoadRevenuSummaryService;
import com.NagiGroup.utility.ApiResponse;
@Service
public class LoadRevenuSummaryServiceIMP implements LoadRevenuSummaryService {

	private LoadRevenuSummaryRepository loadRevenuSummaryRepository ;
	
	public LoadRevenuSummaryServiceIMP(LoadRevenuSummaryRepository loadRevenuSummaryRepository) {
		this.loadRevenuSummaryRepository=loadRevenuSummaryRepository;
	}
	
	@Override
	public ApiResponse<LoadRevenueSummaryDto> getloadRevenuSummary() {
		// TODO Auto-generated method stub
		return loadRevenuSummaryRepository.getloadRevenuSummary();
	}

}
