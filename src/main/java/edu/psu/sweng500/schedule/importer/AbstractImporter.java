package edu.psu.sweng500.schedule.importer;

import java.nio.file.Path;

public abstract class AbstractImporter 
{
	public abstract void take( Path path ) throws Exception;
}
