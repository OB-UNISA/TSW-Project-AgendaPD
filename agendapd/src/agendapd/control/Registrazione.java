package agendapd.control;

import java.io.IOException;
import java.sql.Date;
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

import agendapd.model.DottoreBean;
import agendapd.model.DottoreBeanDM;
import agendapd.model.IoTBean;
import agendapd.model.IoTBeanDM;
import agendapd.model.Model;
import agendapd.model.StudioBean;
import agendapd.model.StudioBeanDM;
import agendapd.model.UtenteBean;
import agendapd.model.UtenteBeanDM;

@WebServlet("/registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<UtenteBean, DataSource> modelUtente = new UtenteBeanDM();
	private static Model<DottoreBean, DataSource> modelDottore = new DottoreBeanDM();
	private static Model<IoTBean, DataSource> modelIoT = new IoTBeanDM();
	private static Model<StudioBean, DataSource> modelStudio = new StudioBeanDM();

	public void init() throws ServletException {
		super.init();
		modelUtente.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		modelDottore.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		modelIoT.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		modelStudio.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public Registrazione() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> errorsFound = new ArrayList<>();
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		Integer role = 0;
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Date birthday = null;
		String fiscalCode = request.getParameter("fiscalCode");
		String addressName = request.getParameter("addressName");
		String addressCity = request.getParameter("addressCity");
		Integer addressCAP = 0;
		String email = request.getParameter("email");

		if (user == null || Pattern.compile("[^A-z0-9\\?\\.\\,\\-]").matcher(user).find() || user.isBlank())
			errorsFound.add("user");
		if (pass == null || !Pattern.compile("[A-Z]").matcher(pass).find()
				|| !Pattern.compile("[0-9]").matcher(pass).find() || pass.isBlank())
			errorsFound.add("pass");
		try {
			role = (Integer.parseInt(request.getParameter("role")));
			if (role < 1 || role > 3)
				errorsFound.add("role");
		} catch (NumberFormatException | NullPointerException e) {
			errorsFound.add("role");
		}
		if (firstName == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(firstName).find()
				|| firstName.isBlank())
			errorsFound.add("firstName");
		if (lastName == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(lastName).find() || lastName.isBlank())
			errorsFound.add("lastName");
		try {
			birthday = Date.valueOf(request.getParameter("birthday"));
			if (birthday == null || birthday.compareTo(new java.util.Date()) > 0)
				errorsFound.add("birthday");
		} catch (IllegalArgumentException e) {
			errorsFound.add("birthday");
		}
		if (fiscalCode == null || Pattern.compile("[^A-Z0-9]").matcher(fiscalCode).find() || fiscalCode.length() < 16
				|| fiscalCode.length() > 16 || fiscalCode.isEmpty())
			errorsFound.add("fiscalCode");
		if (addressName == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(addressName).find()
				|| addressName.isEmpty())
			errorsFound.add("addressName");
		if (addressCity == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(addressCity).find()
				|| addressCity.isEmpty())
			errorsFound.add("addressCity");
		try {
			addressCAP = Integer.parseInt(request.getParameter("addressCAP"));
		} catch (NumberFormatException | NullPointerException e) {
			errorsFound.add("addressCAP");
		}
		if (email == null
				|| !Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$").matcher(email).find()
				|| email.isEmpty())
			errorsFound.add("email");

		UtenteBean utenteBean = new UtenteBean();
		utenteBean.setUser(user);
		utenteBean.setPass(pass);
		utenteBean.setRole(role);
		utenteBean.setEmail(email);
		utenteBean.setFirstName(firstName);
		utenteBean.setLastName(lastName);
		utenteBean.setFiscalCode(fiscalCode);
		utenteBean.setBirthday(birthday);
		utenteBean.setAddressName(addressName);
		utenteBean.setAddressCity(addressCity);
		utenteBean.setAddressCAP(addressCAP);

		if (errorsFound.size() > 0) {
			request.setAttribute("errorsFound", errorsFound);
			request.setAttribute("utenteBean", utenteBean);
			response.setStatus(422);
			getServletContext().getRequestDispatcher(response.encodeURL("/registrazione.jsp")).forward(request,
					response);
		} else {
			try {
				modelUtente.doSave(utenteBean);
				if (utenteBean.getRole() == 3) {
					DottoreBean dottoreBean = new DottoreBean();
					dottoreBean.setDoctorUser(utenteBean.getUser());
					dottoreBean.setBookingVisits(false);
					dottoreBean.setRequestPrescriptions(false);
					dottoreBean.setDoctorCode(null);
					dottoreBean.setVisitingHours(null);
					modelDottore.doSave(dottoreBean);
					IoTBean iotBean = new IoTBean();
					iotBean.setId(utenteBean.getUser());
					iotBean.setPass(utenteBean.getPass());
					iotBean.setPermissions(0);
					modelIoT.doSave(iotBean);
					StudioBean studioBean = new StudioBean();
					studioBean.setDoctorUser(utenteBean.getUser());
					studioBean.setFreeSeats(-1);
					studioBean.setEnteredPatients(-1);
					modelStudio.doSave(studioBean);

				}
				HttpSession session = request.getSession();
				List<UtenteBean> accounts = (List<UtenteBean>) session.getAttribute("accounts");
				if (accounts == null)
					accounts = new ArrayList<>();
				else if (accounts.contains(utenteBean)) {
					response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=409"));
					return;
				}
				accounts.add(utenteBean);
				session.setAttribute("accounts", accounts);
				session.setAttribute("utenteBean", utenteBean);
				switch (utenteBean.getRole()) {
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
			} catch (SQLException e) {
				int errorCode = e.getErrorCode();
				switch (errorCode) {
				case 1062:
					errorsFound.add("user");
					request.setAttribute("errorsFound", errorsFound);
					request.setAttribute("utenteBean", utenteBean);
					response.setStatus(409);
					getServletContext().getRequestDispatcher(response.encodeURL("/registrazione.jsp")).forward(request,
							response);
					break;

				default:
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.sendRedirect(response.encodeRedirectURL("/agendapd/errore?error=500.jsp"));

				}
			}
		}

	}
}
