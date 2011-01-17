package moishd.server.servlets.C2DM;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;
import moishd.server.servlets.GeneralServlet;

import com.google.gson.reflect.TypeToken;

public class RegisterServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws IOException {
		checkRegister = false;
		
		super.doPost(request, response);

		if (user != null) {
			try {
				ClientMoishdUser clientUser = 
					GsonCommon.GetObjFromJsonStream(request.getInputStream(), 
							new TypeToken<ClientMoishdUser>(){}.getType());

				mUser.setRegisterID(clientUser.getRegisterID());
				mUser.setRegistered(true);
				mUser.SaveChanges();
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
