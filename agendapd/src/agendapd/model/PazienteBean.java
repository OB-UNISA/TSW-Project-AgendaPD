package agendapd.model;

import java.io.Serializable;

public class PazienteBean implements Serializable {

	private static final long serialVersionUID = 4907974261554887445L;
	private String patientUser;
	private String doctorUser;
	private Boolean accessMedicalRecords;

	public String getPatientUser() {
		return patientUser;
	}

	public String getDoctorUser() {
		return doctorUser;
	}

	public Boolean getAccessMedicalRecords() {
		return accessMedicalRecords;
	}

	public void setPatientUser(String patientUser) {
		this.patientUser = patientUser;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public void setAccessMedicalRecords(Boolean accessMedicalRecords) {
		this.accessMedicalRecords = accessMedicalRecords;
	}

	@Override
	public String toString() {
		return "PazienteBean [patientUser=" + patientUser + ", doctorUser=" + doctorUser + ", accessMedicalRecords="
				+ accessMedicalRecords + "]";
	}
}
