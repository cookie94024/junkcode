package sodar.client.android;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

public class UserActivity extends Activity {
	public static final String APP_ID = "192393297482948";
	private Facebook mFacebook = new Facebook(APP_ID);
	private Bitmap userPic;
	private String uid;
	private String uid_me;
	private String address;
	private String name;
	private String sex;
	private String aboutMe;
	private String educationHistory;
	private String workHistory;
	private String phone;
	private String checkMsg;
	private String checkinTime;
	private String accessToken;
	private String longitude;
	private String latitude;
	private TextView[] mTextView = new TextView[7];
	private ImageView mUserPic;
	private View layout;
	private EditText editPhone;
	private EditText mCheckinMsg;
	private AddfriendListener addfriendListener = new AddfriendListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		initialize();
		setView();
	}

	public void getAddress(double longitude, double latitude) {
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
			if (addresses != null && addresses.size() > 0) {
				Address add = addresses.get(0);
				address = add.getAddressLine(0) + ", " + add.getLocality();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		uid = UserActivity.this.getIntent().getExtras().getString("uid");
		uid_me = UserActivity.this.getIntent().getExtras().getString("uid_me");
		longitude = UserActivity.this.getIntent().getExtras().getString("longitude");
		latitude = UserActivity.this.getIntent().getExtras().getString("latitude");
		accessToken = UserActivity.this.getIntent().getExtras().getString("access_token");
		getAddress(Double.parseDouble(longitude), Double.parseDouble(latitude));

		SendRequest sendRequest = new SendRequest();
		JSONObject user = sendRequest.getUserProfile(uid_me, uid);
		JSONObject checkin = sendRequest.getcheckin(uid);

		try {
			// ���o�j�Y��
			URL imgValue = new URL("http://graph.facebook.com/" + uid + "/picture?type=square");
			userPic = BitmapFactory.decodeStream(imgValue.openConnection().getInputStream());
			name = user.getString("name");
			sex = user.getString("sex");
			phone = user.getString("phone");
			educationHistory = user.getString("educationHistory");
			workHistory = user.getString("workHistory");
			aboutMe = user.getString("aboutMe");
			checkinTime = checkin.getString("create_time");
			checkMsg = checkin.getString("description");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setView() {
		// ���oLayout����
		mTextView[0] = (TextView) findViewById(R.id.textView_name);
		mTextView[1] = (TextView) findViewById(R.id.textView_checkinAddress);
		mTextView[2] = (TextView) findViewById(R.id.textView_mSex);
		mTextView[3] = (TextView) findViewById(R.id.textView_mEdu);
		mTextView[4] = (TextView) findViewById(R.id.textView_mWork);
		mTextView[5] = (TextView) findViewById(R.id.textView_mAboutMe);
		mTextView[6] = (TextView) findViewById(R.id.textview_mCheckinTime);
		mUserPic = (ImageView) findViewById(R.id.imageView_picSquare);
		mCheckinMsg = (EditText) findViewById(R.id.msg);

		// ��l�Ƥ��e
		mTextView[0].setText(name);
		mTextView[1].setText(address);
		mTextView[2].setText(sex);
		mTextView[3].setText(educationHistory);
		mTextView[4].setText(workHistory);
		mTextView[5].setText(aboutMe);
		mTextView[6].setText(checkinTime);
		mUserPic.setImageBitmap(userPic);
		mCheckinMsg.setText(checkMsg);

		// �ݦۤv
		if (uid.equals(uid_me)) {
			LinearLayout linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout_main);
			Button editButton = new Button(this);
			editButton.setText("�ק��p���q��");
			editButton.setHeight(30);
			editButton.setLayoutParams(new LinearLayout.LayoutParams(-1, 40));
			linearLayoutMain.addView(editButton);

			final EditDialogListener editListener = new EditDialogListener();

			// �ק�ӤH���Button
			editButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View arg0) {
					LayoutInflater inflater = getLayoutInflater();
					layout = inflater.inflate(R.layout.edit_user_profile_dialog,
					        (ViewGroup) findViewById(R.id.dialog));
					editPhone = (EditText) layout.findViewById(R.id.editView_phone);
					editPhone.setText(phone);
					new AlertDialog.Builder(UserActivity.this).setTitle("�ק��p���q��").setView(layout)
					        .setPositiveButton("�T�w", editListener).setNegativeButton("����", null).show();
				}
			});
		}
		// �ݥL�H
		else {
			CheckIsFriend checkisfriend = new CheckIsFriend();
			String isfriend = checkisfriend.check(accessToken, uid);
			if (isfriend.equals("notfriend")) {
				LinearLayout linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout_main);
				Button addfriendButton = new Button(this);
				addfriendButton.setText("�[���n��");
				addfriendButton.setHeight(30);
				addfriendButton.setLayoutParams(new LinearLayout.LayoutParams(-1, 40));
				linearLayoutMain.addView(addfriendButton);
				addfriendButton.setOnClickListener(addfriendListener);
				addfriendListener.getResult(uid);
			}
			
			// �����q��Button
			LinearLayout linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout_main);
			Button callButton = new Button(this);
			callButton.setText("�����q��");
			callButton.setHeight(30);
			callButton.setLayoutParams(new LinearLayout.LayoutParams(-1, 40));
			linearLayoutMain.addView(callButton);

			callButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View arg0) {
					// �I�sAndroid�t��API�����q��
					if (phone.equals("")) {
						Toast.makeText(UserActivity.this, "���ϥΪ̥����G�q�ܸ��X", Toast.LENGTH_SHORT).show();
					}
					else {
						Uri uri = Uri.parse("tel:" + phone);
						Intent intent = new Intent(Intent.ACTION_CALL, uri);
						startActivity(intent);
					}
				}
			});

			// �o�e²�TButton
			Button msgButton = new Button(this);
			msgButton.setText("�o�e²�T");
			msgButton.setHeight(30);
			msgButton.setLayoutParams(new LinearLayout.LayoutParams(-1, 40));
			linearLayoutMain.addView(msgButton);

			msgButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View arg0) {
					// �I�sAndroid�t��API�o�e²�T
					if (phone.equals("")) {
						Toast.makeText(UserActivity.this, "���ϥΪ̥����G�q�ܸ��X", Toast.LENGTH_SHORT).show();
					}
					else {
						Uri uri = Uri.parse("smsto:" + phone);
						Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
						startActivity(intent);
					}
				}
			});
		}
		
		
	}

	private class EditDialogListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			phone = editPhone.getText().toString();
			if (!phone.equals("")) {
				String phoneFormat = "09\\w{8}";
				Pattern phonePattern = Pattern.compile(phoneFormat);
				Matcher phoneMatcher = phonePattern.matcher(phone);
				if (!phoneMatcher.matches()) {
					Toast.makeText(UserActivity.this, "��J�榡���~�A�Э��s��J", Toast.LENGTH_SHORT).show();
				}
				else {
					SendRequest request = new SendRequest();
					JSONObject editUserProfile = request.editUserProfile(uid, name, phone);

					try {
						if (editUserProfile.getString("status").equals("success")) {
							Toast.makeText(UserActivity.this, "�x�s����", Toast.LENGTH_SHORT).show();
						}
						else {
							Log.e("edit_phone", editUserProfile.getString("status"));
							Toast.makeText(UserActivity.this, "�����s�u�Φ��A���o�Ϳ��~�A�ɭP�x�s�L�{�X�{���D�A�еy�ԦA����",
							        Toast.LENGTH_SHORT).show();
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					finally {

					}
				}
			}
		}
	}

	public class AddfriendListener implements OnClickListener {
		private String result;

		@Override
		public void onClick(View v) {
			Bundle params_friend = new Bundle();
			params_friend.putString("id", result);
			mFacebook.dialog(UserActivity.this, "friends", params_friend, new SampleDialogListener());
		}

		public void getResult(String uid) {
			result = uid;
		}
	}

	private class SampleDialogListener implements com.facebook.android.Facebook.DialogListener {

		@Override
		public void onComplete(Bundle values) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onFacebookError(FacebookError e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onError(DialogError e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "�d�ݦa��");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// �̾�itemId�ӧP�_�ϥΪ��I����@��item
		switch (item.getItemId()) {
			case 0:
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putDouble("longitude", Double.parseDouble(longitude));
				bundle.putDouble("latitude", Double.parseDouble(latitude));
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
