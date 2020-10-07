package telran.cars.dto;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class ReturnCarData implements Serializable {

String regNumber;
long licenseId;
			LocalDate returnDate;
			int damages;
			int tankPercent;
			public ReturnCarData(String regNumber, long licenseId, LocalDate returnDate, int damages, int tankPercent) {
				super();
				this.regNumber = regNumber;
				this.licenseId = licenseId;
				this.returnDate = returnDate;
				this.damages = damages;
				this.tankPercent = tankPercent;
			}
			public String getRegNumber() {
				return regNumber;
			}
			public long getLicenseId() {
				return licenseId;
			}
			public LocalDate getReturnDate() {
				return returnDate;
			}
			public int getDamages() {
				return damages;
			}
			public int getTankPercent() {
				return tankPercent;
			}
			public ReturnCarData() {
			}
}
