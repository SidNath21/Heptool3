package heptool3.java;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QuestionsSpreadsheetWebService {
        @POST("e/1FAIpQLSdn2uwjJulP_o7G1C1v_a4yRxvsmNjH9roH0a0K_eE-BjX06g/formResponse")
        @FormUrlEncoded
        Call<Void> completeQuestionnaire1(
                @Field("entry.2082501048") String Patient_Country,
                @Field("entry.1620121582") String Patient_Age,
                @Field("entry.1635645681") String Patient_Gender,
                @Field("entry.704941588")  String Patient_Cirrhosis,
                @Field("entry.1232184041") String Patient_ALT,
                @Field("entry.918783621") String Patient_HBV
        );

        Call<Void> completeQuestionnaire2(
                @Field("entry.2082501048") String Patient_Country,
                @Field("entry.1620121582") String Patient_Age,
                @Field("entry.1635645681") String Patient_Gender,
                @Field("entry.704941588")  String Patient_Cirrhosis

        );

}
