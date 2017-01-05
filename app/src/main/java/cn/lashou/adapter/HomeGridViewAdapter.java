package cn.lashou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lashou.R;
import cn.lashou.entity.HomeImgBean;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/8/18.
 */

public class HomeGridViewAdapter extends BaseAdapter {
    private ArrayList<HomeImgBean> mHomeImgBeen;
    private Context mContext;

    public HomeGridViewAdapter(Context mContext, ArrayList<HomeImgBean> mHomeImgBeen) {
        this.mContext = mContext;
        this.mHomeImgBeen = mHomeImgBeen;
    }

    @Override
    public int getCount() {
        return mHomeImgBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mHomeImgBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_grid, null);
            SupportMultipleScreensUtil.scale(convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mHomeImgBeen.size() > 0) {
            holder.mItemHomeImage.setImageResource(mHomeImgBeen.get(position).getIconId());
            holder.mItemHomeText.setText(mHomeImgBeen.get(position).getIconName());
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_home_image)
        ImageView mItemHomeImage;
        @BindView(R.id.item_home_text)
        TextView mItemHomeText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
