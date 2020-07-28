package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agendapd.model.CartellaClinicaBean;
import agendapd.model.CartellaClinicaBeanDM;
import agendapd.model.DottoreBean;
import agendapd.model.DottoreBeanDM;
import agendapd.model.Model;
import agendapd.model.PazienteBean;
import agendapd.model.PazienteBeanDM;
import agendapd.model.RicettaBean;
import agendapd.model.RicettaBeanDM;
import agendapd.model.UtenteBean;
import agendapd.model.UtenteBeanDM;

@WebServlet("/paziente")
public class Paziente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<UtenteBean, DataSource> utenteModel = new UtenteBeanDM();
	private static Model<PazienteBean, DataSource> pazienteModel = new PazienteBeanDM();
	private static Model<CartellaClinicaBean, DataSource> cartellaModel = new CartellaClinicaBeanDM();
	private static Model<RicettaBean, DataSource> ricettaModel = new RicettaBeanDM();
	private static Model<DottoreBean, DataSource> dottoreModel = new DottoreBeanDM();

	public void init() throws ServletException {
		super.init();
		utenteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		pazienteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		cartellaModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		ricettaModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		dottoreModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public Paziente() {
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
				JSONObject user = new JSONObject(request.getParameter("jsonObj"));
				JSONObject obj = new JSONObject();
				UtenteBean utenteBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
				if (utenteBean != null && utenteBean.getUser() != null
						&& (utenteBean.getUser().equals(user.getString("doctorUser"))
								|| utenteBean.getUser().equals(user.getString("patientUser")))) {
					if (mode.equals("getinfopatients")) {
						Collection<PazienteBean> coll = pazienteModel.doRetrieveAll("nomeuUtentePaziente", null,
								utenteBean.getUser());
						Iterator<PazienteBean> iter = coll.iterator();
						Collection<UtenteBean> patients = new ArrayList<UtenteBean>();
						while (iter.hasNext()) {
							UtenteBean uBean = utenteModel.doRetrieveByKey(iter.next().getPatientUser());
							uBean.setPass("");
							patients.add(uBean);
						}
						((ArrayList<UtenteBean>) patients).sort((UtenteBean a, UtenteBean b) -> {
							if (a.getLastName().compareToIgnoreCase(b.getLastName()) > 1)
								return 1;
							if (a.getLastName().compareToIgnoreCase(b.getLastName()) < 1)
								return -1;
							return 0;
						});
						JSONArray array = new JSONArray(patients);
						obj.put("patients", array);
						obj.put("info", "OK");
					} else if (mode.equals("removepatient")) {
						PazienteBean pBean = pazienteModel.doRetrieveByKey(user.getString("patientRemove"));
						if (pBean.getDoctorUser() != null && pBean.getDoctorUser().equals(utenteBean.getUser())) {
							pazienteModel.doDelete(pBean);
							obj.put("info", "OK");
						} else if (pBean.getPatientUser() == null) {
							response.setStatus(422);
							obj.put("info", "ERROR");
						} else {
							response.setStatus(401);
							obj.put("info", "UNAUTHORIZED");
						}
					} else if (mode.equals("removeprescription")) {
						PazienteBean pBean = pazienteModel.doRetrieveByKey(user.getString("patientUser"));
						if (pBean.getDoctorUser() != null && pBean.getDoctorUser().equals(utenteBean.getUser())) {
							Timestamp ts = Timestamp.valueOf(user.getString("date"));
							ts = Timestamp.valueOf(ts.toLocalDateTime().minusMinutes(120));
							RicettaBean rBean = ricettaModel.doRetrieveByKey(user.getString("patientUser"),
									user.getString("doctorUser"), ts.toString());
							if (rBean.getPatientUser() != null) {
								rBean.setTimeDatePrescription(ts);
								ricettaModel.doDelete(rBean);
								obj.put("info", "OK");
							} else {
								response.setStatus(422);
								obj.put("info", "ERROR");
							}
						} else {
							response.setStatus(401);
							obj.put("info", "UNAUTHORIZED");
						}

					} else if (mode.equals("addpatient")) {
						UtenteBean pUtente = utenteModel.doRetrieveByKey(user.getString("addPatient"));
						if (pUtente.getUser() != null && pUtente.getRole() == 1) {
							PazienteBean pBean = pazienteModel.doRetrieveByKey(user.getString("addPatient"));
							if (pBean.getPatientUser() == null) {
								pBean.setPatientUser(user.getString("addPatient"));
								pBean.setDoctorUser(utenteBean.getUser());
								pBean.setAccessMedicalRecords(Boolean.FALSE);
								pazienteModel.doSave(pBean);
								pUtente.setPass("");
								JSONObject objP = new JSONObject(pUtente);
								obj.put("patient", objP);
								obj.put("info", "OK");
							} else {
								response.setStatus(422);
								obj.put("info", "ERROR");
							}
						} else {
							response.setStatus(422);
							obj.put("info", "ERROR");
						}
					} else if (mode.equals("getrecords")) {
						UtenteBean pUtente = utenteModel.doRetrieveByKey(user.getString("patientUser"));
						if (pUtente.getUser() != null) {
							PazienteBean pBean = pazienteModel.doRetrieveByKey(user.getString("patientUser"));
							if (pBean.getPatientUser() != null
									|| utenteBean.getUser().equals(user.getString("patientUser"))) {
								if (utenteBean.getUser().equals(user.getString("patientUser"))
										|| pBean.getAccessMedicalRecords()
												&& pBean.getDoctorUser().equals(utenteBean.getUser())
										|| pBean.getPatientUser().equals(utenteBean.getUser())) {
									Collection<CartellaClinicaBean> coll = cartellaModel.doRetrieveAll("dataCartella",
											user.getString("patientUser"), null, null);
									Iterator<CartellaClinicaBean> iter = coll.iterator();
									byte[] by = { 0 };
									while (iter.hasNext())
										iter.next().setMedicalRecord(by);
									JSONArray array = new JSONArray(coll);
									obj.put("records", array);
									obj.put("info", "OK");
								} else {
									response.setStatus(401);
									obj.put("info", "UNAUTHORIZED");
								}
							} else {
								response.setStatus(422);
								obj.put("info", "ERROR");
							}
						} else {
							response.setStatus(422);
							obj.put("info", "ERROR");
						}
					} else if (mode.equals("getprescriptions")) {
						UtenteBean pUtente = utenteModel.doRetrieveByKey(user.getString("patientUser"));
						if (pUtente.getUser() != null) {
							PazienteBean pBean = pazienteModel.doRetrieveByKey(user.getString("patientUser"));
							if (pBean.getPatientUser() != null
									|| utenteBean.getUser().equals(user.getString("patientUser"))) {
								if (utenteBean.getUser().equals(user.getString("patientUser"))
										|| pBean.getDoctorUser().equals(utenteBean.getUser())
										|| pBean.getPatientUser().equals(utenteBean.getUser())) {
									Collection<RicettaBean> coll = ricettaModel.doRetrieveAll("dataOrarioRicetta",
											user.getString("patientUser"), null, null);
									Iterator<RicettaBean> iter = coll.iterator();
									byte[] by = { 0 };
									while (iter.hasNext())
										iter.next().setPrescription(by);
									JSONArray array = new JSONArray(coll);
									obj.put("prescriptions", array);
									obj.put("info", "OK");
								} else {
									response.setStatus(401);
									obj.put("info", "UNAUTHORIZED");
								}
							} else {
								response.setStatus(422);
								obj.put("info", "ERROR");
							}
						} else {
							response.setStatus(422);
							obj.put("info", "ERROR");
						}
					} else if (mode.equals("removerecord")) {
						if (utenteBean.getUser().equals(user.getString("patientUser"))) {
							CartellaClinicaBean record = cartellaModel.doRetrieveByKey(user.getString("patientUser"),
									user.getString("date"), user.getString("typology"));
							if (record.getPatientUser() != null) {
								cartellaModel.doDelete(record);
								obj.put("info", "OK");
							} else {
								response.setStatus(422);
								obj.put("info", "ERROR");
							}
						} else {
							response.setStatus(401);
							obj.put("info", "UNAUTHORIZED");
						}
					} else if (mode.equals("updatedoctor")) {
						if (utenteBean.getUser().equals(user.getString("patientUser"))) {
							DottoreBean doctorBean = dottoreModel.doRetrieveByKey(user.getString("doctorUser"));
							if (doctorBean.getDoctorUser() != null
									&& doctorBean.getDoctorCode().equals(user.getString("doctorCode"))) {
								PazienteBean pBean = pazienteModel.doRetrieveByKey(utenteBean.getUser());
								pBean.setDoctorUser(user.getString("doctorUser"));
								pBean.setAccessMedicalRecords(user.getBoolean("accessRecords"));
								if (pBean.getPatientUser() != null) {
									pazienteModel.doUpdate(pBean);
								} else {
									pBean.setPatientUser(utenteBean.getUser());
									pazienteModel.doSave(pBean);
								}
								obj.put("info", "OK");
							} else {
								response.setStatus(422);
								obj.put("info", "ERROR");
							}
						} else {
							response.setStatus(401);
							obj.put("info", "UNAUTHORIZED");
						}
					}
				} else {
					response.setStatus(401);
					obj.put("info", "UNAUTHORIZED");
				}
				response.getWriter().println(obj.toString());

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
