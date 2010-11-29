package moishd.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;


public class AndroidUtility {
	
	public static HttpResponse SendObjToServer(Object obj, String extension){
		
		final int DURATION = 10000;
		String serverPath = "http://10.0.2.2:8888/" ; //to be replaced with http://moish-d.appspot.com/
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, DURATION);
		HttpConnectionParams.setSoTimeout(params, DURATION);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		HttpResponse response ;
		URI uri;
		try {
			uri = new URI(serverPath+extension);

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
			response = httpClient.execute(postMethod);
			return response;
		
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		return null;	
			
	}

}
