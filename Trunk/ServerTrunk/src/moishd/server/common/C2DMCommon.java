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

import javax.servlet.ServletException;

import moishd.server.dataObjects.C2DMAuth;

public class C2DMCommon {
	public enum Actions {
		GameInvitation,
		CheckAlive,
		WordForGame,
		GameResult,
		GameCanceled,
		GameDeclined,
		StartGameTruth,
		StartGameDareSimonPro,
		StartGameDareMixing,
		StartGameDareFastClick,
		StartGameDarePixOPair,
		PlayerBusy, 
		PlayerOffline, 
		GameOffer, 
		Disconnect;
		
		public String getPopular(String popular) {
			return popular + this.toString();
		}
		
		public String getGameName() {
			if (this.toString().startsWith("StartGame")) {
				return (this.toString().substring("StartGame".length()));
			} else {
				return ("NotGameType");
			}
		}
	}

	public static final String URI = "/tasks/c2dm";

	public static final String RETRY_COUNT = "X-AppEngine-TaskRetryCount";

	static int MAX_RETRY = 3;

	public static boolean PushGenericMessage 
	(String regId, String action, 
			Map<String,String> payloads) throws ServletException, IOException {
		String auth_token = getAuthToken(true);

		HttpURLConnection connection = null; 
		try {
			// Building c2dm push message request
			URL url = null; 

			try { 
				url = new URL("https://android.apis.google.com/c2dm/send"); 
			} catch(MalformedURLException e) { 
				LoggerCommon.Get().LogInfo("C2DMCommon", e.getMessage());
				// Exception handling 
			} 
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true); 
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", "GoogleLogin auth=" + auth_token);

		} catch (Exception e) { 
			LoggerCommon.Get().LogInfo("C2DMCommon", e.getMessage());
			// Exception handling 
		} 
		StringBuilder sb = new StringBuilder(); 
		addEncodedParameter(sb, "registration_id", regId);
		addEncodedParameter(sb, "collapse_key", (
				String.valueOf((action + 
						(payloads.size() > 0 ? 
								(String)payloads.values().toArray()[0] : 
									"")).hashCode()))); 
		addEncodedParameter(sb, "data.Action", action);
		for (Map.Entry<String, String> keyVal : payloads.entrySet()) {
			addEncodedParameter(sb, "data." + keyVal.getKey(), keyVal.getValue()); 
		}
		String data = sb.toString();
		try { 
			// Getting push result
			DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
			stream.writeBytes(data); 
			stream.flush(); 
			stream.close();
			LoggerCommon.Get().LogInfo("C2DMCommon", "C2DM response code: " +
					String.valueOf(connection.getResponseCode()));
			String newAuth = connection.getHeaderField("update-client-auth");
			
			// C2DM auth expired
			if (newAuth != null) {
				LoggerCommon.Get().LogInfo("C2DMCommon", "Updating C2DM auth token");
				DSCommon.SetC2DMAuth(newAuth);
			}
			BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    connection.getInputStream()));
			String inputLine;
			String output = "";

			while ((inputLine = in.readLine()) != null) {
				output = output + inputLine + "\n\r";
			}
			in.close();
			LoggerCommon.Get().LogInfo("C2DMCommon", "Connection content: " + output);
			
			switch (connection.getResponseCode()) { 
			case 200: 
				return true;
			case 503: 
				LoggerCommon.Get().LogInfo("C2DMCommon", "Error code 503");
				// Service unavailable
				return false;
			case 401:
				LoggerCommon.Get().LogInfo("C2DMCommon", "Error code 401");
				// Invalid authentication token
				return false;
			} 
		} catch
		(IOException e) { 
			LoggerCommon.Get().LogInfo("C2DMCommon", e.getMessage());
			// Exception handling 
		}
		
		return false;
	}

	// Getting new auth token and updating it on the datastore
	public static String getAuthToken(Boolean getCurrent) throws ServletException, IOException {
		if (getCurrent) {
			List<C2DMAuth> auths = DSCommon.GetAllC2DMAuth();
			if (auths.size() > 0) {
				return auths.get(0).getAuthKey();
			}
		} 	
			
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
