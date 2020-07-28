package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import agendapd.model.Model;
import agendapd.model.PazienteBean;
import agendapd.model.PazienteBeanDM;
import agendapd.model.UtenteBean;
import agendapd.model.UtenteBeanDM;

@WebServlet("/accesso")
public class Accesso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<UtenteBean, DataSource> utenteModel = new UtenteBeanDM();
	private static Model<PazienteBean, DataSource> pazienteModel = new PazienteBeanDM();

	public void init() throws ServletException {
		super.init();
		utenteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		pazienteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public Accesso() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mode = request.getParameter("mode");

		if (mode != null && mode.equals("userExists")) {
			response.setContentType("text/plain");
			String userName = request.getParameter("userName");
			if (userName == null || userName.isBlank())
				response.getWriter().print("true");
			else {
				try {
					UtenteBean bean = utenteModel.doRetrieveByKey(userName);
					if (bean.getUser() == null)
						response.getWriter().print("false");
					else
						response.getWriter().print("true");

				} catch (SQLException e) {
					request.setAttribute("error", "Si è verificato un errore, riprova");
					getServletContext().getRequestDispatcher(response.encodeURL("/errore.jsp")).forward(request,
							response);
				}
			}
		} else {
			List<String> errorsFound = new ArrayList<>();
			String user = request.getParameter("user");
			String pass = request.getParameter("pass");

			if (user == null || Pattern.compile("[^A-z0-9\\?\\.\\,\\-]").matcher(user).find() || user.isBlank())
				errorsFound.add("user");
			if (pass == null || !Pattern.compile("[A-Z]").matcher(pass).find()
					|| !Pattern.compile("[0-9]").matcher(pass).find() || pass.isBlank())
				errorsFound.add("pass");

			UtenteBean utenteBean = new UtenteBean();
			utenteBean.setUser(user);
			utenteBean.setPass(pass);

			if (errorsFound.size() > 0) {
				request.setAttribute("errorsFound", errorsFound);
				request.setAttribute("utenteBean", utenteBean);
				response.setStatus(422);
				getServletContext().getRequestDispatcher(response.encodeURL("/accesso.jsp")).forward(request, response);
			} else {
				try {
					UtenteBean beanUser = utenteModel.doRetrieveByKey(user);
					if (beanUser.getUser() == null) {
						errorsFound.add("user");
						request.setAttribute("errorsFound", errorsFound);
						request.setAttribute("utenteBean", utenteBean);
						response.setStatus(422);
						getServletContext().getRequestDispatcher(response.encodeURL("/accesso.jsp")).forward(request,
								response);
					} else if (beanUser.getPass() == null || !beanUser.getPass().equals(pass)) {
						errorsFound.add("pass");
						request.setAttribute("errorsFound", errorsFound);
						request.setAttribute("utenteBean", utenteBean);
						response.setStatus(422);
						getServletContext().getRequestDispatcher(response.encodeURL("/accesso.jsp")).forward(request,
								response);
					} else {
						HttpSession session = request.getSession();
						List<UtenteBean> accounts = (List<UtenteBean>) session.getAttribute("accounts");
						if (accounts == null)
							accounts = new ArrayList<>();
						else if (accounts.contains(beanUser)) {
							response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=409"));
							return;
						}
						accounts.add(beanUser);
						UtenteBean oldAcc = (UtenteBean) session.getAttribute("utenteBean");
						if (oldAcc != null && oldAcc.getRole() == 1)
							session.removeAttribute("pazienteBean");
						if (beanUser.getRole() == 1) {
							session.setAttribute("pazienteBean", pazienteModel.doRetrieveByKey(beanUser.getUser()));
						}
						session.setAttribute("accounts", accounts);
						session.setAttribute("utenteBean", beanUser);
						switch (beanUser.getRole()) {
						case -1:
							response.sendRedirect(response.encodeRedirectURL("/agendapd/admin.jsp"));
							break;
						case 1:
							response.sendRedirect(response.encodeRedirectURL("/agendapd/paziente.jsp"));
							break;
						case 2:
							response.sendRedirect(response.encodeRedirectURL("/agendapd/segretaria.jsp"));
							break;
						case 3:
							response.sendRedirect(response.encodeRedirectURL("/agendapd/dottore.jsp"));
							break;
						}
					}
				} catch (SQLException e) {
					response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=500"));
				}
			}
		}

	}
}
