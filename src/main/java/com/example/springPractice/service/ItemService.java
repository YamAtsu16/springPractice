package com.example.springPractice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springPractice.model.ItemModel;
import com.example.springPractice.repository.ItemRepository;

/**
 * アイテムテーブルを操作するサービスクラス
 * @author atsu_yyy
 */
@Service
public class ItemService {
	
	/** アイテムリポジトリ */
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * アイテムリストを全件取得する
	 * @return アイテムリスト
	 */
	public List<ItemModel> getAllItem() {
		List<ItemModel> allItems = new ArrayList<>();
		// allItemsに対し、DBより取得した要素を引数にaddを実行することで、要素を変数に格納している
		itemRepository.findAll().forEach(allItems::add);
		return allItems;
	}
	
	/**
	 * 条件に合致するアイテムを取得する
	 * @param itemId
	 * @return 検索結果のアイテム
	 */
	public Optional<ItemModel> getItem(Integer itemId) {
		// 引数を条件に検索取得
		return itemRepository.findById(itemId);
	}
	
	/**
	 * アイテムを追加する
	 * @param itemList 追加アイテムリスト
	 * @return 追加件数
	 */
	public int addItem(List<ItemModel> itemList) {
		int count = 0;
		for (ItemModel item : itemList) {
			// データ保存
			itemRepository.save(item);
			count++;
		}
		return count;
	}

	/**
	 * アイテムを更新する
	 * @param item 更新対象アイテム
	 * @return 更新件数
	 */
	public int updateItem(ItemModel item) {
		if (itemRepository.findById(item.getItemId()).isEmpty()) {
			// 更新対象がDBに存在しなければ処理を実行しない
			return 0;
		} else {
			// データ更新
			itemRepository.save(item);
			return 1;
		}		
	}
	
	/**
	 * アイテムを削除する
	 * @param item 削除対象アイテム
	 * @return 削除件数
	 */
	public int deleteItem(ItemModel item) {
		if (itemRepository.findById(item.getItemId()).isEmpty()) {
			// 削除対象がDBに存在しなければ処理を実行しない
			return 0;
		} else {
			// データ削除
			itemRepository.deleteById(item.getItemId());
			return 1;
		}
	}
}
