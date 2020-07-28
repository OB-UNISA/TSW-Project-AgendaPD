package agendapd.model;

import java.io.Serializable;

public class StudioBean implements Serializable {

	private static final long serialVersionUID = -8497586469435464109L;
	private String doctorUser;
	private int freeSeats;
	private int enteredPatients;

	public String getDoctorUser() {
		return doctorUser;
	}

	public int getFreeSeats() {
		return freeSeats;
	}

	public int getEnteredPatients() {
		return enteredPatients;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public void setFreeSeats(int seatsFree) {
		this.freeSeats = seatsFree;
	}

	public void setEnteredPatients(int enteredPatients) {
		this.enteredPatients = enteredPatients;
	}

	@Override
	public String toString() {
		return "StudioBean [doctorUser=" + doctorUser + ", seatsFree=" + freeSeats + ", enteredPatients="
				+ enteredPatients + "]";
	}
}
