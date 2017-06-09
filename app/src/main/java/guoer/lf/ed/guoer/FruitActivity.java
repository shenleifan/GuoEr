package guoer.lf.ed.guoer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import guoer.lf.ed.guoer.logUtils.LogUtils;

public class FruitActivity extends SimpleBaseActivity implements FruitFragment.OnFragmentInteractionListener{
    private static final String TAG = "FruitActivity";

    public static final String FRUIT_NAME = "fruit_name";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    @Override
    protected Fragment createFragment() {
        int fruitImageId = -1;
        String fruitName = "";
        if (getIntent() != null) {
            Intent intent = getIntent();
            fruitName = intent.getStringExtra(FRUIT_NAME);
            fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, -1);
        }
        return FruitFragment.newInstance(fruitName, fruitImageId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guoer);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_fruit);
//        if (fragment == null) {
//            fragment = createFragment();
//            fragmentManager.beginTransaction().add(R.id.fragment_fruit, fragment).commit();
//        }

    }

    public static void actionIntent(Context context, String fruitName, int fruitImageId) {
        Intent intent = new Intent(context, FruitActivity.class);
        intent.putExtra(FRUIT_NAME, fruitName);
        intent.putExtra(FRUIT_IMAGE_ID, fruitImageId);
        context.startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        LogUtils.d(TAG, "" + uri);
    }
}
