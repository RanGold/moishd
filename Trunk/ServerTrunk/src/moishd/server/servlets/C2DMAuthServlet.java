package moishd.server.servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import moishd.server.common.PMF;
import moishd.server.dataObjects.C2DMAuth;

public class C2DMAuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8795970457297476756L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (UserServiceFactory.getUserService().isUserAdmin()) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				try {
					tx.begin();
					Query q = pm.newQuery(C2DMAuth.class);
					@SuppressWarnings("unchecked")
					C2DMAuth c2dmAuth = ((List<C2DMAuth>) q.execute()).get(0);
					c2dmAuth.setAuthKey(getAuthToken());
					c2dmAuth.setDate(new Date());
					pm.makePersistent(c2dmAuth);
					tx.commit();
				} catch (Exception e) {
					response.addHeader("Error", e.getMessage());
					response.getWriter().println(e.getMessage());
				}
			} catch (IOException e) {
				response.addHeader("Error", e.getMessage());
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		}

	}

	private String getAuthToken() throws Exception {
		HttpURLConnection connection = null;
		try {
			URL url = null;
			try {
				url = new URL("https://www.google.com/accounts/ClientLogin");
			} catch (MalformedURLException e) {
				throw new ServletException(e.getCause());
			}
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			StringBuilder sb = new StringBuilder();

			addEncodedParameter(sb, "accountType", "GOOGLE");
			addEncodedParameter(sb, "Email", "app.moishd@gmail.com");
			addEncodedParameter(sb, "Passwd", "moishdapp123");
			addEncodedParameter(sb, "service", "ac2dm");
			addEncodedParameter(sb, "source", "moishdVer1");
			String data = sb.toString();

			DataOutputStream stream = new DataOutputStream(
					connection.getOutputStream());
			stream.writeBytes(data);
			stream.flush();
			stream.close();
		} catch (Exception e) {
			throw e;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		String line;
		String tokenIdentifier = "Auth=";
		String errorIdentifier = "Error=";
		String token = null;
		StringBuilder errors = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(tokenIdentifier)) {
				token = line.substring(tokenIdentifier.length());
			} else if (line.startsWith(errorIdentifier)) {
				String error = line.substring(errorIdentifier.length());
				errors.append(error + System.getProperty("line.separator"));
			}
		}
		reader.close();
		return token;
	}

	private void addEncodedParameter(StringBuilder sb, String name, String value) {

		if (sb.length() > 0) {
			sb.append("&");
		}

		try {
			sb.append(URLEncoder.encode(name, "UTF-8"));
			sb.append("=");
			sb.append(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
	}

}
