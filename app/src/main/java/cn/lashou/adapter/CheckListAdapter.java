package cn.lashou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lashou.R;
import cn.lashou.widget.SupportMultipleScreensUtil;

/**
 * Created by luow on 2016/12/20.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mCheckList;

    public CheckListAdapter(Context context, ArrayList<String> mCheckList) {
        this.mContext = context;
        this.mCheckList = mCheckList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_check, parent, false);
        SupportMultipleScreensUtil.scale(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTvSingleCheck.setText(mCheckList.get(position));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.onItemClick(isChecked, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCheckList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_single_check)
        TextView mTvSingleCheck;
        @BindView(R.id.cb_checkBox)
        CheckBox mCheckBox;

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
        void onItemClick(boolean ischeck, int pos);
    }
}
