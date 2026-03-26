package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 指定のURLの画面を開く
		webDriver.get("http://localhost:8080/lms");
		// Title確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// フォーム要素の取得
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		WebElement password = webDriver.findElement(By.id("password"));
		WebElement submit = webDriver.findElement(By.className("btn"));
		// フォーム入力
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		password.clear();
		password.sendKeys("StudentAA00");
		submit.click();
		// 画面の遷移を待機
		visibilityTimeout(By.id("wrap"), 60);
		// Title確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」ドロップダウンの展開
		webDriver.findElement(By.linkText("機能")).click();
		// リストの展開を待機
		visibilityTimeout(By.linkText("ヘルプ"), 60);
		// 「ヘルプ」リンク押下
		webDriver.findElement(By.linkText("ヘルプ")).click();
		// 画面の遷移を待機
		visibilityTimeout(By.id("wrap"), 60);
		// Title確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//「よくある質問」リンク押下
		webDriver.findElement(By.linkText("よくある質問")).click();
		// 開かれたタブを選択
		List<String> tabList = new ArrayList<>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabList.get(tabList.size() - 1));
		// 画面の遷移を待機
		visibilityTimeout(By.id("wrap"), 60);
		// Title確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// 「研修関係」押下
		webDriver.findElement(By.linkText("【研修関係】")).click();
		// 検索結果の表示確認 期待値：2件
		assertEquals(2, webDriver.findElements(By.id("question-h[${status.index}]")).size());
		// 検索結果テキストを期待値と比較
		Object[] resultText = webDriver.findElements(By.xpath("//*[@id=\"question-h[${status.index}]\"]/dt/span[2]"))
				.toArray();
		assertEquals("キャンセル料・途中退校について", ((WebElement) resultText[0]).getText());
		assertEquals("研修の申し込みはどのようにすれば良いですか？", ((WebElement) resultText[1]).getText());
		// エビデンス取得のためスクロール
		scrollTo("350");
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 検索結果を押下
		webDriver.findElement(By.id("question-h[${status.index}]")).click();
		// 展開時のclass要素で表示確認
		assertTrue(webDriver.findElement(By.className("fs18")).isDisplayed());
		// 回答テキストの確認
		assertEquals(
				"受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。",
				webDriver.findElement(By.xpath("//*[@id=\"answer-h[${status.index}]\"]/span[2]")).getText());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
