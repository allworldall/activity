package com.example.demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JasyptPasswordTest {
	@Autowired
	private StringEncryptor stringEncryptor;
	
	@Test
	public void encryptPwd() {
		String result = stringEncryptor.encrypt("yourpassword");
		System.out.println(result);
	}

}
