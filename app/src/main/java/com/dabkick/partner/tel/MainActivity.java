package com.dabkick.partner.tel;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dabkick.sdk.Dabkick;
import com.dabkick.sdk.Global.UserIdentifier;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {

    private RelativeLayout container;
    private RelativeLayout userDetails;
    private RelativeLayout emailFields;
    private TextView email;
    private CustomEdTxt emailId;
    private RelativeLayout phoneFields;
    private TextView phone;
    private CustomEdTxt phNum;
    private RelativeLayout uniqueFields;
    private TextView id;
    private CustomEdTxt unId;
    private LinearLayout registeredInfo;
    private TextView emailText;
    private TextView phoneText;
    private TextView uniqueIDText;
    private Button resetBtn;
    private Button cntBtn;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-04-08 23:40:36 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        container = (RelativeLayout)findViewById( R.id.container );
        userDetails = (RelativeLayout)findViewById( R.id.user_details );
        emailFields = (RelativeLayout)findViewById( R.id.email_fields );
        email = (TextView)findViewById( R.id.email );
        emailId = (CustomEdTxt) findViewById( R.id.email_id );
        phoneFields = (RelativeLayout)findViewById( R.id.phone_fields );
        phone = (TextView)findViewById( R.id.phone );
        phNum = (CustomEdTxt)findViewById( R.id.ph_num );
        uniqueFields = (RelativeLayout)findViewById( R.id.unique_fields );
        id = (TextView)findViewById( R.id.id );
        unId = (CustomEdTxt)findViewById( R.id.un_id );
        registeredInfo = (LinearLayout)findViewById( R.id.registeredInfo );
        emailText = (TextView)findViewById( R.id.emailText );
        phoneText = (TextView)findViewById( R.id.phoneText );
        uniqueIDText = (TextView)findViewById( R.id.uniqueIDText );
        resetBtn = (Button)findViewById( R.id.reset_btn );
        cntBtn = (Button)findViewById( R.id.cnt_btn );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        if (Dabkick.isRegistered(this))
        {
            registeredInfo.setVisibility(View.VISIBLE);
            userDetails.setVisibility(View.GONE);
            resetBtn.setVisibility(View.VISIBLE);

            UserIdentifier userIdentifier = UserIdentifier.getStoredValue(this);
            emailText.setText(userIdentifier.email);
            phoneText.setText(userIdentifier.phoneNumber);
            uniqueIDText.setText(userIdentifier.uniqueID);

        }
        else {
            registeredInfo.setVisibility(View.GONE);
            userDetails.setVisibility(View.VISIBLE);
            resetBtn.setVisibility(View.GONE);
        }

        RxView.clicks(resetBtn).throttleFirst(300,TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Dabkick.reset();
                registeredInfo.setVisibility(View.GONE);
                userDetails.setVisibility(View.VISIBLE);
                resetBtn.setVisibility(View.GONE);
            }
        });

        emailId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    requestFocus(phNum);
                    Log.d("vallabh","inside email id");
                    return true;
                }
                return handled;
            }
        });

        phNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    requestFocus(unId);
                    Log.d("vallabh","inside phno");
                    return true;
                }
                return handled;
            }
        });

        RxView.clicks(cntBtn).throttleFirst(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

                if (Dabkick.isRegistered(MainActivity.this))
                {
                    Intent selectVideo = new Intent(MainActivity.this, SelectVideo.class);
                    selectVideo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(selectVideo);
                    finish();
                    return;
                }

                if (!emailId.getText().toString().isEmpty() ||
                        !phNum.getText().toString().isEmpty() || !unId.getText().toString().isEmpty()) {

                    String e = emailId.getText().toString();
                    String p = phNum.getText().toString();
                    String partnerID = unId.getText().toString();

                    UserIdentifier identifier = new UserIdentifier();
                    identifier.email = e;
                    identifier.phoneNumber = p;
                    identifier.uniqueID = partnerID;

                    Dabkick.setOnRegisterFinished(new Dabkick.OnRegisterFinishedListener() {
                        @Override
                        public void onRegistered(boolean b, String s) {

                            Runnable ok = new Runnable() {
                                @Override
                                public void run() {
                                    Intent selectVideo = new Intent(MainActivity.this, SelectVideo.class);
                                    selectVideo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(selectVideo);
                                    finish();
                                }
                            };
                            DialogHelper.popupAlertDialog(MainActivity.this, null, "The app is now registered with DabKick with the provided user credentials.", "ok", ok);
                        }
                    });
                    String packageName = MainActivity.this.getPackageName();
                    Dabkick.register(MainActivity.this,packageName, identifier);


                } else {

                    Toast.makeText(MainActivity.this, "All fields Empty. Enter at least one field", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    void requestFocus(final EditText editText) {
        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
            }
        });
    }
}
