package cn.lashou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lashou.R;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/12/16.
 */

public class SingleListAdapter extends RecyclerView.Adapter<SingleListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mPaixunList;
    public SingleListAdapter(Context context, ArrayList<String> mPaixunList) {
        this.mContext = context;
        this.mPaixunList = mPaixunList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_single, parent, false);
        SupportMultipleScreensUtil.scale(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == 0){
            holder.mTvSingle.setVisibility(View.GONE);
            holder.mHindTv.setVisibility(View.VISIBLE);
        }else{
            holder.mTvSingle.setVisibility(View.VISIBLE);
            holder.mHindTv.setVisibility(View.GONE);
        }
        holder.mTvSingle.setText(mPaixunList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPaixunList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_single)
        TextView mTvSingle;
        @BindView(R.id.hind_tv)
        TextView mHindTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private OnRecycleViewClickListener mListener;

    public void setRecycleViewOnClickListener(OnRecycleViewClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnRecycleViewClickListener {
        void onItemClick(View view, int position);
    }
}
