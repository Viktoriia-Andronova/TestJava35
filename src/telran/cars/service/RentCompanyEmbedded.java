package telran.cars.service;

import telran.cars.dto.*;

import telran.util.Persistable;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static telran.cars.service.RentCompanyLocks.*;
@SuppressWarnings("serial")
public class RentCompanyEmbedded extends AbstractRentCompany
 implements Persistable{
private static final int REMOVE_THRESHOLD = 60;
private static final int BAD_THRESHOULD = 30;
private static final int GOOD_THRESHOLD = 8;
HashMap <String,Car> cars=new HashMap<>();
HashMap<Long,Driver> drivers=new HashMap<>();
HashMap<String,Model> models=new HashMap<>();
HashMap<String,List<Car>>modelCars=new HashMap<>();
HashMap<Long,List<RentRecord>>driverRecords=new HashMap<>();
HashMap<String,List<RentRecord>>carRecords=new HashMap<>();
TreeMap<LocalDate,List<RentRecord>>records=new TreeMap<>();
	@Override
	public CarsReturnCode addModel(Model model) {
		lockUnlock_addModel(true);
		try {
			return models.putIfAbsent(model.getModelName(), model) == null ? CarsReturnCode.OK
					: CarsReturnCode.MODEL_EXISTS;
		} finally {
			lockUnlock_addModel(false);
		}
	}

	

	@Override
	public Model getModel(String modelName) {
		lockUnlock_getModel(true);
		try {
			return models.get(modelName);
		} finally {
			lockUnlock_getModel(false);
		}
	}
	@Override
	public CarsReturnCode addCar(Car car) {
		lockUnlock_addCar(true);
		try {
			if (!models.containsKey(car.getModelName()))
				return CarsReturnCode.NO_MODEL;
			boolean res = cars.putIfAbsent(car.getRegNumber(), car) == null;
			if (!res)
				return CarsReturnCode.CAR_EXISTS;
			addModelCars(car);
			return CarsReturnCode.OK;
		} finally {
			lockUnlock_addCar(false);
		}
	}

	private void addModelCars(Car car) {
//		lockUnloack_addModelCars(true); error code
		try {
			String modelName = car.getModelName();
			List<Car> listCars = modelCars.getOrDefault(modelName, new ArrayList<>());
			listCars.add(car);
			modelCars.putIfAbsent(modelName, listCars);
		} finally {
	//		lockUnloack_addModelCars(false); error code
		}
		
	}

	@Override
	public Car getCar(String regNumber) {
		lockUnlock_getCar(true);
		try {
			return cars.get(regNumber);
		} finally {
			lockUnlock_getCar(false);
		}
	}
	@Override
	public CarsReturnCode addDriver(Driver driver) {
		lockUnlock_addDriver(true);
		try {
			return drivers.putIfAbsent(driver.getLicenseId(), driver) == null ? CarsReturnCode.OK
					: CarsReturnCode.DRIVER_EXISTS;
		} finally {
			lockUnlock_addDriver(false);
		}
	}

	@Override
	public Driver getDriver(long licenseId) {
		lockUnlock_getDriver(true);
		try {
			return drivers.get(licenseId);
		} finally {
			lockUnlock_getDriver(false);
		}
	}
	@Override
	public void save(String fileName) {
		try(ObjectOutputStream output=
				new ObjectOutputStream(new FileOutputStream(fileName))){
			output.writeObject(this);
		}catch(IOException e) {
			System.out.println("Error in method save "+e.getMessage());
		}
		
	}
	public static IRentCompany restoreFromFile(String fileName) {
		try(ObjectInputStream input=
				new ObjectInputStream(new FileInputStream(fileName))){
			return (IRentCompany) input.readObject();
		}catch(Exception e) {
			System.out.println(fileName+" new object has been created "+e.getMessage());
			return new RentCompanyEmbedded();
		}
	}

	@Override
	public CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
		lockUnlock_rentCar(true);
		try {
			Car car = getCar(regNumber);
			if (car == null)
				return CarsReturnCode.NO_CAR;
			if (car.isFlRemoved())
				return CarsReturnCode.CAR_REMOVED;
			if (car.isInUse())
				return CarsReturnCode.CAR_IN_USE;
			if (!drivers.containsKey(licenseId))
				return CarsReturnCode.NO_DRIVER;
			RentRecord record = new RentRecord(regNumber, licenseId, rentDate, rentDays);
			addCarRecords(record);
			addDriverRecords(record);
			addRecords(record);
			car.setInUse(true);
			return CarsReturnCode.OK;
		} finally {
			lockUnlock_rentCar(false);
		}
	}

	



	private void addRecords(RentRecord record) {
		LocalDate rentDate=record.getRentDate();
		List<RentRecord>listRecords=
				records.getOrDefault(rentDate, new ArrayList<>());
		listRecords.add(record);
		records.putIfAbsent(rentDate, listRecords);
		
	}

	private void addDriverRecords(RentRecord record) {
		long licenseId=record.getLicenseId();
		List<RentRecord>listRecords=
				driverRecords.getOrDefault(licenseId, new ArrayList<>());
		listRecords.add(record);
		driverRecords.putIfAbsent(licenseId, listRecords);
		
	}

	private void addCarRecords(RentRecord record) {
		String regNumber=record.getRegNumber();
		List<RentRecord>listRecords=
				carRecords.getOrDefault(regNumber, new ArrayList<>());
		listRecords.add(record);
		carRecords.putIfAbsent(regNumber, listRecords);
		
	}

	@Override
	public List<Car> getCarsDriver(long licenseId) {
		lockUnlock_getCarsDriver(true);
		try {
			List<RentRecord> listRecords = driverRecords.getOrDefault(licenseId, new ArrayList<>());
			return listRecords.stream().map(r -> getCar(r.getRegNumber())).distinct().collect(Collectors.toList());
		} finally {
			lockUnlock_getCarsDriver(true);
		}
	}

	@Override
	public List<Driver> getDriversCar(String regNumber) {
		lockUnlock_getDriversCar(true);
		try {
			List<RentRecord> listRecords = carRecords.getOrDefault(regNumber, new ArrayList<>());
			return listRecords.stream().map(r -> getDriver(r.getLicenseId())).distinct()
					.collect(Collectors.toList());
		} finally {
			lockUnlock_getDriversCar(false);
		}
	}

	@Override
	public List<Car> getCarsModel(String modelName) {
		lockUnlock_getCarsModel(true);
		try {
			List<Car> res = modelCars.getOrDefault(modelName, new ArrayList<>());
			return res.stream().filter(c -> !c.isFlRemoved() && !c.isInUse()).collect(Collectors.toList());
		} finally {
			lockUnlock_getCarsModel(false);
		}
	}
	@Override
	public List<RentRecord> getRentRecordsAtDates(LocalDate from, LocalDate to) {
		lockUnlock_getRentRecords(true);
		try {
			Collection<List<RentRecord>> collRecords = records.subMap(from, to).values();
			return collRecords.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
		} finally {
			lockUnlock_getRentRecords(false);
		}
	}

	



	@Override
	public RemovedCarData removeCar(String regNumber) {
		lockUnlock_removeCar(true);
		try {
			Car car = getCar(regNumber);
			if (car == null || car.isFlRemoved())
				return null;
			car.setFlRemoved(true);
			return car.isInUse() ? new RemovedCarData(car, null) : actualCarRemove(car);
		} finally {
			lockUnlock_removeCar(false);
		}
		
	}

	



	private RemovedCarData actualCarRemove(Car car) {
		car.setFlRemoved(true);
		String regNumber=car.getRegNumber();
		List<RentRecord> removedRecords=
				carRecords.get(regNumber);
		removeFromDriverRecords(removedRecords);
		removeFromRecords(removedRecords);
		cars.remove(regNumber);
		carRecords.remove(regNumber);
		return new RemovedCarData(car, removedRecords);
	}

	private void removeFromRecords(List<RentRecord> removedRecords) {
		removedRecords.forEach(this::removeRecordFromRecords);
		
	}
	
	private void removeRecordFromRecords(RentRecord r) {
		records.get(r.getRentDate()).remove(r);
		
	}

	private void removeFromDriverRecords
	(List<RentRecord> removedRecords) {
		removedRecords.forEach(this::removeRecordFromDriverRecords);
		
	}
	private void removeRecordFromDriverRecords(RentRecord r) {
		driverRecords.get(r.getLicenseId()).remove(r);
	}
	@Override
	public List<RemovedCarData> removeModel(String modelName) {
		lockUnlock_removeModel(true);
		try {
			List<Car> carsModel = modelCars.getOrDefault(modelName, new ArrayList<>());
			return carsModel.stream().filter(c -> !c.isFlRemoved()).map(c -> removeCar(c.getRegNumber()))
					.collect(Collectors.toList());
		} finally {
			lockUnlock_removeModel(false);
		}
	}

	



	@Override
	public RemovedCarData returnCar(String regNumber, long licenseId, LocalDate returnDate, int damages,
			int tankPercent) {
		lockUnlock_returnCar(true);
		try {
			RentRecord record = getRentRecord(regNumber, licenseId);
			if (record == null)
				return null;
			Car car = getCar(regNumber);
			updateRecord(record, returnDate, damages, tankPercent);
			updateCar(car, damages);
			return car.isFlRemoved() || damages > REMOVE_THRESHOLD ? actualCarRemove(car)
					: new RemovedCarData(car, null);
		} finally {
			lockUnlock_returnCar(false);
		}
	}

	



	private void updateRecord(RentRecord record, LocalDate returnDate, int damages, int tankPercent) {
		record.setDamages(damages);
		record.setTankPercent(tankPercent);
		record.setReturnDate(returnDate);
		double cost=computeCost(getRentPrice(record.getRegNumber())
				,record.getRentDays(),
				getDelay(record),tankPercent,
				getTankValume(record.getRegNumber()));
		record.setCost(cost);
		
	}

	

	private int getTankValume(String regNumber) {
		String modelName=cars.get(regNumber).getModelName();	
		return models.get(modelName).getGasTank();
	}

	private int getDelay(RentRecord record) {
		long realDays=ChronoUnit.DAYS.between(record.getRentDate(),
				record.getReturnDate());
		int delta=(int) (realDays-record.getRentDays());
		return delta<=0?0:delta;
	}

	private int getRentPrice(String regNumber) {
		String modelName=cars.get(regNumber).getModelName();
		return models.get(modelName).getPriceDay();
	}

	private RentRecord getRentRecord(String regNumber, long licenseId) {
		return driverRecords.get(licenseId)
				.stream().filter(r->r.getRegNumber().equals(regNumber)&&
				r.getReturnDate()==null).findFirst().orElse(null);
		
	}

	private void updateCar(Car car, int damages) {
		car.setInUse(false);
		if(damages>=BAD_THRESHOULD)
			car.setState(State.BAD);
		else if(damages>=GOOD_THRESHOLD)
			car.setState(State.GOOD);
		
	}

	@Override
	public List<String> getMostPopularCarModels(LocalDate fromDate, LocalDate toDate, int fromAge, int toAge) {
		lockUnlock_popularCars(true);
		try {
			List<RentRecord> lookedRecords = getRentRecordsAtDates(fromDate, toDate);
			Map<String, Long> mapOccurrences = lookedRecords.stream().filter(r -> isProperAge(r, fromAge, toAge))
					.collect(
							Collectors.groupingBy(r -> getCar(r.getRegNumber()).getModelName(), Collectors.counting()));
			long maxOccurrences = Collections.max(mapOccurrences.values());
			List<String> res = new ArrayList<>();
			mapOccurrences.forEach((k, v) -> {
				if (v == maxOccurrences)
					res.add(k);
			});
			return res;
		} finally {
			lockUnlock_popularCars(false);
		}
	}

	



	private boolean isProperAge(RentRecord r, int fromAge, int toAge) {
		LocalDate rentDate=r.getRentDate();
		Driver driver=getDriver(r.getLicenseId());
		int driverAge=rentDate.getYear()-driver.getBirthYear();
		return driverAge>=fromAge&&driverAge<toAge;
	}

	@Override
	public List<String> getMostProfitableCarModels(LocalDate fromDate, LocalDate toDate) {
		lockUnlock_popularCars(true);
		try {
			Collection<List<RentRecord>> collection = records.subMap(fromDate, toDate).values();
			if (collection == null)
				return new ArrayList<>();
			Map<String, Double> modelCost = collection.stream().flatMap(List::stream).collect(Collectors.groupingBy(
					r -> getCar(r.getRegNumber()).getModelName(), Collectors.summingDouble(RentRecord::getCost)));
			if (modelCost.isEmpty())
				return new ArrayList<>();
			double max = getMaxCost(modelCost);
			List<String> res = new ArrayList<>();
			modelCost.forEach((k, v) -> {
				if (v == max)
					res.add(k);
			});
			return res;
		} finally {
			lockUnlock_popularCars(false);
		}
	}

	private double getMaxCost(Map<String, Double> modelCost) {
		return modelCost.values().stream()
				.mapToDouble(x->x).max().getAsDouble();
	}

	@Override
	public List<Driver> getMostActiveDrivers() {
		lockUnlock_activeDrivers(true);
		try {
			Collection<List<RentRecord>> collection = records.values();
			if (collection == null)
				return new ArrayList<>();
			Map<Long, Long> licenseCount = collection.stream().flatMap(List::stream)
					.collect(Collectors.groupingBy(RentRecord::getLicenseId, Collectors.counting()));
			if (licenseCount.isEmpty())
				return new ArrayList<>();
			long max = getMaxCountLicense(licenseCount);
			List<Driver> res = new ArrayList<>();
			licenseCount.forEach((k, v) -> {
				if (v == max)
					res.add(getDriver(k));
			});
			return res;
		} finally {
			lockUnlock_activeDrivers(false);
		}
	}

	private long getMaxCountLicense(Map<Long, Long> licenseCount) {
		
		return licenseCount.values().stream().mapToLong(x->x).max().getAsLong();
	}

	@Override
	public List<String> getModelNames() {
		lockUnlock_getModelNames(true);
		try {
			return new ArrayList<>(models.keySet());
		} finally {
			lockUnlock_getModelNames(false);
		}
	}

}
