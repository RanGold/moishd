package moishd.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.dataObjects.MoishdUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;


public class GetAllUsersServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7171012395494987373L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		ServletOutputStream sos = resp.getOutputStream();
		sos.flush();
		/*UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		if (user == null) {
			resp.getWriter().write("Not Logged In");
		}
		else */{
			// TODO: check if user exists, else add him
			try {
				resp.setContentType("application/json");
				
				@SuppressWarnings("unchecked")
				List<MoishdUser> users = (List<MoishdUser>) PMF.get().getPersistenceManager().newQuery(MoishdUser.class).execute();

				Gson g = new Gson();
				String json = g.toJson(users);
				oos.writeObject(json);

				//resp.getWriter().print(json.toCharArray());
				sos.write(baos.toByteArray());
			}
			finally {
				//sos.close();
				baos.close();
				oos.close();
				PMF.get().close();
			}
		}
	}
}
