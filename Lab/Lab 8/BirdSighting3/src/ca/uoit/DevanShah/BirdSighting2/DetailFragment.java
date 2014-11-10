package ca.uoit.DevanShah.BirdSighting2;

import java.text.DateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DetailFragment extends Fragment {

	ArrayList<Sighting> sightings;
	Activity myActivity;
	TextView view1;
	TextView view2;
	TextView view3;
	TextView view4;
	static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
			DateFormat.SHORT);

	public DetailFragment() {
		// TODO Auto-generated constructor stub
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.detail_view, container, false);
		myActivity = getActivity();
		sightings = ((MainActivity) myActivity).sightings;
		((MainActivity) myActivity).myDetailFragment = this;
		view1 = (TextView) view.findViewById(R.id.textView1);
		view2 = (TextView) view.findViewById(R.id.textView2);
		view3 = (TextView) view.findViewById(R.id.textView3);
		view4 = (TextView) view.findViewById(R.id.textView4);
		view1.setText(sightings.get(0).bird);
		view2.setText(df.format(sightings.get(0).when));
		view3.setText(sightings.get(0).location);
		view4.setText(sightings.get(0).description);
		return view;
	}

	public void displayDetail(int p) {
		Sighting sighting;
		sighting = sightings.get(p);
		view1.setText(sighting.bird);
		view2.setText(df.format(sighting.when));
		view3.setText(sighting.location);
		view4.setText(sighting.description);
	}

}
