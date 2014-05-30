package jp.co.techfun.englishwordbook;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// 発音Check画面Activity
public class CheckPronounceActivity extends Activity implements
    TextToSpeech.OnInitListener {

    // 音声認識処理のインテント用リクエストコード
    private static final int REQUEST_CODE = 0;

    // 読み上げスピード用(標準)
    private static final float SPEECH_RATE_NORMAL = 1.0f;

    // TextToSpeechインスタンス
    private TextToSpeech tts = null;

    // 発音チェック対象の英単語
    private String intentword = null;

    // ボタンのタグ文字列定義
    // 「英単語帳へ」ボタンタグ
    static final String BTN_CHECK_WORD = "btnCheckWord";
    // 「正しい発音」ボタンタグ
    static final String BTN_CORRECT_PRONOUNCE = "btnCorrectPronounce";
    // 「START」ボタンタグ
    static final String BTN_CHECK_PRONOUNCE = "btnCheckPronounce";

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.checkpronounce);

        // 「英単語帳へ」ボタンにリスナー設定
        Button btnCheckWordPage = (Button) findViewById(R.id.btn_checkwordpage);
        btnCheckWordPage.setTag(BTN_CHECK_WORD);
        btnCheckWordPage.setOnClickListener(buttonClickListener);

        // 「正しい発音」ボタンにリスナー設定
        Button btnCorrectPronounce =
            (Button) findViewById(R.id.btn_correctpronounce);
        btnCorrectPronounce.setTag(BTN_CORRECT_PRONOUNCE);
        btnCorrectPronounce.setOnClickListener(buttonClickListener);

        // 「START」ボタンにリスナー設定
        Button btnCheckPronounce =
            (Button) findViewById(R.id.btn_checkpronounce);
        btnCheckPronounce.setTag(BTN_CHECK_PRONOUNCE);
        btnCheckPronounce.setOnClickListener(buttonClickListener);

        // インテントから発音チェック対象の英単語取得
        Intent checkWordPageIntent = getIntent();
        Bundle bundle = checkWordPageIntent.getExtras();
        intentword = bundle.getString("englishword");

        // 取得した英単語をTextViewに設定
        TextView tvEnglishword = (TextView) findViewById(R.id.tv_englishword);
        tvEnglishword.setText(intentword);

        // TextToSpeechインスタンス化
        tts = new TextToSpeech(this, this);
    }

    // ボタンクリックリスナーの定義
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClickメソッド(クリックイベント)
        @Override
        public void onClick(View v) {
            // ボタンオブジェクト取得
            Button button = (Button) v;
            // 「英単語帳へ」ボタンの場合、英単語帳画面へ遷移
            if (button.getTag().equals(BTN_CHECK_WORD)) {
                // 画面クローズ
                finish();
                // 「正しい発音」ボタンの場合、単語読み上げ処理
            } else if (button.getTag().equals(BTN_CORRECT_PRONOUNCE)) {
                if (intentword.length() > 0) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    }
                    // 読み上げ開始
                    tts.setSpeechRate(SPEECH_RATE_NORMAL);
                    tts.speak(intentword, TextToSpeech.QUEUE_FLUSH, null);
                }
                // 「START」ボタンの場合、音声認識処理
            } else if (button.getTag().equals(BTN_CHECK_PRONOUNCE)) {
                try {
                    // 音声認識用インテント生成
                    Intent intent =
                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        R.string.check_pronounce_text);
                    // インテント実行
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    // 音声認識用インテントに対応するアクティビティが存在しない場合、
                    // メッセージをトースト表示
                    Toast.makeText(CheckPronounceActivity.this,
                        R.string.activity_notfound_text, Toast.LENGTH_LONG)
                        .show();
                }
            }
        }
    };

    // onActivityResultメソッド(音声認識後イベント)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // リクエストコードと結果コードチェック
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";
            // 音声認識結果の文字列取得
            List<String> results =
                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            for (int i = 0; i < results.size(); i++) {
                resultsString += results.get(i);
            }
            // 取得した文字列をTextViewに設定
            TextView speakword = (TextView) findViewById(R.id.tv_speakword);
            speakword.setText(resultsString);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    // onInitメソッド(音声合成初期化処理)
    @Override
    public void onInit(int status) {
        // 音声合成初期化処理が成功した場合
        if (TextToSpeech.SUCCESS == status) {
            // ロケールをENGLISHに設定
            Locale locale = Locale.ENGLISH;
            if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(locale);
            } else {
                Log.e(getClass().getSimpleName(), "Locale Setting Error");
            }
        } else {
            Log.e(getClass().getSimpleName(), "TextToSpeech Init Error");
        }
    }

    // onDestroyメソッド(音声合成終了処理)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.shutdown();
        }
    }
}
