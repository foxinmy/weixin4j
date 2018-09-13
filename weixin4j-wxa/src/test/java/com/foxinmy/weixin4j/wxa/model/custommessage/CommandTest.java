package com.foxinmy.weixin4j.wxa.model.custommessage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandTest {

	@Test
	public void testToString() {
		assertEquals("Typing", Command.TYPING.toString());
		assertEquals("CancelTyping", Command.CANCEL_TYPING.toString());
	}

}
