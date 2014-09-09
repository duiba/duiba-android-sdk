package cn.com.duiba.credits;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
				intent.putExtra("url", "http://www.duiba.com.cn/test/demoRedirectSAdfjosfdjdsa");
				startActivity(intent);
			}
		});
	}
}
