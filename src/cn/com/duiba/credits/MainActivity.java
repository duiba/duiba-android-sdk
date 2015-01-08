package cn.com.duiba.credits;


import cn.com.duiba.credits.CreditActivity.CreditsListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public final class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Button btn=new Button(this);
		btn.setText("Enter");
		
		setContentView(btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CreditActivity.class);
                intent.putExtra("navColor", "#0acbc1");    //配置导航条的背景颜色，请用#ffffff长格式。
                intent.putExtra("titleColor", "#ffffff");    //配置导航条标题的颜色，请用#ffffff长格式。
                intent.putExtra("url", "http://www.duiba.com.cn/test/demoRedirectSAdfjosfdjdsa");    //配置自动登陆地址，每次需动态生成。
				startActivity(intent);
				
				CreditActivity.creditsListener = new CreditsListener() {
					/**
			         * 当点击分享按钮被点击
			         * @param shareUrl 分享的地址
			         * @param shareThumbnail 分享的缩略图
			         * @param shareTitle 分享的标题
			         * @param shareSubtitle 分享的副标题
			         */
					public void onShareClick(WebView webView, String shareUrl,String shareThumbnail, String shareTitle,  String shareSubtitle) {
						//当分享按钮被点击时，会调用此处代码。
					}
					
					/**
			         * 当点击登录
			         * @param webView 用于登录成功后返回到当前的webview并刷新。
			         * @param currentUrl 当前页面的url
			         */
					public void onLoginClick(WebView webView, String currentUrl) {
						//当未登录的用户点击去登录时，会调用此处代码。
						//为了用户登录后能回到之前未登录前的页面。
						//当用户登录成功后，需要重新动态生成一次自动登录url，需包含redirect参数，将currentUrl放入redirect参数。
					}
				};
			}
		});
	}
	
}
