package cn.lashou.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lashou.R;
import cn.lashou.entity.HomeBean;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/8/14.
 */

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeBean.ResultBean.GoodlistBean> mGoodlist;

    public HomeAdapter(Context context, List<HomeBean.ResultBean.GoodlistBean> goodlist) {
        this.mContext = context;
        this.mGoodlist = goodlist;
    }

    @Override
    public int getCount() {
        return mGoodlist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home, null);
            SupportMultipleScreensUtil.scale(convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mGoodlist.size() > 0)
        {
            holder.mListImage.setImageURI(Uri.parse(mGoodlist.get(position).getImages().get(0).getImage()));
            holder.mTitle.setText(mGoodlist.get(position).getProduct());
            holder.mDesc.setText(mGoodlist.get(position).getShort_title());
            holder.mListMoney.setText(mGoodlist.get(position).getPrice());
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.list_image)
        SimpleDraweeView mListImage;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.desc)
        TextView mDesc;
        @BindView(R.id.list_money)
        TextView mListMoney;
        @BindView(R.id.list_yishou)
        TextView mListYishou;
        @BindView(R.id.imageView)
        ImageView mImageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
