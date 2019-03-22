package com.developmentapps.summerschool.course;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/enroll/enroll.php")
    public void insertUser(
            @Field("username") String username,
            @Field("course") String course,
            @Field("Instructorname") String Instructorname,
            @Field("Institutionname") String Institutionname,
            Callback<Response> callback);
}