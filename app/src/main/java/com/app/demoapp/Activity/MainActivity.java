package com.app.demoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.app.demoapp.Adapter.ViewPagerAdapter;
import com.app.demoapp.Fragment.HomeFragment;
import com.app.demoapp.Fragment.MapFragment;
import com.app.demoapp.R;
import com.app.demoapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupViewPager(binding.vpViewPager);
        binding.tlHomeTab.setupWithViewPager(binding.vpViewPager);

        SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        binding.fabNext.setOnClickListener(v->{
            String location=pref.getString("location", "");
            if (location.equals("")){
                Toast.makeText(this, "Please enter Location", Toast.LENGTH_SHORT).show();
            }
            else {

                editor.clear();
                editor.apply();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
               // String myMessage = "Stackoverflow is cool!";
                bundle.putString("location", location );
                MapFragment fragInfo = new MapFragment();
                fragInfo.setArguments(bundle);
                transaction.replace(R.id.vpViewPager, fragInfo);
                transaction.commit();

                /*Fragment newFragment = MapFragment.newInstance(location);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.vpViewPager, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();*/
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new MapFragment(), "MAP");

        viewPager.setAdapter(adapter);
    }
}