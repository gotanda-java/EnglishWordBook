package jp.co.techfun.englishwordbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// トップ画面Activity
public class TopActivity extends Activity {

    // ボタンのタグ文字列定義
    // 「英単語帳を開く」ボタンタグ
    static final String BTN_CHECK_WORD = "btnCheckWord";
    // 「単語を登録する」ボタンタグ
    static final String BTN_ADD_WORD = "btnAddWord";

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.top);

        // 「英単語帳を開く」ボタンにリスナー設定
        Button btnCheckWord = (Button) findViewById(R.id.btn_checkword);
        btnCheckWord.setTag(BTN_CHECK_WORD);
        btnCheckWord.setOnClickListener(buttonClickListener);

        // 「単語を登録する」ボタンにリスナー設定
        Button btnAddWord = (Button) findViewById(R.id.btn_addword);
        btnAddWord.setTag(BTN_ADD_WORD);
        btnAddWord.setOnClickListener(buttonClickListener);

    }

    // ボタンクリックリスナーの定義
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClickメソッド(クリックイベント)
        @Override
        public void onClick(View v) {
            // ボタンオブジェクト取得
            Button button = (Button) v;
            // 「英単語帳を開く」ボタンの場合
            if (button.getTag().equals(BTN_CHECK_WORD)) {
                Intent intent =
                    new Intent(TopActivity.this, CheckWordActivity.class);
                startActivity(intent);
                // 「単語を登録する」ボタンの場合
            } else if (button.getTag().equals(BTN_ADD_WORD)) {
                Intent intent =
                    new Intent(TopActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        }
    };
}
