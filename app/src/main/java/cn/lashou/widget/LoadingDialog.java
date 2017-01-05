package cn.lashou.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import cn.lashou.R;

/**
 * loadingDialog
 * 
 */
public class LoadingDialog extends Dialog {
	private Context context;
	private AnimationDrawable animationDrawable;
	private ImageView ivAnimView;
	private Handler handler = new Handler();

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.loading_dialog_layout);
		View rootView = findViewById(android.R.id.content);
		SupportMultipleScreensUtil.scale(rootView);
		setCanceledOnTouchOutside(false);
		ivAnimView = (ImageView) findViewById(R.id.loading_animview);
		ivAnimView.setBackgroundResource(R.drawable.dialog_loading);

		handler.postDelayed(new Runnable() {
			public void run() {
				Object backgroundObject = ivAnimView.getBackground();
				animationDrawable = (AnimationDrawable) backgroundObject;
				animationDrawable.setOneShot(false);
				animationDrawable.stop();
				animationDrawable.start();
			}
		}, 50);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.dismiss();
	}
	
	@Override
	public void dismiss() {
		if(animationDrawable != null && animationDrawable.isRunning()){
			animationDrawable.stop();
		}
		super.dismiss();
	}

}
