package com.NagiGroup.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.NagiGroup.connection.web.DbContextService;
import com.NagiGroup.dto.load.LoadStatusSummaryDto;
import com.NagiGroup.dto.loadRevenueSunmmary.LoadRevenueSummaryDto;
import com.NagiGroup.query.QueryMaster;
import com.NagiGroup.service.MailService;
import com.NagiGroup.utility.ApiResponse;

@Repository
public class LoadRevenuSummaryRepository {
	private static final Logger logger = LoggerFactory.getLogger(LoadRepository.class);

	private DbContextService dbContextserviceBms;
	private MailService mailService;

	public LoadRevenuSummaryRepository(DbContextService dbContextserviceBms, MailService mailService) {
		this.dbContextserviceBms = dbContextserviceBms;
		this.mailService = mailService;
	}

	public ApiResponse<LoadRevenueSummaryDto> getloadRevenuSummary() {
		// TODO Auto-generated method stub
		logger.info("LoadRepository : getLoadById Start");
		LoadRevenueSummaryDto loadRevenueSummaryDto = null;
		try {

			loadRevenueSummaryDto = dbContextserviceBms.QueryToFirst(QueryMaster.get_load_revenue_summary,
					LoadRevenueSummaryDto.class);
			logger.info("LoadRepository : getLoadById End");
			return new ApiResponse<LoadRevenueSummaryDto>(true, "Total Record " + 1 + " ", true, loadRevenueSummaryDto,
					1);
		} catch (Exception e) {
			logger.info("LoadRepository : Exception At : getLoadById :", e);
			return new ApiResponse<LoadRevenueSummaryDto>(false, "No record found", false, null, 0);
		}
	}
}
