package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agendapd.model.CommunicazioneBean;
import agendapd.model.CommunicazioniBeanDM;
import agendapd.model.Model;
import agendapd.model.UtenteBean;

@WebServlet("/news")
public class News extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<CommunicazioneBean, DataSource> model = new CommunicazioniBeanDM();

	public void init() throws ServletException {
		super.init();
		model.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public News() {
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
				JSONObject news = new JSONObject(request.getParameter("news"));
				JSONObject obj = new JSONObject();
				String user = news.getString("user");
				if (user != null) {
					if (mode.equals("getnews")) {
						Collection<CommunicazioneBean> newsList = model.doRetrieveAll("dataOrarioCommunicazione", user,
								null);
						obj.put("user", user);
						JSONArray array = new JSONArray(newsList);
						obj.put("coll", array);
						obj.put("info", "OK");
					} else if (mode.equals("addnews") || mode.equals("deletenews")) {
						UtenteBean utenteBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
						if (utenteBean != null && utenteBean.getUser() != null && utenteBean.getUser().equals(user)) {
							CommunicazioneBean newsBean = new CommunicazioneBean();
							newsBean.setDoctorUser(user);
							if (mode.equals("addnews")) {
								newsBean.setTimeDateNews(
										Timestamp.valueOf(LocalDateTime.parse(news.getString("time"))));
								newsBean.setDescription(news.getString("text"));
								model.doSave(newsBean);
							} else {
								Timestamp ts = Timestamp.valueOf(news.getString("time"));
								newsBean.setTimeDateNews(Timestamp.valueOf(ts.toLocalDateTime().minusMinutes(120)));
								model.doDelete(newsBean);
							}
							obj.put("info", "OK");
						} else {
							response.setStatus(401);
							obj.put("info", "UNAUTHORIZED");
						}
					}
					response.getWriter().println(obj.toString());
				}
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
