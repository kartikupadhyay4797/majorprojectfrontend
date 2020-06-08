package com.e.majorprojectfrontend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Register extends AppCompatActivity {

    TextView name, contact, password, refercode, register;
    static ProgressBar progress;
    static EditText nameEdit, contactEdit, passwordEdit, refercodeEdit;
    static Activity Register;

    Context context;
    JSONObject object;
    JSONObject jsonObject;
    JSONObject user;
    String responseString;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = Register.this;
        FrameLayout relativeLayout = findViewById(R.id.frame_in_register);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });
        progress = findViewById(R.id.progress_in_register);


        Register = Register.this;

        name = findViewById(R.id.nametextviewtoregister);
        contact = findViewById(R.id.contacttextviewtoregister);
        password = findViewById(R.id.passwordtextviewtoregister);
        refercode = findViewById(R.id.refercodetextviewtoregister);
        nameEdit = findViewById(R.id.nameedittoregister);
        contactEdit = findViewById(R.id.contactedittoregister);
        passwordEdit = findViewById(R.id.passwordedittoregister);
        refercodeEdit = findViewById(R.id.refercodeedittoregister);
        register = findViewById(R.id.registerkeytext);

        nameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setHint();
                nameEdit.setHint("");
                makeVisible();
                name.setVisibility(View.VISIBLE);
            }
        });
        contactEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setHint();
                contactEdit.setHint("");
                makeVisible();
                contact.setVisibility(View.VISIBLE);
            }
        });
        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setHint();
                passwordEdit.setHint("");
                makeVisible();
                password.setVisibility(View.VISIBLE);
            }
        });
        refercodeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setHint();
                refercodeEdit.setHint("");
                makeVisible();
                refercode.setVisibility(View.VISIBLE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        TextView signin = findViewById(R.id.signinregistertextview);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Login.class));
                finish();
            }
        });
    }

    public void registerUser() throws JSONException {
        if ((nameEdit.getText().toString().equals("") || contactEdit.getText().toString().equals(""))|| passwordEdit.getText().toString().equals("") ) {  //|| passwordEdit.getText().toString().equals("")
            new Toast(this).makeText(this, "invalid details", Toast.LENGTH_LONG).show();
        } else {
                this.context = context;
                responseString = "";
                jsonObject = null;
                Register.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.VISIBLE);
// To dismiss the dialog
                    }
                });
                OkHttpClient client = new OkHttpClient();
                user = new JSONObject();
                user.put("name", this.nameEdit.getText().toString());
                user.put("address", this.contactEdit.getText().toString());
                user.put("email", this.passwordEdit.getText().toString());
                user.put("promoode",refercodeEdit.getText().toString());
                Log.d("sendData", user.toString());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), user.toString());
                Request request = new Request.Builder()
                        .url("http://kartik4797.pythonanywhere.com/user/")
                        .post(body)
                        .build();
//                try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("on failure : ","exception " + e.getMessage());
                        final String message = e.getMessage();

                        Register.runOnUiThread(new Runnable() {
                            public void run() {
                                progress.setVisibility(View.GONE);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Register.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Intent home = new Intent(Register, Login.class);
//                                try {
//                                    home.putExtra("user_id", jsonObject.getJSONArray("result").getJSONObject(0).getString("id"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                                progress.setVisibility(View.GONE);
                                Toast.makeText(Register, "Registered successfully, your password is sent to your Email Id!!!", Toast.LENGTH_LONG).show();
                                Register.startActivity(new Intent(Register.this,Login.class));
                                Register.finish();
                            }
                        });
                    }
                });
            }
        }


    public void setHint(){
        contactEdit.setHint(contact.getText());
        nameEdit.setHint(name.getText());
        passwordEdit.setHint(password.getText());
        refercodeEdit.setHint(refercode.getText());
    }

    public void makeVisible(){
        if (name.getVisibility() == View.VISIBLE && nameEdit.getText().toString().equals("")) {
            name.setVisibility(View.INVISIBLE);
        }if (contact.getVisibility() == View.VISIBLE && contactEdit.getText().toString().equals("")) {
            contact.setVisibility(View.INVISIBLE);
        }if (password.getVisibility() == View.VISIBLE && passwordEdit.getText().toString().equals("")) {
            password.setVisibility(View.INVISIBLE);
        }if (refercode.getVisibility() == View.VISIBLE && refercodeEdit.getText().toString().equals("")) {
            refercode.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
