package ca.uoit.DevanShah.lab6;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private final String[] courses = { "CSCI 1030", "CSCI 1040", "CSCI 2000",
			"CSCI 2050", "CSCI 3010", "CSCI 3020", "CSCI 3040", "CSCI 3055",
			"CSCI 3090", "CSCI 4100", "CSCI 4160" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, courses);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(myMessageClickedHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnItemClickListener myMessageClickedHandler = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			CheckedTextView cell = (CheckedTextView) v;
			onOptionsItemSelected(cell);
		}
	};

	@SuppressLint("NewApi")
	public boolean onOptionsItemSelected ( CheckedTextView cell) {

		if ( cell.isChecked() ) {
			Toast toast = Toast.makeText ( this, String.valueOf(cell.getText()),Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, Math.round (cell.getX()), (Math.round (cell.getY())-84) );
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}

		return true;
	}
}
