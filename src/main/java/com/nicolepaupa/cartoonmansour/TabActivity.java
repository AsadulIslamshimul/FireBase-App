package com.nicolepaupa.cartoonmansour;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.google.android.material.navigation.NavigationView;
import com.nicolepaupa.cartoonmansour.databinding.ActivityTabBinding;
import com.yalantis.colormatchtabs.colormatchtabs.adapter.ColorTabAdapter;
import com.yalantis.colormatchtabs.colormatchtabs.listeners.ColorTabLayoutOnPageChangeListener;
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnColorTabSelectedListener;
import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab;

import org.jetbrains.annotations.Nullable;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public final class TabActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private ActivityTabBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private long backPressedTime;
    private Toast backToast;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTabBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        //removing shadow of toolbar
        binding.toolbar.setElevation(0.0F);
        addTab();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private final void addTab() {
        //adding tab
        binding.colorMatchTabLayout.addTab(ColorTabAdapter.Companion.createColorTab(binding.colorMatchTabLayout, "Home", Color.parseColor("#00695C"), getResources().getDrawable(R.drawable.ic_home_black_24dp)));
        binding.colorMatchTabLayout.addTab(ColorTabAdapter.Companion.createColorTab(binding.colorMatchTabLayout, "Recent", Color.parseColor("#5F158C"), getResources().getDrawable(R.drawable.recent_24)));
        binding.colorMatchTabLayout.addTab(ColorTabAdapter.Companion.createColorTab(binding.colorMatchTabLayout, "Popular", Color.parseColor("#08555C"), getResources().getDrawable(R.drawable.popular_24)));

        binding.viewPager.setAdapter((PagerAdapter) (new ColorTabsAdapter(this.getSupportFragmentManager())));
        binding.viewPager.addOnPageChangeListener((OnPageChangeListener) (new ColorTabLayoutOnPageChangeListener(binding.colorMatchTabLayout)));

        //default background color
        binding.viewPager.setBackgroundColor(Color.parseColor("#79BC32"));

 binding.viewPager.getBackground().setAlpha(128);

binding.colorMatchTabLayout.addOnColorTabSelectedListener((OnColorTabSelectedListener) (new OnColorTabSelectedListener() {
            public void onSelectedTab(@Nullable ColorTab tab) {
                //ahanging active tab color

                binding.viewPager.setCurrentItem(tab != null ? tab.getPosition() : 0);
                binding.viewPager.setBackgroundColor(tab != null ? tab.getSelectedColor() : ContextCompat.getColor( TabActivity.this,  R.color.colorPrimary));
                binding.viewPager.getBackground().setAlpha(128);
                binding.textView.setText(tab != null ? tab.getText() : null);
                binding.textView.setTextColor(tab != null ? tab.getSelectedColor() : ContextCompat.getColor( TabActivity.this,  R.color.colorPrimary));
            }

            public void onUnselectedTab(@Nullable ColorTab tab) {
                Log.e("Unselected ", "tab");
            }
        }));
    }



    @SuppressLint("NonConstantResourceId")

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()){

            case R.id.action_shareId:
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String body = "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName();
                    intent.putExtra(Intent.EXTRA_SUBJECT,R.string.app_name);
                    intent.putExtra(Intent.EXTRA_TEXT,body);
                    startActivity(Intent.createChooser(intent,"Share With"));
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to share",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_feedbackId:
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = new String[] {"nicolepaupa@gmail.com","",};
                    emailIntent.putExtra(Intent.EXTRA_EMAIL,recipients);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Comment: Mansour Cartoon Videos");
                    emailIntent.putExtra(Intent.EXTRA_TEXT,"Please Select Your Gmail");
                    emailIntent.setType("text/email");
                    startActivity(Intent.createChooser(emailIntent,"sent mail.."));
                    finish();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to open",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.action_rate_appId:
                try {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to open Ratting",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.action_more_apps:
                try {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Nicole+Paupa");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to open More Apps",Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.action_exitd:
                try {
                    TabActivity.this.finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Unable to Exit",Toast.LENGTH_SHORT).show();
                }

                break;



            default:
                break;

        }

        return false;
    }

    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast =  Toast.makeText(getApplicationContext(),"Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

    }


}
