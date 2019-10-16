package com.example.tourismof;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tourismof.Fragments.FavoriteFragment;
import com.example.tourismof.Fragments.Fragment_mypost;
import com.example.tourismof.Fragments.Fragment_post;
import com.example.tourismof.Fragments.Fragment_setting;
import com.example.tourismof.Fragments.HomeFragment;
import com.example.tourismof.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.tourismof.LoginActivity.MY_PREFS_NAME;

public class FirstActivity extends AppCompatActivity {

    private TextView mTextMessage;
     String Host;
     int first;
     int a;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(first==2)
            {
                switch (item.getItemId()) {
                    case R.id.post:
                        FragmentManager fmhh = getSupportFragmentManager();
                        FragmentTransaction fth = fmhh.beginTransaction();
                        Fragment fragmenth = new Fragment_post();
                        fth.replace(R.id.frame, fragmenth);
                        fth.commit();
                        return true;

                    case R.id.my_posts:
//                    mTextMessage.setText(R.string.title_notifications);
                        FragmentManager fmp = getSupportFragmentManager();
                        FragmentTransaction ftp = fmp.beginTransaction();
                        Fragment fragmentpost = new Fragment_mypost();
                        ftp.replace(R.id.frame, fragmentpost);
                        ftp.commit();
                        return true;
                    case R.id.nav_Profile:
//                    mTextMessage.setText(R.string.title_profile);
                        FragmentManager fmn = getSupportFragmentManager();
                        FragmentTransaction ftn = fmn.beginTransaction();
                        Fragment fragmentprofile = new ProfileFragment();
                        ftn.replace(R.id.frame, fragmentprofile);
                        ftn.commit();
                        return true;
                    case R.id.nav_settings:
//                    mTextMessage.setText(R.string.title_profile);
                        FragmentManager fms = getSupportFragmentManager();
                        FragmentTransaction fts = fms.beginTransaction();
                        Fragment fragmentsetting = new Fragment_setting();
                        fts.replace(R.id.frame, fragmentsetting);
                        fts.commit();
                        return true;


                }

            }

               else if (first==1)
            {
                switch (item.getItemId()) {

                    case R.id.Home_button:
                        FragmentManager fmh = getSupportFragmentManager();
                        FragmentTransaction fth = fmh.beginTransaction();
                        Fragment fragmenth = new HomeFragment();
                        fth.replace(R.id.frame, fragmenth);
                        fth.commit();
                        return true;

                    case R.id.favourite_posts_button:
//                    mTextMessage.setText(R.string.title_notifications);
                        FragmentManager fmp = getSupportFragmentManager();
                        FragmentTransaction ftp = fmp.beginTransaction();
                        Fragment fragmentpost = new FavoriteFragment();
                        ftp.replace(R.id.frame, fragmentpost);
                        ftp.commit();
                        return true;
                    case R.id.profile_button:
//                    mTextMessage.setText(R.string.title_profile);
                        FragmentManager fmn = getSupportFragmentManager();
                        FragmentTransaction ftn = fmn.beginTransaction();
                        Fragment fragmentprofile = new ProfileFragment();
                        ftn.replace(R.id.frame, fragmentprofile);
                        ftn.commit();
                        return true;
                    case R.id.setting_button:
//                    mTextMessage.setText(R.string.title_profile);
                        FragmentManager fms = getSupportFragmentManager();
                        FragmentTransaction fts = fms.beginTransaction();
                        Fragment fragmentset = new Fragment_setting();
                        fts.replace(R.id.frame, fragmentset);
                        fts.commit();
                        return true;


                }

            }
            return false;





        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        mTextMessage = (TextView) findViewById(R.id.message);
        FragmentManager fmh=getSupportFragmentManager();
        FragmentTransaction fth=fmh.beginTransaction();


        if(bd != null) {
            Host = bd.get("hostt").toString();
            if (Host.matches("12"))
            {
                setContentView(R.layout.activity_second);
                navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
                Fragment fragmenth= new Fragment_post();
                fth.replace(R.id.frame, fragmenth);
                fth.commit();
                first=2;
                navigation.setSelectedItemId(R.id.post);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            }
            else if (Host.matches("14")) {
                setContentView(R.layout.activity_first);
                navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
                navigation.setSelectedItemId(R.id.Home_button);
                Fragment fragmenth= new HomeFragment();
                fth.replace(R.id.frame, fragmenth);
                fth.commit();
                first=1;
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            }
            else if (Host.matches("20")) {
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Integer name = prefs.getInt("First",1);//"No name defined" is the default value.
                if(name%2 !=0)
                {
                    setContentView(R.layout.activity_first);
                    navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
                    first=1;
                    Fragment fragmenth= new ProfileFragment();
                    fth.replace(R.id.frame, fragmenth);
                    fth.commit();
                    navigation.setSelectedItemId(R.id.profile_button);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                }
                else{
                    setContentView(R.layout.activity_second);
                    navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
                    first=2;

                    Fragment fragmenth= new ProfileFragment();
                    fth.replace(R.id.frame, fragmenth);
                    fth.commit();
                    navigation.setSelectedItemId(R.id.profile_button);

                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                }

            }
        }
        else {

                setContentView(R.layout.activity_first);
                navigation = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
//                navigation.setSelectedItemId(R.id.Home_button);
            Fragment fragmenth= new HomeFragment();
            fth.replace(R.id.frame, fragmenth);
            fth.commit();
            first=1;
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


            }




//        if(a==2)
//        {
//        Fragment fragmenth= new Fragment_post();
//        fth.replace(R.id.frame, fragmenth);
//            fth.commit();
//        }
//        else if(a==1)
//        {
//
//            Fragment fragmenth= new HomeFragment();
//            fth.replace(R.id.frame, fragmenth);
//            fth.commit();
//        }
//        else if(a==3)
//        {
//            Fragment fragmenth= new ProfileFragment();
//             fth.replace(R.id.frame, fragmenth);
//              fth.commit();}
//
//        }




























































































































//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_first);
//
//        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
//        bottomNavigationView.setSelectedItemId(R.id.Home_button);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame,new HomeFragment());
//        transaction.commit();
//
//
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                switch (menuItem.getItemId())
//                {
//                    case R.id.Home_button:
//                        transaction.replace(R.id.frame,new HomeFragment());
//                        break;
//                    case R.id.favourite_posts_button:
//                        transaction.replace(R.id.frame,new FavoriteFragment());
//                        break;
//                    case R.id.profile_button:
//                        transaction.replace(R.id.frame,new ProfileFragment());
//                        break;
//                }
//                transaction.commit();
//                return true;
//            }
//        });
//
//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content);
//                if(currentFragment instanceof HomeFragment)
//                    bottomNavigationView.setSelectedItemId(R.id.Home_button);
//                else if(currentFragment instanceof FavoriteFragment)
//                    bottomNavigationView.setSelectedItemId(R.id.favourite_posts_button);
//                else if(currentFragment instanceof ProfileFragment)
//                    bottomNavigationView.setSelectedItemId(R.id.profile_button);
//
//            }
//        });
//
//    }
}}
