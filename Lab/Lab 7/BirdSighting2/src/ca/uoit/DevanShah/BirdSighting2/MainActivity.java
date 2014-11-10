package ca.uoit.DevanShah.BirdSighting2;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity {

	public ArrayList<Sighting> sightings;

	public DetailFragment myDetailFragment;
	public EditDetail myEditDetail;
	ArrayAdapter<Sighting> adapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sightings = new ArrayList<Sighting>();
		sightings.add(new Sighting("Pigeon", "everywhere", "An ugly bird"));
		sightings.add(new Sighting("Robin", "back yard",
				"The early bird gets the worm"));
		sightings.add(new Sighting("Blue Jay", "AC Centre", "Let's play ball"));
		setContentView(R.layout.activity_main);
		myDetailFragment = new DetailFragment();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.detail_fragment, myDetailFragment);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		EditDetail myEditDetail;
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add_item:
			myEditDetail = new EditDetail();
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.detail_fragment, myEditDetail);
			transaction.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onBirdClicked(int position) {
		myDetailFragment.displayDetail(position);
	}

	@SuppressLint("NewApi")
	public void editCancel() {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.detail_fragment, myDetailFragment);
		transaction.commit();
		myDetailFragment.displayDetail(0);
	}

	@SuppressLint("NewApi") public void editDone(Sighting sighting) {
		sightings.add(sighting);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.detail_fragment, myDetailFragment);
		transaction.commit(); 
	}
}
