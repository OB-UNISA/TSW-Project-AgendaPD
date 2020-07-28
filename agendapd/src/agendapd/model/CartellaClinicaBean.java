package agendapd.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class CartellaClinicaBean implements Serializable {

	private static final long serialVersionUID = 5518763999064437396L;
	private String patientUser;
	private Date medicalRecordDate;
	private String typology;
	private String place;
	private String result;
	private byte[] medicalRecord;

	public String getPatientUser() {
		return patientUser;
	}

	public Date getMedicalRecordDate() {
		return medicalRecordDate;
	}

	public String getTypology() {
		return typology;
	}

	public String getPlace() {
		return place;
	}

	public String getResult() {
		return result;
	}

	public byte[] getMedicalRecord() {
		return medicalRecord;
	}

	public void setPatientUser(String patientUser) {
		this.patientUser = patientUser;
	}

	public void setMedicalRecordDate(Date medicalRecordDate) {
		this.medicalRecordDate = medicalRecordDate;
	}

	public void setTypology(String typology) {
		this.typology = typology;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setMedicalRecord(byte[] bs) {
		this.medicalRecord = bs;
	}

	@Override
	public String toString() {
		return "CartellaClinicaBean [patientUser=" + patientUser + ", medicalRecordDate=" + medicalRecordDate
				+ ", typology=" + typology + ", place=" + place + ", result=" + result + ", medicalRecord="
				+ Arrays.toString(medicalRecord) + "]";
	}

}