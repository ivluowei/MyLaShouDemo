package cn.lashou.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lashou.R;
import cn.lashou.entity.MovieBean;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/8/20.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    private Context mContext;
    private List<MovieBean.ResultBean> mResultList;
    public HorizontalAdapter(Context context, List<MovieBean.ResultBean> resultlist) {
        this.mContext = context;
        this.mResultList = resultlist;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_recycleview, parent, false);
        SupportMultipleScreensUtil.scale(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mRcyImage.setImageURI(Uri.parse(mResultList.get(position).getImageUrl()));
        holder.mRcyTitle.setText(mResultList.get(position).getFilmName());
        holder.mRcyTv.setText(mResultList.get(position).getGrade());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
            return mResultList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rcy_image)
        SimpleDraweeView mRcyImage;
        @BindView(R.id.rcy_title)
        TextView mRcyTitle;
        @BindView(R.id.rcy_tv)
        TextView mRcyTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnRecycleViewClickListener mListener;

    public void setRecycleViewOnClickListener(OnRecycleViewClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnRecycleViewClickListener {
        void onClick(View view, int position);
    }
}