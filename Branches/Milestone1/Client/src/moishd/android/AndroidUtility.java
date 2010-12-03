package moishd.android;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.ServerRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AndroidUtility {

	private  static final String serverPath = "http://moish-d.appspot.com"; // ""http://10.0.2.2:8888/"
	//to be replaced with http://moish-d.appspot.com/
	//when change - change also in ServerRequest

	/*public enum serverExtEnum {

	    USER_LOGIN("UserLogin"), 
	    GET_ALL_USERS("GetAllUsers"),
	    GUESTBOOK("guestbook"),
	    REGISTRATION("registration");

	    private String ext;

	    private serverExtEnum(String ext) {
	    this.ext = ext;
	    }

	     public String getExt() {
	    return ext;
	    }
	}*/

	public static boolean enlistUser(ClientMoishdUser user, String authString){

		ServerRequest.Get().GetCookie(authString);
		HttpResponse response = SendObjToServer(user, "UserLogin", authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		} 
	}

	@SuppressWarnings("unchecked")
	public static List<ClientMoishdUser> getAllUsers(String authString){

		HttpResponse response = SendReqToServer("GetAllUsers", null, authString);
		try {
			InputStream contentStream = response.getEntity().getContent();
			if (response.containsHeader("Error")){
				Log.d("GAE ERROR", "an Error occured");
			}
			else{
				if (contentStream != null) {
					ObjectInputStream ois = new ObjectInputStream(contentStream);
					try {
						String json = (String) ois.readObject();
						Gson g = new Gson();
						return (List<ClientMoishdUser>)g.fromJson(json, new TypeToken<Collection<ClientMoishdUser>>(){}.getType());
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}			}
		}
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String inviteUser(ClientMoishdUser user, String authString){

		HttpResponse response = SendObjToServer(user, "InviteUser", authString);
		try {
			String content = convertStreamToString(response.getEntity().getContent());
			if (response.containsHeader("Error")){
				Log.d("GAE ERROR", "an Error occured");
				return null;
			}
			else{
				return content;
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static ClientMoishdUser retrieveInvitation(String gameId, String authString) {

		HttpResponse response = SendReqToServer("GetTimeGameInitiator", gameId, authString);
		try {
			InputStream contentStream = response.getEntity().getContent();
			if (response.containsHeader("Error")){
				Log.d("GAE ERROR", "an Error occured");
			}
			else{
				if (contentStream != null) {
					ObjectInputStream ois = new ObjectInputStream(contentStream);
					try {
						String json = (String) ois.readObject();
						Gson g = new Gson();
						return (ClientMoishdUser)g.fromJson(json, ClientMoishdUser.class);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}			}
		}
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean sendWinToServer(String gameId, String authString) {
		HttpResponse response = SendReqToServer("GameTimeWin", gameId, authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		}			
	}
	
	public static boolean sendInvitationResponse(String gameId, String responseString, String authString) {
		
		String invitationResponse = gameId + "#" + responseString;
		HttpResponse response = SendReqToServer("InvitationResponse", invitationResponse, authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		}			
	}
	
	


	private static HttpResponse SendObjToServer(Object obj, String ext, String authString){

		final int DURATION = 10000;
		//to be replaced with http://moish-d.appspot.com/
		//when change - change also in ServerRequest
		/*
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, DURATION);
		HttpConnectionParams.setSoTimeout(params, DURATION);

		DefaultHttpClient httpClient = new DefaultHttpClient(params);*/
		HttpResponse response ;
		URI uri;
		String uriPath = serverPath + "/" + ext;
		ServerRequest.Get().GetCookie(authString);
		try {
			uri = new URI(uriPath);

			HttpPost postMethod = new HttpPost(uri);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Gson g = new Gson();
			String json = g.toJson(obj);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(json);
			ByteArrayEntity req_entity = new ByteArrayEntity(baos.toByteArray());
			req_entity.setContentType("application/json");

			// associating entity with method
			postMethod.setEntity(req_entity);
			response = ServerRequest.Get().doPost(postMethod);
			return response;

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

		return null;	

	}

	private static HttpResponse SendReqToServer(String ext, String content, String authString){

		final int DURATION = 10000;

		/*HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, DURATION);
		HttpConnectionParams.setSoTimeout(params, DURATION);

		//DefaultHttpClient httpClient = new DefaultHttpClient(params);*/
		URI uri;
		String uriPath = serverPath + "/" + ext;
		ServerRequest.Get().GetCookie(authString);
		try {
			uri = new URI(uriPath);
			HttpPost postMethod = new HttpPost(uri);

			if (content!=null){
				ByteArrayEntity entity = new ByteArrayEntity(content.getBytes());
				entity.setContentEncoding("UTF-8");
				postMethod.setEntity(entity);
			}
			return ServerRequest.Get().doPost(postMethod);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

		return null;	

	}

	private static String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the
		 * Reader.read(char[] buffer) method. We iterate until the
		 * Reader return -1 which means there's no more data to
		 * read. We use the StringWriter class to produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}

}