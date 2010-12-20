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

import moishd.client.dataObjects.ClientLocation;
import moishd.client.dataObjects.ClientMoishdUser;
import moishd.common.ServerRequest;
import moishd.common.ServletNamesEnum;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServerCommunication {

	private  static final String serverPath = "http://moish-d.appspot.com"; // ""http://10.0.2.2:8888/"
	//to be replaced with http://moish-d.appspot.com/
	//when change - change also in ServerRequest

	public static int registerC2DMToServer(ClientMoishdUser user){
		HttpResponse resp = SendObjToServer(user, ServletNamesEnum.RegisterUser, null);
		return resp.getStatusLine().getStatusCode();
	}
	
	public static int unregisterC2DMToServer(){
		HttpResponse resp = activateServlet(ServletNamesEnum.UnregisterUser);
		return resp.getStatusLine().getStatusCode();
	}

	
	public static boolean enlistUser(ClientMoishdUser user, String authString){

		ServerRequest.Get().GetCookie(authString);
		HttpResponse response = SendObjToServer(user, ServletNamesEnum.UserLogin, authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		} 
	}
	
	public static boolean updateLocationInServer(Location location, String authString){

		ServerRequest.Get().GetCookie(authString);
		ClientLocation sendLocation = new ClientLocation(location.getLongitude(), location.getLatitude());
		HttpResponse response = SendObjToServer(sendLocation, ServletNamesEnum.UpdateLocation, authString);
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

		HttpResponse response = SendReqToServer(ServletNamesEnum.GetAllUsers, null, authString);
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
						//Gson g = new Gson();
						Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
						return (List<ClientMoishdUser>)g.fromJson(json, new TypeToken<Collection<ClientMoishdUser>>(){}.getType());
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}			}
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ClientMoishdUser> getFacebookFriends(String authString){

		HttpResponse response = SendReqToServer(ServletNamesEnum.GetFriendUsers, null, authString);
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
						//Gson g = new Gson();
						Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
						return (List<ClientMoishdUser>)g.fromJson(json, new TypeToken<Collection<ClientMoishdUser>>(){}.getType());
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}			}
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String inviteUser(ClientMoishdUser user, String authString){

		HttpResponse response = SendObjToServer(user, ServletNamesEnum.InviteUser, authString);
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ClientMoishdUser retrieveInvitation(String gameId, String authString) {

		HttpResponse response = SendReqToServer(ServletNamesEnum.GetTimeGameInitiator, gameId, authString);
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
						//Gson g = new Gson();
						Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
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
	
	public static boolean sendInvitationResponse(String gameId, String responseString, String authString) {
		
		String invitationResponse = gameId + "#" + responseString;
		HttpResponse response = SendReqToServer(ServletNamesEnum.InvitationResponse, invitationResponse, authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		}			
	}
	
	public static boolean sendWinToServer(String gameId, String authString, String gameType) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.GameWin, gameId + ":" + gameType, authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		}			
	}

	public static boolean sendLoseToServer(String gameId, String authString, String gameType) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.GameLose, gameId + ":" + gameType, authString);
		if (response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		}			
	}
	
	private static HttpResponse activateServlet(ServletNamesEnum servletName){
		HttpResponse response ;
		URI uri;
		String uriPath = serverPath + "/" + servletName.toString();
		
		ServerRequest.Get().GetCookie();
		
		try {
			uri = new URI(uriPath);
			HttpPost postMethod = new HttpPost(uri);
			
			response = ServerRequest.Get().doPost(postMethod);
			return response;
	
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static HttpResponse SendObjToServer(Object obj, ServletNamesEnum servletName, String authString){

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
		String uriPath = serverPath + "/" + servletName.toString();
		if (authString == null)
			ServerRequest.Get().GetCookie();
		else
			ServerRequest.Get().GetCookie(authString);
		try {
			uri = new URI(uriPath);

			HttpPost postMethod = new HttpPost(uri);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//Gson g = new Gson();
			Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
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

	private static HttpResponse SendReqToServer(ServletNamesEnum servletName, String content, String authString){

		final int DURATION = 10000;

		/*HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, DURATION);
		HttpConnectionParams.setSoTimeout(params, DURATION);

		//DefaultHttpClient httpClient = new DefaultHttpClient(params);*/
		URI uri;
		String uriPath = serverPath + "/" + servletName.toString();
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