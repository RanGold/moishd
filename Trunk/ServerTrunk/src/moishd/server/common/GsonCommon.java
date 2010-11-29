package moishd.server.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonCommon {

	public static <T> T GetObjFromJsonStream(InputStream s, Class<T> classof) throws IOException,
			ClassNotFoundException {
		// TODO : make this work
		ObjectInputStream ois = new ObjectInputStream(s);
		String json = (String) ois.readObject();
		@SuppressWarnings("unchecked")
		T obj = (T)(new Gson()).fromJson(json, new TypeToken<T>() {
		}.getType());
		return obj;
	}
}
