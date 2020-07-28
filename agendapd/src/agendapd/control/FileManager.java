package agendapd.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import agendapd.model.CartellaClinicaBean;
import agendapd.model.CartellaClinicaBeanDM;
import agendapd.model.Model;
import agendapd.model.PazienteBean;
import agendapd.model.PazienteBeanDM;
import agendapd.model.RicettaBean;
import agendapd.model.RicettaBeanDM;
import agendapd.model.UtenteBean;
import agendapd.model.UtenteBeanDM;

@WebServlet("/filemanager")
@MultipartConfig
public class FileManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<UtenteBean, DataSource> utenteModel = new UtenteBeanDM();
	private static Model<PazienteBean, DataSource> pazienteModel = new PazienteBeanDM();
	private static Model<CartellaClinicaBean, DataSource> cartellaModel = new CartellaClinicaBeanDM();
	private static Model<RicettaBean, DataSource> ricettaModel = new RicettaBeanDM();

	public void init() throws ServletException {
		super.init();
		utenteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		pazienteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		cartellaModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		ricettaModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public FileManager() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("mode");
		UtenteBean utenteBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
		Boolean error422 = false;
		if (mode != null && utenteBean != null) {
			try {
				String obj = request.getParameter("obj");
				if (mode.equals("download")) {
					if (obj != null) {
						if (obj.equals("record")) {
							PazienteBean pBean = pazienteModel.doRetrieveByKey(request.getParameter("patUser"));
							if (utenteBean.getUser().equals(request.getParameter("patUser"))
									|| pBean.getPatientUser() != null
											&& (pBean.getPatientUser().equals(utenteBean.getUser())
													|| pBean.getDoctorUser().equals(utenteBean.getUser()))) {
								CartellaClinicaBean record = cartellaModel.doRetrieveByKey(
										request.getParameter("patUser"), request.getParameter("date"),
										request.getParameter("typology"));
								response.setContentType("application/pdf");
								if (record.getPatientUser() != null) {
									UtenteBean pUtenteBean = utenteModel.doRetrieveByKey(record.getPatientUser());
									response.setHeader("Content-Disposition",
											"inline; filename=" + pUtenteBean.getFiscalCode() + "-"
													+ record.getMedicalRecordDate().toString() + "-"
													+ record.getTypology().replace(" ", "_"));
									response.getOutputStream().write(record.getMedicalRecord());
								} else
									response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=401"));
							} else
								response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=401"));
						} else if (obj.equals("prescription")) {
							PazienteBean pBean = pazienteModel.doRetrieveByKey(request.getParameter("patUser"));
							if (pBean.getPatientUser() != null && (pBean.getPatientUser().equals(utenteBean.getUser())
									|| pBean.getDoctorUser().equals(utenteBean.getUser()))) {
								Timestamp ts = Timestamp.valueOf(request.getParameter("date"));
								RicettaBean prescription = ricettaModel.doRetrieveByKey(request.getParameter("patUser"),
										request.getParameter("docUser"),
										Timestamp.valueOf(ts.toLocalDateTime().minusMinutes(120)).toString());
								response.setContentType("application/pdf");
								if (prescription.getPatientUser() != null) {
									UtenteBean pUtenteBean = utenteModel.doRetrieveByKey(prescription.getPatientUser());
									response.setHeader("Content-Disposition",
											"inline; filename=" + pUtenteBean.getFiscalCode() + "-"
													+ prescription.getTimeDatePrescription().toString()
															.replace(" ", "_").replace(":", "-"));
									response.getOutputStream().write(prescription.getPrescription());
								} else
									response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=404"));
							}
						}
					} else
						error422 = true;
				} else if (mode.equals("upload")) {
					response.setContentType("text/html");
					String resp = "<h1 style=\"background-color:green;text-align:center;\">Inviato</h1><script>setInterval(window.close, 1500);</script>";
					if (obj.equals("record")) {
						String date = request.getParameter("date");
						String typology = request.getParameter("typology");
						String place = request.getParameter("place");
						String result = request.getParameter("result");
						Part part = request.getPart("file");

						if (date != null && typology != null && !typology.isBlank() && place != null && !place.isBlank()
								&& result != null && !result.isBlank() && part != null && part.getSize() > 0
								&& part.getContentType().equals("application/pdf")) {
							CartellaClinicaBean caBean = cartellaModel.doRetrieveByKey(request.getParameter("patUser"),
									request.getParameter("date"), request.getParameter("typology"));
							if (caBean.getPatientUser() == null) {
								CartellaClinicaBean cBean = new CartellaClinicaBean();
								cBean.setPatientUser(utenteBean.getUser());
								cBean.setMedicalRecordDate(Date.valueOf(date));
								cBean.setTypology(typology);
								cBean.setPlace(place);
								cBean.setResult(result);
								cBean.setMedicalRecord(part.getInputStream().readAllBytes());
								cartellaModel.doSave(cBean);
								response.getWriter().println(resp);
							} else {
								response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=409"));
								return;
							}
						} else
							error422 = true;

					} else if (obj.equals("prescription")) {
						PazienteBean pBean = pazienteModel.doRetrieveByKey(request.getParameter("patUser"));
						if (pBean.getPatientUser() != null && pBean.getDoctorUser().equals(utenteBean.getUser())) {
							Part part = request.getPart("file");
							if (part != null && part.getSize() > 0 && part.getContentType().equals("application/pdf")) {
								Timestamp ts = Timestamp.valueOf(LocalDateTime.parse(request.getParameter("date")));
								RicettaBean riBean = ricettaModel.doRetrieveByKey(request.getParameter("patUser"),
										request.getParameter("docUser"),
										Timestamp.valueOf(ts.toLocalDateTime().minusMinutes(120)).toString());
								if (riBean.getPatientUser() == null) {
									RicettaBean rBean = new RicettaBean();
									rBean.setPatientUser(pBean.getPatientUser());
									rBean.setDoctorUser(pBean.getDoctorUser());
									rBean.setTimeDatePrescription(
											Timestamp.valueOf(LocalDateTime.parse(request.getParameter("date"))));
									rBean.setPrescription(part.getInputStream().readAllBytes());
									ricettaModel.doSave(rBean);
									response.getWriter().println(resp);
								} else {
									response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=409"));
									return;
								}
							} else
								error422 = true;
						} else
							response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=401"));
					} else
						error422 = true;
				} else
					error422 = true;
			} catch (SQLException e) {
				response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=500"));
			} catch (IllegalArgumentException e) {
				response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=422"));
			}
		} else
			error422 = true;

		if (error422)
			response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=422"));
	}
}
