package agendapd.control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class JSPFilter implements Filter {

	public JSPFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(res.encodeRedirectURL("/agendapd/errore.jsp?error=403"));
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
