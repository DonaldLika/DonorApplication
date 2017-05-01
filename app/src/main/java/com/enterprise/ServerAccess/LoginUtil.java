package com.enterprise.ServerAccess;

import android.content.Context;

import com.enterprise.Session.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by albal on 11/02/2017.
 */

public class LoginUtil {

    private SessionManager sessionManager;

    public LoginUtil(Context context)
    {
        sessionManager=new SessionManager(context);
    }

    public boolean Login(String username,String password)
    {


        sessionManager.setLogin(true,username,"tests");
        return true;
//        HttpPost post = new HttpPost("http://192.168.43.196:8899/userauth/oauth/token");
//        String clientId = "mobile";
//        String clientSecret = "mobilesecret";
//        List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();
//        parametersBody.add(new BasicNameValuePair(OAuthConstants.GRANT_TYPE,
//                "password"));
//        parametersBody.add(new BasicNameValuePair(OAuthConstants.USERNAME,
//                username));
//        parametersBody.add(new BasicNameValuePair(OAuthConstants.PASSWORD,
//                password));
//
//        if (isValid(clientId)) {
//            parametersBody.add(new BasicNameValuePair(OAuthConstants.CLIENT_ID,
//                    clientId));
//        }
//        if (isValid(clientSecret)) {
//            parametersBody.add(new BasicNameValuePair(
//                    OAuthConstants.CLIENT_SECRET, clientSecret));
//        }
//
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpResponse response = null;
//        Token accessToken = null;
//        try {
//            post.addHeader(
//                    OAuthConstants.AUTHORIZATION,
//                    getBasicAuthorizationHeader(clientId, clientSecret));
//            post.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));
//            response = client.execute(post);
//            System.out.println(response.toString());
//            int code = response.getStatusLine().getStatusCode();
//            if (code ==200) {
//                Map map=handleJsonResponse(response);
//                sessionManager.setLogin(true,username,(String)map.get("access_token"));
//                return true;
//            }
//
//        } catch (ClientProtocolException e) {
//        } catch (IOException e) {
//        }
//
//        return false;
    }

    public void Logout()
    {
       sessionManager.logout();
    }

    public boolean isLogined()
    {
        return sessionManager.isLoggedIn();
    }

    public String getToken()
    {
        return sessionManager.getToken();
    }


    private  Map handleJsonResponse(HttpResponse response) {
        JSONObject oauthLoginResponse = null;
        String contentType = response.getEntity().getContentType().getValue();
        try {
            oauthLoginResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
        } catch (ParseException e1) {
        } catch (JSONException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println();
        System.out.println("********** JSON Response Received **********");

        Map<String, Object> outMap = new HashMap<String, Object>();
        Iterator<String> keysIterator = oauthLoginResponse.keys();
        while (keysIterator.hasNext())
        {
            String keyStr = (String)keysIterator.next();
            Object value = null;
            try {
                value = oauthLoginResponse.get(keyStr);
            } catch (JSONException e) {
            }
            outMap.put(keyStr,value);
            System.out.println(String.format("  %s = %s", keyStr, value));
        }

        return outMap;
    }





}
