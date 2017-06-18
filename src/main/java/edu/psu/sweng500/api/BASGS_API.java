package edu.psu.sweng500.api;

import java.util.ArrayList;

import org.apache.camel.spring.Main;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/*
 * Author: Brian Abate
 * This class provides an API for external application to interact
 * with BASGS.
 */
@Service
public class BASGS_API {
	
	/*
	 * Constructor for BASGS_API
	 */
	public BASGS_API() {
	}
	
	// Class structure used to store API Object 
	static class API_Object {
		String name;
		int id;
		int action_id;
		int num_of_obj;
		ArrayList<BacnetObj> bacnet = new ArrayList<BacnetObj>();
	}
	
	// Class structure used to store bacnet object 
	static class BacnetObj {
		String object_identifier;
		String object_name;
		String object_type;
		int present_value;
		String description;
		String decive_type;
		String status_flag;
		String event_state;
		String reliability;
		String out_of_service;
		String update_interval;
		String units;
		String min_pres_value;
		String max_pres_value;
		String resolution;
		String cov_increment;
		String time_delay;
		String notificaiton_class;
		String high_limit;
		String low_limit;
		String deadband;
		String limit_enabled;
		String event_enabled;
		String acked_transitions;
		String notify_types;
	}
	
	/*
	 * This method allows external applications to create BASGS events.
	 */
	private void create(API_Object api) {
		System.out.println("TEst");
	}
	
	/*
	 * This method allows external applications to read BASGS events.
	 */
	private void read(API_Object api) {
		
	}
	
	/*
	 * This method allows external applications to update BASGS events.
	 */
	private void update(API_Object api) {
		
	}
	
	/*
	 * This method allows external applications to delete BASGS events.
	 */
	private void delete(API_Object api) {
		
	}
	
	/*
	 * This method is called when the Server receives data, parses the data, 
	 * and sends the converted api object to the appropriate message.
	 * @param [in] api_obj - String of the json api object
	 */
	public void parseMsg(String api_obj) {
		API_Object api = null;
		try {
			Gson gson = new Gson();        
			api = gson.fromJson(api_obj, API_Object.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(api.action_id) {
			case 0:
				create(api);
				break;
			case 1:
				read(api);
				break;
			case 2:
				update(api);
				break;
			case 3:
				delete(api);
				break;
			default:
				break;
		}
	}
	
	/*
	 * The main method used for running the BASGS_API and starts Camel
	 * Spring Main.
	 * @param [in] args - String array of arguements passed in.
	 */
	public static void main(String[] args) throws Exception {
	    new Main().run(args);
	  }

}
