package com.zl.fund;


import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zl.fund.service.OrderService;


@Configuration
public class CxfConfig {
	@Autowired
	private Bus bus;
	
	@Autowired
	OrderService orderService;
	
	 @Bean
	    public Endpoint endpoint() {
	        EndpointImpl endpoint = new EndpointImpl(bus, orderService);
	        endpoint.publish("/OrderService");
	        return endpoint;
	    }

	
}
