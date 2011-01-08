package moishd.server.servlets.game;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moishd.server.common.C2DMCommon;
import moishd.server.common.DSCommon;
import moishd.server.common.DataAccessException;
import moishd.server.common.LoggerCommon;
import moishd.server.dataObjects.MoishdGame;
import moishd.server.dataObjects.MoishdUser;
import moishd.server.servlets.GeneralServlet;

public class GameInvReplyServlet extends GeneralServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8439643553907371646L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		super.doPost(request, response);

		if (user != null) {
			try {
				String paramters = request.getReader().readLine();
				
				if (!paramters.endsWith("#") && paramters.split("#").length != 3) {
					LoggerCommon.Get().LogError(this, response, 
							"TimeGameInvReplyServlet: invalid parameters " + paramters);
				} else {
					String gameId = paramters.split("#")[0];
					String invReply = paramters.split("#")[1];
					String popular = (paramters.endsWith("#") ? "" : paramters.split("#")[2]);
					
					MoishdGame tg = DSCommon.GetGameById(gameId);
					MoishdUser mInitUser = DSCommon.GetUserByGoogleId(tg.getPlayerInitId());
					MoishdUser mRecUser = DSCommon.GetUserByGoogleId(tg.getPlayerRecId());
					HashMap<String, String> payload = new HashMap<String, String>();
					payload.put("GameId", String.valueOf(tg.getGameLongId()));
					payload.put("InitName", mInitUser.getUserNick());
					payload.put("RecName", mRecUser.getUserNick());
					boolean deleteGame = true;
					
					if (invReply.equals("Decline")) {
						LoggerCommon.Get().LogInfo(this,"Decline");
						if (mInitUser.isPartnerWith(tg.getPlayerRecId()) &&
								mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
									C2DMCommon.Actions.GameDeclined.toString(), payload);
							mInitUser.setNotBusy();
							mInitUser.SaveChanges();
							mRecUser.setNotBusy();
							mRecUser.SaveChanges(); 
							LoggerCommon.Get().LogInfo(this,"in 1");
						} else if (mInitUser.isPartnerWith(tg.getPlayerRecId()) && 
								!mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
									C2DMCommon.Actions.GameDeclined.toString(), payload);
							mInitUser.setNotBusy();
							mInitUser.SaveChanges();
							LoggerCommon.Get().LogInfo(this,"in 2");
						} else if (!mInitUser.isPartnerWith(tg.getPlayerRecId()) && 
								mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							mRecUser.setNotBusy();
							mRecUser.SaveChanges();
							LoggerCommon.Get().LogInfo(this,"in 3");
						}  else if (!mInitUser.isPartnerWith(tg.getPlayerRecId()) && !mInitUser.isBusy() && 
								!mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
									C2DMCommon.Actions.GameDeclined.toString(), payload);
							LoggerCommon.Get().LogInfo(this,"in 4");
						}
					} else { 
						LoggerCommon.Get().LogInfo(this,"Not Decline");
						if (mInitUser.isPartnerWith(tg.getPlayerRecId()) && 
								!mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
									C2DMCommon.Actions.GameDeclined.toString(), payload);
							C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
									C2DMCommon.Actions.GameCanceled.toString(), payload);
							mInitUser.setNotBusy();
							mInitUser.SaveChanges();
							LoggerCommon.Get().LogInfo(this,"in 5");
						} else if (!mInitUser.isPartnerWith(tg.getPlayerRecId()) && 
								mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
									C2DMCommon.Actions.GameCanceled.toString(), payload);
							mRecUser.setNotBusy();
							mRecUser.SaveChanges();
							LoggerCommon.Get().LogInfo(this,"in 6");
						}  else if (!mInitUser.isPartnerWith(tg.getPlayerRecId()) && 
								!mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							if (!mInitUser.isBusy()) {
//								C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
//										C2DMCommon.Actions.GameDeclined.toString(), payload);
//								LoggerCommon.Get().LogInfo(this,"in 7");
							}
							if (!mRecUser.isBusy()) {
								C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
										C2DMCommon.Actions.GameCanceled.toString(), payload);
								LoggerCommon.Get().LogInfo(this,"in 8");
							}
						} else if (mInitUser.isPartnerWith(tg.getPlayerRecId()) &&
								mRecUser.isPartnerWith(tg.getPlayerInitId())) {
							LoggerCommon.Get().LogInfo(this,"in 9");
							deleteGame = false;
							if (invReply.equals("AcceptTruth")) {
								C2DMCommon.PushGenericMessage(mInitUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameTruth
												.getPopular(popular), payload);
								C2DMCommon.PushGenericMessage(mRecUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameTruth
												.getPopular(popular), payload);
								tg.setGameType(C2DMCommon.Actions.StartGameTruth
										.toString());
								tg.SaveChanges();
							} else if (invReply.equals("AcceptDareSimonPro")) {
								C2DMCommon.PushGenericMessage(mInitUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameDareSimonPro
												.getPopular(popular), payload);
								C2DMCommon.PushGenericMessage(mRecUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameDareSimonPro
												.getPopular(popular), payload);
								tg.setGameType(C2DMCommon.Actions.StartGameDareSimonPro
										.toString());
								tg.SaveChanges();
							} else if (invReply.equals("AcceptDareMixing")) {
								C2DMCommon.PushGenericMessage(mInitUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameDareMixing
												.getPopular(popular), payload);
								C2DMCommon.PushGenericMessage(mRecUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameDareMixing
												.getPopular(popular), payload);
								tg.setGameType(C2DMCommon.Actions.StartGameDareMixing
										.toString());
								tg.SaveChanges();
							} else if (invReply.equals("AcceptDareFastClick")) {
								C2DMCommon.PushGenericMessage(mInitUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameDareFastClick
												.getPopular(popular), payload);
								C2DMCommon.PushGenericMessage(mRecUser
										.getRegisterID(),
										C2DMCommon.Actions.StartGameDareFastClick
												.getPopular(popular), payload);
								tg.setGameType(C2DMCommon.Actions.StartGameDareFastClick
										.toString());
								tg.SaveChanges();
							} else {
								LoggerCommon.Get().LogInfo(this,"in 10");
								C2DMCommon.PushGenericMessage(
										mInitUser.getRegisterID(),
										C2DMCommon.Actions.GameCanceled.toString(),
										payload);
								mInitUser.setNotBusy();
								mInitUser.SaveChanges();
								mRecUser.setNotBusy();
								mRecUser.SaveChanges();
								deleteGame = true;
							}
						}
					}
					
					if (deleteGame) {
						DSCommon.DeleteGameById(gameId);
					}
				}
					
