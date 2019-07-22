package heptool3.java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private TextView info, info2;
    private Button start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.textView20);
        info.setVisibility(View.INVISIBLE);
        info2 = (TextView) findViewById(R.id.textView_Info2);
        info2.setVisibility(View.INVISIBLE);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                openQuestions();
            }
        });

    }

    public void openQuestions(){
        Intent intent = new Intent(this, Questions.class);
        startActivity(intent);
    }

    public void displayInfo(View view){
        String str = view.getTag().toString();
        info.setText("\n" + str + "\n");
        info.setVisibility(View.VISIBLE);

    }

    public void displayInfo2(View view){
        String str = view.getTag().toString();
        info2.setText("\n" + str + "\n");
        info2.setVisibility(View.VISIBLE);

    }
}
