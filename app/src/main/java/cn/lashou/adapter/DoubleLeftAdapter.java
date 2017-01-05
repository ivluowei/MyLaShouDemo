package cn.lashou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lashou.R;
import cn.lashou.entity.CategoryBean;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/12/16.
 */

public class DoubleLeftAdapter extends BaseAdapter {
    private Context mContext;
    private List<CategoryBean.DataBean> mLeftDataList;
    private int mSelectItem;//默认为0

    public DoubleLeftAdapter(Context context, List<CategoryBean.DataBean> mLeftDataList) {
        this.mContext = context;
        this.mLeftDataList = mLeftDataList;
    }

    @Override
    public int getCount() {
        return mLeftDataList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_left, null);
            SupportMultipleScreensUtil.scale(convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mLeftDataList.get(position).getSymptomName().size() > 0){
            holder.mLeftArrow.setVisibility(View.VISIBLE);
        }else{
            holder.mLeftArrow.setVisibility(View.INVISIBLE);
        }

        if (mLeftDataList.size() > 0)
        {
            if (mSelectItem == position){
                holder.rl.setBackgroundResource(R.color.more_hui);
            }else{
                holder.rl.setBackgroundResource(R.color.white);
            }

            holder.mTitleLeft.setText(mLeftDataList.get(position).getCategoryName());
            holder.mNumLeft.setText(mLeftDataList.get(position).getCategoryNount());
        }
        return convertView;
    }

    public void setSelectItem(int pos){
        this.mSelectItem = pos;
    }

    class ViewHolder {
        @BindView(R.id.img_lfet)
        ImageView mImgLfet;
        @BindView(R.id.title_left)
        TextView mTitleLeft;
        @BindView(R.id.num_left)
        TextView mNumLeft;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.left_arrow)
        ImageView mLeftArrow;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}