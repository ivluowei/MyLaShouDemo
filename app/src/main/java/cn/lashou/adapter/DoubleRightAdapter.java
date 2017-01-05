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
import cn.lashou.entity.CategoryBean;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/12/16.
 */

public class DoubleRightAdapter extends RecyclerView.Adapter<DoubleRightAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CategoryBean.DataBean.SymptomNameBean> mSymptomNameList;

    public DoubleRightAdapter(Context context, ArrayList<CategoryBean.DataBean.SymptomNameBean> symptomNameList) {
        this.mContext = context;
        this.mSymptomNameList = symptomNameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_right, parent, false);
        SupportMultipleScreensUtil.scale(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTitleRight.setText(mSymptomNameList.get(position).getItemName());
        holder.mNumRight.setText(mSymptomNameList.get(position).getSymptomNount() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSymptomNameList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_right)
        TextView mTitleRight;
        @BindView(R.id.num_right)
        TextView mNumRight;

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
        void onItemClick(View view, int position);
    }
}
