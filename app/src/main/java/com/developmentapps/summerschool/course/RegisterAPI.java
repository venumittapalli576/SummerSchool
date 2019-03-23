package com.developmentapps.summerschool.course;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("enroll/enroll.php")
    Call<String>putUser(
            @Field("username") String username,
            @Field("course") String course,
            @Field("Instructorname") String Instructorname,
            @Field("Institutionname") String Institutionname
           );
}