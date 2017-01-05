package cn.lashou.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import cn.lashou.R;
import cn.lashou.adapter.SingleListAdapter;

/**
 * Created by luow on 2016/12/16.
 */

public class SingleListPopwindows extends PopupWindow implements OnClickListener {
    private Context mContext;
    private ArrayList<String> mPaixunList;
    private View view;
    private RecyclerView mRecyclerView;
    private SingleListAdapter adapter;
    private LinearLayout ll;

    public SingleListPopwindows(Context context, ArrayList<String> mPaixunList) {
        super(context);
        this.mContext = context;
        this.mPaixunList = mPaixunList;
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        initView();
    }

    private void initView() {
        findView();
        setListener();
        this.setContentView(view);
    }

    private void findView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_single_list, null);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        SupportMultipleScreensUtil.scale(view);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.single_list);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, R.drawable.list_divider));
        adapter = new SingleListAdapter(mContext, mPaixunList);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        ll.setOnClickListener(this);
        adapter.setRecycleViewOnClickListener(new SingleListAdapter.OnRecycleViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onSingleChange(mPaixunList.get(position));
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll:
                dismiss();
                break;

            default:
                break;
        }
    }

    private OnSingleChangeListener mListener;

    public void setOnSingleChangeListener(OnSingleChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnSingleChangeListener {
        void onSingleChange(String text);
    }

}