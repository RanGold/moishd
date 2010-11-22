/*package android.Twitternator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TwitterPoster extends Activity {
    /** Called when the activity is first created. */
/*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button postTweet = (Button) findViewById(R.id.TwitterPosterPostButton);
        final EditText tweetEditText = (EditText) findViewById(R.id.TwitterPosterMessageText);
        postTweet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
                            String twitterStatus = tweetEditText.getText().toString();
			    Toast.makeText(TwitterPoster.this, 
                                "You tweeted "+twitterStatus, 
                                Toast.LENGTH_LONG).show();
			}
		});
    }
}*/

package moishd.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class TwitterPoster extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button postTweet = (Button) findViewById(R.id.TwitterPosterPostButton);
		final EditText tweetEditText = (EditText) findViewById(R.id.TwitterPosterMessageText);
		postTweet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				objectToTest obj = new objectToTest(10, 10);
				Toast.makeText(TwitterPoster.this, 
						"object is "+obj.GetFirst()+" "+obj.GetLast(), 
						Toast.LENGTH_LONG).show();
				
				try {
					objectToTest res =  send(obj);
					Toast.makeText(TwitterPoster.this, 
							"object is "+res.GetFirst()+" "+res.GetLast(), 
							Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});
	}


	private objectToTest send(objectToTest obj) throws IOException, URISyntaxException{
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 100000);
		HttpConnectionParams.setSoTimeout(params, 100000);

		DefaultHttpClient httpClient = new DefaultHttpClient(params);

		URI uri = new URI("http://moish-d.appspot.com/guestbook");
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


		HttpResponse response = httpClient.execute(postMethod);
		HttpEntity resp_entity = response.getEntity();
		if (resp_entity != null) {
			ObjectInputStream ois = new ObjectInputStream(response.getEntity().getContent());
			try {
				json = (String) ois.readObject();
				obj= (objectToTest)g.fromJson(json, objectToTest.class);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		/*  httpClient.execute(postMethod, new ResponseHandler<Void>() {
	        public Void handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
	        	HttpEntity resp_entity = response.getEntity();
	        	if (resp_entity != null) {
	        		ObjectInputStream ois = new ObjectInputStream(response.getEntity().getContent());
	        		try {
	        			String json = (String) ois.readObject();

	        			objectToTest obj ;
	        			Gson g = new Gson();
	        			obj= (objectToTest)g.fromJson(json, objectToTest.class);
	        			result.setFirst(obj.GetFirst());
	        			result.setlast(obj.GetLast());
	        		} catch (ClassNotFoundException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		}

	        	}
	      /*      try {
	      //       objectToTest returnObj = response.

	            	// byte[] data = EntityUtils.toByteArray(resp_entity);
	            //  ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
	            //  dataFromServlet = (Hashtable<DataKeys, Serializable>) ois.readObject();
	           //   Log.i(getClass().getSimpleName(), "data size from servlet=" + data.toString());
	          //    Log.i(getClass().getSimpleName(), "data hashtable from servlet=" + dataFromServlet.toString());
	            }
	            catch (Exception e) {
	              Log.e(getClass().getSimpleName(), "problem processing post response", e);
	            }

	          }
	          else {
	            throw new IOException(
	                new StringBuffer()
	                    .append("HTTP response : ").append(response.getStatusLine())
	                    .toString());
	          }
	          return null;*/
		return obj;
	}

}