package ba.sum.fpmoz.my2fullauth.ui.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class LoginAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment>fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    public LoginAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public LoginAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment f, String title){
        this.fragmentList.add(f);
        this.fragmentTitleList.add(title);
        }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.fragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }
}
