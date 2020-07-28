package agendapd.control;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ServletContListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/agendapd");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}

		context.setAttribute("DataSource", ds);
		System.out.println("DataSource creation...." + ds.toString());

		context.setAttribute("dataStartingServer", Timestamp.valueOf(LocalDateTime.now()));
		context.setAttribute("numberRequests", Long.valueOf(0));
	}

	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		DataSource ds = (DataSource) context.getAttribute("DataSource");
		System.out.println("DataSource deletion...." + ds.toString());

	}
}
