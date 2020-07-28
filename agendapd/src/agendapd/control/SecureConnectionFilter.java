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

public class SecureConnectionFilter implements Filter {

	public SecureConnectionFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!request.isSecure()) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(
					resp.encodeRedirectURL("https://localhost:8443" + ((HttpServletRequest) request).getRequestURI()));
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
