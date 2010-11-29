package moishd.server.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.objectToTest;

import com.google.gson.Gson;



@SuppressWarnings("serial")
public class GuestbookServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Gson g = new Gson();
		ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
		try {
			String json = (String) ois.readObject();
			objectToTest obj ;
			obj= (objectToTest)g.fromJson(json, objectToTest.class);
			obj.setFirst(obj.GetFirst()-3);
			obj.setlast(obj.GetLast()-3);
			
			resp.setContentType("application/json");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			json = g.toJson(obj);
			oos.writeObject(json);
			
			ServletOutputStream sos = resp.getOutputStream();
			sos.write(baos.toByteArray());
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//resp.getWriter()., classOfT)
	}
}