//					if (invReply.equals("Decline") && mInitUser.isBusy() && 
//							mInitUser.getBusyWith().equals(tg.getPlayerRecId())) {
//						C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
//								C2DMCommon.Actions.GameDeclined.toString(), payload);
//						mInitUser.setNotBusy();
//						mInitUser.SaveChanges();
//						mRecUser.setNotBusy();
//						mRecUser.SaveChanges(); 
//					} else if ((!mRecUser.isBusy() || !mRecUser.getBusyWith().equals(tg.getPlayerInitId())) || 
//							(mInitUser.isBusy() && !mInitUser.getBusyWith().equals(tg.getPlayerRecId()))) {
//						if (mRecUser.isPartnerWith(tg.getPlayerRecId()) || !mRecUser.isBusy()) {
//							if (!invReply.equals("Decline")) {
//								C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
//										C2DMCommon.Actions.GameCanceled.toString(), payload);
//							}
//							mRecUser.setNotBusy();
//							mRecUser.SaveChanges();
//						} else {
//							if (!invReply.equals("Decline")) {
//								C2DMCommon.PushGenericMessage(mRecUser.getRegisterID(), 
//										C2DMCommon.Actions.GameCanceled.toString(), payload);
//							}
//							C2DMCommon.PushGenericMessage(mInitUser.getRegisterID(), 
//									C2DMCommon.Actions.GameCanceled.toString(), payload);
//						}
//					} else if (!invReply.equals("Decline") && mInitUser.isBusy() && 
//							mInitUser.getBusyWith().equals(tg.getPlayerRecId())) {
//						if (invReply.equals("AcceptTruth")) {
//							C2DMCommon.PushGenericMessage(mInitUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameTruth
//											.getPopular(popular), payload);
//							C2DMCommon.PushGenericMessage(mRecUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameTruth
//											.getPopular(popular), payload);
//							tg.setGameType(C2DMCommon.Actions.StartGameTruth
//									.toString());
//							tg.SaveChanges();
//						} else if (invReply.equals("AcceptDareSimonPro")) {
//							C2DMCommon.PushGenericMessage(mInitUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameDareSimonPro
//											.getPopular(popular), payload);
//							C2DMCommon.PushGenericMessage(mRecUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameDareSimonPro
//											.getPopular(popular), payload);
//							tg.setGameType(C2DMCommon.Actions.StartGameDareSimonPro
//									.toString());
//							tg.SaveChanges();
//						} else if (invReply.equals("AcceptDareMixing")) {
//							C2DMCommon.PushGenericMessage(mInitUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameDareMixing
//											.getPopular(popular), payload);
//							C2DMCommon.PushGenericMessage(mRecUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameDareMixing
//											.getPopular(popular), payload);
//							tg.setGameType(C2DMCommon.Actions.StartGameDareMixing
//									.toString());
//							tg.SaveChanges();
//						} else if (invReply.equals("AcceptDareFastClick")) {
//							C2DMCommon.PushGenericMessage(mInitUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameDareFastClick
//											.getPopular(popular), payload);
//							C2DMCommon.PushGenericMessage(mRecUser
//									.getRegisterID(),
//									C2DMCommon.Actions.StartGameDareFastClick
//											.getPopular(popular), payload);
//							tg.setGameType(C2DMCommon.Actions.StartGameDareFastClick
//									.toString());
//							tg.SaveChanges();
//						} else {
//							C2DMCommon.PushGenericMessage(
//									mInitUser.getRegisterID(),
//									C2DMCommon.Actions.GameCanceled.toString(),
//									payload);
//							mInitUser.setNotBusy();
//							mInitUser.SaveChanges();
//							mRecUser.setNotBusy();
//							mRecUser.SaveChanges();
//						}
//					}
//				}
			} catch (DataAccessException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			} catch (ServletException e) {
				LoggerCommon.Get().LogError(this, response, e.getMessage(), e.getStackTrace());
			}
		}
	}
}
