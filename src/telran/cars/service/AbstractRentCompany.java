package telran.cars.service;

//import telran.cars.dto.Car;
//import telran.cars.dto.CarsReturnCode;
//import telran.cars.dto.Driver;
//import telran.cars.dto.Model;

@SuppressWarnings("serial")
public abstract class AbstractRentCompany implements IRentCompany {
protected int finePercent;
protected int gasPrice;
public AbstractRentCompany() {
	gasPrice=10;
	finePercent=15;
}
	@Override
	public int getGasPrice() {
		return gasPrice;
	}

	@Override
	public void setGasPrice(int price) {
		gasPrice=price;

	}

	@Override
	public int getFinePercent() {
		return finePercent;
	}

	@Override
	public void setFinePercent(int finePercent) {
		this.finePercent=finePercent;
	}
	protected double computeCost(int rentPrice, int rentDays, int delay, int tankPercent, int tankValume) {
		double cost=rentPrice*rentDays;
		if(delay>0)
			cost+=additionalDelayCost(delay,rentPrice);
		if(tankPercent<100)
			cost+=additionalGasCost(tankPercent,tankValume);
		
		return cost;
	}
	private double additionalGasCost(int tankPercent,int gasTank) {
		
		return gasTank*((double)(100-tankPercent)/100)*gasPrice;
	}
	private double additionalDelayCost(int delay,int pricePerDay) {
		
		return delay*(pricePerDay*(1+(double)finePercent/100));
	}
	
}
