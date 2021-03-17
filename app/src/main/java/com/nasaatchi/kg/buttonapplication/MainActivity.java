package com.nasaatchi.kg.buttonapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nasaatchi.kg.buttonapplication.util.PasswordCheck;

public class MainActivity extends AppCompatActivity {
    private EditText login_edit;
    private EditText password_edit;
    private ProgressBar progressBar;
    private TextView strengthView;
    private Button login_btn;
    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_edit = findViewById(R.id.login_edit);
        password_edit = findViewById(R.id.password_edit);
        login_btn = findViewById(R.id.login_btn);


        login_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buttonActive(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login = login_edit.getText().toString();
                password = password_edit.getText().toString();
                login_btn.setEnabled(!login.isEmpty() && !password.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buttonActive(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login = login_edit.getText().toString();
                password = password_edit.getText().toString();
                login_btn.setEnabled(!login.isEmpty() && !password.isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePasswordStrengthView(s.toString());
            }
        });

        onClick();
    }

    private void buttonActive(CharSequence sequence, int start, int count, int after){
        if (sequence.toString().trim().length() == 0){
            login_btn.setEnabled(false);
        }else {
            login_btn.setEnabled(true);
        }
    }

    private void updatePasswordStrengthView(String password) {

        progressBar = findViewById(R.id.progressBar);
        strengthView = findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordCheck passwordCheck = PasswordCheck.calculatePassword(password);
        strengthView.setText(passwordCheck.getText(this));
        strengthView.setTextColor(passwordCheck.getColor());

        progressBar.getProgressDrawable().setColorFilter(passwordCheck.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (passwordCheck.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (passwordCheck.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (passwordCheck.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    public void onClick(){
        login = login_edit.getText().toString();
        password = password_edit.getText().toString();
        login_btn.setOnClickListener(v -> {
            if (!login.isEmpty() && !password.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){
            if(password_edit.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off);
                //Show Password
                password_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility);
                //Hide Password
                password_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}