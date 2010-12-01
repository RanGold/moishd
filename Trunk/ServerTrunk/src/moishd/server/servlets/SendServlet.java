package moishd.server.servlets;

//TODO : change use to server
/*
 * Gson g = new Gson(); ObjectInputStream ois = new
 * ObjectInputStream(req.getInputStream()); DevRegAndIDPairs pair = new
 * DevRegAndIDPairs(); try { String json = (String) ois.readObject();
 * pair = (DevRegAndIDPairs)g.fromJson(json, DevRegAndIDPairs.class);
 * }catch(ClassNotFoundException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * String auth_token = getAuth_token();
 * 
 * //this is a send test - copy this pattern for future use
 * 
 * HttpURLConnection connection = null; try { URL url = null; try { url
 * = new URL("https://android.apis.google.com/c2dm/send"); } catch
 * (MalformedURLException e) { // Exception handling } connection =
 * (HttpURLConnection) url.openConnection();
 * connection.setDoOutput(true); connection.setUseCaches(false);
 * connection.setRequestProperty("Content-Type",
 * "application/x-www-form-urlencoded");
 * connection.setRequestProperty("Authorization", "GoogleLogin auth=" +
 * auth_token);
 * 
 * } catch (Exception e) { // Exception handling } StringBuilder sb =
 * new StringBuilder(); addEncodedParameter(sb, "registration_id",
 * pair.getRegId()); addEncodedParameter(sb, "collapse_key",
 * "someCollapseKey"); addEncodedParameter(sb, "data.payload1",
 * "payload1 message data"); addEncodedParameter(sb, "data.payload2",
 * "payload2 message data"); addEncodedParameter(sb,
 * "data.anotherPayload", "even more message data"); String data =
 * sb.toString();
 * 
 * try { DataOutputStream stream = new
 * DataOutputStream(connection.getOutputStream());
 * stream.writeBytes(data); stream.flush(); stream.close();
 * 
 * switch (connection.getResponseCode()) { case 200: // Success, but
 * check for errors in the body break; case 503: // Service unavailable
 * break; case 401: // Invalid authentication token break; } } catch
 * (IOException e) { // Exception handling }
 */

public class SendServlet {

}
