package my.testtask.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class RingtonesListActivity extends Activity implements OnItemClickListener {
    private ListView ringtones;
    private String host = "http://onoapps.com/Dev/ringtones.txt";
	private Handler h;
	static List<String> rings;
	private HttpResponse response;
	private InputStream is;
	private String json;
	private JSONObject jObj;
	private JSONArray jRingtones;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ringtones_list);
		ringtones = (ListView) findViewById(R.id.lvRings);
		if(rings == null) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					getAvatars();
					
				}
			});
			t.start();
			
		} else {
			ringtones.setAdapter(new ArrayAdapter<String>(RingtonesListActivity.this, android.R.layout.simple_list_item_1, rings));
			ringtones.setOnItemClickListener(RingtonesListActivity.this);
		}
		h = new Handler() {
		      public void handleMessage(android.os.Message msg) {
		    	ringtones.setAdapter(new ArrayAdapter<String>(RingtonesListActivity.this, android.R.layout.simple_list_item_1, rings));
		        ringtones.setOnItemClickListener(RingtonesListActivity.this);
		  		return;
		      }
		};
		
	}
	
	private void getAvatars() {
		rings = new ArrayList<String>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(host);
	    try {
			response = httpclient.execute(httpPost);
			is = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.i("JSON: ", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
	    try {
			jObj = new JSONObject(json);
			jRingtones = jObj.getJSONArray("ringtones");
			for(int i = 0; i < jRingtones.length(); i++) {
				String ring = ((JSONObject) jRingtones.get(i)).getString("ringName") + " - " + 
						((JSONObject) jRingtones.get(i)).getString("ringDec");
				rings.add(ring);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.sendEmptyMessage(0);
		
	}
 

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("ring", rings.get(arg2));
		startActivity(i);
	}
}
