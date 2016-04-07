package com.dabkick.partner.tel;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dabkick.sdk.DabKick_Agent;
import com.dabkick.sdk.Dabkick;


public class MainActivity extends AppCompatActivity {

    Button continueBtn;
    EditText email, phone, id;
    //CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Dabkick.init(this);
        Dabkick.register("com.dabkick.partner.tel","email.com","fbID","partnerID","phone");

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email.getText().toString().isEmpty() ||
                        !phone.getText().toString().isEmpty() || !id.getText().toString().isEmpty()){

                    /*DabKick_Agent.DK_Register("TELCO_ID", null, email.getText().toString(),
                            phone.getText().toString(), id.getText().toString(), MainActivity.this);*/
                    //Move to next Activity

                    Intent selectVideo = new Intent(MainActivity.this, SelectVideo.class);
                    selectVideo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(selectVideo);
                    finish();


                }else{

                    Toast.makeText(MainActivity.this, "All fields Empty. Enter atleast one field", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    void init(){
        
        continueBtn = (Button)findViewById(R.id.cnt_btn);
        email = (EditText)findViewById(R.id.email_id);
        phone = (EditText)findViewById(R.id.ph_num);
        id = (EditText)findViewById(R.id.un_id);

    }
}
