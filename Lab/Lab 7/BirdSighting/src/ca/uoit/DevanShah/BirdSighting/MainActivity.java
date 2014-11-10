package ca.uoit.DevanShah.BirdSighting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	Sighting[] sightings = {
			new Sighting("Pigeon", "everywhere", "An ugly bird"),
			new Sighting("Robin", "back yard", "The early bird gets the worm"),
			new Sighting("Blue Jay", "AC Centre", "Let's play ball") };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayAdapter<Sighting> adapter = new ArrayAdapter<Sighting> ( this, android.R.layout.simple_list_item_1, sightings ) ;
		
		ListView listView = (ListView) findViewById ( R.id.listView1 );
		listView.setAdapter ( adapter );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
