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

// ����Check���Activity
public class CheckPronounceActivity extends Activity implements
    TextToSpeech.OnInitListener {

    // �����F�������̃C���e���g�p���N�G�X�g�R�[�h
    private static final int REQUEST_CODE = 0;

    // �ǂݏグ�X�s�[�h�p(�W��)
    private static final float SPEECH_RATE_NORMAL = 1.0f;

    // TextToSpeech�C���X�^���X
    private TextToSpeech tts = null;

    // �����`�F�b�N�Ώۂ̉p�P��
    private String intentword = null;

    // �{�^���̃^�O�������`
    // �u�p�P�꒠�ցv�{�^���^�O
    static final String BTN_CHECK_WORD = "btnCheckWord";
    // �u�����������v�{�^���^�O
    static final String BTN_CORRECT_PRONOUNCE = "btnCorrectPronounce";
    // �uSTART�v�{�^���^�O
    static final String BTN_CHECK_PRONOUNCE = "btnCheckPronounce";

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.checkpronounce);

        // �u�p�P�꒠�ցv�{�^���Ƀ��X�i�[�ݒ�
        Button btnCheckWordPage = (Button) findViewById(R.id.btn_checkwordpage);
        btnCheckWordPage.setTag(BTN_CHECK_WORD);
        btnCheckWordPage.setOnClickListener(buttonClickListener);

        // �u�����������v�{�^���Ƀ��X�i�[�ݒ�
        Button btnCorrectPronounce =
            (Button) findViewById(R.id.btn_correctpronounce);
        btnCorrectPronounce.setTag(BTN_CORRECT_PRONOUNCE);
        btnCorrectPronounce.setOnClickListener(buttonClickListener);

        // �uSTART�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnCheckPronounce =
            (Button) findViewById(R.id.btn_checkpronounce);
        btnCheckPronounce.setTag(BTN_CHECK_PRONOUNCE);
        btnCheckPronounce.setOnClickListener(buttonClickListener);

        // �C���e���g���甭���`�F�b�N�Ώۂ̉p�P��擾
        Intent checkWordPageIntent = getIntent();
        Bundle bundle = checkWordPageIntent.getExtras();
        intentword = bundle.getString("englishword");

        // �擾�����p�P���TextView�ɐݒ�
        TextView tvEnglishword = (TextView) findViewById(R.id.tv_englishword);
        tvEnglishword.setText(intentword);

        // TextToSpeech�C���X�^���X��
        tts = new TextToSpeech(this, this);
    }

    // �{�^���N���b�N���X�i�[�̒�`
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClick���\�b�h(�N���b�N�C�x���g)
        @Override
        public void onClick(View v) {
            // �{�^���I�u�W�F�N�g�擾
            Button button = (Button) v;
            // �u�p�P�꒠�ցv�{�^���̏ꍇ�A�p�P�꒠��ʂ֑J��
            if (button.getTag().equals(BTN_CHECK_WORD)) {
                // ��ʃN���[�Y
                finish();
                // �u�����������v�{�^���̏ꍇ�A�P��ǂݏグ����
            } else if (button.getTag().equals(BTN_CORRECT_PRONOUNCE)) {
                if (intentword.length() > 0) {
                    if (tts.isSpeaking()) {
                        tts.stop();
                    }
                    // �ǂݏグ�J�n
                    tts.setSpeechRate(SPEECH_RATE_NORMAL);
                    tts.speak(intentword, TextToSpeech.QUEUE_FLUSH, null);
                }
                // �uSTART�v�{�^���̏ꍇ�A�����F������
            } else if (button.getTag().equals(BTN_CHECK_PRONOUNCE)) {
                try {
                    // �����F���p�C���e���g����
                    Intent intent =
                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        R.string.check_pronounce_text);
                    // �C���e���g���s
                    startActivityForResult(intent, REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    // �����F���p�C���e���g�ɑΉ�����A�N�e�B�r�e�B�����݂��Ȃ��ꍇ�A
                    // ���b�Z�[�W���g�[�X�g�\��
                    Toast.makeText(CheckPronounceActivity.this,
                        R.string.activity_notfound_text, Toast.LENGTH_LONG)
                        .show();
                }
            }
        }
    };

    // onActivityResult���\�b�h(�����F����C�x���g)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ���N�G�X�g�R�[�h�ƌ��ʃR�[�h�`�F�b�N
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultsString = "";
            // �����F�����ʂ̕�����擾
            List<String> results =
                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            for (int i = 0; i < results.size(); i++) {
                resultsString += results.get(i);
            }
            // �擾�����������TextView�ɐݒ�
            TextView speakword = (TextView) findViewById(R.id.tv_speakword);
            speakword.setText(resultsString);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    // onInit���\�b�h(������������������)
    @Override
    public void onInit(int status) {
        // �����������������������������ꍇ
        if (TextToSpeech.SUCCESS == status) {
            // ���P�[����ENGLISH�ɐݒ�
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

    // onDestroy���\�b�h(���������I������)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.shutdown();
        }
    }
}
