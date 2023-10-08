package com.example.springPractice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 複数のコントローラーで発生する可能性のある例外をキャッチし、統一的な方法で処理するためのクラス
 * @author atsu_yyy
 */
@ControllerAdvice
public class ItemNotFoundExceptionControllerAdvice {
	
	@ResponseBody // メソッドの戻り値をHTTPレスポンスの本文として返却する。JSONやXMLなどのデータフォーマットをクライアントに返す場合に使用する。
	@ExceptionHandler(ItemNotFoundException.class) // ItemNotFoundExceptionがスローされたときに、itemNotFoundExceptionHandlerメソッドが呼び出される。
	@ResponseStatus(HttpStatus.NOT_FOUND) // 例外がスローされた場合、HTTPレスポンスのステータスコードが404に設定される。
	public String itemNotFoundExceptionHandler(ItemNotFoundException ex) {
		return ex.getMessage();
	}
}
