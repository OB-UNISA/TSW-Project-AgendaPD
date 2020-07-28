package agendapd.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import agendapd.model.DottoreBean;
import agendapd.model.DottoreBeanDM;
import agendapd.model.Model;
import agendapd.model.UtenteBean;
import agendapd.model.UtenteBeanDM;

@WebServlet("/utente")
public class Utente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Model<UtenteBean, DataSource> modelUtente = new UtenteBeanDM();
	private static Model<DottoreBean, DataSource> modelDottore = new DottoreBeanDM();

	public void init() throws ServletException {
		super.init();
		modelDottore.setDB((DataSource) getServletContext().getAttribute("DataSource"));
		modelUtente.setDB((DataSource) getServletContext().getAttribute("DataSource"));
	}

	public Utente() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mode = request.getParameter("mode");
		if (mode != null) {
			try {
				response.setContentType("text/plain");
				JSONObject user = new JSONObject(request.getParameter("jsonStr"));
				JSONObject errorsFound = new JSONObject();
				int errors = 0;
				String userN = user.getString("user");
				String pass = user.getString("pass");
				String firstName = user.getString("firstName");
				String lastName = user.getString("lastName");
				Date birthday = null;
				String fiscalCode = user.getString("fiscalCode");
				String addressName = user.getString("addressName");
				String addressCity = user.getString("addressCity");
				Integer addressCAP = 0;
				String email = user.getString("email");

				if (user == null || Pattern.compile("[^A-z0-9\\?\\.\\,\\-]").matcher(userN).find() || userN.isBlank()) {
					errorsFound.put("user", true);
					errors++;
				}

				UtenteBean utBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
				if (mode.equals("updateUser") && utBean != null && userN != null && utBean.getUser().equals(userN)) {

					if (pass == null || !Pattern.compile("[A-Z]").matcher(pass).find()
							|| !Pattern.compile("[0-9]").matcher(pass).find() || pass.isBlank()) {
						errorsFound.put("password", true);
						errors++;
					}

					if (firstName == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(firstName).find()
							|| firstName.isBlank()) {
						errorsFound.put("firstName", true);
						errors++;
					}
					if (lastName == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(lastName).find()
							|| lastName.isBlank()) {
						errorsFound.put("lastName", true);
						errors++;
					}
					try {
						birthday = Date.valueOf(user.getString("birthday"));
						if (birthday == null || birthday.compareTo(new java.util.Date()) > 0) {
							errorsFound.put("birthday", true);
							errors++;
						}
					} catch (IllegalArgumentException e) {
						{
							errorsFound.put("birthday", true);
							errors++;
						}
					}
					if (fiscalCode == null || Pattern.compile("[^A-Z0-9]").matcher(fiscalCode).find()
							|| fiscalCode.length() < 16 || fiscalCode.length() > 16 || fiscalCode.isEmpty()) {
						errorsFound.put("fiscalCode", true);
						errors++;
					}
					if (addressName == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(addressName).find()
							|| addressName.isEmpty()) {
						errorsFound.put("addressName", true);
						errors++;
					}
					if (addressCity == null || Pattern.compile("[^A-Za-zÀ-ÖØ-öø-ÿ ]").matcher(addressCity).find()
							|| addressCity.isEmpty()) {
						errorsFound.put("addressCity", true);
						errors++;
					}
					try {
						addressCAP = Integer.parseInt(user.getString("addressCAP"));
					} catch (NumberFormatException | NullPointerException e) {
						{
							errorsFound.put("addressCAP", true);
							errors++;
						}
					}
					if (email == null || !Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
							.matcher(email).find() || email.isEmpty()) {
						errorsFound.put("email", true);
						errors++;
					}

					UtenteBean bean = (UtenteBean) request.getSession().getAttribute("utenteBean");
					UtenteBean utenteBean = new UtenteBean();
					utenteBean.setUser(bean.getUser());
					utenteBean.setPass(pass);
					utenteBean.setRole(bean.getRole());
					utenteBean.setEmail(email);
					utenteBean.setFirstName(firstName);
					utenteBean.setLastName(lastName);
					utenteBean.setFiscalCode(fiscalCode);
					utenteBean.setBirthday(birthday);
					utenteBean.setAddressName(addressName);
					utenteBean.setAddressCity(addressCity);
					utenteBean.setAddressCAP(addressCAP);

					errorsFound.put("length", errors);
					if (errors > 0)
						response.getWriter().println(errorsFound.toString());
					else {
						try {
							modelUtente.doUpdate(utenteBean);
							HttpSession session = request.getSession();
							List<UtenteBean> accounts = (List<UtenteBean>) session.getAttribute("accounts");
							accounts.remove(bean);
							accounts.add(utenteBean);
							session.setAttribute("accounts", accounts);
							session.setAttribute("utenteBean", utenteBean);
							response.getWriter().println(errorsFound.toString());
						} catch (SQLException e) {
							int errorCode = e.getErrorCode();
							switch (errorCode) {

							default:
								response.getWriter().println("error");
							}
						}
					}
				} else {
					errors++;
					errorsFound.put("length", errors);
					errorsFound.put("user", true);
					response.getWriter().println(errorsFound.toString());
				}
			} catch (JSONException e) {
				response.getWriter().println("error");
			}
		} else {
			response.setStatus(422);
			response.getWriter().println("ERROR");
		}

	}
}
