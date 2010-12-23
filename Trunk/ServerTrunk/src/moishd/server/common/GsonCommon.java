package moishd.server.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonCommon {
	private GsonCommon() {
	}
	
	public static void WriteJsonToResponse(Object obj, HttpServletResponse response) 
	throws IOException {
		response.setContentType("application/json");

		//Gson g = new Gson();
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
		String json = g.toJson(obj);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(json);
		} finally {
			if (oos != null) {
				oos.flush();
				oos.close();
			}
		}
	}
	public static <T> T GetObjFromJsonStream(InputStream s, Type t) 
	throws IOException,ClassNotFoundException {
		// TODO : make this work
		ObjectInputStream ois = new ObjectInputStream(s);
		String json = (String) ois.readObject();
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
		@SuppressWarnings("unchecked")
		T obj = (T)g.fromJson(json, t);
		ois.close();
		return obj;
	}
}
