package edu.psu.sweng500.api;

import org.apache.camel.builder.RouteBuilder;
 
/*
 * Author: Brian Abate
 * This class provides a means to configure the Camel Netty server
 * with BASGS.
 */
public class ServerRoute extends RouteBuilder {
 
  @Override
  public void configure() throws Exception {
    from("netty:tcp://localhost:7002?sync=true&allowDefaultCodec=false&encoder=#stringEncoder&decoder=#stringDecoder")
      .to("bean:BASGS_API");
  }
}