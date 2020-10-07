package telran.cars.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class RemovedCarData implements Serializable{
@Override
	public String toString() {
		return "RemovedCarData [car=" + car + ", records=" + records + "]";
	}
Car car;
List<RentRecord> records;
public RemovedCarData() {
}
public RemovedCarData(Car car, List<RentRecord> records) {
	super();
	this.car = car;
	this.records = records;
}
public Car getCar() {
	return car;
}
public List<RentRecord> getRecords() {
	return records;
}

}
