package my.testtask.screens;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class AvatarListActivity extends Activity implements OnItemClickListener {
    private ListView avatars;
    private String host = "http://onoapps.com/Dev/avatars/";
    private String a ="a0";
    private String a1 = "a";
	private Handler h;
    static List<Drawable> pics;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avatars_list);
		avatars = (ListView) findViewById(R.id.lvAvas);
		if(pics == null) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					getAvatars();
					
				}
			});
			t.start();
			
		} else {
			AvatarsAdapter adapter = new AvatarsAdapter(this);
			avatars.setAdapter(adapter);
			avatars.setOnItemClickListener(AvatarListActivity.this);
		}
		h = new Handler() {
		      public void handleMessage(android.os.Message msg) {
		        AvatarsAdapter adapter = new AvatarsAdapter(AvatarListActivity.this);
		  		avatars.setAdapter(adapter);
		  		avatars.setOnItemClickListener(AvatarListActivity.this);
		  		return;
		      }
		};
		
	}
	
	private void getAvatars() {
		pics = new ArrayList<Drawable>(13);
		for(int i = 1; i < 14; i++){
			String ina = (i < 10) ? a : a1;
			URL url = null;
			try {
				url = new URL(host + ina + String.valueOf(i) + ".png");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStream content = null;
			try {
				content = (InputStream)url.getContent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Drawable d = Drawable.createFromStream(content , "src");
			pics.add(d);
		}
		h.sendEmptyMessage(0);
		
	}
    
	class AvatarsAdapter extends BaseAdapter{
		private LayoutInflater mLayoutInflater;
		
		public AvatarsAdapter(Context ctx) {
			mLayoutInflater = LayoutInflater.from(ctx);
		}

		@Override
		public int getCount() {
			return pics.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mLayoutInflater.inflate(R.layout.item, null);
			ImageView ava = (ImageView) convertView.findViewById(R.id.ivAva);
			if(position % 2 == 0) {
				
			} else {
				convertView.setBackgroundResource(R.drawable.item_list);
			}
			ava.setImageDrawable(pics.get(position));
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("id", arg2);
		startActivity(i);
	}
}
