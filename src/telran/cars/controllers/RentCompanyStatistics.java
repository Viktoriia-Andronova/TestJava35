package telran.cars.controllers;

import static telran.cars.api.ApiConstants.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
//String GET_MOST_ACTIVE_DRIVERS="/drivers/active"; //GET+
//String GET_MOST_POPULAR_MODELS="/models/popular"; //GET+
//String GET_MOST_PROFITABLE_MODELS="/models/profitable"; //GET+
//String GET_RECORDS="/records"; //GET+
import org.springframework.web.bind.annotation.RestController;

import telran.cars.dto.Driver;
import telran.cars.dto.RentRecord;
import telran.cars.service.IRentCompany;

@RestController
public class RentCompanyStatistics {
	
	@Autowired
	IRentCompany company;
	@GetMapping(value=GET_MOST_POPULAR_MODELS)
	List<String> getMostPopularModels(@RequestParam(name=DATE_FROM)
	String dateFrom, @RequestParam(name=DATE_TO)
	String dateTo,@RequestParam(name=AGE_FROM) int ageFrom,
	@RequestParam(name=AGE_TO) int ageTo) 
	{
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		return company.getMostPopularCarModels(from, to, ageFrom, ageTo);
	}
	@GetMapping(value=GET_MOST_PROFITABLE_MODELS)
	List<String> getMostProfitableModels(@RequestParam(name=DATE_FROM)
	String dateFrom, @RequestParam(name=DATE_TO)
	String dateTo) 
	{
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		return company.getMostProfitableCarModels(from, to);
	}
	@GetMapping(value=GET_MOST_ACTIVE_DRIVERS)
	List<Driver> getMostActiveDrivers() 
	{
		return company.getMostActiveDrivers();
	}
	@GetMapping(value=GET_RECORDS)
	List<RentRecord> getRecords(@RequestParam(name=DATE_FROM)
	String dateFrom, @RequestParam(name=DATE_TO)
	String dateTo) 
	{
		LocalDate from = LocalDate.parse(dateFrom);
		LocalDate to = LocalDate.parse(dateTo);
		return company.getRentRecordsAtDates(from, to);
	}
}
