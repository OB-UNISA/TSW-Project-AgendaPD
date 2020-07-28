package agendapd.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class RicettaBean implements Serializable {

	private static final long serialVersionUID = 7641586479445117335L;
	private String patientUser;
	private String doctorUser;
	private Timestamp timeDatePrescription;
	private byte[] prescription;

	public String getPatientUser() {
		return patientUser;
	}

	public String getDoctorUser() {
		return doctorUser;
	}

	public Timestamp getTimeDatePrescription() {
		return timeDatePrescription;
	}

	public byte[] getPrescription() {
		return prescription;
	}

	public void setPatientUser(String patientUser) {
		this.patientUser = patientUser;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public void setTimeDatePrescription(Timestamp timeDatePrescription) {
		this.timeDatePrescription = timeDatePrescription;
	}

	public void setPrescription(byte[] prescription) {
		this.prescription = prescription;
	}

	@Override
	public String toString() {
		return "RicettaBean [patientUser=" + patientUser + ", doctorUser=" + doctorUser + ", timeDatePrescription="
				+ timeDatePrescription + ", prescription=" + Arrays.toString(prescription) + "]";
	}
}
