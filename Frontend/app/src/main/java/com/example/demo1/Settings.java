package com.example.demo1;

import Models.User;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import interfaces.IUser;

public class Settings extends AppCompatActivity {
    private ImageButton back;
    public ImageButton apply;
    public TextView username;
    private ImageButton logout;
    private Switch displayName;
    private User user;
    private IUser callback;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        user = new User();
        callback = new IUser() {
            @Override
            public int onSuccess() {
                return 0;
            }

            @Override
            public int onError() {
                return -1;
            }
        };
        user.getUser(this, callback, FirebaseAuth.getInstance().getCurrentUser().getUid());
        back = findViewById(R.id.settings_back);
        apply = findViewById(R.id.apply_button);
        username = findViewById(R.id.editTextTextPersonName);
        logout = findViewById(R.id.logout_but);
        displayName = findViewById(R.id.display_name_toggle);
        displayName.setOnCheckedChangeListener(null);

        if(getIntent().getBooleanExtra("displayname", false))
            displayName.setChecked(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.updateUser(Settings.this, callback);
                Intent intent = new Intent(Settings.this, UserHome.class);
                intent.putExtra("displayname", user.getDisplayName());
                startActivity(intent);
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = username.getText().toString();
                if(input.isEmpty()){
                    username.setError("Please provide username");
                    username.requestFocus();
                }
                else{
                    user.setUsername(input);
                    user.updateUser(Settings.this, callback);
                    Intent I = new Intent(Settings.this, UserHome.class);
                    startActivity(I);
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                user.updateUser(Settings.this, callback);
                Intent I = new Intent(Settings.this, ActivityLogin.class);
                startActivity(I);
                Toast.makeText(Settings.this, "User logged out", Toast.LENGTH_SHORT).show();
            }
        });
        displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setDisplayName(!user.getDisplayName());
            }
        });
    }
}
