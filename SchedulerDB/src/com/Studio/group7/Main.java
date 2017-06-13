package com.Studio.group7;

import com.Studio.group7.MysqlAccess;;
/*
 * Class to read the database.
 */
public class Main {

	public static void main(String[] args) throws Exception {
		MysqlAccess dao =  new MysqlAccess();
		dao.readDB();
	}
}
