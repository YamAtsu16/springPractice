package com.example.springPractice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springPractice.model.ItemModel;

/**
 * アイテムリポジトリ
 * @author atsu_yyy
 */
@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Integer>{
	
	/** "itemId"の昇順でソートし、全件取得するメソッド */
	public List<ItemModel> findAllByOrderByItemId();
}
