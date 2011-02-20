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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServerCommunication {

	private static final String serverPath = "http://moish-d.appspot.com";
	private static Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
	public final static int SERVER_ERROR = 0;
	
	public final static int ENLIST_FACEBOOK_ACCOUNT_NOT_MATCH_ERROR = 1;
	public final static int ENLIST_OK = 2;
	public final static int ENLIST_ALREADY_LOGIN = 3;
	
	public final static int HAS_LOCATION_TRUE = 1;
	public final static int HAS_LOCATION_FALSE = 2;
	
	//if fails the user wont be inserted to application any way
	public static int registerC2DMToServer(ClientMoishdUser user, String authString){
		HttpResponse resp = SendObjToServer(user, ServletNamesEnum.RegisterUser, authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return SERVER_ERROR;
		}else{
			return resp.getStatusLine().getStatusCode();
		}
	}

	public static int unregisterC2DMToServer(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.UnregisterUser, authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return SERVER_ERROR;
		}else{		
			return resp.getStatusLine().getStatusCode();
		}
	}

	//not checking for success in sending. if fails 3 times - the server will disconnect the user and he will
	//get notified once he'll try to make an active action with the server
	public static int sendAlive(){
		HttpResponse resp = activateServlet(ServletNamesEnum.Alive, null);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return SERVER_ERROR;
		}else{
			return resp.getStatusLine().getStatusCode();
		}
	}

	public static int hasLocation(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.HasLocation,authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return SERVER_ERROR;
		}else if(resp.containsHeader("HasLocation")){
			return HAS_LOCATION_TRUE;
		}else{
			return HAS_LOCATION_FALSE;
		}
	}

	public static int enlistUser(ClientMoishdUser user, String authString){
		HttpResponse response = SendObjToServer(user, ServletNamesEnum.UserLogin, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return SERVER_ERROR;		
		}else if (response.containsHeader("AccountNotMatch")){
			Log.d("GAE ERROR", "Account not match the one on the server");
			return ENLIST_FACEBOOK_ACCOUNT_NOT_MATCH_ERROR;
		} else if (response.containsHeader("AlreadyLoggedIn")){
			Log.d("GAE ERROR", "User already login to the server");
			return ENLIST_ALREADY_LOGIN;
		} else{
			return ENLIST_OK;
		}
	}

	public static ClientMoishdUser getCurrentUser(String authString) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.GetCurrentUser, null, authString);
		return getUserFromResponse(response);
	}
	


	public static boolean updateLocationInServer(Location location, String authString){
		ClientLocation sendLocation = new ClientLocation(location.getLongitude(), location.getLatitude());
		HttpResponse response = SendObjToServer(sendLocation, ServletNamesEnum.UpdateLocation, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		} 
	}

	public static List<ClientMoishdUser> getMergedUsers(List<String> friendsID, String authString){
		HttpResponse response = SendObjToServer(friendsID,ServletNamesEnum.GetMergedUsers, authString);
		return getUserListFromResponse(response);
	}

	public static List<ClientMoishdUser> getFacebookFriends(List<String> friendsID, String authString){
		HttpResponse response = SendObjToServer(friendsID, ServletNamesEnum.GetFriendUsers, authString);
		return getUserListFromResponse(response);
	}

	public static List<ClientMoishdUser> getNearbyUsers(String authString){
		Log.d("loc","started getNearBy");
		HttpResponse response = SendReqToServer(ServletNamesEnum.GetNearbyUsers, null, authString);
		Log.d("loc","ended getNearBy");
		return getUserListFromResponse(response);
	}


	public static boolean setUserBusy(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.SetBusy,authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}else{
			return true;
		}
	}

	public static boolean setSingleUserUnbusy(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.SetNotBusy,authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean cancelGame(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.CancelGame,authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}else{
			return true;
		}
	}

	public static boolean IsBusy(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.IsBusy,authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else if (resp.containsHeader("Busy")){
			return true;
		}
		else{
			return false;
		}

	}

	public static String getMostPopularGame(String authString){
		HttpResponse resp = activateServlet(ServletNamesEnum.GetMostPopularGame,authString);
		if (resp == null || resp.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return null;
		}else{
			try {
				HttpEntity entity = resp.getEntity();
				if (entity==null){
					return null;
				}else {
					String popularGame = convertStreamToString(entity.getContent());
					if (popularGame.equals("")){
						return null;
					}else{
						return popularGame;
					}
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	public static boolean inviteUser(String userGoogleIdentifier, String authString){
		HttpResponse response = SendReqToServer(ServletNamesEnum.InviteUser, userGoogleIdentifier, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		return true;
	}

	//TODO check if there's a need in the game_id
	public static boolean sendRankToServer(String gameType,int rank, String authString) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.RankGame,gameType+":"+rank, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}else{
			return true;
		}
	}

	public static boolean sendGamePlayedToServer(String gameType,String authString) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.AddGamePlayed,gameType, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}else{
			return true;
		}
	}

	public static boolean isThirdTimePlayed(String gameType, String authString) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.IsFirstTimePlayed,gameType, authString);
		if (response==null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else if (response.containsHeader("ThirdTimePlayed")){
			return true;
		}
		else{
			return false;
		}
	}

	public static boolean sendInvitationResponse(String gameId, String responseString, String authString, String isPopular) {

		String invitationResponse = gameId + "#" + responseString + "#" + isPopular;
		HttpResponse response = SendReqToServer(ServletNamesEnum.InvitationResponse, invitationResponse, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}else{
			return true;
		}			
	}

	public static boolean sendWinToServer(String gameId, String authString, String gameType) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.GameWin, gameId + ":" + gameType, authString);
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return false;
		}
		else{
			return true;
		}			
	}

	public static boolean sendLoseToServer(String gameId, String authString, String gameType) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.GameLose, gameId + ":" + gameType, authString);
		if (response == null || response.containsHeader("Error")){
			return false;
		}else{
			return true;
		}			
	}
	
	public static boolean sendTechnicalLoseToServer(String gameId, String authString, String gameType) {
		HttpResponse response = SendReqToServer(ServletNamesEnum.GameTechnicalLose, gameId + ":" + gameType, authString);
		if (response == null || response.containsHeader("Error")){
			return false;
		}else{
			return true;
		}			
	}
	private static HttpResponse activateServlet(ServletNamesEnum servletName, String authString){
		return SendToServer(servletName, null, null, authString);
	}

	private static HttpResponse SendObjToServer(Object obj, ServletNamesEnum servletName, String authString){
		return SendToServer(servletName, obj, null, authString);
	}

	private static HttpResponse SendReqToServer(ServletNamesEnum servletName, String content, String authString){
		return SendToServer(servletName, null, content, authString);
	}
	
	//assumes the response is not NULL
	private static String getJsonFromResponse(HttpResponse response){
		InputStream contentStream;
		String json = null;
		if (response == null || response.containsHeader("Error")){
			Log.d("GAE ERROR", "an Error occured");
			return null;
		} else
			try {
				HttpEntity entity = response.getEntity();
				if (entity == null){
					return null;
				}
				contentStream = entity.getContent();
				if (contentStream == null){
					Log.d("GAE ERROR", "an Error occured - contentStream in null");
				}else {
					ObjectInputStream ois = new ObjectInputStream(contentStream);
					json = (String) ois.readObject();
					ois.close();
					contentStream.close();
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return json;
	}

	//the check if response is null is checked inside getJsonFromResponse
	@SuppressWarnings("unchecked")
	private static List<ClientMoishdUser> getUserListFromResponse(HttpResponse response){
		String json = getJsonFromResponse(response);
		if (json == null){
			return null;
		}
		return (List<ClientMoishdUser>)g.fromJson(json, new TypeToken<Collection<ClientMoishdUser>>(){}.getType());
	}
	
	//the check if response is null is checked inside getJsonFromResponse
	private static ClientMoishdUser getUserFromResponse(HttpResponse response){
		String json = getJsonFromResponse(response);
		if (json==null){
			return null;
		}else{
			return (ClientMoishdUser)g.fromJson(json, ClientMoishdUser.class);
		}
	}

	private static HttpResponse SendToServer(ServletNamesEnum servletName, Object obj, String content, String authString){
		HttpResponse response = null;
		URI uri;
		String uriPath = serverPath + "/" + servletName.toString();
		ByteArrayEntity req_entity;
		if (authString == null)
			ServerRequest.Get().GetCookie();
		else
			ServerRequest.Get().GetCookie(authString);
		try {
			uri = new URI(uriPath);
			HttpPost postMethod = new HttpPost(uri);

			if (obj==null && content!=null){ //SendReq
				req_entity = new ByteArrayEntity(content.getBytes());
				req_entity.setContentEncoding("UTF-8");
				postMethod.setEntity(req_entity);
			} else if (obj!=null && content==null){ //SendObj
				req_entity = byteArrayEntityFromObj(obj);
				req_entity.setContentType("application/json");
				postMethod.setEntity(req_entity);
			}
			//	if (obj==null && content==null) than activateServlet so only ServerRequest.Get().doPost(postMethod)
			response =  ServerRequest.Get().doPost(postMethod);		

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

		return response;
	}

	private static ByteArrayEntity byteArrayEntityFromObj(Object obj) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String json = g.toJson(obj);
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(json);
		ByteArrayEntity result = new ByteArrayEntity(baos.toByteArray());
		oos.close();
		baos.close();
		return result;
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
			Reader reader = null;
			char[] buffer = new char[1024];
			try {
				reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
				if (reader !=null)
					reader.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}
	
	public static List<String> getTopFiveRanked(String authString){
		HttpResponse response = activateServlet(ServletNamesEnum.GetTopFiveRanked, authString);
		return getTopFiveFromResponse(response);
	}
	
	public static List<String> getTopFivePopular(String authString){
		HttpResponse response = activateServlet(ServletNamesEnum.GetTopFivePopular, authString);
		return getTopFiveFromResponse(response);
	}
	
	public static List<ClientMoishdUser> getTopMoishers(String gameType, String authString){
		HttpResponse response = SendReqToServer(ServletNamesEnum.GetTopGamePlayers, gameType, authString); 
		return getUserListFromResponse(response);
	}

	@SuppressWarnings("unchecked")
	private static List<String> getTopFiveFromResponse(HttpResponse response){
		if (response==null){
			return null;
		}
		String json = getJsonFromResponse(response);
		if (json!=null){
			return (List<String>)g.fromJson(json, new TypeToken<Collection<String>>(){}.getType() );
		}
		else{
			return null;
		}
	}
}