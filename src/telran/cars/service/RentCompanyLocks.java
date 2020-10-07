package telran.cars.service;

import java.util.Arrays;
import java.util.concurrent.locks.*;

public class RentCompanyLocks {
static final ReadWriteLock carsLock=new ReentrantReadWriteLock();
static final ReadWriteLock driversLock=new ReentrantReadWriteLock();
static final ReadWriteLock modelsLock=new ReentrantReadWriteLock();
static final ReadWriteLock recordsLock=new ReentrantReadWriteLock();
static final int CARS_INDEX=0;
static final int DRIVERS_INDEX=1;
static final int MODELS_INDEX=2;
static final int RECORDS_INDEX=3;
static Lock[][] locks=new Lock[2][4];
static final int WRITE = 0;
static final int READ = 1;
static final boolean flLock = true;
static {
	ReadWriteLock[] readWriteLocks= {
		carsLock,driversLock,modelsLock,recordsLock	
	};
	fillLocksWrite(readWriteLocks);
	fillLocksRead(readWriteLocks);
}
private static void fillLocksWrite(ReadWriteLock[] readWriteLocks) {
	for(int i=0; i<readWriteLocks.length; i++) {
		locks[WRITE][i]=readWriteLocks[i].writeLock();
	}
}
private static void fillLocksRead(ReadWriteLock[] readWriteLocks) {
	for(int i=0; i<readWriteLocks.length; i++) {
		locks[READ][i]=readWriteLocks[i].readLock();
	}
	
}
private static void lockUnlock(boolean flLock, int typeLock, int ...indexes) {
	if(flLock)
		lock(typeLock,indexes);
	else 
		unlock(typeLock,indexes);
}
private static void unlock(int typeLock,int[] indexes) {
	Arrays.sort(indexes);
	for(int index: indexes)
		locks[typeLock][index].unlock();
	
}
private static void lock(int typeLock,int[] indexes) {
	Arrays.sort(indexes);
	for(int index: indexes)
		locks[typeLock][index].lock();
	
}
public static void lockUnlock_addModel(boolean flLock) {
	lockUnlock(flLock,WRITE,MODELS_INDEX);
	
}
public static void lockUnlock_save(boolean flLock) {
	lockUnlock(flLock,READ,0,1,2,3);
	
}
public static void lockUnlock_rentCar(boolean flLock) {
	lockUnlock(flLock,READ,DRIVERS_INDEX);
	lockUnlock(flLock,WRITE,CARS_INDEX,RECORDS_INDEX);
	
}
public static void lockUnlock_getModel(boolean flLock) {
	lockUnlock(flLock,READ,MODELS_INDEX);	
}
static void lockUnlock_addCar(boolean flLock) {
//lockUnlock(flLock,READ,MODELS_INDEX); error code
	lockUnlock(flLock,WRITE,CARS_INDEX,MODELS_INDEX);
}

static void lockUnlock_addDriver(boolean flLock) {
	lockUnlock(flLock,WRITE,DRIVERS_INDEX);
	
}
static void lockUnlock_getDriver(boolean flLock) {
	lockUnlock(flLock,READ,DRIVERS_INDEX);
	
}
static void lockUnlock_getCarsDriver(boolean flLock) {
	lockUnlock(flLock,READ,RECORDS_INDEX);
}
static void lockUnlock_getCar(boolean flLock) {
	lockUnlock(flLock,READ,CARS_INDEX);
	
}
static void lockUnlock_getDriversCar(boolean flLock) {
	lockUnlock(flLock,READ,RECORDS_INDEX);
	
}
static void lockUnlock_getCarsModel(boolean flLock) {
	lockUnlock(flLock,READ,CARS_INDEX,MODELS_INDEX);
	
}
static void lockUnlock_getRentRecords(boolean flLock) {
	lockUnlock(flLock,READ,RECORDS_INDEX);
}
static void lockUnlock_removeCar(boolean flLock) {
	lockUnlock(flLock,WRITE,CARS_INDEX,MODELS_INDEX,RECORDS_INDEX                      );
	
}
static void lockUnlock_removeModel(boolean flLock) {
	lockUnlock(flLock,WRITE,CARS_INDEX,MODELS_INDEX,RECORDS_INDEX);
}
static void lockUnlock_returnCar(boolean flLock) {
	lockUnlock(flLock,WRITE,CARS_INDEX,MODELS_INDEX,RECORDS_INDEX);
	
}
static void lockUnlock_activeDrivers(boolean flLock) {
	lockUnlock(flLock,READ,DRIVERS_INDEX, RECORDS_INDEX);
	
}
static void lockUnlock_getModelNames(boolean flLock) {
	lockUnlock(flLock,READ,CARS_INDEX,MODELS_INDEX);
	
}
static void lockUnlock_popularCars(boolean flLock) {
	lockUnlock(flLock,READ,CARS_INDEX,MODELS_INDEX,RECORDS_INDEX);
	
}
}
