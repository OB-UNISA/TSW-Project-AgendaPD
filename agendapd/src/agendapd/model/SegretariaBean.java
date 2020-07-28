package agendapd.model;

import java.io.Serializable;

public class SegretariaBean implements Serializable {

	private static final long serialVersionUID = 9047691059153967641L;
	private String secretaryUser;
	private String doctorUser;
	private boolean accessPatients;
	private boolean accessMedicalRecords;
	private boolean accessPrescriptions;
	private boolean accessBookingVisits;
	private boolean editPrescriptions;
	private boolean addPatients;
	private boolean deletePatients;
	private boolean addNews;
	private boolean deleteNews;

	public String getSecretaryUser() {
		return secretaryUser;
	}

	public String getDoctorUser() {
		return doctorUser;
	}

	public boolean getAccessPatients() {
		return accessPatients;
	}

	public boolean getAccessMedicalRecords() {
		return accessMedicalRecords;
	}

	public boolean getAccessPrescriptions() {
		return accessPrescriptions;
	}

	public boolean getAccessBookingVisits() {
		return accessBookingVisits;
	}

	public boolean getEditPrescriptions() {
		return editPrescriptions;
	}

	public boolean getAddPatients() {
		return addPatients;
	}

	public boolean getDeletePatients() {
		return deletePatients;
	}

	public boolean getAddNews() {
		return addNews;
	}

	public boolean getDeleteNews() {
		return deleteNews;
	}

	public void setSecretaryUser(String secretaryUser) {
		this.secretaryUser = secretaryUser;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public void setAccessPatients(boolean accessPatients) {
		this.accessPatients = accessPatients;
	}

	public void setAccessMedicalRecords(boolean accessMedicalRecords) {
		this.accessMedicalRecords = accessMedicalRecords;
	}

	public void setAccessPrescriptions(boolean accessPrescriptions) {
		this.accessPrescriptions = accessPrescriptions;
	}

	public void setAccessBookingVisits(boolean accessBookingVisits) {
		this.accessBookingVisits = accessBookingVisits;
	}

	public void setEditPrescriptions(boolean editPrescriptions) {
		this.editPrescriptions = editPrescriptions;
	}

	public void setAddPatients(boolean addPatients) {
		this.addPatients = addPatients;
	}

	public void setDeletePatients(boolean deletePatients) {
		this.deletePatients = deletePatients;
	}

	public void setAddNews(boolean addNews) {
		this.addNews = addNews;
	}

	public void setDeleteNews(boolean deleteNews) {
		this.deleteNews = deleteNews;
	}

	@Override
	public String toString() {
		return "SegretariaBean [secretaryUser=" + secretaryUser + ", doctorUser=" + doctorUser + ", accessPatients="
				+ accessPatients + ", accessMedicalRecords=" + accessMedicalRecords + ", accessPrescriptions="
				+ accessPrescriptions + ", accessBookingVisits=" + accessBookingVisits + ", editPrescriptions="
				+ editPrescriptions + ", addPatients=" + addPatients + ", deletePatients=" + deletePatients
				+ ", addNews=" + addNews + ", deleteNews=" + deleteNews + "]";
	}

}