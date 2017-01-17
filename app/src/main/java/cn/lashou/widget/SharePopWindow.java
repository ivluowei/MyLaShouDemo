package cn.lashou.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.HashMap;

import cn.lashou.R;
import cn.lashou.util.ToastUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.lashou.R.id.ll;

/**
 * Created by luow on 2017/1/12.
 */

public class SharePopWindow extends PopupWindow implements View.OnClickListener {
    private final String mTitle;
    private final String mImgUrl;
    private Context mContext;
    private View view;
    private LinearLayout mSinaShare;
    private LinearLayout mWeiXinShare;
    private LinearLayout mQQShare;
    private Animation mIn_PopupwindAnimation;
    private Animation mOut_PopupwindAnimation;
    private LinearLayout mShareLayout;

    public SharePopWindow(Context context, String title, String imgUrl) {
        super(context);
        this.mContext = context;
        this.mTitle = title;
        this.mImgUrl = imgUrl;

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        mIn_PopupwindAnimation = AnimationUtils.loadAnimation(mContext,
                R.anim.in_popupwind);
        mOut_PopupwindAnimation = AnimationUtils.loadAnimation(mContext,
                R.anim.out_popupwindow);
        initView();
    }

    private void initView() {
        findView();
        setListener();
        this.setContentView(view);
    }

    private void findView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_share, null);
        mShareLayout = (LinearLayout) view.findViewById(R.id.share);
        mSinaShare = (LinearLayout) view.findViewById(R.id.sina_share);
        mWeiXinShare = (LinearLayout) view.findViewById(R.id.weixin_share);
        mQQShare = (LinearLayout) view.findViewById(R.id.qq_share);
    }

    private void setListener() {
        mSinaShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(mContext, "开始分享...");
                SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                sp.setText(mTitle);
                sp.setTitle("测试Test");
                sp.setTitleUrl("https://github.com/ivluowei");
                sp.setImageUrl(mImgUrl);
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);

                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        ToastUtils.showShort(mContext, "分享成功");
                        dismissAnimation();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        ToastUtils.showShort(mContext, "分享取消");
                    }
                });
                // 设置分享事件回调
                // 执行图文分享
                weibo.share(sp);
            }
        });


        mWeiXinShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(mContext, "开始分享...");

                Wechat.ShareParams wsp = new Wechat.ShareParams();
                wsp.setShareType(Platform.SHARE_WEBPAGE);
                wsp.setText("微信测试");//分享的文本
                wsp.setTitle(mTitle);//分享的标题
                wsp.setTitleUrl("https://github.com/ivluowei");
                wsp.setImageUrl(mImgUrl);//图片网络地址
                wsp.setUrl("https://github.com/ivluowei");
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);

                weChat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        dismissAnimation();
                        ToastUtils.showShort(mContext, "分享成功...");

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        ToastUtils.showShort(mContext, "分享取消...");

                    }
                });
                // 设置分享事件回调
                // 执行图文分享
                weChat.share(wsp);
            }
        });

        mQQShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(mContext, "开始分享...");

                QQ.ShareParams sp = new QQ.ShareParams();
                sp.setTitle("测试");//测试分享的标题
                sp.setTitleUrl("https://github.com/ivluowei"); // 标题的超链接
                sp.setText(mTitle);//测试分享的文本
                sp.setImageUrl(mImgUrl);//测试图片网络地址.jpg
                sp.setSite("ivluowei");//发布分享的网站名称
                sp.setSiteUrl("https://github.com/ivluowei");//发布分享网站的地址

                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
                qq.setPlatformActionListener(new PlatformActionListener() {
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                        ToastUtils.showShort(mContext, "Error");
                    }

                    public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                        dismissAnimation();
                        //分享成功的回调
                        ToastUtils.showShort(mContext, "分享成功");

                    }

                    public void onCancel(Platform arg0, int arg1) {
                        //取消分享的回调
                        ToastUtils.showShort(mContext, "分享取消");
                    }
                });
                // 执行图文分享
                qq.share(sp);
            }
        });

        mOut_PopupwindAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            dismiss();
        }
    };

    public void dismissAnimation() {
        mShareLayout.startAnimation(mOut_PopupwindAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ll:
                dismiss();
                break;

            default:
                break;
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        mShareLayout.startAnimation(mIn_PopupwindAnimation);
    }

    private SingleListPopwindows.OnSingleChangeListener mListener;

    public void setOnSingleChangeListener(SingleListPopwindows.OnSingleChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnSingleChangeListener {
        void onSingleChange(String text);
    }

}