package com.NagiGroup.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.NagiGroup.connection.web.DbContextService;
import com.NagiGroup.service.MailService;
import com.NagiGroup.utility.ApiResponse;
import com.NagiGroup.utility.EmailThreadUtility;

@Repository
public class EmailThreadRepository {
	private static final Logger logger = LoggerFactory.getLogger(LoadRepository.class);
	private DbContextService dbContextserviceBms;
	private MailService mailService;

	public EmailThreadRepository(DbContextService dbContextserviceBms, MailService mailService) {
		this.dbContextserviceBms = dbContextserviceBms;
		this.mailService = mailService;
	}

	public ApiResponse<List<String>> geAllThreadIdsForLoadNumber(String loadNumber,String load_recieving_date) {

		List<String> thread_ids = null;
		try {
			logger.info("EmailThreadRepository : geAllThreadIdsForLoadNumber Start");
			Set<String> threadsForLoad = EmailThreadUtility.getThreadsForLoad(loadNumber,load_recieving_date);
			//loadDtos = dbContextserviceBms.QueryToList(QueryMaster.get_all_loads, LoadDto.class);
			thread_ids = new ArrayList<>(threadsForLoad);
			logger.info("EmailThreadRepository : geAllThreadIdsForLoadNumber end");
			return new ApiResponse<List<String>>(true, "Total Record " + thread_ids.size() + " ", true, thread_ids,
					thread_ids.size());

		} catch (Exception e) {
			logger.info("EmailThreadRepository : Exception At : geAllThreadIdsForLoadNumber :", e);
			return new ApiResponse<List<String>>(false, e.getMessage(), false, null, 0);
		}
	}

}
