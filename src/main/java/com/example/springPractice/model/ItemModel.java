package com.example.springPractice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * アイテムモデル
 * @author atsu_yyy
 */
@Data
@Entity(name = "item")
public class ItemModel {
	
	/** アイテムID */
	@Id
	private Integer itemId;
	
	/** アイテム名 */
	private String itemName;
	
	/** アイテムカテゴリー */
	private String itemCategory;
	
	/** アイテム価格 */
	private Integer itemPrice;
}
