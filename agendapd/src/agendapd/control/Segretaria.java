package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import agendapd.model.Model;
import agendapd.model.SegretariaBean;
import agendapd.model.SegretariaBeanDM;
import agendapd.model.UtenteBean;
import agendapd.model.UtenteBeanDM;

@WebServlet("/segretaria")
public class Segretaria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<SegretariaBean, DataSource> segModel = new SegretariaBeanDM();
	private static Model<UtenteBean, DataSource> utenteModel = new UtenteBeanDM();

	public void init() throws ServletException {
		super.init();
		segModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		utenteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public Segretaria() {
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
				SegretariaBean bean = segModel.doRetrieveByKey(obj.getString("doctorUser"));
				JSONObject secretary = new JSONObject();
				if (mode.equals("getinfo")) {
					if (bean.getSecretaryUser() != null)
						secretary.put("secretaryUser", bean.getSecretaryUser());
					else
						secretary.put("secretaryUser", "");
					secretary.put("accessPatients", bean.getAccessPatients());
					secretary.put("accessMedicalRecords", bean.getAccessMedicalRecords());
					secretary.put("accessPrescriptions", bean.getAccessPrescriptions());
					secretary.put("accessBookedVisits", bean.getAccessBookingVisits());
					secretary.put("editPrescriptions", bean.getEditPrescriptions());
					secretary.put("addPatients", bean.getAddPatients());
					secretary.put("deletePatients", bean.getDeletePatients());
					secretary.put("addNews", bean.getAddNews());
					secretary.put("deleteNews", bean.getDeleteNews());
					secretary.put("info", "OK");
				} else if (mode.equals("updateinfo")) {
					UtenteBean utBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
					if (utBean != null && utBean.getUser() != null
							&& utBean.getUser().equals(obj.getString("doctorUser"))) {
						SegretariaBean seg = segModel.doRetrieveByKey(obj.getString("secretaryUser"), null);
						if (bean.getDoctorUser() == null && !obj.getString("secretaryUser").isBlank()
								&& utenteModel.doRetrieveByKey(obj.getString("secretaryUser")).getUser() != null
								&& seg.getSecretaryUser() == null) {
							bean.setDoctorUser(obj.getString("doctorUser"));
							bean.setSecretaryUser(obj.getString("secretaryUser"));
							bean.setAccessPatients(obj.getBoolean("accessPatients"));
							bean.setAccessMedicalRecords(obj.getBoolean("accessMedicalRecords"));
							bean.setAccessPrescriptions(obj.getBoolean("accessPrescriptions"));
							bean.setAccessBookingVisits(obj.getBoolean("accessBookedVisits"));
							bean.setEditPrescriptions(obj.getBoolean("editPrescriptions"));
							bean.setAddPatients(obj.getBoolean("addPatients"));
							bean.setDeletePatients(obj.getBoolean("deletePatients"));
							bean.setAddNews(obj.getBoolean("addNews"));
							bean.setDeleteNews(obj.getBoolean("deleteNews"));
							segModel.doSave(bean);
							secretary.put("info", "OK");
						} else if (bean.getSecretaryUser() != null && (seg.getSecretaryUser() == null
								|| seg.getSecretaryUser().equals(bean.getSecretaryUser()))) {
							bean.setDoctorUser(obj.getString("doctorUser"));
							bean.setSecretaryUser(obj.getString("secretaryUser"));
							bean.setAccessPatients(obj.getBoolean("accessPatients"));
							bean.setAccessMedicalRecords(obj.getBoolean("accessMedicalRecords"));
							bean.setAccessPrescriptions(obj.getBoolean("accessPrescriptions"));
							bean.setAccessBookingVisits(obj.getBoolean("accessBookedVisits"));
							bean.setEditPrescriptions(obj.getBoolean("editPrescriptions"));
							bean.setAddPatients(obj.getBoolean("addPatients"));
							bean.setDeletePatients(obj.getBoolean("deletePatients"));
							bean.setAddNews(obj.getBoolean("addNews"));
							bean.setDeleteNews(obj.getBoolean("deleteNews"));
							segModel.doUpdate(bean);
							secretary.put("info", "OK");
						} else {
							response.setStatus(422);
							response.getWriter().println("ERROR");
							return;
						}

					} else {
						response.setStatus(401);
						secretary.put("info", "UNAUTHORIZED");
					}
				}
				response.getWriter().println(secretary.toString());

			} catch (JSONException e) {
				response.setStatus(422);
				response.getWriter().println("ERROR");
			} catch (SQLException e) {
				response.setStatus(500);
				response.getWriter().println("ERROR");
			}
		} else {
			response.setStatus(422);
			response.getWriter().println("ERROR");
		}
	}
}
