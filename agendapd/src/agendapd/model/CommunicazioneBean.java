package agendapd.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommunicazioneBean implements Serializable {

	private static final long serialVersionUID = 9004914913753174294L;
	private String doctorUser;
	private String description;
	private Timestamp timeDateNews;

	public String getDoctorUser() {
		return doctorUser;
	}

	public String getDescription() {
		return description;
	}

	public Timestamp getTimeDateNews() {
		return timeDateNews;
	}

	public void setDoctorUser(String doctorUser) {
		this.doctorUser = doctorUser;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTimeDateNews(Timestamp timeDateNews) {
		this.timeDateNews = timeDateNews;
	}

	@Override
	public String toString() {
		return "CommunicazioniBean [doctorUser=" + doctorUser + ", description=" + description + ", timeDateNews="
				+ timeDateNews + "]";
	}

}
