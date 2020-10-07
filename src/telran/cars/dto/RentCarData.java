package telran.cars.dto;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")

public class RentCarData implements Serializable {
	String regNumber;
	long licenseId;
	LocalDate rentDate;
	int rentDays;
	public RentCarData() {
		
	}
	public RentCarData(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
		super();
		this.regNumber = regNumber;
		this.licenseId = licenseId;
		this.rentDate = rentDate;
		this.rentDays = rentDays;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public long getLicenseId() {
		return licenseId;
	}
	public LocalDate getRentDate() {
		return rentDate;
	}
	public int getRentDays() {
		return rentDays;
	}
	
}
