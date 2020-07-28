package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import agendapd.model.DottoreBean;
import agendapd.model.DottoreBeanDM;
import agendapd.model.Model;
import agendapd.model.PazienteBean;
import agendapd.model.PazienteBeanDM;
import agendapd.model.StudioBean;
import agendapd.model.StudioBeanDM;
import agendapd.model.UtenteBean;

@WebServlet("/dottore")
public class Dottore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<DottoreBean, DataSource> dottoreModel = new DottoreBeanDM();
	private static Model<PazienteBean, DataSource> pazienteModel = new PazienteBeanDM();
	private static Model<StudioBean, DataSource> studioModel = new StudioBeanDM();

	public void init() throws ServletException {
		super.init();
		dottoreModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		pazienteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		studioModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public Dottore() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		String mode = request.getParameter("mode");

		if (mode != null) {
			try {
				JSONObject obj = new JSONObject(request.getParameter("jsonObj"));
				DottoreBean bean = dottoreModel.doRetrieveByKey(obj.getString("doctorUser"));
				JSONObject doctor = new JSONObject();
				if (mode.equals("getinfo")) {
					PazienteBean pazienteBean = null;
					boolean patient = false;
					try {
						pazienteBean = pazienteModel.doRetrieveByKey(obj.getString("patientUser"));
					} catch (JSONException e) {
					}

					if (pazienteBean != null) {
						patient = true;
						if (pazienteBean.getPatientUser() != null) {
							bean = dottoreModel.doRetrieveByKey(pazienteBean.getDoctorUser());
							doctor.put("accessRecords", pazienteBean.getAccessMedicalRecords());
							StudioBean studio = studioModel.doRetrieveByKey(bean.getDoctorUser());
							if (studio.getFreeSeats() != -1)
								doctor.put("freeSeats", studio.getFreeSeats());
							if (studio.getEnteredPatients() != -1)
								doctor.put("entered", studio.getEnteredPatients());
						}
					}
					if (bean.getDoctorUser() != null) {
						doctor.put("doctorUser", bean.getDoctorUser());
						doctor.put("bookingVisits", bean.getBookingVisits().booleanValue());
						doctor.put("requestPrescriptions", bean.getRequestPrescriptions().booleanValue());
						if (bean.getDoctorCode() == null)
							doctor.put("doctorCode", "");
						else
							doctor.put("doctorCode", bean.getDoctorCode());
						if (bean.getVisitingHours() == null) {
							JSONObject visitingHours = new JSONObject();
							JSONObject emptyDay = new JSONObject();
							emptyDay.put("from", "");
							emptyDay.put("to", "");
							visitingHours.put("lun", emptyDay);
							visitingHours.put("mar", emptyDay);
							visitingHours.put("mer", emptyDay);
							visitingHours.put("gio", emptyDay);
							visitingHours.put("ven", emptyDay);
							visitingHours.put("sab", emptyDay);
							doctor.put("visitingHours", visitingHours);
						} else
							doctor.put("visitingHours", new JSONObject(bean.getVisitingHours()));
						doctor.put("info", "OK");
					} else {
						if (patient) {
							doctor.put("patient", true);
						} else {
							response.setStatus(422);
							doctor.put("info", "ERROR");
						}
					}
				} else if (mode.equals("updateinfo")) {
					UtenteBean utBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
					if (utBean != null && utBean.getUser() != null
							&& utBean.getUser().equals(obj.getString("doctorUser"))) {
						bean.setBookingVisits(obj.getBoolean("bookingVisits"));
						bean.setRequestPrescriptions(obj.getBoolean("requestPrescriptions"));
						String docCode = obj.getString("doctorCode");
						if (docCode.isBlank())
							bean.setDoctorCode(null);
						else
							bean.setDoctorCode(obj.getString("doctorCode"));
						bean.setVisitingHours(obj.getJSONObject("visitingHours").toString());
						dottoreModel.doUpdate(bean);
						doctor.put("info", "OK");
					} else {
						response.setStatus(401);
						doctor.put("info", "UNAUTHORIZED");
					}
				}
				response.getWriter().println(doctor.toString());

			} catch (JSONException e) {
				response.setStatus(422);
				response.getWriter().println("ERROR");
			} catch (SQLException | DateTimeParseException e) {
				response.setStatus(500);
				response.getWriter().println("ERROR");
			}
		} else {
			response.setStatus(422);
			response.getWriter().println("ERROR");
		}

	}

}
