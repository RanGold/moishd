package moishd.server.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;

import moishd.server.dataObjects.C2DMAuth;

public class C2DMCommon {
	public enum Actions {
		GameInvitation,
		CheckAlive,
		WordForGame,
		GameResult,
		GameCanceled,
		GameDenyed
	}
	// TODO : change use to server
	public static boolean PushGenericMessage 
	(String regId, String action, 
			Map<String,String> payloads) throws ServletException, IOException {
		String auth_token = getAuthToken(true);

		HttpURLConnection connection = null; 
		try { 
			URL url = null; 

			try { 
				url = new URL("https://android.apis.google.com/c2dm/send"); 
			} catch(MalformedURLException e) { 
				// Exception handling 
			} 
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true); 
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", "GoogleLogin auth=" + auth_token);

		} catch (Exception e) { 
			// Exception handling 
		} 
		StringBuilder sb = new StringBuilder(); 
		addEncodedParameter(sb, "registration_id", regId);
		addEncodedParameter(sb, "collapse_key", (
				String.valueOf((action + 
						(payloads.size() > 0 ? 
								(String)payloads.values().toArray()[0] : 
									"")).hashCode()))); 
		addEncodedParameter(sb, "Action", action);
		for (Map.Entry<String, String> keyVal : payloads.entrySet()) {
			addEncodedParameter(sb, keyVal.getKey(), keyVal.getValue()); 
		}
		String data = sb.toString();
		try { 
			DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
			stream.writeBytes(data); 
			stream.flush(); 
			stream.close();

			switch (connection.getResponseCode()) { 
			case 200: 
				// Success, but check for errors in the body
				return true;
			case 503: 
				// Service unavailable
				return false;
			case 401: 
				// Invalid authentication token
				return false;
			} 
		} catch
		(IOException e) { 
			// Exception handling 
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	public static String getAuthToken(Boolean getCurrent) throws ServletException, IOException {
		if (getCurrent) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				return (((List<C2DMAuth>) pm.newQuery(C2DMAuth.class).execute())
						.get(0)).getAuthKey();
			} finally {
				pm.close();
			}
		} else {
			HttpURLConnection connection = null;
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
	}

	private static void addEncodedParameter(StringBuilder sb, String name,
			String value) {

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
