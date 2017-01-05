package cn.lashou.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import cn.lashou.R;
import cn.lashou.adapter.DoubleLeftAdapter;
import cn.lashou.adapter.DoubleRightAdapter;
import cn.lashou.entity.CategoryBean;

/**
 * Created by luow on 2016/12/16.
 */

public class DoubleListPopWindows extends PopupWindow implements OnClickListener {
    private Context mContext;
    private View view;
    //  private Animation mIn_PopupwindAnimation;
//  private Animation mOut_PopupwindAnimation;
    private RecyclerView mRightRecyclerView;
    private ListView mLeftListView;//这里不用RecyclerView是因为刷新的问题
    private DoubleRightAdapter mDoubleRightAdapter;
    private DoubleLeftAdapter mDoubleLeftAdapter;
    private List<CategoryBean.DataBean> mLeftDataList;
    private ArrayList<CategoryBean.DataBean.SymptomNameBean> mSymptomNameList;
    private LinearLayout mLlTranslate;
    private LinearLayout mViewLayout;
    /**
     * 左边listview选中的位置
     */
    private int leftSelectItem = 0;


    public DoubleListPopWindows(Context context, List<CategoryBean.DataBean> mLeftDataList) {
        super(context);
        this.mContext = context;
        this.mLeftDataList = mLeftDataList;
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.FILL_PARENT);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
//        mIn_PopupwindAnimation = AnimationUtils.loadAnimation(mContext,
//                R.anim.in_popupwind);
//        mOut_PopupwindAnimation = AnimationUtils.loadAnimation(mContext,
//                R.anim.out_popupwindow);
        initView();
    }

    private void initView() {
        findView();
        setListener();
        this.setContentView(view);
    }

    private void findView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_double_list, null);
        SupportMultipleScreensUtil.scale(view);
        mLeftListView = (ListView) view.findViewById(R.id.left_list);
        mRightRecyclerView = (RecyclerView) view.findViewById(R.id.right_list);
        mLlTranslate = (LinearLayout) view.findViewById(R.id.ll_Translate);
        mViewLayout = (LinearLayout) view.findViewById(R.id.view_layout);
        mSymptomNameList = new ArrayList<>();
        //左边
        mDoubleLeftAdapter = new DoubleLeftAdapter(mContext, mLeftDataList);
        mLeftListView.setDivider(mContext.getResources().getDrawable(R.drawable.list_divider));
        mLeftListView.setAdapter(mDoubleLeftAdapter);

        //右边
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRightRecyclerView.setLayoutManager(linearLayoutManager);
        mRightRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, R.drawable.list_divider));
        mDoubleRightAdapter = new DoubleRightAdapter(mContext, mSymptomNameList);
        mRightRecyclerView.setAdapter(mDoubleRightAdapter);

    }

    private void setListener() {
        //   mOut_PopupwindAnimation.setAnimationListener(this);

        mLlTranslate.setOnClickListener(this);

        mLeftListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && position == leftSelectItem) {
                    return;
                }

                if (mLeftDataList.get(position).getSymptomName().size() > 0) {
                    //保存新选中的位置
                    leftSelectItem = position;
                    //item被点击后刷新listview
                    mDoubleLeftAdapter.setSelectItem(position);
                    mDoubleLeftAdapter.notifyDataSetChanged();
                    mSymptomNameList.clear();
                    mSymptomNameList.addAll(mLeftDataList.get(position).getSymptomName());
                    mDoubleRightAdapter.notifyDataSetChanged();
                } else {
                    mListener.onDoubleChange(mLeftDataList.get(position).getCategoryName(), "");
                    dismiss();
                }
            }
        });

        mDoubleRightAdapter.setRecycleViewOnClickListener(new DoubleRightAdapter.OnRecycleViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onDoubleChange(mLeftDataList.get(leftSelectItem).getCategoryName(), mSymptomNameList.get(position).getItemName());
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_Translate:
                dismiss();
                break;

            default:
                break;
        }
    }

    private OnDoubleChangeListener mListener;

    public void setOnDoubleChangeListener(OnDoubleChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnDoubleChangeListener {
        void onDoubleChange(String leftValue, String rightValue);
    }

//    public void dismissAnimation() {
//        mViewLayout.startAnimation(mOut_PopupwindAnimation);
//    }
//
//
//    @Override
//    public void showAsDropDown(View anchor) {
//        super.showAsDropDown(anchor);
//        // mViewLayout.startAnimation(mIn_PopupwindAnimation);
//    }
//
//    Handler mHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            dismiss();
//        }
//    };
//
//    @Override
//    public void onAnimationStart(Animation animation) {
//
//    }
//
//    @Override
//    public void onAnimationEnd(Animation animation) {
//        mHandler.sendEmptyMessage(0);
//    }
//
//    @Override
//    public void onAnimationRepeat(Animation animation) {
//
//    }


}
