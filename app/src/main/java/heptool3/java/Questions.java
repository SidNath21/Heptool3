package heptool3.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class Questions extends AppCompatActivity {

    private Button button;
    private RadioButton country1, country2, country3, male, female, yes, no, ALT1, ALT2, ALT3, HBV1, HBV2, HBV3,
            radioButton_gender, radioButton_cirrhosis, radioButton_ALT, radioButton_HBV, radioButton_country;
    private RadioGroup radioGroup_country, radioGroup_gender, radioGroup_cirrhosis, radioGroup_ALT, radioGroup_HBVDNA;
    private EditText age;
    private String Patient_gender, Patient_cirrhosis, Patient_ALT , Patient_HBV , Patient_country;
    private String errorMessage = "";
    private boolean hasError = false;




    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        getInputs();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/")
                .build();

        final QuestionsSpreadsheetWebService spreadsheetWebService = retrofit.create(QuestionsSpreadsheetWebService.class);


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                getButtonInputs();
                if(Patient_cirrhosis.equals("Yes")){
                    Patient_ALT = "";
                    Patient_HBV = "";
                }
                    Call<Void> completeQuestionnaireCall = spreadsheetWebService.completeQuestionnaire1(
                            Patient_country, age.getText().toString(), Patient_gender, Patient_cirrhosis, Patient_ALT, Patient_HBV);
                    completeQuestionnaireCall.enqueue(callCallback);
                    openResults(hasError, errorMessage);
            }
        });
    }

    private final Callback<Void> callCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Log.d("XXX", "Submitted. " + response + " " + Patient_HBV);
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("XXX", "Failed", t);
        }
    };

        public void getButtonInputs(){

        int country_Id = radioGroup_country.getCheckedRadioButtonId();

        if(country_Id == -1){
            hasError = true;
            errorMessage = "Please select the prevalence of HBV in your country";
            return;
        }

        radioButton_country = findViewById(country_Id);
        Patient_country = (String) radioButton_country.getText();

        if(age.getText().toString().equals("") ||
                (Integer.parseInt(age.getText().toString()) < 5 || Integer.parseInt(age.getText().toString()) > 110)){
            hasError = true;
            errorMessage = "Please enter an age between 5 and 110";
            return;
        }

        int gender_Id = radioGroup_gender.getCheckedRadioButtonId();

        if(gender_Id == -1){
            hasError = true;
            errorMessage = "Please indicate your gender";
            return;
        }

        radioButton_gender = findViewById(gender_Id);
        Patient_gender = (String) radioButton_gender.getText();

        int cirrhosis_Id = radioGroup_cirrhosis.getCheckedRadioButtonId();

        if(cirrhosis_Id == -1){
            hasError = true;
            errorMessage = "Please answer if you have cirrhosis";
            return;
        }

        radioButton_cirrhosis = findViewById(cirrhosis_Id);
        Patient_cirrhosis = (String) radioButton_cirrhosis.getText();

        if(Patient_cirrhosis.equals("No")) {

            int ALT_Id = radioGroup_ALT.getCheckedRadioButtonId();
            int HBV_Id = radioGroup_HBVDNA.getCheckedRadioButtonId();

            if(ALT_Id == -1 || HBV_Id == -1){
                hasError = true;
                errorMessage = "Please answer questions 5 and 6";
                return;
            }
            else {
                radioButton_HBV = findViewById(HBV_Id);
                Patient_HBV = (String) radioButton_HBV.getText();

                radioButton_ALT = findViewById(ALT_Id);
                Patient_ALT = (String) radioButton_ALT.getText();


            }

        }

    }

        public void getInputs(){
        button = (Button) findViewById(R.id.button_Calculate);

        country1 = (RadioButton) findViewById(R.id.radioButton_country1);
        country2 = (RadioButton) findViewById(R.id.radioButton_country2);
        country3 = (RadioButton) findViewById(R.id.radioButton_country3);

        male = (RadioButton) findViewById(R.id.radioButton_Male);
        female = (RadioButton) findViewById(R.id.radioButton_Female);
        yes = (RadioButton) findViewById(R.id.radioButton_Yes);
        no = (RadioButton) findViewById(R.id.radioButton_No);
        ALT1 = (RadioButton) findViewById(R.id.radioButton_ALT1);
        ALT2 = (RadioButton) findViewById(R.id.radioButton_ALT2);
        ALT3 = (RadioButton) findViewById(R.id.radioButton_ALT3);
        HBV1 = (RadioButton) findViewById(R.id.radioButton_HBV1);
        HBV2 = (RadioButton) findViewById(R.id.radioButton_HBV2);
        HBV3 = (RadioButton) findViewById(R.id.radioButton_HBV3);

        radioGroup_country = (RadioGroup) findViewById(R.id.radioGroup_country);
        radioGroup_gender = (RadioGroup) findViewById(R.id.radioGroup_Gender);
        radioGroup_cirrhosis = (RadioGroup) findViewById(R.id.radioGroup_cirrhosis);
        radioGroup_ALT = (RadioGroup) findViewById(R.id.radioGroup_ALT);
        radioGroup_HBVDNA = (RadioGroup) findViewById(R.id.radioGroup_HBVDNA);

        age = (EditText) findViewById(R.id.editText_Age);

    }

    public void openResults(boolean hasError, String errorMessage){

        if(hasError) {
            Intent error = new Intent(getApplicationContext(), Questions.class);
            startActivity(error);
            Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }

        else {

            Intent intent = new Intent(this, Results.class);
            intent.putExtra("Patient_Age", Integer.parseInt(age.getText().toString()));
            intent.putExtra("Patient_Gender", Patient_gender);
            intent.putExtra("Patient_Cirrhosis", Patient_cirrhosis);
            intent.putExtra("Patient_ALT", Patient_ALT);
            intent.putExtra("Patient_HBV", Patient_HBV);
            intent.putExtra("Patient_country", Patient_country);

            startActivity(intent);
        }
    }
}
