package moishd.android;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

import moishd.common.ServerRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

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
	
	private static HttpResponse SendObjToServer(Object obj, String ext){
		
		final int DURATION = 10000;
		String serverPath = "http://moish-d.appspot.com"; // ""http://10.0.2.2:8888/"
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
