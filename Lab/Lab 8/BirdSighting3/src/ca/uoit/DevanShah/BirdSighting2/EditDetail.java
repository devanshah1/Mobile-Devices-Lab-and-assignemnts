package ca.uoit.DevanShah.BirdSighting2;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class EditDetail extends Fragment {
	ArrayList<Sighting> sightings;
	Activity myActivity;
	TextView view1;
	TextView view2;
	TextView view3;
	TextView view4;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.edit_view, container, false);
		myActivity = getActivity();
		sightings = ((MainActivity) myActivity).sightings;
		view1 = (EditText) view.findViewById(R.id.editText1);
		view2 = (EditText) view.findViewById(R.id.editText2);
		view3 = (EditText) view.findViewById(R.id.editText3);
		Button cancel = (Button) view.findViewById(R.id.button1);
		cancel.setOnClickListener(myCancelListener);
		Button done = (Button) view.findViewById(R.id.button2);
		done.setOnClickListener(myDoneListener);
		return view;
	}

	private OnClickListener myDoneListener = new OnClickListener() {
		public void onClick(View view) {
			Sighting sighting = new Sighting(view1.getText().toString(), view2
					.getText().toString(), view3.getText().toString());
			((MainActivity) myActivity).editDone(sighting);
		}
	};
	private OnClickListener myCancelListener = new OnClickListener() {
		public void onClick(View view) {
			((MainActivity) myActivity).editCancel();
		}
	};
}
