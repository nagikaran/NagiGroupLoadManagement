package com.NagiGroup.dto.loadRevenueSunmmary;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoadRevenueSummaryDto {
	
	private Double today;
	private Double this_week;
	private Double this_month;
	private Double this_year_to_date;
	public Double getToday() {
		return today;
	}
	public void setToday(Double today) {
		this.today = today;
	}
	public Double getThis_week() {
		return this_week;
	}
	public void setThis_week(Double this_week) {
		this.this_week = this_week;
	}
	public Double getThis_month() {
		return this_month;
	}
	public void setThis_month(Double this_month) {
		this.this_month = this_month;
	}
	public Double getThis_year_to_date() {
		return this_year_to_date;
	}
	public void setThis_year_to_date(Double this_year_to_date) {
		this.this_year_to_date = this_year_to_date;
	}
	

}
