package telran.cars.dto;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class RentRecord implements Serializable {
private String regNumber;
private long licenseId;
private LocalDate rentDate;
private LocalDate returnDate;
private int damages;
private int tankPercent;
private double cost;
private int rentDays;
public RentRecord() {
}
public RentRecord(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
	super();
	this.regNumber = regNumber;
	this.licenseId = licenseId;
	this.rentDate = rentDate;
	this.rentDays = rentDays;
}
public LocalDate getReturnDate() {
	return returnDate;
}
public void setReturnDate(LocalDate returnDate) {
	this.returnDate = returnDate;
}
public int getDamages() {
	return damages;
}
public void setDamages(int damages) {
	this.damages = damages;
}
public int getTankPercent() {
	return tankPercent;
}
public void setTankPercent(int tankPercent) {
	this.tankPercent = tankPercent;
}
public double getCost() {
	return cost;
}
public void setCost(double cost) {
	this.cost = cost;
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
@Override
public String toString() {
	return "RentRecord [regNumber=" + regNumber + ", licenseId=" + licenseId + ", rentDate=" + rentDate
			+ ", returnDate=" + returnDate + ", damages=" + damages + ", tankPercent=" + tankPercent + ", cost=" + cost
			+ ", rentDays=" + rentDays + "]";
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(cost);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + damages;
	result = prime * result + (int) (licenseId ^ (licenseId >>> 32));
	result = prime * result + ((regNumber == null) ? 0 : regNumber.hashCode());
	result = prime * result + ((rentDate == null) ? 0 : rentDate.hashCode());
	result = prime * result + rentDays;
	result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
	result = prime * result + tankPercent;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	RentRecord other = (RentRecord) obj;
	if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
		return false;
	if (damages != other.damages)
		return false;
	if (licenseId != other.licenseId)
		return false;
	if (regNumber == null) {
		if (other.regNumber != null)
			return false;
	} else if (!regNumber.equals(other.regNumber))
		return false;
	if (rentDate == null) {
		if (other.rentDate != null)
			return false;
	} else if (!rentDate.equals(other.rentDate))
		return false;
	if (rentDays != other.rentDays)
		return false;
	if (returnDate == null) {
		if (other.returnDate != null)
			return false;
	} else if (!returnDate.equals(other.returnDate))
		return false;
	if (tankPercent != other.tankPercent)
		return false;
	return true;
}

}
