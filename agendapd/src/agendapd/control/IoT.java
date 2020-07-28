package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import agendapd.model.IoTBean;
import agendapd.model.IoTBeanDM;
import agendapd.model.Model;
import agendapd.model.StudioBean;
import agendapd.model.StudioBeanDM;
import agendapd.model.UtenteBean;

@WebServlet("/iot")
public class IoT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<IoTBean, DataSource> modelIoT = new IoTBeanDM();
	private static Model<StudioBean, DataSource> modelStudio = new StudioBeanDM();

	public void init() throws ServletException {
		super.init();
		modelIoT.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		modelStudio.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public IoT() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(403);
		response.getWriter().println("NOT_ALLOWED");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mode = request.getParameter("mode");
		if (mode != null) {
			JSONObject iot = new JSONObject();
			try {
				if (request.isSecure()) {
					String id = "";

					if (mode.equals("entered") || mode.equals("seats"))
						iot = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
					else
						iot = new JSONObject(request.getParameter("jsonObj"));

					id = iot.getString("id");
					IoTBean iotBean = modelIoT.doRetrieveByKey(id);
					if (mode.equals("entered") || mode.equals("seats")) {
						String pass = iot.getString("pass");
						if (iotBean.getId() != null && iotBean.getPass().equals(pass)) {
							StudioBean studioBean = modelStudio.doRetrieveByKey(iotBean.getId());
							if (mode.equals("entered")) {
								if (iotBean.getPermissions() >= 2) {
									studioBean.setEnteredPatients(Integer.parseInt(iot.getString("entered")));
									modelStudio.doUpdate(studioBean);
									iot.put("info", "OK");
								} else {
									response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
									iot.put("info", "UNAUTHORIZED");
								}
							} else if (mode.equals("seats")) {
								if (iotBean.getPermissions() == 1 || iotBean.getPermissions() == 3) {
									studioBean.setFreeSeats(Integer.parseInt(iot.getString("freeSeats")));
									modelStudio.doUpdate(studioBean);
									iot.put("info", "OK");
								} else {
									response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
									iot.put("info", "UNAUTHORIZED");
								}
							}
						} else {
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							iot.put("info", "UNAUTHORIZED");
						}
					} else if (mode.equals("updateinfoiot") || mode.equals("getinfoiot")) {
						UtenteBean utBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
						if (utBean != null && iotBean.getId() != null && utBean.getUser().equals(iotBean.getId())) {
							StudioBean studioBean = modelStudio.doRetrieveByKey(iotBean.getId());
							if (mode.equals("getinfoiot")) {
								iot.put("id", iotBean.getId());
								iot.put("pass", iotBean.getPass());
								iot.put("permissions", iotBean.getPermissions());
								iot.put("freeSeats", studioBean.getFreeSeats());
								iot.put("entered", studioBean.getEnteredPatients());
								iot.put("info", "OK");
							} else if (mode.equals("updateinfoiot")) {
								iotBean.setId(iot.getString("id"));
								iotBean.setPass(iot.getString("pass"));
								iotBean.setPermissions(Integer.parseInt(iot.getString("permissions")));
								modelIoT.doUpdate(iotBean);
								studioBean.setEnteredPatients(Integer.parseInt(iot.getString("entered")));
								studioBean.setFreeSeats(Integer.parseInt(iot.getString("freeSeats")));
								modelStudio.doUpdate(studioBean);
								iot.put("info", "OK");
							}
						}
					}
				} else {
					response.setStatus(403);
					iot.put("info", "NOT_SECURE");
				}
			} catch (JSONException | IllegalArgumentException e) {
				response.setStatus(422);
				try {
					iot.put("info", "ERROR");
				} catch (JSONException e1) {
					response.setStatus(422);
					response.getWriter().println("ERROR");
				}
			} catch (SQLException e) {
				response.setStatus(500);
				try {
					iot.put("info", "ERROR");
				} catch (JSONException e1) {
					response.setStatus(422);
					response.getWriter().println("ERROR");
				}
			}
			response.setContentType("text/plain");
			response.getWriter().println(iot.toString());
		} else {
			response.setStatus(422);
			response.getWriter().println("ERROR");
		}
	}
}
