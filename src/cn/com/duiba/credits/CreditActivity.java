package cn.com.duiba.credits;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CreditActivity extends Activity {
	
	protected String url;
	
	protected WebView mWebView;
	
	protected LinearLayout mLinearLayout;
	
	protected RelativeLayout mNavigationBar;
	
	protected TextView mTitle;
	
	protected TextView mBack;
	
	private int RequestCode=100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		url=getIntent().getStringExtra("url");
		if(url==null){
			throw new RuntimeException("url 必须传入");
		}
		
		initView();
		setContentView(mLinearLayout);
		
		mBack.setClickable(true);
		mBack.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				onBackClick();
			}
		});

	    
	    mWebView.setWebChromeClient(new WebChromeClient(){
	    	@Override
	    	public void onReceivedTitle(WebView view, String title) {
	    		CreditActivity.this.onReceivedTitle(view, title);
	    	}
	    });
		
	    mWebView.setWebViewClient(new WebViewClient(){
	    	@Override
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		return shouldOverrideUrlByDuiba(view, url);
	    	}
	    });
	    
	    mWebView.loadUrl(url);
	}
	
	
	
	protected void onBackClick(){
		Intent intent=new Intent();
		setResult(99,intent);
		finish();
	}
	
	protected void initNavigationBar(){
		mNavigationBar=new RelativeLayout(this);
		mNavigationBar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,48));
		
		mTitle=new TextView(this);
		mTitle.setText("加载中");
		mTitle.setMaxWidth(250);
		mTitle.setTextSize(20.0f);
		
		mNavigationBar.addView(mTitle);
		
		android.widget.RelativeLayout.LayoutParams p=(android.widget.RelativeLayout.LayoutParams)mTitle.getLayoutParams();
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		
		mBack=new TextView(this);
		mBack.setText("返回");
		mBack.setTextSize(20.0f);
		
		RelativeLayout.LayoutParams mBackLayout=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		mBackLayout.setMargins(50, 10, 0, 0);
		mNavigationBar.addView(mBack,mBackLayout);
		
		
	}
	
	protected void initWebView(){
		mWebView=new WebView(this);
	    mWebView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	    
		WebSettings settings = mWebView.getSettings();

		// User settings
		settings.setJavaScriptEnabled(true);
		settings.setLoadsImagesAutomatically(true);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(false);
		settings.setSaveFormData(true);
		settings.setSavePassword(true);
		settings.setDefaultZoom(ZoomDensity.MEDIUM);

		CookieManager.getInstance().setAcceptCookie(true);

		if (Build.VERSION.SDK_INT > 8) {
			settings.setPluginState(PluginState.ON_DEMAND);
		}

		settings.setSupportZoom(true);

		// Technical settings
		settings.setSupportMultipleWindows(true);
		mWebView.setLongClickable(true);
		mWebView.setScrollbarFadingEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.setDrawingCacheEnabled(true);

		settings.setAppCacheEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setDomStorageEnabled(true);
	}
	
	protected void initView(){
		mLinearLayout=new LinearLayout(this);
		mLinearLayout.setBackgroundColor(Color.GRAY);
		mLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		
		initNavigationBar();
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(  
		        LayoutParams.FILL_PARENT, 100); 
		
		mLinearLayout.addView(mNavigationBar,mLayoutParams);
		
		initWebView();
		
		mLinearLayout.addView(mWebView);
		
	}
	
	protected void onReceivedTitle(WebView view,String title){
		mTitle.setText(title);
	}
	
	protected boolean shouldOverrideUrlByDuiba(WebView view,String url){
		if(this.url.equals(url)){
			view.loadUrl(url);
			return true;
		}
		if(url.contains("dbnewopen")){
			Intent intent = new Intent();
			intent.setClass(CreditActivity.this, CreditActivity.this.getClass());
			intent.putExtra("url", url);
			startActivityForResult(intent, RequestCode);
		}else if(url.contains("dbbackrefresh")){
			Intent intent = new Intent();
			intent.putExtra("url", url);
			setResult(RequestCode, intent);
			finish();
		}else{
			view.loadUrl(url);
		}
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==RequestCode){
			if(data.getStringExtra("url")!=null){
				this.url=data.getStringExtra("url");
				mWebView.loadUrl(this.url);
			}
		}
	}
}
