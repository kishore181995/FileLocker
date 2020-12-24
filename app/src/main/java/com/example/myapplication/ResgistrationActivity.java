package com.example.myapplication;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class ResgistrationActivity extends AppCompatActivity {

    AppCompatTextView mRegisterTv,mLoginTv;
    AppCompatEditText pin_one,pin_two,pin_three,pin_four,anwserEt;
    AppCompatEditText pinCon_one,pinCon_two,pinCon_three,pinCon_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration_screen);

        mLoginTv = findViewById(R.id.alreadyUser);
        mLoginTv.setVisibility(View.GONE);
        mRegisterTv= findViewById(R.id.resgisterSignTv);

        pin_one = findViewById(R.id.pin1Et);
        pin_two = findViewById(R.id.pin2Et);
        pin_three = findViewById(R.id.pin3Et);
        pin_four = findViewById(R.id.pin4Et);
        anwserEt = findViewById(R.id.secretAnwser);

        pinCon_one = findViewById(R.id.pin1ConfirmEt);
        pinCon_two = findViewById(R.id.pin2ConfirmEt);
        pinCon_three = findViewById(R.id.pin3ConfirmEt);
        pinCon_four = findViewById(R.id.pinConfirm4Et);


        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin_one.getText().toString().length()>0 && pin_two.getText().toString().length()>0 && pin_three.getText().toString().length()>0 && pin_four.getText().toString().length()>0)
                {
                    if (pinCon_one.getText().toString().length() > 0 && pinCon_two.getText().toString().length() > 0 && pinCon_three.getText().toString().length() > 0 && pinCon_four.getText().toString().length() > 0)
                    {
                        if (pin_one.getText().toString().equalsIgnoreCase(pinCon_one.getText().toString())) {
                            if (pin_two.getText().toString().equalsIgnoreCase(pinCon_two.getText().toString())) {
                                if (pin_three.getText().toString().equalsIgnoreCase(pinCon_three.getText().toString())) {
                                    if (pin_four.getText().toString().equalsIgnoreCase(pinCon_four.getText().toString())) {
                                        if (anwserEt.getText().toString().length()>0)
                                        {
                                            Toast.makeText(ResgistrationActivity.this, "New pin created , successfully !", Toast.LENGTH_SHORT).show();
                                            PrefManager prefManager = new PrefManager(ResgistrationActivity.this);
                                            prefManager.setUserToken(pin_one.getText().toString()+pin_two.getText().toString()+pin_three.getText().toString()+pin_four.getText().toString());
                                            prefManager.setUserEmail(anwserEt.getText().toString());
                                            Intent loginScreen = new Intent(ResgistrationActivity.this,LoginActivity.class);
                                            startActivity(loginScreen);
                                            finish();
                                        }else{
                                            Toast.makeText(ResgistrationActivity.this, "Enter secret anwser.", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        popupError();
                                    }
                                } else {
                                    popupError();
                                }
                            } else {
                                popupError();
                            }
                        } else {
                            popupError();
                        }
                    } else {
                        Toast.makeText(ResgistrationActivity.this, "Enter confirm pin", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(ResgistrationActivity.this, "Enter 4 digit pin", Toast.LENGTH_SHORT).show();
                }
            }
        });


        pin_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_one.setCursorVisible(true);
            }
        });

        pin_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_two.setCursorVisible(true);
            }
        });

        pin_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_three.setCursorVisible(true);
            }
        });

        pin_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin_four.setCursorVisible(true);
            }
        });


        mLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        pin_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pin_two.requestFocus();
                    pin_two.setCursorVisible(true);
                    pin_two.setSelection(pin_two.getText().length());
                }
                else {
                }
            }
        });

        pin_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pin_three.requestFocus();
                    pin_three.setCursorVisible(true);
                    pin_three.setSelection(pin_three.getText().length());
                }
                else {
                }
            }
        });

        pin_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pin_four.requestFocus();
                    pin_four.setCursorVisible(true);
                    pin_four.setSelection(pin_four.getText().length());
                }
                else {
                }
            }
        });

        pin_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pin_four.requestFocus();
                    pin_four.setCursorVisible(true);
                    pin_four.setSelection(pin_four.getText().length());
                    CommonUtils.hideKeyboardFrom(ResgistrationActivity.this,pin_four);
                }
                else {
                }
            }
        });

        // Confirm Pins

        pinCon_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCon_one.setCursorVisible(true);
            }
        });

        pinCon_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCon_two.setCursorVisible(true);
            }
        });

        pinCon_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCon_three.setCursorVisible(true);
            }
        });

        pinCon_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinCon_four.setCursorVisible(true);
            }
        });


        pinCon_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pinCon_two.requestFocus();
                    pinCon_two.setCursorVisible(true);
                    pinCon_two.setSelection(pinCon_two.getText().length());
                }
                else {
                }
            }
        });

        pinCon_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pinCon_three.requestFocus();
                    pinCon_three.setCursorVisible(true);
                    pinCon_three.setSelection(pinCon_three.getText().length());
                }
                else {
                }
            }
        });

        pinCon_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pinCon_four.requestFocus();
                    pinCon_four.setCursorVisible(true);
                    pinCon_four.setSelection(pinCon_four.getText().length());
                }
                else {
                }
            }
        });

        pinCon_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 1) {
                    pinCon_four.requestFocus();
                    pinCon_four.setCursorVisible(true);
                    pinCon_four.setSelection(pinCon_four.getText().length());
                    CommonUtils.hideKeyboardFrom(ResgistrationActivity.this,pinCon_four);
                }
                else {
                }
            }
        });




    }

    private void popupError() {
        Toast.makeText(ResgistrationActivity.this,"Pins does not match !",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }
}
