package cn.lashou.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import cn.lashou.R;
import cn.lashou.adapter.CheckListAdapter;

/**
 * 带checkBox选项的数据传值不好搞
 * Created by luow on 2016/12/20.
 */

public class CheckListPopwindows extends PopupWindow implements OnClickListener {
    private Context mContext;
    private ArrayList<String> mCheckList;
    private View view;
    private RecyclerView mRecyclerView;
    private CheckListAdapter mCheckListAdapter;
    private LinearLayout ll;
    private Button mBtnComplete;
    private ArrayList<String> data;

    public CheckListPopwindows(Context context, ArrayList<String> mCheckList) {
        super(context);
        this.mContext = context;
        this.mCheckList = mCheckList;
        data = new ArrayList<>();
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_check_list, null);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        SupportMultipleScreensUtil.scale(view);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.check_list);
        mBtnComplete = (Button) view.findViewById(R.id.btn_complete);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, R.drawable.list_divider));
        mCheckListAdapter = new CheckListAdapter(mContext, mCheckList);
        mRecyclerView.setAdapter(mCheckListAdapter);
    }

    private void setListener() {
        ll.setOnClickListener(this);
        mCheckListAdapter.setRecycleViewOnClickListener(new CheckListAdapter.OnRecycleViewClickListener() {
            @Override
            public void onItemClick(boolean isCheckd, int pos) {
                if (isCheckd) {
                    // if (!text.contains(mCheckList.get(pos))) {
                    //     text += mCheckList.get(pos);
                    //TODO 这样写不行，暂时先这样
                    data.add(mCheckList.get(pos));
                    //   }
                } else {
                    if (data.size() == 1) {//以免data的size为1时，删除下标
                        pos = 0;
                    }
                    if (data.size() == 2 && pos ==2){
                        pos = 1;
                    }
                    data.remove(pos);
                }

            }
        });

        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCheckChange(data);
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

    private OnCheckChangeListener mListener;

    public void setOnCheckChangeListener(OnCheckChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnCheckChangeListener {
        void onCheckChange(ArrayList<String> data);
    }

}
