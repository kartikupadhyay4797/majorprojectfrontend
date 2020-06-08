package com.e.majorprojectfrontend;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {


    EditText nameEdit,passEdit;
    TextView showhideText;
    ProgressBar progressBar;
    static boolean temp;
    Session session;
    org.json.simple.JSONObject user;
    static boolean isDealer=true;
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        session=new Session(getApplicationContext());
        LinearLayout relativeLayout=findViewById(R.id.linear_in_login);
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
        nameEdit=findViewById(R.id.nameEdit);
        passEdit=findViewById(R.id.pass);
        progressBar=findViewById(R.id.progress_in_login);
        showhideText=findViewById(R.id.showhidetextview);
        showhideText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password();
            }
        });

        TextView registerText=findViewById(R.id.registertextview);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),Register.class));
                finish();
            }
        });


        TextView loginText=findViewById(R.id.logintextview);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    public void loginUser() {
        if (nameEdit.getText().toString().equals("") || passEdit.getText().toString().equals("")) {
            new Toast(this).makeText(this, "please fill details properly!!!", Toast.LENGTH_LONG).show();
        } else {
            if (nameEdit.getText().toString().length() < 10) {
                new Toast(this).makeText(this, "please enter a valid mobile number!!!", Toast.LENGTH_LONG).show();
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
// To dismiss the dialog
                    }
                });
                client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://kartik4797.pythonanywhere.com/user/")
                        .get().build();

//                        new Request.Builder()
//                        .url("http://kartik4797.pythonanywhere.com/user/")
//                        .post(body)
//                        .build();
//                try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("on failure : ","exception " + e.getMessage());
                        final String message = e.getMessage();

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Login.this, "sorry we can't process right now", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        String responseBody=response.body().string();
                        Log.e("responsebody",responseBody);
                        JSONParser parser=new JSONParser();
                        temp=false;
                        try {
                            //org.json.JSONArray array=(JSONArray) parser.parse(res);
//                            JSONArray array=new JSONArray(responseBody);
                            JSONArray array=(JSONArray)parser.parse(responseBody);
                            Log.e("response: ",array.toString());
                            for(int i=0;i<array.size();i++){
                                user=(org.json.simple.JSONObject)parser.parse(array.get(i).toString());
                                Log.e("response: ",user.toString());
                                if(user.get("email").equals(nameEdit.getText().toString()) && user.get("password").equals(passEdit.getText().toString())) {
                                    temp = true;
                                    isDealer=false;
                                    break;
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Intent home = new Intent(Register, Login.class);
//                                try {
//                                    home.putExtra("user_id", jsonObject.getJSONArray("result").getJSONObject(0).getString("id"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                                progressBar.setVisibility(View.GONE);

                                if(temp) {
                                    Toast.makeText(Login.this, "login successful", Toast.LENGTH_LONG).show();
                                    session.createLoginSession(user.get("name").toString(),user.get("email").toString(),user.get("id").toString(),user.get("address").toString(),"user");
                                    startActivity(new Intent(Login.this,DealerList.class));
                                }
                                else if(isDealer){
                                    Request request = new Request.Builder()
                                            .url("http://kartik4797.pythonanywhere.com/dealer/")
                                            .get().build();

//                        new Request.Builder()
//                        .url("http://kartik4797.pythonanywhere.com/user/")
//                        .post(body)
//                        .build();
//                try {
                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Request request, IOException e) {
                                            Log.e("on failure : ","exception " + e.getMessage());
                                            final String message = e.getMessage();

                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(Login.this, "sorry we can't process right now", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            });

                                        }

                                        @Override
                                        public void onResponse(Response response) throws IOException {

                                            String responseBody=response.body().string();
                                            Log.e("responsebody",responseBody);
                                            JSONParser parser=new JSONParser();
                                            temp=false;
                                            try {
                                                //org.json.JSONArray array=(JSONArray) parser.parse(res);
//                            JSONArray array=new JSONArray(responseBody);
                                                JSONArray array=(JSONArray)parser.parse(responseBody);
                                                Log.e("response: ",array.toString());
                                                for(int i=0;i<array.size();i++){
                                                    user=(org.json.simple.JSONObject)parser.parse(array.get(i).toString());
                                                    Log.e("response: ",user.toString());
                                                    if(user.get("email").equals(nameEdit.getText().toString()) && user.get("password").equals(passEdit.getText().toString())) {
                                                        temp = true;
                                                        break;
                                                    }
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                Intent home = new Intent(Register, Login.class);
//                                try {
//                                    home.putExtra("user_id", jsonObject.getJSONArray("result").getJSONObject(0).getString("id"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
                                                    progressBar.setVisibility(View.GONE);
                                                    if(temp) {
                                                        Toast.makeText(Login.this, "login successful", Toast.LENGTH_LONG).show();
                                                        session.createLoginSession(user.get("name").toString(),user.get("email").toString(),user.get("id").toString(),user.get("address").toString(),"dealer");
                                                        startActivity(new Intent(Login.this,OrderList.class));
                                                    }
                                                    else
                                                        Toast.makeText(Login.this,"invalid details",Toast.LENGTH_LONG).show();
                                                    //Register.startActivity(home);
                                                    //Register.finish();
                                                }
                                            });
                                        }
                                    });
                                }
                                //Register.startActivity(home);
                                //Register.finish();
                            }
                        });
                    }
                });
            }
        }
    }

    //    public void makeVisible() {
//        if (nameText.getVisibility() == View.VISIBLE && nameEdit.getText().toString().equals("")) {
//            nameText.setVisibility(View.INVISIBLE);
//        }
//        if (passText.getVisibility() == View.VISIBLE && passEdit.getText().toString().equals("")){
//            passText.setVisibility(View.INVISIBLE);
//    }
//}
    public void password(){
        if (passEdit.getTransformationMethod()!=null)
            passEdit.setTransformationMethod(null);
        else passEdit.setTransformationMethod(new PasswordTransformationMethod());
//        if (passEdit.getInputType()== InputType.TYPE_TEXT_VARIATION_PASSWORD)
//            passEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD |InputType.TYPE_CLASS_TEXT);
//        if (passEdit.getInputType()==TYPE_TEXT_VARIATION_VISIBLE_PASSWORD || passEdit.getInputType()==InputType.TYPE_CLASS_TEXT)
//            passEdit.setInputType(TYPE_TEXT_VARIATION_PASSWORD);
        passEdit.setSelection(passEdit.length());
    }
//    public void setHint(){
//        passEdit.setHint(passText.getText());
//        nameEdit.setHint(nameText.getText());
//    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
