package com.vsc.demo.utils;

import java.util.UUID;

public class RandomStringGenerator {
	public static String generateToken() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
}
