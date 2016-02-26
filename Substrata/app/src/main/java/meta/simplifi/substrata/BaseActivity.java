package meta.simplifi.substrata;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import meta.simplifi.substrata.fragment.PracticeFragment;

public class BaseActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        transaction.add(R.id.contentFrame, new PracticeFragment());

        transaction.commit();
    }
}
