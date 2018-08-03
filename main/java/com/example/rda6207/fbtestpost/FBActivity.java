package com.example.rda6207.fbtestpost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBActivity extends AppCompatActivity {

    public LoginButton btnLogin;
    public Button btnPost;
    public Button btnLogout;
    public String accessToken;
    public CallbackManager callbackManager = CallbackManager.Factory.create();
    private AccessToken Accesstoken;
    public ShareDialog shareDialog;

    private FBGraphApiClientInterface apiInterface;

    void initFBLoginManager() {
        Log.d("Asking for Permission", "asking for the permissions");
        callbackManager = CallbackManager.Factory.create();
        //Login Radius Login Response Flow
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //app code
                        Accesstoken = loginResult.getAccessToken();
                        accessToken = loginResult.getAccessToken().getToken();
                        Log.d("Login Result", loginResult.toString());
                    }
                    @Override
                    public void onCancel() {
                        //app code
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG);
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        //app code
                        Log.d("Login Result", exception.toString());
                    }

                });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LoginRadius SDK Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_fb);

        btnLogin = findViewById(R.id.btn_login);
        btnPost = findViewById(R.id.btn_post);
        btnLogout = findViewById(R.id.btn_logout);
        accessToken = "";
        //Ensure Fresh Login Every Instance
        LoginManager.getInstance().logOut();

        initFBLoginManager();


        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Log Out of Facebook
                LoginManager.getInstance().logOut();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Access token to determine Permissions, if needed
                Accesstoken = AccessToken.getCurrentAccessToken();
                //Facebook Share Handled Here
                shareDialog = new ShareDialog(FBActivity.this);
                    if (ShareDialog.canShow(SharePhotoContent.class)) {
                        //Posted image converted to Bitmap
                        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.carnival_horizon);
                        //Facebook SharePhoto Conversion from Bitmap
                        SharePhoto sp = new SharePhoto.Builder()
                                .setBitmap(image)
                                .build();
                        //Facebook Post Content
                        SharePhotoContent Content = new SharePhotoContent.Builder()
                                .addPhoto(sp)
                                //Can only have 1 hashtag, will always take last defined hashtag
                                .setShareHashtag(
                                        new ShareHashtag.Builder()
                                                .setHashtag("#CarnivalHorizon").build())
                                .build();
                        //Show post to allow user to press post
                        shareDialog.show(Content);
                    }
                    else {
                        Log.d("ShareError", "error");
                    }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Get Access Token after logging into facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
