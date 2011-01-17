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

		String json = GetJsonString(obj);
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
	
	public static String GetJsonString(Object obj) {
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
		return g.toJson(obj);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T GetObjFromJsonString(String s, Type t) {
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").create();
		return (T)g.fromJson(s, t);
	}
	
	public static <T> T GetObjFromJsonStream(InputStream s, Type t) 
	throws IOException,ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(s);
		String json = (String) ois.readObject();
		T obj = GetObjFromJsonString(json,t);
		ois.close();
		return obj;
	}
}
