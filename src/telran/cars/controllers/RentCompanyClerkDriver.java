package telran.cars.controllers;
//String ADD_DRIVER="/driver/add"; //POST +
//String GET_MODEL="/model"; //GET +
//String GET_DRIVER_CARS="/driver/cars"; //GET+
//String GET_CAR_MODELS="/models"; //GET+
//String GET_MODEL_CARS="/model/cars"; //GET+
//String RENT_CAR="/car/rent"; //POST
//String RETURN_CAR="/car/return"; //POST+
//String GET_CAR_DRIVERS="/drivers/car"; //GET+
//String GET_CAR="/car"; //GET +
//String GET_DRIVER="/driver"; //GET

import static telran.cars.api.ApiConstants.ADD_DRIVER;
import static telran.cars.api.ApiConstants.GET_CAR_DRIVERS;
import static telran.cars.api.ApiConstants.GET_CAR_MODELS;
import static telran.cars.api.ApiConstants.GET_DRIVER;
import static telran.cars.api.ApiConstants.GET_DRIVER_CARS;
import static telran.cars.api.ApiConstants.GET_MODEL_CARS;
import static telran.cars.api.ApiConstants.RENT_CAR;
import static telran.cars.api.ApiConstants.RETURN_CAR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telran.cars.api.*;
import telran.cars.dto.*;
import telran.cars.service.IRentCompany;

@RestController
public class RentCompanyClerkDriver {
@Autowired
IRentCompany company;

@GetMapping(value=ApiConstants.GET_MODEL)
Model getModel
(@RequestParam(name=ApiConstants.MODEL_NAME) String modelName) {
	return company.getModel(modelName);
}
@GetMapping(value=ApiConstants.GET_CAR) 
Car getCar
(@RequestParam(name=ApiConstants.CAR_NUMBER) String regNumber ) {
	return company.getCar(regNumber);
}
@PostMapping(value=ADD_DRIVER)
CarsReturnCode addDriver(@RequestBody Driver driver) {
	return company.addDriver(driver);
}
@GetMapping(value=GET_CAR_DRIVERS)
List<Driver> getCarDrivers(@RequestParam(name=ApiConstants.CAR_NUMBER)
String regNumber) {
	return company.getDriversCar(regNumber);
}
@GetMapping(value=GET_DRIVER_CARS)
List<Car> getDriverCars(@RequestParam(name=ApiConstants.LICENSE_ID)
long licenseId) {
	return company.getCarsDriver(licenseId);
}
@GetMapping(value=GET_MODEL_CARS)
List<Car> getModelCars(@RequestParam(name=ApiConstants.MODEL_NAME)
String modelName) {
	return company.getCarsModel(modelName);
}
@GetMapping(value=GET_CAR_MODELS)
List<String> getModelNames() {
	return company.getModelNames();
}
@PostMapping(value=RETURN_CAR)
RemovedCarData returnCar(@RequestBody ReturnCarData rcd) {
	return company.returnCar
			(rcd.getRegNumber(), rcd.getLicenseId(),
					rcd.getReturnDate(), rcd.getDamages(),
					rcd.getTankPercent());
}
@PostMapping(value=RENT_CAR)
CarsReturnCode rentCar(@RequestBody RentCarData rcd) {
	return company.rentCar
			(rcd.getRegNumber(), rcd.getLicenseId(),
					rcd.getRentDate(), rcd.getRentDays());
}
@GetMapping(value=GET_DRIVER)
Driver getCar(@RequestParam(name=ApiConstants.LICENSE_ID)
long licenseId) {
	return company.getDriver(licenseId);
}
}
