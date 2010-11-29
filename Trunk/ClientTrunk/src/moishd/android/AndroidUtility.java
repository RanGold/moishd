package moishd.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import moishd.client.dataObjects.objectToTest;
import moishd.common.ServerRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;


public class AndroidUtility {
	
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
	
	public static boolean enlist(moishd.client.dataObjects.ClientMoishdUser user){
		HttpResponse response = SendObjToServer(user, "UserLogin");
		HttpEntity resp_entity = response.getEntity();

		if (resp_entity==null)
			return false;
		else 
			return true;
	}
	
	
	private static HttpResponse SendObjToServer(Object obj, String ext){
		
		final int DURATION = 10000;
		String serverPath = "http://10.0.2.2:8888/" ; 
		//to be replaced with http://moish-d.appspot.com/
		//when change - change also in ServerRequest
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, DURATION);
		HttpConnectionParams.setSoTimeout(params, DURATION);
		
		//DefaultHttpClient httpClient = new DefaultHttpClient(params);
		HttpResponse response ;
		URI uri;
		try {
			uri = new URI(serverPath+ext);

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

}
