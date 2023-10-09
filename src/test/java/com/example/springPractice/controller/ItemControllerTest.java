package com.example.springPractice.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springPractice.model.ItemModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {
	
	@Autowired
	private MockMvc mvc;

//	@Test
//	void testGetAllItem() {
//		fail("まだ実装されていません");
//	}

	@Test
	@DisplayName("指定したアイテムIDの情報を正常に取得できるかを検証")
	void testGetItem() throws Exception{
		Integer itemId = 1;
		String responseJsonString = 
				mvc.perform(get("/item/{itemId}", itemId)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
					.characterEncoding("UTF-8"))
				.andExpect(status().isOk()) // ステータスコードチェック
				.andReturn().getResponse().getContentAsString(); // レスポンスチェック
		
		// 取得したレスポンス（JSONオブジェクト）をJavaオブジェクトにマッピングする
		ObjectMapper objectMapper = new ObjectMapper();
		ItemModel responseItem = objectMapper.readValue(responseJsonString, ItemModel.class);
		
		assertAll(
			() -> { assertThat(responseItem.getItemId()).isEqualTo(1); },
			() -> { assertThat(responseItem.getItemName()).isEqualTo("アイテム1"); },
			() -> { assertThat(responseItem.getItemCategory()).isEqualTo("カテゴリー1"); },
			() -> { assertThat(responseItem.getItemPrice()).isEqualTo(2000); }
		);
	}

//	@Test
//	void testAddItem() {
//		fail("まだ実装されていません");
//	}

//	@Test
//	void testUpdateItem() {
//		fail("まだ実装されていません");
//	}

//	@Test
//	void testDeleteItem() {
//		fail("まだ実装されていません");
//	}
}
