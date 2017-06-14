package edu.psu.sweng500.database;

/*
 * Class to read the database.
 */
public class Main {

	public static void main(String[] args) throws Exception {
		MysqlAccess dao =  new MysqlAccess();
		dao.readDB();
	}
}
