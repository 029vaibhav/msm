package com.mobiweb.msm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiweb.msm.models.GCM;
import com.mobiweb.msm.models.GCMmessage;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.List;

public class Constants {

    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public final static String GCM_URL = "https://gcm-http.googleapis.com/gcm/send";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static List<String> tokenList;


    public static DateTime getDate(String date) {
        DateTime startDateTime = DateTime.parse(date, DateTimeFormat.forPattern(DATE_FORMAT));
        return startDateTime;
    }

    public static DateTime getShortDate(String date) {
        DateTime startDateTime = DateTime.parse(date, DateTimeFormat.forPattern(SHORT_DATE_FORMAT));
        return startDateTime;
    }

    public static void SendMessageToGCM(List<String> registrationIDS, Object data) {
        GCM gcm = new GCM();
        gcm.setRegistration_ids(registrationIDS);
        GCMmessage gcMmessage = new GCMmessage();
        gcMmessage.setMessage(data);
        gcm.setData(gcMmessage);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(gcm);
            RequestBody body = RequestBody.create(JSON, jsonInString);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            okhttp3.OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
            Request request = new okhttp3.Request.Builder()
                    .url(GCM_URL)
                    .addHeader("Authorization", "key=AIzaSyAGpIWuz7RhtLqBbz457HjZmBpQx9knHhA")
                    .post(body)
                    .build();
            Response execute = client.newCall(request).execute();
            ResponseBody body1 = execute.body();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
