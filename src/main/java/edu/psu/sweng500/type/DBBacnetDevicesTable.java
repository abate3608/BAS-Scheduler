package edu.psu.sweng500.type;

import java.util.Date;

public class DBBacnetDevicesTable {
	private String object_identifier;
	private String object_name;
	private String object_type;
	private String system_status;
	private String vendor_name;
	private String vendor_identifier;
	private String model_name;
	private String application_software_version;
	private String protocol_version;
	private int protocol_conformance_class;
	private String protocol_service_supported;
	private String protocol_object_types_supported;
	private String max_apdu_length_supported;
	private String segmentation_supported;
	private String vt_classes_supported;
	private String active_vt_sessions;
	private int utc_offset;
	private String daylight_savings_status;
	private String apdu_timeout;
	private int number_of_apu_retries;
	private String list_of_session_keys;
	private String time_synchronization_recipients;
	private String max_master;
	private String device_address_binding;
	private String port;

	public DBBacnetDevicesTable () {
		this.object_identifier = "";
		this.object_name = "";
		this.object_type = "";
		this.system_status = "";
		this.vendor_name = "";
		this.vendor_identifier = "";
		this.model_name = "";
		this.application_software_version = "";
		this.protocol_version = "";
		this.protocol_conformance_class = 0;
		this.protocol_service_supported = "";
		this.protocol_object_types_supported = "";
		this.max_apdu_length_supported = "";
		this.segmentation_supported = "";
		this.vt_classes_supported = "";
		this.active_vt_sessions = "";
		this.utc_offset = 0;
		this.daylight_savings_status = "";
		this.apdu_timeout = "";
		this.number_of_apu_retries = 0;
		this.list_of_session_keys = "";
		this.time_synchronization_recipients = "";
		this.max_master = "";
		this.device_address_binding = "";
		this.port = "0xBAC0";
	}

	public void setObject_Identifier(String s) {
		this.object_identifier = s;
	}

	public String getObject_Identifier() {
		return this.object_identifier;
	}

	public void setObject_Name (String s) {
		this.object_name = s;
	}

	public String getObject_Name () {
		return this.object_name;
	}

	public void setObject_Type (String s) {
		this.object_type = s;
	}

	public String getObject_Type () {
		return this.object_type;
	}

	public void setSystem_Status (String s) {
		this.system_status = s;
	}

	public String getSystem_Status () {
		return this.system_status;
	}

	public void setVendor_Name (String s) {
		this.vendor_name = s;
	}

	public String getVendor_Name () {
		return this.vendor_name;
	}

	public void setVendor_Identifier(String s) {
		this.vendor_identifier = s;
	}

	public String getVendor_Identifier() {
		return this.vendor_identifier;
	}

	public void setModel_Name(String s) {
		this.model_name = s;
	}

	public String getModel_Name () {
		return this.model_name;
	}

	public void setApplication_Software_Version (String s) {
		this.application_software_version = s;
	}

	public String getApplication_Software_Version () {
		return this.application_software_version;
	}

	public void setProtocol_Version (String s) {
		this.protocol_version = s;
	}

	public String getProtocol_Version () {
		return this.protocol_version;
	}

	public void setProtocol_Conformance_Class (int i) {
		this.protocol_conformance_class = i;
	}

	public int getProtocol_Conformance_Class() {
		return this.protocol_conformance_class;
	}

	public void setProtocol_Service_Supported (String s) {
		this.protocol_service_supported = s;
	}

	public String getProtocol_Service_Supported () {
		return this.protocol_service_supported;
	}

	public void setProtocol_Object_Types_Supported (String s) {
		this.protocol_object_types_supported = s;
	}

	public String getProtocol_Object_Types_Supported () {
		return this.protocol_object_types_supported;
	}


	public void setMax_APDU_Length_Supported (String s) {
		this.max_apdu_length_supported = s;
	}

	public String getMax_APDU_Length_Supported () {
		return this.max_apdu_length_supported;
	}

	public void setSegmentation_Supported (String s) {
		this.segmentation_supported = s;
	}

	public String getSegmentation_Supported () {
		return this.segmentation_supported;
	}

	public void setVT_Classes_Supported (String s) {
		this.vt_classes_supported = s;
	}

	public String getVT_Classes_Supported () {
		return this.vt_classes_supported;
	}

	public void setActive_VT_Sessions (String s) {
		this.active_vt_sessions = s;
	}

	public String getActive_VT_Sessions () {
		return this.active_vt_sessions;
	}

	public void setUTC_Offset (int i) {
		this.utc_offset = i;
	}

	public int getUTC_Offset () {
		return this.utc_offset;
	}

	public void setDaylight_Savings_Status (String s) {
		this.daylight_savings_status = s;
	}


	public String getDaylight_Savings_Status () {
		return this.daylight_savings_status;
	}

	public void setAPDU_Timeout (String s) {
		this.apdu_timeout = s;
	}

	public String getAPDU_Timeout() {
		return this.apdu_timeout;
	}

	public void setNumber_Of_APU_Retries (int i) {
		this.number_of_apu_retries = i;
	}

	public int getNumber_Of_APU_Retires() {
		return this.number_of_apu_retries;
	}

	public void setList_Of_Session_Keys (String s) {
		this.list_of_session_keys = s;
	}

	public String getList_Of_Session_Keys () {
		return this.list_of_session_keys;
	}

	public void setTime_Synchronization_Recipients (String s) {
		this.time_synchronization_recipients = s;
	}

	public String getTime_Synchronization_Recipients () {
		return this.time_synchronization_recipients;
	}

	public void setMax_Master (String s) {
		this.max_master = s;
	}

	public String getMax_Master() {
		return this.max_master;
	}

	public void setDevice_Address_Binding (String s) {
		this.device_address_binding = s;
	}

	public String getDevice_Address_Binding () {
		return this.device_address_binding;
	}

	public void setPort (String s) {
		this.port = s;
	}

	public String getPort () {
		return this.port;
	}

	public int getIntPort () {

		int intPort;

		switch (this.port) {
			case "0xBAC0":  
				intPort = 0xBAC0;
				break;
			case "0xBAC1":  
				intPort = 0xBAC1;
				break;
			case "0xBAC2":  
				intPort = 0xBAC2;
				break;
			default: 
				intPort = 0xBAC0;
				break;
		}

		return intPort;
	}
}
