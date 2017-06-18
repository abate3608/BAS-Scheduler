package edu.psu.sweng500.database;

import edu.psu.sweng500.database.*;

public interface DataEventListener {

	public void readData(DataEvent b);

	public void updateData(DataEvent b);

	public void deleteData(DataEvent b);

	public void validateData(DataEvent b);

	public void notify(DataEvent b);
}
