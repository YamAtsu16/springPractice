package com.example.springPractice.exception;

/**
 * 独自に定義したアイテム例外
 * @author atsu_yyy
 */
public class ItemNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ItemNotFoundException(Integer itemId) {
		super("商品コード" + itemId + "は見つかりません");
	}
}