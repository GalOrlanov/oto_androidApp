package com.example.oto;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.firebase.FirebaseException;
import com.firebase.client.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    //TODO: Create User class, Ride class, Car class, Review class.
    //TODO: Update the ip according to the wifi network. Later we will upload the server to the web and get URL.
    static final public String url = "http://192.168.1.20:8080/";
    static public String token;
    static public String first_name;
    static public String last_name;
    static public String email;
    static public String password;
    static public String address;
    static public String city;
    static public String country;
    static public String phone;
    static public String UID;
    static public String carModel;
    static public String Model;
    static public String carColor;
    static public String license;
    static public String gender = "";
    static public String birthday = "";

    public static String getUrl() {
        return url;
    }

    public static String getFirst_name() {
        return first_name;
    }

    public static void setFirst_name(String first_name) {
        App.first_name = first_name;
    }

    public static String getLast_name() {
        return last_name;
    }

    public static void setLast_name(String last_name) {
        App.last_name = last_name;
    }

    public static String getCarModel() {
        return carModel;
    }

    public static void setCarModel(String carModel) {
        App.carModel = carModel;
    }

    public static String getModel() {
        return Model;
    }

    public static void setModel(String model) {
        Model = model;
    }

    public static String getCarColor() {
        return carColor;
    }

    public static void setCarColor(String carColor) {
        App.carColor = carColor;
    }


    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        App.gender = gender;
    }

    public static String getBirthday() {
        return birthday;
    }

    public static void setBirthday(String birthday) {
        App.birthday = birthday;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        App.driver = driver;
    }

    public static String getLicense() {
        return license;
    }

    public static void setLicense(String license) {
        App.license = license;
    }

    public static String getId_() {
        return id_;
    }

    public static void setId_(String id_) {
        App.id_ = id_;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static void setmAuth(FirebaseAuth mAuth) {
        App.mAuth = mAuth;
    }

    public static FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }

    public static void setmAuthListener(FirebaseAuth.AuthStateListener mAuthListener) {
        App.mAuthListener = mAuthListener;
    }

    public static Application getsApplication() {
        return sApplication;
    }

    public static void setsApplication(Application sApplication) {
        App.sApplication = sApplication;
    }

    static public String date;
    static public String time;
    static public String source;
    static public String dest;
    static public String freePlaces;
    static public String driver;
    static public String id_;
    //
    static public FirebaseAuth mAuth;
    static public FirebaseAuth.AuthStateListener mAuthListener;


    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        Firebase.setAndroidContext(this);
    }

    public static void setToken(String nToken) {
        token = nToken;
    }

    public static String getToken() {
        return token;
    }

    public static void setFirstName(String nFirstName) {
        first_name = nFirstName;
    }

    public static String getFirstName() {
        return first_name;
    }

    public static void setLastName(String nLastName) {
        last_name = nLastName;
    }

    public static String getLastName() {
        return last_name;
    }

    public static void setEmail(String mEmail) {
        email = mEmail;
    }

    public static String getEmail() {
        return email;
    }

    public static void setPassword(String mPassword) {
        password = mPassword;
    }

    public static String getPassword() {
        return password;
    }

    public static void setAddress(String nAddress) {
        address = nAddress;
    }

    public static String getAddress() {
        return address;
    }

    public static void setCity(String nCity) {
        city = nCity;
    }

    public static String getCity() {
        return city;
    }

    public static void setCountry(String nCountry) {
        country = nCountry;
    }

    public static String getCountry() {
        return country;
    }

    public static void setPhone(String nPhone) {
        phone = nPhone;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setDate(String adate) {
        date = adate;
    }

    public static String getDate() {
        return date;
    }

    public static void setTime(String atime) {
        time = atime;
    }

    public static String getTime() {
        return time;
    }

    public static void setDest(String adest) {
        dest = adest;
    }

    public static String getDest() {
        return dest;
    }

    public static void setSource(String asource) {
        source = asource;
    }

    public static String getSource() {
        return source;
    }

    public static String getFreePlaces() {
        return freePlaces;
    }

    public static void setFreePlaces(String afree) {
        freePlaces = afree;
    }

    public static void setUID(String nUID) {
        UID = nUID;
    }

    public static String getUID() {
        return UID;
    }



    public static void conectTofireBase(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.i(" uid is not valid", "onComplete: "+ getUID());

                } else {
                    FirebaseUser user = task.getResult().getUser();
                    setEmail(user.getEmail());
                    setPassword(App.password);
                    setUID(user.getUid());
                    Log.i(" uid is valid", "onComplete: "+ getUID());


                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    mUser.getIdToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {
                                        String idToken = task.getResult().getToken();
                                        App.setToken(idToken);
                                        // Send token to your backend via HTTPS
                                        Log.i(" uid is valid sdddddd", "onComplete: "+ getUID());
                                        getUserDetails();
                                        // ...
                                    } else {
                                        // Handle error -> task.getException();
                                    }
                                }
                            });

                }
            }
        });

    }


    public static void getUserDetails() {
        String url = App.getUrl()+"user/validate?uid=" + UID;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                  

                    JSONObject objRespone = new JSONObject(response);
                    setFirstName(objRespone.getString("fullname"));
                    //Log.i("App", "onResponse: " + response);
                    setEmail(objRespone.getString("email"));
                    setPhone(objRespone.getString("phone"));
                    //setAddress(objRespone.getString("address"));
                   goToLoginActivity(getContext());


                } catch (JSONException e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("response:", "cannot connect " + error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", App.getToken());
                Log.i("asdasd" , App.getToken());
                return headers;
            }
        };
        queue.add(request);
        queue.start();
    }

    public void setdet(JSONObject o){


    }

    public static void goToLoginActivity(Context mContext) {

        Intent login = new Intent(mContext, MainPageActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.getApplicationContext().startActivity(login);
    }
}