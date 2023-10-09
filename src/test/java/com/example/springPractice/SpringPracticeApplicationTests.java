package com.example.springPractice;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springPractice.controller.ItemController;
import com.example.springPractice.repository.ItemRepository;
import com.example.springPractice.service.ItemService;

@SpringBootTest
class SpringPracticeApplicationTests {
	
	@Autowired
	ItemController controller;
	
	@Autowired
	ItemService service;
	
	@Autowired
	ItemRepository repository;

	@Test
	@DisplayName("アプリケーションがSpringコンテキストを正常にロードできたかを検証")
	void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(service).isNotNull();
		assertThat(repository).isNotNull();
	}

}
