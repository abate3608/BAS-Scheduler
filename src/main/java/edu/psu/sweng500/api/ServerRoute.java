package edu.psu.sweng500.api;

import org.apache.camel.builder.RouteBuilder;
 
/*
 * Author: Brian Abate
 * This class provides a means to configure the Camel Netty server
 * with BASGS.
 */
public class ServerRoute extends RouteBuilder {
	
	String IP_ADDRESS = "127.0.0.1";
	String PORT = "7000";
 
	@Override
	public void configure() throws Exception {
		from("netty:tcp://"+IP_ADDRESS+":"+PORT+"?sync=true&allowDefaultCodec=false&encoder=#stringEncoder&decoder=#stringDecoder")
		.to("bean:BASGS_API");
	}
}