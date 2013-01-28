package sodar.client.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowScheduleActivity extends Activity {
	private TextView placeDisplay;
	private TextView typeView;
	private TextView titleView;
	private ImageView createrPictureView;
	private TextView createrNameView;
	private TextView createrPhoneView;
	private TextView startDateDisplay;
	private TextView endDateDisplay;
	private TextView descriptionView;

	private String uid;
	private String createrUid;
	private String eid;
	private String Createrphone;
	private String address;
	private double longitude;
	private double latitude;
	private String accessToken;
	private int userActivityRequestCode = 6;
	private int showScheduleRequestCode = 5;
	JSONObject jsonResult = new JSONObject();

	// private int scheduleRequestCode = 3;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_schedule);

		placeDisplay = (TextView) findViewById(R.id.placedisplay);
		typeView = (TextView) findViewById(R.id.event_type);
		typeView.setText("�ӤH��{");
		titleView = (TextView) findViewById(R.id.event_title);
		createrPictureView = (ImageView) findViewById(R.id.imageView2);
		createrNameView = (TextView) findViewById(R.id.user_name);
		createrPhoneView = (TextView) findViewById(R.id.user_phone);
		startDateDisplay = (TextView) findViewById(R.id.start_time);
		endDateDisplay = (TextView) findViewById(R.id.end_time);
		descriptionView = (TextView) findViewById(R.id.description_content);

		uid = ShowScheduleActivity.this.getIntent().getExtras()
				.getString("uid");
		eid = ShowScheduleActivity.this.getIntent().getExtras()
				.getString("eid");
		accessToken = ShowScheduleActivity.this.getIntent().getExtras()
				.getString("access_token");
		GetSchedule();

	}

	public void GetSchedule() {
		SendRequest sendRequest = new SendRequest();
		JSONObject jsonResult = sendRequest.getSchedule(eid);
		try {
			titleView.setText(jsonResult.getString("title"));
			GetUserNameAndPicture(jsonResult.getString("uid"),
					jsonResult.getString("name"));
			createrPhoneView.setText(jsonResult.getString("phone"));
			Createrphone = jsonResult.getString("phone");
			String start_time[] = jsonResult.getString("start_time").split(
					"\\.");
			startDateDisplay.setText(start_time[0]);
			String end_time[] = jsonResult.getString("end_time").split("\\.");
			endDateDisplay.setText(end_time[0]);
			descriptionView.setText(jsonResult.getString("description"));

			longitude = Double.parseDouble(jsonResult.getString("longitude"));
			latitude = Double.parseDouble(jsonResult.getString("latitude"));
			getAddress(longitude, latitude);
			placeDisplay.setText(address);

			createrUid = jsonResult.getString("uid");

			if (createrUid.equals(uid)) {
				LinearLayout linearLayoutMain = (LinearLayout) findViewById(R.id.linear_editbutton);
				Button editButton = new Button(this);
				editButton.setText("�ק�");
				editButton.setHeight(30);
				editButton
						.setLayoutParams(new LinearLayout.LayoutParams(-1, 40));
				linearLayoutMain.addView(editButton);
				editButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// �i�J�s��planActivity
						// �}�ҽs��ƥ�activity
						Intent intent = new Intent();
						intent.setClass(ShowScheduleActivity.this,
								EditScheduleActivity.class);
						String orig_startTime = startDateDisplay.getText()
								.toString();
						String orig_endTime = endDateDisplay.getText()
								.toString();
						String orig_description = descriptionView.getText()
								.toString();
						String orig_title = titleView.getText().toString();
						Bundle bundle = new Bundle();
						bundle.putString("uid", uid);
						bundle.putString("eid", eid);
						bundle.putString("orig_title", orig_title);
						bundle.putString("orig_startTime", orig_startTime);
						bundle.putString("orig_endTime", orig_endTime);
						bundle.putString("orig_description", orig_description);
						bundle.putString("longitude", "" + longitude);
						bundle.putString("latitude", "" + latitude);
						bundle.putString("address", address);
						intent.putExtras(bundle);
						startActivityForResult(intent, showScheduleRequestCode);
					}
				});
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getAddress(double longitude, double latitude) {
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(latitude,
					longitude, 1);
			if (addresses != null && addresses.size() > 0) {
				Address add = addresses.get(0);
				address = add.getAddressLine(0) + ", " + add.getLocality();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// �T�{�^�Ǫ�requestCode�MresultCode
		if (requestCode == this.showScheduleRequestCode
				&& resultCode == RESULT_OK) {
			// ���Activity2�ǹL�Ӫ��ƭ�
			Bundle bundle = data.getExtras();
			String type = bundle.getString("type");
			if (type.equals("0")) {
				String start_time = bundle.getString("start_time");
				String end_time = bundle.getString("end_time");
				String description = bundle.getString("description");
				String title = bundle.getString("title");
				address = bundle.getString("address");
				startDateDisplay.setText(start_time);
				endDateDisplay.setText(end_time);
				descriptionView.setText(description);
				titleView.setText(title);
				placeDisplay.setText(address);
			} else if (type.equals("1")) {
				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, data);
				finish();
			}
		}
	}

	// �B�z���o�ƥ�o�_�̪��Ӥ�
	public void GetUserNameAndPicture(String uid, String name) {
		String pictureURL = null;
		Bitmap userPic;
		try {
			pictureURL = "http://graph.facebook.com/" + uid
					+ "/picture?type=square";
			userPic = BitmapFactory.decodeStream(new URL(pictureURL)
					.openStream());
			createrPictureView.setImageBitmap(userPic);// �ƥ�o�_�̪��Ӥ�
			createrNameView.setText(name); // �ƥ�o�_�̪��W�r
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (createrUid.equals(uid)) {
			menu.add(0, 2, 2, "�d�ݦa��");
		} else {
			menu.add(0, 0, 0, "�����q��");
			menu.add(0, 1, 1, "�d�ݵo�_�H��T");
			menu.add(0, 2, 2, "�d�ݦa��");
		}

		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// �̾�itemId�ӧP�_�ϥΪ��I����@��item
		switch (item.getItemId()) {
		// �����q��
		case 0:
			if (Createrphone.equals("")) {
				Toast.makeText(ShowScheduleActivity.this, "���ϥΪ̥����G�q�ܸ��X",
						Toast.LENGTH_SHORT).show();
			} else {
				Uri uri = Uri.parse("tel:" + Createrphone);
				Intent intent = new Intent(Intent.ACTION_CALL, uri);
				startActivity(intent);
			}
			break;

		// �}��user activity
		case 1:
			SendRequest sendRequest = new SendRequest();
			JSONObject jsonResult = sendRequest.getcheckin(createrUid);
			;
			try {
				String longitude = jsonResult.getString("longitude");
				String latitude = jsonResult.getString("latitude");
				Intent intent = new Intent();
				intent.setClass(ShowScheduleActivity.this, UserActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("uid", createrUid);
				bundle.putString("uid_me", uid);
				bundle.putString("longitude", longitude);
				bundle.putString("latitude", latitude);
				bundle.putString("access_token", accessToken);
				intent.putExtras(bundle);
				startActivityForResult(intent, userActivityRequestCode);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			Intent intent = new Intent();
			intent.setClass(ShowScheduleActivity.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putDouble("longitude", longitude);
			bundle.putDouble("latitude", latitude);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
