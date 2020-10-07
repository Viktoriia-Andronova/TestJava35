package telran.cars.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Car implements Serializable {
private String regNumber;
private String color;
private State state;
private String modelName;
private boolean inUse;
private boolean  flRemoved;
public Car() {
}
public Car(String regNumber, String color, String modelName) {
	super();
	this.regNumber = regNumber;
	this.color = color;
	this.modelName = modelName;
	state=State.EXCELLENT;
}
public State getState() {
	return state;
}
public void setState(State state) {
	this.state = state;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((regNumber == null) ? 0 : regNumber.hashCode());
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
	Car other = (Car) obj;
	if (regNumber == null) {
		if (other.regNumber != null)
			return false;
	} else if (!regNumber.equals(other.regNumber))
		return false;
	return true;
}
@Override
public String toString() {
	return "Car [regNumber=" + regNumber + ", color=" + color + ", modelName=" + modelName + ", inUse=" + inUse
			+ ", flRemoved=" + flRemoved + "]";
}
public boolean isInUse() {
	return inUse;
}
public void setInUse(boolean inUse) {
	this.inUse = inUse;
}
public boolean isFlRemoved() {
	return flRemoved;
}
public void setFlRemoved(boolean flRemoved) {
	this.flRemoved = flRemoved;
}
public String getRegNumber() {
	return regNumber;
}
public String getColor() {
	return color;
}
public String getModelName() {
	return modelName;
}

}
