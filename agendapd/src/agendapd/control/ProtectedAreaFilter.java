package agendapd.control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import agendapd.model.UtenteBean;

public class ProtectedAreaFilter implements Filter {

	public ProtectedAreaFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		UtenteBean bean = (UtenteBean) ((HttpServletRequest) request).getSession().getAttribute("utenteBean");
		HttpServletResponse resp = (HttpServletResponse) response;
		if (bean == null) {
			resp.sendRedirect(resp.encodeRedirectURL("https://localhost:8443/agendapd/accesso.jsp"));
			return;
		} else if (bean != null && !request.isSecure()) {
			resp.sendRedirect(
					resp.encodeRedirectURL("https://localhost:8443" + ((HttpServletRequest) request).getRequestURI()));
			return;
		} else {
			HttpServletRequest req = (HttpServletRequest) request;
			int role = bean.getRole();
			if ((role == -1 && !req.getRequestURI().contains("admin.jsp"))
					|| (role == 1 && !req.getRequestURI().contains("paziente.jsp"))
					|| (role == 2 && !req.getRequestURI().contains("segretaria.jsp"))
					|| (role == 3 && !req.getRequestURI().contains("dottore.jsp"))) {
				resp.sendRedirect(resp.encodeRedirectURL("https://localhost:8443/agendapd/accesso.jsp"));
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
