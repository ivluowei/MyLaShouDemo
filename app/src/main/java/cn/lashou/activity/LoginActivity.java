package cn.lashou.activity;

import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lashou.R;
import cn.lashou.core.BaseActivity;
import cn.lashou.entity.UserBean;
import cn.lashou.listener.MyTextWatch;
import cn.lashou.widget.LoadingDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by luow on 2016/9/10.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.ic_back)
    ImageView mIcBack;
    @BindView(R.id.zhuce)
    TextView mZhuce;
    @BindView(R.id.tv_quick_register)
    TextView mTvQuickRegister;
    @BindView(R.id.tv_count_register)
    TextView mTvCountRegister;
    @BindView(R.id.view_line_left)
    ImageView mViewLineLeft;
    @BindView(R.id.view_line_right)
    ImageView mViewLineRight;
    @BindView(R.id.quick_phone)
    EditText mQuickPhone;
    @BindView(R.id.btn_get_code)
    Button mBtnGetCode;
    @BindView(R.id.et_quick_code)
    EditText mEtQuickCode;
    @BindView(R.id.quick_btn)
    Button mQuickBtn;
    @BindView(R.id.quick_login)
    LinearLayout mQuickLogin;
    @BindView(R.id.account_phone)
    EditText mAccountPhone;
    @BindView(R.id.cb_show_pwd)
    CheckBox mCbShowPwd;
    @BindView(R.id.account_pwd)
    EditText mAccountPwd;
    @BindView(R.id.account_btn)
    Button mAccountBtn;
    @BindView(R.id.forget_pwd)
    TextView mForgetPwd;
    @BindView(R.id.account_login)
    LinearLayout mAccountLogin;
    @BindView(R.id.qq)
    ImageView mQq;
    @BindView(R.id.sina)
    ImageView mSina;

    /**
     * tab下划线动画
     */
    private Animation mAnimRight;
    private Animation mAnimLeft;
    private int mOrrange;
    private int mGray;
    private boolean isSelect = true;
    private boolean isSelectTwo = true;

    /**
     * 帐号名密码是否为空
     **/
    private boolean isUsernameNull = false;
    private boolean isPwdNull = false;
    /**
     * 手机号验证码是否为空
     **/
    private boolean isPhoneNull = false;
    private boolean isCodeNull = false;
    private int mCount;

    public static LoginActivity currentActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        currentActivity = this;
        mOrrange = getResources().getColor(R.color.home_title_bar_color);
        mGray = getResources().getColor(R.color.content_color);
        initAnimation();
    }

    @Override
    protected void initData() {
        mQuickPhone.addTextChangedListener(new MyTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                isPhoneNull = TextUtils.isEmpty(s.toString()) ? false : true;
                mQuickBtn.setEnabled((isPhoneNull && isCodeNull) ? true : false);
            }
        });

        mEtQuickCode.addTextChangedListener(new MyTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                isCodeNull = TextUtils.isEmpty(s.toString()) ? false : true;
                mQuickBtn.setEnabled((isPhoneNull && isCodeNull) ? true : false);
            }
        });

        mAccountPhone.addTextChangedListener(new MyTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                isUsernameNull = TextUtils.isEmpty(s.toString()) ? false : true;
                mAccountBtn.setEnabled((isUsernameNull && isPwdNull) ? true : false);
            }
        });

        mAccountPwd.addTextChangedListener(new MyTextWatch() {
            @Override
            public void afterTextChanged(Editable s) {
                isPwdNull = TextUtils.isEmpty(s.toString()) ? false : true;
                mAccountBtn.setEnabled((isUsernameNull && isPwdNull) ? true : false);
            }
        });

        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAccountPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    int inputType = InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                    mAccountPwd.setInputType(inputType);
                }
            }
        });
    }

    private Handler mHandler = new Handler();

    @OnClick({R.id.ic_back, R.id.tv_quick_register, R.id.tv_count_register, R.id.btn_get_code, R.id.quick_btn, R.id.account_btn, R.id.qq, R.id.sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_back:
                finish();
                break;
            case R.id.tv_quick_register:
                if (isSelect) {
                    isSelect = false;
                    isSelectTwo = true;
                    mViewLineRight.startAnimation(mAnimRight);
                }
                break;
            case R.id.tv_count_register:
                if (isSelectTwo) {
                    isSelect = true;
                    isSelectTwo = false;
                    mViewLineLeft.startAnimation(mAnimLeft);
                }

                break;
            case R.id.btn_get_code:
                showShare();

                break;
            case R.id.quick_btn:
                LoadingDialog dialog = new LoadingDialog(this,R.style.LoadingDialog);
                dialog.show();
                break;
            case R.id.account_btn:

                break;
            case R.id.qq:
                Platform qq = ShareSDK.getPlatform(this,QQ.NAME);
                qq.SSOSetting(false);  //设置false表示使用SSO授权方式
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {



                        //通过打印res数据看看有哪些数据是你想要的
                        if (i == Platform.ACTION_USER_INFOR) {
                            PlatformDb platDB = platform.getDb();//获取数平台数据DB

                            EventBus.getDefault().post(new UserBean(platDB.getUserName(),hashMap.get("figureurl_qq_2").toString()));

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },800);
                        }


                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        Log.d("sp","ss");
                    }
                }); // 设置分享事件回调

                qq.authorize();//单独授权
                qq.showUser(null);//授权并获取用户信息
                break;

            case R.id.sina:
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.SSOSetting(false);
                //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                        //通过打印res数据看看有哪些数据是你想要的
                        if (i == Platform.ACTION_USER_INFOR) {
                            PlatformDb platDB = platform.getDb();//获取数平台数据DB

                            EventBus.getDefault().post(new UserBean(platDB.getUserName(),hashMap.get("figureurl_qq_2").toString()));

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },800);
                        }
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                //authorize与showUser单独调用一个即可
                weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
                weibo.showUser(null);//授权并获取用户信息
                break;
        }
    }

    //点击分享
    private void showShare() {
        ShareSDK.initSDK(getApplicationContext());

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        //   oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    /***
     * 初始化动画
     */
    private void initAnimation() {
          /* 线左边移动 */
        mAnimRight = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.view_line_move_left);
        mAnimRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTvQuickRegister.setTextColor(mOrrange);
                mTvCountRegister.setTextColor(mGray);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewLineLeft.setVisibility(View.VISIBLE);
                mViewLineRight.setVisibility(View.INVISIBLE);
                mQuickLogin.setVisibility(View.VISIBLE);
                mAccountLogin.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        /* 线右边移动 */
        mAnimLeft = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.view_line_move_right);
        mAnimLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTvQuickRegister.setTextColor(mGray);
                mTvCountRegister.setTextColor(mOrrange);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewLineLeft.setVisibility(View.INVISIBLE);
                mViewLineRight.setVisibility(View.VISIBLE);
                mQuickLogin.setVisibility(View.GONE);
                mAccountLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 发送验证码倒计时
     */
    private void countdownTimer() {
        mBtnGetCode.setEnabled(false);
        mCount = 60;
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCount--;
                        mBtnGetCode.setText(mCount + "");
                        if (mCount <= 0) {
                            mBtnGetCode.setText("重新发送");
                            mBtnGetCode.setEnabled(true);
                            timer.cancel();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
