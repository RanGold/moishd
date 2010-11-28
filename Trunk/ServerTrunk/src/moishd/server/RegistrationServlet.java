package moishd.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = -1494607313404414818L;

	//Ran:
	//the servlet will get User object filled only with google account and its registration_id
	//it will store the reg_id for the given user in the datastore.
	//nothing needs to be sent back (is 200 send back anyway? )

	/*try {
	String json = (String) ois.readObject();
	objectToTest obj ;
	obj= (objectToTest)g.fromJson(json, objectToTest.class);
	obj.setFirst(obj.GetFirst()-3);
	obj.setlast(obj.GetLast()-3);

	resp.setContentType("application/json");
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream oos = new ObjectOutputStream(baos);

	json = g.toJson(obj);
	oos.writeObject(json);

	ServletOutputStream sos = resp.getOutputStream();
	sos.write(baos.toByteArray());

} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Gson g = new Gson();
		ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
		DevRegAndIDPairs pair = new DevRegAndIDPairs();
		try {
			String json = (String) ois.readObject();
			pair = (DevRegAndIDPairs)g.fromJson(json, DevRegAndIDPairs.class);
		}catch(ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String auth_token = getAuth_token();

		//this is a send test - copy this pattern for future use
		
		HttpURLConnection connection = null;
		try {
		    URL url = null;
		    try {
		        url = new URL("https://android.apis.google.com/c2dm/send");
		    } catch (MalformedURLException e) {
		        // Exception handling
		    }
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setDoOutput(true);
		    connection.setUseCaches(false);
		    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    connection.setRequestProperty("Authorization", "GoogleLogin auth=" + auth_token);
		    
		} catch (Exception e) { 
		    // Exception handling
		}
		StringBuilder sb = new StringBuilder();
		addEncodedParameter(sb, "registration_id", pair.getRegId());
		addEncodedParameter(sb, "collapse_key", "someCollapseKey");
		addEncodedParameter(sb, "data.payload1", "payload1 message data");
		addEncodedParameter(sb, "data.payload2", "payload2 message data");
		addEncodedParameter(sb, "data.anotherPayload", "even more message data");
		String data = sb.toString();
		
		try {
		    DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
		    stream.writeBytes(data);
		    stream.flush();
		    stream.close();
		 
		    switch (connection.getResponseCode()) {
		    case 200:
		        // Success, but check for errors in the body
		        break;
		    case 503:
		        // Service unavailable
		        break;
		    case 401:
		        // Invalid authentication token
		        break;
		    }
		} catch (IOException e) {
		    // Exception handling
		}


	}

	private String getAuth_token() throws IOException{
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
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			StringBuilder sb = new StringBuilder(); 

			addEncodedParameter(sb, "accountType", "GOOGLE");
			addEncodedParameter(sb, "Email", "app.moishd@gmail.com");
			addEncodedParameter(sb, "Passwd", "moishdapp123");
			addEncodedParameter(sb, "service", "ac2dm");
			addEncodedParameter(sb, "source", "myCompany-demoApp-1.0.0");
			String data = sb.toString();

			DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
			stream.writeBytes(data);
			stream.flush();
			stream.close();
		} catch (Exception e) {
			// Exception handling
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 

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
