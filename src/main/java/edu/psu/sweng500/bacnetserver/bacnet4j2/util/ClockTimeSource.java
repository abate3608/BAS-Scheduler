package edu.psu.sweng500.bacnetserver.bacnet4j2.util;

public class ClockTimeSource implements TimeSource {
	// @Override
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}
