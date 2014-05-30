package jp.co.techfun.englishwordbook;

import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// 英単語帳Activity
public class CheckWordActivity extends Activity {

    // 単語情報保存用DBインスタンス
    private EnglishWordBookDbUtil dbUtil;

    // 単語格納用リストインスタンス
    private List<WordBean> list;

    // ListIteratorインスタンス
    private ListIterator<WordBean> itr;

    // 英単語TextViewインスタンス
    private TextView tvEnglishword;

    // 日本語訳TextViewインスタンス
    private TextView tvJapaneseword;
    
    // 補足TextViewインスタンス
    private TextView tvExtras;

    // ボタンのタグ文字列定義
    // 「次へ」ボタンタグ
    static final String BTN_NEXT = "btnNext";
    // 「前へ」ボタンタグ
    static final String BTN_BACK = "btnBack";
    // 「トップへ」ボタンタグ
    static final String BTN_TOP = "btnTop";
    // 「発音Check」ボタンタグ
    static final String BTN_CHECK_PRONOUNCE = "btnCheckPronounce";

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.checkword);

        // 「次へ」ボタンにリスナー設定
        Button btnNextPage = (Button) findViewById(R.id.btn_nextpage);
        btnNextPage.setTag(BTN_NEXT);
        btnNextPage.setOnClickListener(buttonClickListener);

        // 「前へ」ボタンにリスナー設定
        Button btnBackPage = (Button) findViewById(R.id.btn_backpage);
        btnBackPage.setTag(BTN_BACK);
        btnBackPage.setOnClickListener(buttonClickListener);

        // 「トップへ」ボタンにリスナー設定
        Button btnTopPage = (Button) findViewById(R.id.btn_toppage);
        btnTopPage.setTag(BTN_TOP);
        btnTopPage.setOnClickListener(buttonClickListener);

        // 「発音Check」ボタンにリスナー設定
        Button btnCheckPronounce =
            (Button) findViewById(R.id.btn_checkpronounce);
        btnCheckPronounce.setTag(BTN_CHECK_PRONOUNCE);
        btnCheckPronounce.setOnClickListener(buttonClickListener);

        // DBUtilインスタンス生成
        dbUtil = new EnglishWordBookDbUtil(this);

        // データベースから単語取得
        list = dbUtil.getWordList();

        // 取得した単語をTextViewに設定
        itr = list.listIterator();
        if (itr.hasNext()) {
            tvEnglishword = (TextView) findViewById(R.id.tv_englishword);
            tvJapaneseword = (TextView) findViewById(R.id.tv_japaneseword);
            tvExtras = (TextView) findViewById(R.id.tv_extras);
            WordBean wbn = itr.next();
            tvEnglishword.setText(wbn.getEnglishword());
            tvJapaneseword.setText(wbn.getJapaneseword());
            tvExtras.setText(wbn.getExtras());
            // 登録された単語がない場合、メッセージをトースト表示
        } else {
            Toast.makeText(CheckWordActivity.this, R.string.noword_input_text,
                Toast.LENGTH_SHORT).show();
        }
    }

    // ボタンクリックリスナーの定義
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClickメソッド(クリックイベント)
        @Override
        public void onClick(View v) {
            // ボタンオブジェクト取得
            Button button = (Button) v;
            // 「次へ」ボタンの場合、次の単語を表示
            if (button.getTag().equals(BTN_NEXT)) {
                if (itr.hasNext()) {
                    WordBean wbn = itr.next();
                    tvEnglishword.setText(wbn.getEnglishword());
                    tvJapaneseword.setText(wbn.getJapaneseword());
                    tvExtras.setText(wbn.getExtras());
                } else {
                    Toast.makeText(CheckWordActivity.this,
                        R.string.noword_next_text, Toast.LENGTH_SHORT).show();
                }
                // 「前へ」ボタンの場合、前の単語を表示
            } else if (button.getTag().equals(BTN_BACK)) {
                if (itr.hasPrevious()) {
                    WordBean wbn = itr.previous();
                    tvEnglishword.setText(wbn.getEnglishword());
                    tvJapaneseword.setText(wbn.getJapaneseword());
                    tvExtras.setText(wbn.getExtras());
                } else {
                    Toast.makeText(CheckWordActivity.this,
                        R.string.noword_back_text, Toast.LENGTH_SHORT).show();
                }
                // 「発音Check」ボタンの場合、発音チェック画面へ遷移
            } else if (button.getTag().equals(BTN_CHECK_PRONOUNCE)) {
                if (list.size() > 0) {
                    Intent intent =
                        new Intent(CheckWordActivity.this,
                            CheckPronounceActivity.class);
                    intent.putExtra("englishword", tvEnglishword.getText()
                        .toString());
                    startActivity(intent);
                }
                // 「トップへ」ボタンの場合、トップ画面へ遷移
            } else if (button.getTag().equals(BTN_TOP)) {
                // 画面クローズ
                finish();
            }
        }
    };
}
