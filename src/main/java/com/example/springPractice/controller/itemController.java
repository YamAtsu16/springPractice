package com.example.springPractice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springPractice.exception.ItemNotFoundException;
import com.example.springPractice.model.ItemModel;
import com.example.springPractice.service.ItemService;

/**
 * アイテムコントローラー
 * @author atsu_yyy
 */
@RestController
public class itemController {
	
	@Autowired
	private ItemService service;
	
	@GetMapping("/item")
	public ResponseEntity<List<ItemModel>> getAllItem() {
		List<ItemModel> allItems = service.getAllItem();
		if (allItems.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(allItems);			
		}
	}
	
	@GetMapping("/item/{itemId}")
	public ResponseEntity<ItemModel> getItem(@PathVariable(name = "itemId") Integer itemId) {
		// サービスクラスからの戻り値がNULLならば、独自に定義した例外を返す
		ItemModel item =  service.getItem(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		return ResponseEntity.ok(item);
	}
	
	@PostMapping("/item/add")
	public ResponseEntity<Integer> addItem(@RequestBody List<ItemModel> itemList) {
		Integer count =  service.addItem(itemList);
		if (count == 0) {
			return ResponseEntity.noContent().build();
		} else {			
			return ResponseEntity.ok(count);
		}
	}
	
	@PutMapping("/item/update")
	public ResponseEntity<Integer> updateItem(@RequestBody ItemModel item) {
		Integer count = service.updateItem(item);
		if (count == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(count);			
		}
	}
	
	@DeleteMapping("/item/delete")
	public ResponseEntity<Integer> deleteItem(@RequestBody ItemModel item) {
		Integer count = service.deleteItem(item);
		if (count == 0) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(count);
		}
	}
} 