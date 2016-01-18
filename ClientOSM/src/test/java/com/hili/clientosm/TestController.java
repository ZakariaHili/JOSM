package com.hili.clientosm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.hili.clientosm.Controllers.MainController;
import com.hili.clientosm.Controllers.SetConfigController;

public class TestController {
	SetConfigController mainController = new SetConfigController();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
	mainController.newSettings();
	}

}
