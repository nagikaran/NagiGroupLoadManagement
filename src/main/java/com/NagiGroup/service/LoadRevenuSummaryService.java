package com.NagiGroup.service;

import com.NagiGroup.dto.loadRevenueSunmmary.LoadRevenueSummaryDto;
import com.NagiGroup.utility.ApiResponse;

public interface LoadRevenuSummaryService {

	ApiResponse<LoadRevenueSummaryDto> getloadRevenuSummary();

}
