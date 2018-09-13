package com.foxinmy.weixin4j.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ConstsTest {

	@Test
	public void test() {
		assertEquals("weixin4j", Consts.WEIXIN4J);
		assertFalse(Consts.VERSION.equals("${project.version}"));
	}

}
