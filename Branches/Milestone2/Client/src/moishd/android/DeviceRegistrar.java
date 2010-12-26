/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package moishd.android;

import moishd.client.dataObjects.ClientMoishdUser;
import android.content.Context;
import android.util.Log;

/**
 * Register/unregister with the Moish'd! App Engine server.
 */
public class DeviceRegistrar {
    public static final String STATUS_EXTRA = "Status";
    public static final int REGISTERED_STATUS = 1;
    public static final int AUTH_ERROR_STATUS = 2;
    public static final int UNREGISTERED_STATUS = 3;
    public static final int ERROR_STATUS = 4;

    private static final String TAG = "DeviceRegistrar";
    static final String SENDER_ID = "app.moishd@gmail.com";


    public static void registerWithServer(final Context context, final String deviceRegistrationID) {
        new Thread(new Runnable() {
            public void run() {
                //Intent updateUIIntent = new Intent("com.google.ctp.UPDATE_UI");
                try {
                    
                	ClientMoishdUser user = new ClientMoishdUser();
                	user.setRegisterID(deviceRegistrationID);
                	
                	int statusCode = ServerCommunication.registerC2DMToServer(user); 
                    if (statusCode == 200) {
                    	Log.d("TEST","Registration Successful");
                      /*  SharedPreferences settings = Prefs.get(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("deviceRegistrationID", deviceRegistrationID);
                        editor.commit();
                     //   updateUIIntent.putExtra(STATUS_EXTRA, REGISTERED_STATUS);
*/                    } else if (statusCode == 400) {
                     //  updateUIIntent.putExtra(STATUS_EXTRA, AUTH_ERROR_STATUS);
                    } else {
                        Log.w(TAG, "Registration error " +
                                String.valueOf(statusCode));
                      //  updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
                    }
                   // context.sendBroadcast(updateUIIntent);
              //  } catch (AppEngineClient.PendingAuthException pae) {
              //      // Ignore - we'll reregister later
                } catch (Exception e) {
                    Log.w(TAG, "Registration error " + e.getMessage());
                 //   updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
                  //  context.sendBroadcast(updateUIIntent);
                }
            }
        }).start();
    }

    public static void unregisterWithServer(final Context context) {
        new Thread(new Runnable() {
            public void run() {
              //  Intent updateUIIntent = new Intent("com.google.ctp.UPDATE_UI");
                try {
                    
                	int statusCode = ServerCommunication.unregisterC2DMToServer();
                	
                    if (statusCode == 200) {
                    	Log.d("TEST","Unregistration Successful");
                      /*  SharedPreferences settings = Prefs.get(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.remove("deviceRegistrationID");
                        editor.commit();
                        updateUIIntent.putExtra(STATUS_EXTRA, UNREGISTERED_STATUS);*/
                    } else {
                        Log.w(TAG, "Unregistration error " + String.valueOf(statusCode));
                       // updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
                    }
                } catch (Exception e) {
                   // updateUIIntent.putExtra(STATUS_EXTRA, ERROR_STATUS);
                    Log.w(TAG, "Unegistration error " + e.getMessage());
                }

                // Update dialog activity
               // context.sendBroadcast(updateUIIntent);
            }
        
        }).start();
    }

}
