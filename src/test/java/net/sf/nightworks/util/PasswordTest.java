package net.sf.nightworks.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordTest {

	@Test
	public void testPasswordHashIsTrue() {
		String password = "Password.001";
		assertTrue(Password.checkPassword(password, Password.hashPassword(password)));
	}
	
	@Test
	public void testPasswordHashIsFalse() {
		String password1 = "Password.001";
		String falsePassword = "Password.002";
		assertFalse(Password.checkPassword(password1, Password.hashPassword(falsePassword)));
	}

}
