package agendapd.control;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class DelayResponseFilter implements Filter {

	public DelayResponseFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		try {
			Thread.sleep(new Random().nextInt(400));
		} catch (InterruptedException e) {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(res.encodeRedirectURL("/agendapd/errore.jsp?error=500"));
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
