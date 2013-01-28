package sodar.client.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NotifyService extends Service {
	private Handler handler = new Handler();
	private LocalCache db;
	private String uid = "default";
	private String accessToken;
	private Cursor cursor;
	private double longitude = 121.45982;
	private double latitude = 25.08608;
	private SendRequest sendRequest = new SendRequest();
	private JSONArray friendList;
	private ArrayList<JSONObject> oriFriends = new ArrayList<JSONObject>();
	private ArrayList<JSONObject> newFriends = new ArrayList<JSONObject>();
	private int notifyMode = 0;
	private int searchRange = 2;
	private int relationship = 1; // �q���n�� �j�M"�ȭ��B��"
	private int eventType = 0; // �q���ƥ� �j�M"�L"
	private int eventTimeRange = 0; // ���j�M�ƥ� ���ѼƤ��v�T
	private int frequency = 0;
	private int[] selectRange = { 5, 10, 30, 50, 100 };
	private int[] time = { 5 * 1000, 5 * 60 * 1000, 10 * 60 * 1000, 20 * 60 * 1000, 30 * 60 * 1000 };
	private boolean first = true;
	private MyLocationListener myLocationListener;
	private MyLocationListener2 myLocationListener2;
	private LocationManager locationManager;
	int a = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		uid = intent.getExtras().getString("uid");
		accessToken = intent.getExtras().getString("access_token");
		
		// ��l��GPS�A��
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		myLocationListener = new MyLocationListener();
		myLocationListener2 = new MyLocationListener2();
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (location != null) {
			longitude = location.getLongitude();
			latitude = location.getLatitude();
		}
		else {
			Toast.makeText(NotifyService.this, "�����s�u��GPS�w�즳���D�A�Э��s�s�u", Toast.LENGTH_LONG).show();
		}

		db = new LocalCache(this);

		cursor = db.getLocalCache(uid);
		int rowNum = cursor.getCount();

		cursor.moveToFirst();

		if (rowNum > 0) {
			notifyMode = cursor.getInt(6);
			searchRange = cursor.getInt(7);
			frequency = cursor.getInt(8);
		}
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1, myLocationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, myLocationListener2);

		// �j�@�q�ɶ��I�sNotify Thread
		handler.postDelayed(notify, time[frequency]);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// ������Ʈw
		db.close();
		handler.removeCallbacks(notify);
	}
	
	class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			longitude = location.getLongitude();
			latitude = location.getLatitude();
		}

		@Override
		public void onProviderDisabled(String provider) {
			if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
	
	class MyLocationListener2 implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			longitude = location.getLongitude();
			latitude = location.getLatitude();
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private Runnable notify = new Runnable() {
		@SuppressWarnings("unchecked")
		public void run() {
			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			myLocationListener = new MyLocationListener();
			myLocationListener2 = new MyLocationListener2();
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			if (location != null) {
				longitude = location.getLongitude();
				latitude = location.getLatitude();
			}
			else {
				Toast.makeText(NotifyService.this, "�����s�u��GPS�w�즳���D�A�Э��s�s�u", Toast.LENGTH_LONG).show();
			}

			String names = "";
			ArrayList<JSONObject> friendResult = new ArrayList<JSONObject>();

			// ���o��ƪ�C��
			int rowNum = cursor.getCount();
			// �N���в��ܲĤ@�����
			cursor.moveToFirst();
			if (rowNum > 0) {
				notifyMode = cursor.getInt(6);
				searchRange = cursor.getInt(7);
				frequency = cursor.getInt(8);
			}

			JSONObject jsonResult = sendRequest.search(uid, selectRange[searchRange], relationship,
			        eventType, eventTimeRange, longitude, latitude, accessToken);

			// �M��s�X�{�b���񪺨ϥΪ�
			try {
				friendList = jsonResult.getJSONArray("check_in");

				// �Ĥ@���i�JNotify�A���o�Ĥ@��friends��ƼȦs
				if (first) {
					for (int i = 0; i < friendList.length(); i++) {
						oriFriends.add((JSONObject) friendList.get(i));
					}

					first = false;
				}
				// �ĤG���H�����o��friends��Ʃ�Jmap������A��s"�[�J"���B�ͧ�X��(���ްh�X��)
				else {
					newFriends.clear();
					friendResult.clear();

					for (int i = 0; i < friendList.length(); i++) {
						newFriends.add((JSONObject) friendList.get(i));
					}

					// �M��s�[�J���B��
					for (JSONObject newOne : newFriends) {
						String newUid = newOne.getString("uid");
						boolean isNew = true;

						for (JSONObject oriOne : oriFriends) {
							String oriUid = oriOne.getString("uid");
							if (oriUid.equals(newUid)) {
								isNew = false;
							}
						}

						if (isNew == true && !uid.equals(newUid)) {
							friendResult.add(newOne);

							if (names.equals("")) {
								names += newOne.getString("name");
							}
							else {
								names += ", " + newOne.getString("name");
							}
						}
					}

					oriFriends = (ArrayList<JSONObject>) newFriends.clone();
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}

			// ���oNotification�A��
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			// �]�w����U�o�ӳq������n���檺activity
			Intent notifyIntent = new Intent(NotifyService.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("uid", uid);
			bundle.putString("access_token", accessToken);
			notifyIntent.putExtras(bundle);
			notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent appIntent = PendingIntent.getActivity(NotifyService.this, 0, notifyIntent, 0);
			Notification notification = new Notification();
			// �]�w�X�{�b���A�C���ϥ�
			notification.icon = R.drawable.icon;
			// ��ܦb���A�C����r
			notification.tickerText = "���B�ͥX�{�b�A������";
			// �|���q���G���`�B�a�n�B���ʡBlight
			switch (notifyMode) {
				case 0:
					notification.defaults = Notification.DEFAULT_ALL;
					break;
				case 1:
					notification.defaults = Notification.DEFAULT_VIBRATE;
					break;
				case 2:
					notification.defaults = Notification.DEFAULT_SOUND;
					break;
			}

			// �]�w�q�������D�B���e
			if (friendResult.size() > 0) {
				notification.setLatestEventInfo(NotifyService.this, names + "���b�a��A", "�@��"
				        + friendResult.size() + "��B�ͥX�{�b����A�d�ݭ��ǪB��", appIntent);
				// �e�XNotification
				notificationManager.notify(0, notification);
			}

			// �j�@�q�ɶ��I�s�ۤv(Notify Thread)
			handler.postDelayed(this, time[frequency]);
		}
	};

}
