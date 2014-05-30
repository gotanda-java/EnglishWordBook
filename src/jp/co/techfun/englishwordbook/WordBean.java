package jp.co.techfun.englishwordbook;

// 英単語・日本語訳JavaBeans
public class WordBean {
	// 英単語
	private String englishword;
	// 日本語訳
	private String japaneseword;
	// 補足
	private String extras;

	// コンストラクタ
	public WordBean(String englishword, String japaneseword, String extras) {

		// 英単語を設定
		this.englishword = englishword;

		// 日本語訳を設定
		this.japaneseword = japaneseword;

		// 補足を設定
		this.extras = extras;
	}

	// 英単語を返すメソッド
	public String getEnglishword() {
		return englishword;
	}

	// 英単語を設定するメソッド
	public void setEnglishword(String englishword) {
		this.englishword = englishword;
	}

	// 日本語訳を返すメソッド
	public String getJapaneseword() {
		return japaneseword;
	}

	// 日本語訳を設定するメソッド
	public void setJapaneseword(String japaneseword) {
		this.japaneseword = japaneseword;
	}

	// 補足を返すメソッド
	public String getExtras() {
		return extras;
	}

	// 補足を設定するメソッド
	public void setExtras(String extras) {
		this.extras = extras;
	}
}
