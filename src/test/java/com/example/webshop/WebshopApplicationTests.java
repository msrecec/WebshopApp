package com.example.webshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-test.properties"
)
class WebshopApplicationTests {

	@Test
	void contextLoads() {
	}

}
