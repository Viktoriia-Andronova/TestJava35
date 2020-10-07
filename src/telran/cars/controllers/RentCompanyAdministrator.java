package telran.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static telran.cars.api.ApiConstants.*;
//String ADD_CAR="/car/add"; //POST
//String ADD_MODEL="/model/add"; //POST
//String REMOVE_CAR="/car/remove"; //DELETE
//String REMOVE_MODEL="/model/remove"; //DELETE

import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.web.bind.annotation.RestController;

import telran.cars.dto.*;
import telran.cars.service.IRentCompany;
import telran.util.Persistable;

@RestController
public class RentCompanyAdministrator {
@Autowired
IRentCompany company;
@Value("${fileName:company.data}")
String fileName;
@Value("${finePercent:15}")
private int finePercent;
@Value("${gasPrice:10}")
private int price;
@PostMapping(value=ADD_CAR) 
CarsReturnCode addCar(@RequestBody Car car) {
	return company.addCar(car);
}
@PostMapping(value=ADD_MODEL)
CarsReturnCode addModel(@RequestBody Model model) {
	return company.addModel(model);
}
@DeleteMapping(value=REMOVE_CAR)
RemovedCarData removeCar(@RequestParam(name=CAR_NUMBER)
String regNumber) {
	return company.removeCar(regNumber);
}
@DeleteMapping(value=REMOVE_MODEL)
List<RemovedCarData> removeModel
(@RequestParam(name=MODEL_NAME) String modelName) {
	return company.removeModel(modelName);
}
@PreDestroy 
public void saveCompanyToFile() {
	if(company instanceof Persistable) {
		((Persistable)company).save(fileName);
	}
}
@PostConstruct 
public void settings() {
	company.setFinePercent(finePercent);
	company.setGasPrice(price);
}
}
