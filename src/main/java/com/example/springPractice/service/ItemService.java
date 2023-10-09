package com.example.springPractice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	
	private RestTemplate restTemplate;
	
	// 他APIを呼び出すために、RestTemplateをビルドする
	ItemService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	/**
	 * アイテムリストを全件取得する
	 * @return アイテムリスト
	 */
	@Cacheable("getItems") // "getItems"という名称でキャッシュ管理
	public List<ItemModel> getAllItem() {
		List<ItemModel> allItems = new ArrayList<>();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		// allItemsに対し、DBより取得した要素を引数にaddを実行することで、要素を変数に格納している
		itemRepository.findAll(Sort.by(Sort.Direction.ASC, "itemId")).forEach(allItems::add); // ソート機能をサービスクラスにて実装
		return allItems;

		// 以下は、リポジトリクラスにて実装したメソッドを呼び出す方法
		// itemRepository.findAllByOrderByItemId().forEach(allItems::add);
	}
	
	/**
	 * 条件に合致するアイテムを取得する
	 * @param itemId
	 * @return 検索結果のアイテム
	 */
	@Cacheable(value="getItem", key="#p0") // キー値を指定してキャッシュ管理
	public Optional<ItemModel> getItem(Integer itemId) {
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		// 引数を条件に検索取得
		return itemRepository.findById(itemId);
	}
	
	/**
	 * アイテムを追加する
	 * @param itemList 追加アイテムリスト
	 * @return 追加件数
	 */
	@CacheEvict(value="getItems", allEntries=true) // "getItems"で管理されたキャッシュを削除する
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
	@Caching(evict = {
			@CacheEvict(value="getItem", key="#p0"),
			@CacheEvict(value="getItems", allEntries=true)
	}) // 複数のキャッシュを削除
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
	@CacheEvict(value="getItems", allEntries=true) // "getItems"で管理されたキャッシュを削除する
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
	
	/**
	 * 他APIの呼び出し、レスポンスを取得する
	 * <p>
	 * あくまで練習用として作成したため、"http://localhost:8099/hello"で呼ばれるAPIは実際には実装されていない
	 * <p>
	 * @return 他APIより取得したレスポンス
	 */
	public String getOtherApiResponse() {
		String url = "http://localhost:8099/hello"; // このURLより呼ばれるAPI（このサーバーが起動していることが前提）
		String response = restTemplate.getForObject(url, String.class); // 上記により呼び出したAPIのレスポンス（第一引数: URL, 第二引数: レスポンスの型）
		
		return response;
	}
}
