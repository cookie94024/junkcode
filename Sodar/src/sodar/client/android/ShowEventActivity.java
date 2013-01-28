package sodar.client.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEventActivity extends Activity {
	private int showEventRequestCode = 4;
	private int userActivityRequestCode = 6;
	private String uid;
	private String eid;
	private String createrUid;
	private String Createrphone;
	private String address;
	private double longitude;
	private double latitude;
	private String accessToken;
	private String[] eventType = { "", "���", "�@�P", "�E�|" };
	private List<Bitmap> eventPhotoForList = new ArrayList<Bitmap>();

	private String orig_event_type; // �O���ƥ����O(���)�A�H�ѽs��ƥ�ϥ�
	private TextView eventTypeView; // �ƥ�����
	private TextView eventTitleView; // �ƥ�W��
	private ImageView eventPictureView;
	private ImageView CreaterPictureView; // �o�_�H�Ӥ�
	private TextView eventCreaterView; // �o�_�H�m�W
	private TextView eventPhoneView; // �o�_�H�q��
	private TextView startTimeView; // �}�l�ɶ�
	private TextView endTimeView; // �����ɶ�
	private TextView placeDisplay;
	private TextView descriptionView; // �ԭz
	private Button JoinButton;// �ѻP�ƥ�
	private ListView participantsView; // �ƥ�ѻP��
	private SimpleAdapter participantsViewAdapter;
	private ButtonListener JoinButtonListener = new ButtonListener();
	private ArrayList<HashMap<String, Object>> participantsList = new ArrayList<HashMap<String, Object>>();
	private JSONObject jsonResult = new JSONObject();
	private JSONArray jsonResultForpart = new JSONArray();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_event);
		findViews();
		initialize();
		GetEvent();
	}

	public void findViews() {
		eventPictureView = (ImageView) findViewById(R.id.imageView1);
		eventTypeView = (TextView) findViewById(R.id.event_type);
		eventTitleView = (TextView) findViewById(R.id.event_title);
		eventCreaterView = (TextView) findViewById(R.id.user_name);
		eventPhoneView = (TextView) findViewById(R.id.user_phone);
		startTimeView = (TextView) findViewById(R.id.start_time);
		endTimeView = (TextView) findViewById(R.id.end_time);
		placeDisplay = (TextView) findViewById(R.id.placedisplay);
		descriptionView = (TextView) findViewById(R.id.description_content);
		CreaterPictureView = (ImageView) findViewById(R.id.imageView2);
		participantsView = (ListView) findViewById(R.id.listView1);
		JoinButton = (Button) findViewById(R.id.button);
		JoinButton.setOnClickListener(JoinButtonListener);
	}

	public void initialize() {
		eventPhotoForList.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.murgent));
		eventPhotoForList.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.mrequest));
		eventPhotoForList.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.mmeeting));
		eventPhotoForList.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.mschedule));

		uid = ShowEventActivity.this.getIntent().getExtras().getString("uid");
		eid = ShowEventActivity.this.getIntent().getExtras().getString("eid");
		accessToken = ShowEventActivity.this.getIntent().getExtras()
				.getString("access_token");

		participantsViewAdapter = new SimpleAdapter(this, participantsList,
				R.layout.participant_list, new String[] { "pic", "info" },
				new int[] { R.id.participant_imageView, R.id.information });

		participantsViewAdapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else {
					return false;
				}
			}
		});

		participantsView.setAdapter(participantsViewAdapter);
		participantsView.setTextFilterEnabled(true);
		participantsView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				JSONObject jsonTemp = new JSONObject();
				String uidhit = null;
				String longitude = null;
				String latitude = null;
				try {
					jsonTemp = jsonResultForpart.getJSONObject(position);
					uidhit = jsonTemp.getString("uid");
					longitude = jsonTemp.getString("longitude");
					latitude = jsonTemp.getString("latitude");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent intent = new Intent();
				intent.setClass(ShowEventActivity.this, UserActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("uid", uidhit);
				bundle.putString("uid_me", uid);
				bundle.putString("longitude", longitude);
				bundle.putString("latitude", latitude);
				bundle.putString("access_token", accessToken);
				intent.putExtras(bundle);
				startActivityForResult(intent, userActivityRequestCode);
			}
		});

	}

	// �V��Ʈw���o���ƥ���
	public void GetEvent() {
		SendRequest sendRequest = new SendRequest();
		jsonResult = sendRequest.getEvent(eid);

		try {
			eventTypeView.setText(eventType[Integer.parseInt(jsonResult
					.getString("type"))]);
			eventTitleView.setText(jsonResult.getString("title"));
			GetUserNameAndPicture(jsonResult.getString("uid"),
					jsonResult.getString("name"));
			createrUid = jsonResult.getString("uid");
			Createrphone = jsonResult.getString("phone");
			eventPhoneView.setText(jsonResult.getString("phone"));
			String start_time[] = jsonResult.getString("start_time").split(
					"\\.");
			startTimeView.setText(start_time[0]);
			String end_time[] = jsonResult.getString("end_time").split("\\.");
			eventPictureView.setImageBitmap(eventPhotoForList.get(Integer
					.parseInt(jsonResult.getString("type")) - 1));
			endTimeView.setText(end_time[0]);
			descriptionView.setText(jsonResult.getString("description"));
			longitude = Double.parseDouble(jsonResult.getString("longitude"));
			latitude = Double.parseDouble(jsonResult.getString("latitude"));
			getAddress(longitude, latitude);
			placeDisplay.setText(address);

			// �O���ƥ����O�A�H���ѵ��s��ƥ�ϥ�
			orig_event_type = jsonResult.getString("type");

			if (jsonResult.getString("uid").equals(uid)) {
				JoinButton.setText("�s��");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GetEventParticipant(eid);
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

	public class ButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (JoinButton.getText().equals("�s��")) {
				Intent intent = new Intent();
				intent.setClass(ShowEventActivity.this, EditEventActivity.class);
				String orig_title = eventTitleView.getText().toString();
				String orig_startTime = startTimeView.getText().toString();
				String orig_endTime = endTimeView.getText().toString();
				String orig_description = descriptionView.getText().toString();
				Bundle bundle = new Bundle();
				bundle.putString("uid", uid);
				bundle.putString("eid", eid);
				bundle.putString("orig_event_type", orig_event_type);
				bundle.putString("orig_title", orig_title);
				bundle.putString("orig_startTime", orig_startTime);
				bundle.putString("orig_endTime", orig_endTime);
				bundle.putString("orig_description", orig_description);
				bundle.putString("longitude", "" + longitude);
				bundle.putString("latitude", "" + latitude);
				bundle.putString("address", address);
				intent.putExtras(bundle);
				startActivityForResult(intent, showEventRequestCode);
			} else { // �_�h�P�_��L�ƥ�B�z
				if (JoinButton.getText().equals("�ѻP")) {
					SendRequest sendRequest = new SendRequest();
					jsonResult = sendRequest.addParticipant(uid, eid);
					JoinButton.setText("�h�X");
					participantsList.clear();
					GetEventParticipant(eid);
					participantsViewAdapter.notifyDataSetChanged();
				} else if (JoinButton.getText().equals("�h�X")) {
					SendRequest sendRequest = new SendRequest();
					jsonResult = sendRequest.removeParticipant(uid, eid);
					JoinButton.setText("�ѻP");
					participantsList.clear();
					GetEventParticipant(eid);
					participantsViewAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// �T�{�^�Ǫ�requestCode�MresultCode
		if (requestCode == this.showEventRequestCode && resultCode == RESULT_OK) {

			// ���EditEventActivity�ǹL�Ӫ��ƭ�
			Bundle bundle = new Bundle();
			bundle = data.getExtras();
			if (bundle.getString("type").equals("0")) {
				String event_type = bundle.getString("event_type");
				String title = bundle.getString("title");
				String start_time = bundle.getString("start_time");
				String end_time = bundle.getString("end_time");
				String description = bundle.getString("description");
				orig_event_type = event_type;
				eventTypeView.setText(eventType[Integer.parseInt(event_type)]);
				eventTitleView.setText(title);
				startTimeView.setText(start_time);
				endTimeView.setText(end_time);
				descriptionView.setText(description);
			} else if (bundle.getString("type").equals("1")) {
				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
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
			CreaterPictureView.setImageBitmap(userPic);// �ƥ�o�_�̪��Ӥ�
			eventCreaterView.setText(name); // �ƥ�o�_�̪��W�r
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void GetParticipantNameAndPicture(String uid, String name) {
		String pictureURL = null;
		Bitmap userPic;
		HashMap<String, Object> participantsItem = new HashMap<String, Object>();
		try {
			pictureURL = "http://graph.facebook.com/" + uid
					+ "/picture?type=square";
			userPic = BitmapFactory.decodeStream(new URL(pictureURL)
					.openStream());
			participantsItem.put("pic", userPic);
			participantsItem.put("info", name);
			participantsList.add(participantsItem);
			participantsViewAdapter.notifyDataSetChanged();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void GetEventParticipant(String eid) {
		SendRequest sendRequest = new SendRequest();
		jsonResult = sendRequest.getEventParticipant(eid);

		try {
			jsonResultForpart = jsonResult.getJSONArray("event_participant");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// �]�w�i�H���A�H��ܰѻP�̸ԲӸ�T�A�s����UserProfile
		// participantsView.setClickable(true);

		for (int i = 0; i < jsonResultForpart.length(); i++) {
			JSONObject jsonResult = new JSONObject();
			try {
				jsonResult = jsonResultForpart.getJSONObject(i);

				if (jsonResult.getString("uid").equals(uid)) {
					JoinButton.setText("�h�X");
				}
				GetParticipantNameAndPicture(jsonResult.getString("uid"),
						jsonResult.getString("name"));

			} catch (JSONException e) {
				e.printStackTrace();
			}
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
				Toast.makeText(ShowEventActivity.this, "���ϥΪ̥����G�q�ܸ��X",
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
				intent.setClass(ShowEventActivity.this, UserActivity.class);
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
			intent.setClass(ShowEventActivity.this, MainActivity.class);
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
