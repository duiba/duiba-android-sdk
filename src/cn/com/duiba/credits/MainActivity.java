package cn.com.duiba.credits;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import cn.com.duiba.credits.CreditActivity.CreditsListener;

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
                intent.putExtra("url", "http://www.duiba.com.cn/test/demoRedirectSAdfjosfdjdsa");    //配置自动登陆地址，每次需服务端动态生成。
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
						//当分享按钮被点击时，会调用此处代码。在这里处理分享的业务逻辑。
						new AlertDialog.Builder(webView.getContext())
						.setTitle("分享信息")
						.setItems(new String[] {"标题："+shareTitle,"副标题："+shareSubtitle,"缩略图地址："+shareThumbnail,"链接："+shareUrl}, null)
						.setNegativeButton("确定", null)
						.show();
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
						new AlertDialog.Builder(webView.getContext()) 
					 	.setTitle("跳转登录")
					 	.setMessage("跳转到登录页面？")
					 	.setPositiveButton("是", null)
					 	.setNegativeButton("否", null)
					 	.show();
					}
					
					/**
			         * 当点击“复制”按钮时，触发该方法，回调获取到券码code
			         * @param webView webview对象。
			         * @param code 复制的券码
			         */
					public void onCopyCode(WebView webView, String code) {
						//当未登录的用户点击去登录时，会调用此处代码。
						new AlertDialog.Builder(webView.getContext()) 
					 	.setTitle("复制券码")
					 	.setMessage("已复制，券码为："+code)
					 	.setPositiveButton("是", null)
					 	.setNegativeButton("否", null)
					 	.show();
					}
				};
			}
		});
	}
	
}
