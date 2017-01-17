package cn.lashou.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lashou.R;
import cn.lashou.core.BaseActivity;
import cn.lashou.widget.MyPhotoView;
import cn.lashou.widget.SharePopWindow;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by luow on 2016/12/28.
 */

public class GoodDetailsActivity extends BaseActivity {


    @BindView(R.id.details)
    TextView mDetails;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.myphotoview)
    MyPhotoView mMyphotoview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_deatils;
    }

    @Override
    protected void initView() {

        mDetails.setText(getIntent().getStringExtra("title").toString());

        mMyphotoview.setImageUri(getIntent().getStringExtra("imageUrl").toString(), null);

        //整个view的单击事件监听
        mMyphotoview.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.share)
    public void onClick() {
        SharePopWindow popWindow = new SharePopWindow(this,getIntent().getStringExtra("title").toString(),getIntent().getStringExtra("imageUrl").toString());
        popWindow.showAtLocation(mDetails, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
    }

}
