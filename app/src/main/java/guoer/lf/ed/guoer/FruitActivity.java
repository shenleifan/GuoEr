package guoer.lf.ed.guoer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import guoer.lf.ed.guoer.utils.LogUtils;
import guoer.lf.ed.guoer.view.FruitFragment;

public class FruitActivity extends SimpleBaseActivity implements FruitFragment.OnFragmentInteractionListener {
    private static final String TAG = "FruitActivity";

    public static final String FRUIT_NAME = "fruit_name";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    @BindView(R.id.frame_container)
    FrameLayout mFrameContainer;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

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
        ButterKnife.bind(this);
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
