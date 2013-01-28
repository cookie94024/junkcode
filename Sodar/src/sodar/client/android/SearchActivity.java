package sodar.client.android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private LocalCache db;
	private String uid = "default";
	private int range = 0;
	private int relationship = 0;
	private int eventType = 0;
	private int eventTimeRange = 0;
	private Spinner[] spinner = new Spinner[4];
	private ImageButton searchBarButton;
	private Button searchButton;
	private EditText searchEditText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		initialize();
		findViews();
		setViews();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		db.close();
	}

	public void initialize() {
		uid = SearchActivity.this.getIntent().getExtras().getString("uid");

		// ���o����ݸ�Ʈw�O��
		db = new LocalCache(this);

		Cursor cursor = db.getLocalCache(uid);
		int rowNum = cursor.getCount();
		cursor.moveToFirst();

		if (rowNum > 0) {
			range = cursor.getInt(1);
			relationship = cursor.getInt(2);
			eventType = cursor.getInt(3);
			eventTimeRange = cursor.getInt(4);
		}
	}

	public void findViews() {
		spinner[0] = (Spinner) findViewById(R.id.search_range_spinner);
		spinner[1] = (Spinner) findViewById(R.id.relationship_spinner);
		spinner[2] = (Spinner) findViewById(R.id.event_type_spinner);
		spinner[3] = (Spinner) findViewById(R.id.event_time_range_spinner);
		searchBarButton = (ImageButton) findViewById(R.id.image_button1);
		searchButton = (Button) findViewById(R.id.button2);
		searchEditText = (EditText) findViewById(R.id.editText1);
	}

	public void setViews() {
		// �j�M�d��Spinner
		// �إߤ@��ArrayAdapter����A�é�m�U�Կ�檺���e
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		        new String[] { "5 ����", "10 ����", "30 ����", "50 ����", "100 ����" });
		// �]�w�U�Կ�檺�˦�
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner[0].setAdapter(adapter1);
		// �]�w���سQ������᪺�ʧ@
		spinner[0].setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				range = position;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		// �j�M���YSpinner
		// �إߤ@��ArrayAdapter����A�é�m�U�Կ�檺���e
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		        new String[] { "�Ҧ��H", "�ȭ��B��" });
		// �]�w�U�Կ�檺�˦�
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner[1].setAdapter(adapter2);
		// �]�w���سQ������᪺�ʧ@
		spinner[1].setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				relationship = position;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		// �ƥ����OSpinner
		// �إߤ@��ArrayAdapter����A�é�m�U�Կ�檺���e
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		        new String[] { "�Ҧ��ƥ�", "���", "�@�P", "�E�|", "�ӤH��{" });
		// �]�w�U�Կ�檺�˦�
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner[2].setAdapter(adapter3);
		// �]�w���سQ������᪺�ʧ@
		spinner[2].setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				eventType = position;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		// �ƥ�ɶ�Spinner
		// �إߤ@��ArrayAdapter����A�é�m�U�Կ�檺���e
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		        new String[] { "1 �Ѥ�", "1 �P��", "2 �P��", "1 �Ӥ뤺", "3 �Ӥ뤺" });
		// �]�w�U�Կ�檺�˦�
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner[3].setAdapter(adapter4);
		// �]�w���سQ������᪺�ʧ@
		spinner[3].setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				eventTimeRange = position;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		// �j�MBar Button
		searchBarButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				String searchText = searchEditText.getText().toString();
				if(searchText == null) {
					finish();
				}
				else {
					Intent intent = new Intent();
					intent.setClass(SearchActivity.this, MainActivity.class);
					Bundle data = new Bundle();
					data.putString("search_text", searchText);
					intent.putExtras(data);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});

		// �j�MButton
		searchButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (!db.setSearchLocalCache(uid, range, relationship, eventType, eventTimeRange)) {
					Toast.makeText(SearchActivity.this, "��Ʈw�X�F�I���D�A�Э��s�j�M", Toast.LENGTH_SHORT).show();
				}
				else {
					Intent intent = new Intent();
					intent.setClass(SearchActivity.this, MainActivity.class);
					intent.putExtras(new Bundle());
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});

		// �]�w�U�ӿﶵ���e
		spinner[0].setSelection(range);
		spinner[1].setSelection(relationship);
		spinner[2].setSelection(eventType);
		spinner[3].setSelection(eventTimeRange);
	}
}