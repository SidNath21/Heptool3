package heptool3.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import heptool3.java.R;

public class Results extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();


        int age = (intent.getIntExtra("Patient_Age", -1));
        String cirrhosis = intent.getStringExtra("Patient_Cirrhosis");
        String gender = intent.getStringExtra("Patient_Gender");
        String ALT = intent.getStringExtra("Patient_ALT");
        String HBV = intent.getStringExtra("Patient_HBV");
        String country = intent.getStringExtra("Patient_country");

        TextView textView = (TextView) findViewById(R.id.textView_PatientResults);
        textView.setText(getPatientText(age, gender, cirrhosis , ALT , HBV ));

        TextView whoRec = (TextView) findViewById(R.id.textView_WHO);
        whoRec.setText(getWhoRec(cirrhosis, ALT, HBV));


    }

    public String getPatientText(int age, String gender, String cirrhosis , String ALT , String HBV){
        String str = "";
        str += age + " year old ";
        str += gender.toLowerCase();
        if(cirrhosis.equals("Yes")) str += " with cirrhosis";
        else {
            str += " without cirrhosis, " + ALT + " ALT Level, and " + HBV + " HBV DNA";
        }
        return str;
    }

    public String getWhoRec(String cirrhosis, String ALT, String HBV){
        String str = "\n You Need Monitoring";
        if(cirrhosis.equals("Yes") || (cirrhosis.equals("No") && ALT.equals("Persistently Abnormal")
                && HBV.equals("Greater than 20,000 IU/mL"))){
            str += " and Treatment";
        }

        return str += ". All people with CHB should be monitored regularly for disease activity/progression " +
                "and detection of HCC, and after stopping treatment for evidence of reactivation.\n";
    }
}
