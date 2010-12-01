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

import moishd.common.ServerRequest;

import org.apache.http.HttpEntity;
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
		
	public static boolean enlist(moishd.client.dataObjects.ClientMoishdUser user){
		HttpResponse response = SendObjToServer(user, "UserLogin");
		String content;
		try {
			content = convertStreamToString(response.getEntity().getContent());
		if (!content.equals("")){
			Log.d("GAE ERROR",content);
			return false;
		} else
			return true;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static List<moishd.client.dataObjects.ClientMoishdUser> getAllUsers(){
		List<moishd.client.dataObjects.ClientMoishdUser> users;
		HttpResponse response = SendReqToServer("GetAllUsers");
		String content;
		try {
			content = convertStreamToString(response.getEntity().getContent());
			if (!content.equals(""))
				Log.d("GAE ERROR",content);
			else{
				HttpEntity respEntity = response.getEntity();
				if (respEntity != null) {
					ObjectInputStream ois = new ObjectInputStream(response.getEntity().getContent());
					try {
						String json = (String) ois.readObject();
						Gson g = new Gson();
						return (List<moishd.client.dataObjects.ClientMoishdUser>)g.fromJson(json, new TypeToken<Collection<moishd.client.dataObjects.ClientMoishdUser>>(){}.getType());
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
	
	private static HttpResponse SendObjToServer(Object obj, String ext){
		
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
		String uriPath = serverPath+"/"+ext;
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
	
private static HttpResponse SendReqToServer(String ext){
		
		final int DURATION = 10000;
		
		/*HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, DURATION);
		HttpConnectionParams.setSoTimeout(params, DURATION);
		
		//DefaultHttpClient httpClient = new DefaultHttpClient(params);*/
		URI uri;
		String uriPath = serverPath+"/"+ext;
		try {
			uri = new URI(uriPath);

			HttpPost postMethod = new HttpPost(uri);

			//ByteArrayEntity req_entity = new ByteArrayEntity(baos.toByteArray());
			//req_entity.setContentType("application/json");

			// associating entity with method
			//postMethod.setEntity(req_entity);
			return ServerRequest.Get().doPost(postMethod);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return null;	
			
	}

}