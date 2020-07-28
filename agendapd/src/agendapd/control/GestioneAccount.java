package agendapd.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

@WebServlet("/accmng")
public class GestioneAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<PazienteBean, DataSource> pazienteModel = new PazienteBeanDM();

	public void init() throws ServletException {
		super.init();
		pazienteModel.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public GestioneAccount() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accountName = request.getParameter("accountName");
		HttpSession session = request.getSession();
		List<UtenteBean> accounts = (List<UtenteBean>) session.getAttribute("accounts");
		UtenteBean accName = new UtenteBean();
		accName.setUser(accountName);

		if (accountName == null || session.getAttribute("utenteBean") == null || accounts.size() < 1
				|| !accounts.contains(accName)) {
			response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=401"));
			return;
		} else {
			if (((UtenteBean) session.getAttribute("utenteBean")).getRole() == 1)
				session.removeAttribute("pazienteBean");
			if (request.getParameter("accountOP").equals("switch")) {
				session.setAttribute("utenteBean", accounts.get(accounts.indexOf(accName)));
			} else {
				accounts.remove(accName);
				if (accounts.size() < 1)
					session.invalidate();
				else {
					session.setAttribute("utenteBean", accounts.get(0));
					session.setAttribute("accounts", accounts);
				}
			}
			try {
				UtenteBean beanS = (UtenteBean) session.getAttribute("utenteBean");
				if (beanS.getRole() == 1)
					session.setAttribute("pazienteBean", pazienteModel.doRetrieveByKey(beanS.getUser()));
				if (beanS.getRole() == -1)
					response.sendRedirect(response.encodeRedirectURL("/agendapd/admin.jsp"));
				else if (beanS.getRole() == 1)
					response.sendRedirect(response.encodeRedirectURL("/agendapd/paziente.jsp"));
				else if (beanS.getRole() == 2)
					response.sendRedirect(response.encodeRedirectURL("/agendapd/segretaria.jsp"));
				else if (beanS.getRole() == 3)
					response.sendRedirect(response.encodeRedirectURL("/agendapd/dottore.jsp"));
			} catch (IllegalStateException e) {
				response.sendRedirect(response.encodeRedirectURL("/agendapd/home.jsp"));
			} catch (SQLException e) {
				response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=500"));
			}

		}

	}

}
