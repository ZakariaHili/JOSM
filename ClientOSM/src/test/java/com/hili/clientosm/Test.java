package com.hili.clientosm;

import static org.junit.Assert.*;

import org.junit.Before;

import com.hili.clientosm.Controllers.GPSController;
import com.hili.clientosm.Modele.Metier.GPSMetierImpl;
import com.hili.clientosm.Services.HttpServicesImpl;

public class Test {
HttpServicesImpl httpServicesImpl = new HttpServicesImpl();
GPSMetierImpl gps= new GPSMetierImpl();
	@Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void test() {
		String url=gps.makeLinkForRoutesGoogle("44.84018","-0.61214", "44.83938","-0.59197");
		//System.out.println(url);
		System.out.println(httpServicesImpl.sendGetHttpRequestGoogle(url).get(0)[0]);
		//fail("Not yet implemented");
	}

}
