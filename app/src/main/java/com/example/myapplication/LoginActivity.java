package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity  extends AppCompatActivity {

    AppCompatTextView mRegisterTv,mLoginTv,forgotPassTv;
    AppCompatImageView loginHow;
    AppCompatEditText pin_one,pin_two,pin_three,pin_four;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        pin_one = findViewById(R.id.pin1Et);
        pin_two = findViewById(R.id.pin2Et);
        pin_three = findViewById(R.id.pin3Et);
        pin_four = findViewById(R.id.pin4Et);
        forgotPassTv = findViewById(R.id.forgotTv);
        loginHow = findViewById(R.id.loginHow);


        loginHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginHow();
            }
        });

        mRegisterTv = findViewById(R.id.resgisterTv);
        mLoginTv = findViewById(R.id.loginTv);
        prefManager = new PrefManager(LoginActivity.this);

        PrefManager prefManager = new PrefManager(LoginActivity.this);
        if (prefManager.isUserFirstTimeLaunch())
        {
            mRegisterTv.setVisibility(View.VISIBLE);
        }else{
            mRegisterTv.setVisibility(View.GONE);
        }

        forgotPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassQuestion();
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

        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this,ResgistrationActivity.class);
                startActivity(register);
                pin_one.setText("");
                pin_two.setText("");
                pin_three.setText("");
                pin_four.setText("");
            }
        });

        String s ="23";
        ArrayList<String> subsList = new ArrayList();
        check(s);


        mLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin_one.getText().toString().length()>0 && pin_two.getText().toString().length()>0 && pin_three.getText().toString().length()>0 && pin_four.getText().toString().length()>0)
                {
                    String enteredPin = pin_one.getText().toString()+pin_two.getText().toString()+pin_three.getText().toString()+pin_four.getText().toString();
                    Log.d("EnteredPin",enteredPin+" ---- Pref-->"+prefManager.getUserToken());
                    if (enteredPin.equalsIgnoreCase(prefManager.getUserToken()))
                    {
                        Intent register = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(register);
                        finish();
                        prefManager.setIsUserFirstTimeLaunch(false);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Invalid Pin",Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    popupError();
                }


            }
        });

        //pin 1 text listner

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
                    CommonUtils.hideKeyboardFrom(LoginActivity.this,pin_four);
                }
                else {
                }
            }
        });


    }

    private void check(String digits) {
        List<String> ansList = new ArrayList();

        List<String> data = new ArrayList<String>();
        data.add("");
        data.add("");
        data.add("abc");
        data.add("def");
        data.add("ghi");
        data.add("jkl");
        data.add("mno");
        data.add("pqrs");
        data.add("tuv");
        data.add("wxyz");


        if(digits.length()>1)
        {
            int count= digits.length();
            int k=0;
            for(int i=0;i<digits.length();i++)
            {
                    char ch = digits.charAt(k);
                    String number = Character.toString(ch);
                    int val = Integer.parseInt(number);
                    Log.d("DIGIT",""+val);
                    for(int l =0;l<data.size();l++)
                    {

                        if(val==l)
                        {
                            Log.d("CHECK",""+val+" --- "+l);
                            String str = data.get(i);
                            for(int j=0;j<str.length();j++)
                            {
                                char c = str.charAt(j);
                                String strans = Character.toString(c);
                                ansList.add(j,strans);
                            }
                        }
                    }

            }

        }

    }

    public String checkSubsString(String str , ArrayList<String> subs)
    {
        int i = 0, j = str.length();
        int length=0;
        String subss="";
        // While there are characters toc compare
        while (j>0)
        {
            subss = subss+str.charAt(i);
            i++;
            j--;
            // checkSubstring(subs);
            String reverse="";
            for ( int si = subss.length() - 1; si >= 0; si-- )
            {
                reverse = reverse + subss.charAt(si);
            }
            if(reverse.equalsIgnoreCase(subss))
            {
                subs.add(subss);
            }

        }


        int count=0;
        for(int is=0;is<subs.size();is++)
        {
            if(subs.get(is).length()>length)
            {
                length = subs.get(is).length();
                str = subs.get(is);
                count++;
            }
        }

        if(count==1){
            String subsStringcheck = str.substring(1);
            String subsAns = checkSubsString(subsStringcheck,subs);
            str = subsAns;
        }
        return str;
    }

    private void forgotPassQuestion() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginActivity.this);
        LayoutInflater inflater = (LayoutInflater) LoginActivity.this
                .getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_password, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
        AppCompatTextView okay,cancel;
        AppCompatEditText anwser;
        okay = dialog.findViewById(R.id.submitAnwser);
        anwser = dialog.findViewById(R.id.secretAnwser);
        cancel= dialog.findViewById(R.id.closeTv);;

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anwser.getText().toString().length()>0)
                {
                    CommonUtils.hideKeyboardFrom(LoginActivity.this,okay);
                    if (anwser.getText().toString().equalsIgnoreCase(prefManager.getUserEmail()))
                    {
                        Toast.makeText(LoginActivity.this, "Your Pin is "+prefManager.getUserToken(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid answer", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Enter the answer", Toast.LENGTH_SHORT).show();

                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CommonUtils.hideKeyboardFrom(LoginActivity.this,cancel);
            }
        });

    }


    private void loginHow() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginActivity.this);
        LayoutInflater inflater = (LayoutInflater) LoginActivity.this
                .getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_loginhow, null);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();


    }



    private void popupError() {
        Toast.makeText(LoginActivity.this,"Enter Pin to login",Toast.LENGTH_SHORT).show();
    }
}
