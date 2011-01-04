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

import moishd.server.common.DSCommon;
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
		PlayerBusy, 
		PlayerOffline,
		RankUpdated,
		TrophiesUpdated,
		RankAndTrophiesUpdated;

	}
	
	 //private static final Logger log = Logger.getLogger(moishd.server.common.C2DMCommon.class.getName());

	 public static final String URI = "/tasks/c2dm";

	 public static final String RETRY_COUNT = "X-AppEngine-TaskRetryCount";

	 static int MAX_RETRY = 3;
	
//	public static int PushGenericMessage1 
//	(String regId, String action, int retries, ServletContext context,
//			Map<String,String> payloads) {
//		String registrationId = regId;
//        int retryCount = retries;
//        if (retryCount != -1) {
//            if (retryCount > MAX_RETRY) {
//                log.severe("Too many retries, drop message for :" + registrationId);
//                return 200;
//            }
//        }
//        
//        @SuppressWarnings("unchecked")
//		//Map<String, String[]> params = req.getParameterMap();
//        String collapse =  String.valueOf((action + 
//				(payloads.size() > 0 ? 
//						(String)payloads.values().toArray()[0] : 
//							"")).hashCode());
//        boolean delayWhenIdle = false;
//            //null != req.getParameter(C2DMessaging.PARAM_DELAY_WHILE_IDLE);
//
//        try {
//            // Send doesn't retry !! 
//            // We use the queue exponential backoff for retries.
//            boolean sentOk = C2DMessaging.get(context)
//              .sendNoRetry(registrationId, collapse, params, delayWhenIdle);
//            log.info("Retry result " + sentOk + " " + registrationId);
//            if (sentOk) {
//                resp.setStatus(200); 
//                resp.getOutputStream().write("OK".getBytes());
//            } else {
//                resp.setStatus(500); // retry this task
//            }
//        } catch (IOException ex) {
//            resp.setStatus(200); 
//            resp.getOutputStream().write(("Non-retriable error:" + 
//              ex.toString()).getBytes());            
//        }
//		
//	}
	
	
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
		addEncodedParameter(sb, "data.Action", action);
		for (Map.Entry<String, String> keyVal : payloads.entrySet()) {
			addEncodedParameter(sb, "data." + keyVal.getKey(), keyVal.getValue()); 
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
