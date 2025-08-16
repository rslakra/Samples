package com.rslakra.springbootsamples.emailservice.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Rohtash Lakra
 * @created 8/12/21 3:26 PM
 */
public final class VerifyRecaptcha {
    
    private static final String GOOGLE_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRETS = "6LemD2YUAAAAAB4Oei3dOI0nSk1NLTOpPB3HdGGT";
    private final static String USER_AGENT = "Mozilla/5.0";
    
    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
    
    static {
        GSON_BUILDER.setPrettyPrinting();
    }
    
    /**
     * @param gRecaptchaResponse
     * @return
     * @throws IOException
     */
    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }
        
        try {
            URL obj = new URL(GOOGLE_URL);
            HttpsURLConnection httpConnection = (HttpsURLConnection) obj.openConnection();
            
            // add request header
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            
            String postParams = "secret=" + SECRETS + "&response=" + gRecaptchaResponse;
            
            // Send post request
            httpConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // parse JSON response and return 'success' value
            JsonObject jsonObject = GSON_BUILDER.create().fromJson(response.toString(), JsonObject.class);
            return jsonObject.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

