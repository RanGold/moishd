package moishd.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.client.dataObjects.ClientMoishdUser;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.GsonCommon;
import moishd.server.common.LoggerCommon;

import com.google.gson.reflect.TypeToken;

public class GetMergedUsersServlet extends GeneralServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5998019628799671093L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		super.doPost(request, response);

		if (user != null) {
			try {
				List<String> filterValues = GsonCommon.GetObjFromJsonStream(
						request.getInputStream(),
						new TypeToken<List<String>>() {
						}.getType());

				if (filterValues.size() == 0) {
					filterValues = mUser.getFriendsFacebookIds();
				} else {
					mUser.setFriendsFacebookIds(DSCommon
							.GetExistingFacebookIds(filterValues));
					mUser.SaveChanges();
				}

				List<ClientMoishdUser> allUsers = DSCommon
						.GetAllRegisteredClientUsers(user.getEmail(), true);

				for (ClientMoishdUser cUser : allUsers) {
					cUser.setFacebookFriend(mUser.getFriendsFacebookIds()
							.contains(cUser.getFacebookID()));

					cUser.setNearByUser(mUser.getLocation().isInitialized() &&
							cUser.getLocation().isInitialized() &&
							DSCommon.CalculateDistance(mUser
							.getLocation().toClientLocaion(), cUser
							.getLocation()) <= ((double) 1));
				}

				GsonCommon.WriteJsonToResponse(allUsers, response);

			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			} catch (ClassNotFoundException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			} catch (SecurityException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(),
						e.getStackTrace());
			}
		}
	}

}
