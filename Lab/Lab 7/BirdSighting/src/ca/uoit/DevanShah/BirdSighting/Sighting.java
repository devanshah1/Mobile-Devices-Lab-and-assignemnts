package ca.uoit.DevanShah.BirdSighting;

import java.util.Date;
import java.text.DateFormat;

public class Sighting {
	public String bird;
	public Date when;
	public String location;
	public String description;
	static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
			DateFormat.SHORT);

	public Sighting(String Bird, String Location, String Description) {
		bird = Bird;
		location = Location;
		description = Description;
		when = new Date();
	}

	@Override
	public String toString() {
		return bird + ": " + df.format(when);
	}
}