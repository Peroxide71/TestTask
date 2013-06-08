package my.testtask.screens;

import java.net.URLEncoder;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Button chooseRington, shareByEmail;
	private ImageView pic;
	private EditText first, last, phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		chooseRington = (Button) findViewById(R.id.bChoose);
		first = (EditText) findViewById(R.id.etName);
		last = (EditText) findViewById(R.id.etSecondName);
		phone = (EditText) findViewById(R.id.etPhone);
		shareByEmail = (Button) findViewById(R.id.bShare);
		pic = (ImageView) findViewById(R.id.ivAvatar);
		if(AvatarListActivity.pics != null && getIntent().hasExtra("id")) {
			pic.setImageDrawable(AvatarListActivity.pics.get(getIntent().getIntExtra("id", 0)));
		}
		if(RingtonesListActivity.rings != null && getIntent().hasExtra("ring")) {
			chooseRington.setText(RingtonesListActivity.rings.get(getIntent().getIntExtra("ring", 0)));
		}
		
		pic.setOnClickListener(this);
		chooseRington.setOnClickListener(this);
		shareByEmail.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v == pic) {
			Intent i = new Intent(this, AvatarListActivity.class);
			startActivity(i);
		} else if(v == chooseRington) {
			Intent i = new Intent(this, RingtonesListActivity.class);
			startActivity(i);
		} else if (v == shareByEmail){ 
			if(first.getText().toString().length() > 0 &&
					phone.getText().toString().length() > 0 &&
					getIntent().hasExtra("id")) {
				String mail = "mailto:welcome@onoapps.com" + 
					    "?subject=" + URLEncoder.encode("Share Contact") + 
					    "&body=" + URLEncoder.encode("Name: " + first.getText().toString() + "\n" +
					    "Number: " + phone.getText().toString() +
					    		"\n" + "http://onoapps.com/Dev/avatars/" + getIntent().getIntExtra("id", 0));
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mail)));
			}
			
		}
		
	}

}
