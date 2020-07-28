package agendapd.model;

import java.io.Serializable;

public class DottoreBean implements Serializable {

	private static final long serialVersionUID = -8982429360648621554L;
	private String doctorUser;
	private Boolean bookingVisits;
	private Boolean requestPrescriptions;
	private String doctorCode;
	private String visitingHours;

	public String getDoctorUser() {
		return doctorUser;
	}

	public Boolean getBookingVisits() {
		return bookingVisits;
	}

	public Boolean getRequestPrescriptions() {
		return requestPrescriptions;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public String getVisitingHours() {
		return visitingHours;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public void setBookingVisits(Boolean bookingVisits) {
		this.bookingVisits = bookingVisits;
	}

	public void setRequestPrescriptions(Boolean requestPrescriptions) {
		this.requestPrescriptions = requestPrescriptions;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public void setVisitingHours(String visitingHours) {
		this.visitingHours = visitingHours;
	}

	@Override
	public String toString() {
		return "DottoreBean [doctorUser=" + doctorUser + ", bookingVisits=" + bookingVisits + ", requestPrescriptions="
				+ requestPrescriptions + ", doctorCode=" + doctorCode + ", visitingHours=" + visitingHours + "]";
	}
}
