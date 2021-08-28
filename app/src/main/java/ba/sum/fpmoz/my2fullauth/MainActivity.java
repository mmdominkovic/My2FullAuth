package ba.sum.fpmoz.my2fullauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import ba.sum.fpmoz.my2fullauth.ui.adapters.LoginAdapter;
import ba.sum.fpmoz.my2fullauth.ui.fragments.LoginFragment;
import ba.sum.fpmoz.my2fullauth.ui.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {
   private TabLayout tabs;
   private ViewPager pager;
   private LoginAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.pager =findViewById(R.id.pager);
        this.tabs=findViewById(R.id.tabs);

        this.adapter= new LoginAdapter(getSupportFragmentManager(),0);
        this.adapter.addFragment(new LoginFragment(), "LOGIN");
        this.adapter.addFragment(new RegisterFragment(), "REGISTER");

        this.pager.setAdapter(this.adapter);
        this.tabs.setupWithViewPager(this.pager);
    }
}