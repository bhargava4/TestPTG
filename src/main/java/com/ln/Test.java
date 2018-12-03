package com.ln;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
		String password = "123456";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPwd = encoder.encode(password);
		System.out.println(encodedPwd);
		String doubleEncoded = encoder.encode(encodedPwd);
		System.out.println(doubleEncoded);
		System.out.println(encoder.matches("123456", doubleEncoded));
	}

}
