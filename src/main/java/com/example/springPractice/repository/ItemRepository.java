package com.example.springPractice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springPractice.model.ItemModel;

/**
 * アイテムリポジトリ
 * @author atsu_yyy
 */
@Repository
public interface ItemRepository extends CrudRepository<ItemModel, Integer>{
	// CrudRepositoryを実装したインターフェースを定義するのみ
}
