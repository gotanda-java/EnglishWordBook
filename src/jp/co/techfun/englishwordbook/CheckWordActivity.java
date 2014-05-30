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

// �p�P�꒠Activity
public class CheckWordActivity extends Activity {

    // �P����ۑ��pDB�C���X�^���X
    private EnglishWordBookDbUtil dbUtil;

    // �P��i�[�p���X�g�C���X�^���X
    private List<WordBean> list;

    // ListIterator�C���X�^���X
    private ListIterator<WordBean> itr;

    // �p�P��TextView�C���X�^���X
    private TextView tvEnglishword;

    // ���{���TextView�C���X�^���X
    private TextView tvJapaneseword;
    
    // �⑫TextView�C���X�^���X
    private TextView tvExtras;

    // �{�^���̃^�O�������`
    // �u���ցv�{�^���^�O
    static final String BTN_NEXT = "btnNext";
    // �u�O�ցv�{�^���^�O
    static final String BTN_BACK = "btnBack";
    // �u�g�b�v�ցv�{�^���^�O
    static final String BTN_TOP = "btnTop";
    // �u����Check�v�{�^���^�O
    static final String BTN_CHECK_PRONOUNCE = "btnCheckPronounce";

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.checkword);

        // �u���ցv�{�^���Ƀ��X�i�[�ݒ�
        Button btnNextPage = (Button) findViewById(R.id.btn_nextpage);
        btnNextPage.setTag(BTN_NEXT);
        btnNextPage.setOnClickListener(buttonClickListener);

        // �u�O�ցv�{�^���Ƀ��X�i�[�ݒ�
        Button btnBackPage = (Button) findViewById(R.id.btn_backpage);
        btnBackPage.setTag(BTN_BACK);
        btnBackPage.setOnClickListener(buttonClickListener);

        // �u�g�b�v�ցv�{�^���Ƀ��X�i�[�ݒ�
        Button btnTopPage = (Button) findViewById(R.id.btn_toppage);
        btnTopPage.setTag(BTN_TOP);
        btnTopPage.setOnClickListener(buttonClickListener);

        // �u����Check�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnCheckPronounce =
            (Button) findViewById(R.id.btn_checkpronounce);
        btnCheckPronounce.setTag(BTN_CHECK_PRONOUNCE);
        btnCheckPronounce.setOnClickListener(buttonClickListener);

        // DBUtil�C���X�^���X����
        dbUtil = new EnglishWordBookDbUtil(this);

        // �f�[�^�x�[�X����P��擾
        list = dbUtil.getWordList();

        // �擾�����P���TextView�ɐݒ�
        itr = list.listIterator();
        if (itr.hasNext()) {
            tvEnglishword = (TextView) findViewById(R.id.tv_englishword);
            tvJapaneseword = (TextView) findViewById(R.id.tv_japaneseword);
            tvExtras = (TextView) findViewById(R.id.tv_extras);
            WordBean wbn = itr.next();
            tvEnglishword.setText(wbn.getEnglishword());
            tvJapaneseword.setText(wbn.getJapaneseword());
            tvExtras.setText(wbn.getExtras());
            // �o�^���ꂽ�P�ꂪ�Ȃ��ꍇ�A���b�Z�[�W���g�[�X�g�\��
        } else {
            Toast.makeText(CheckWordActivity.this, R.string.noword_input_text,
                Toast.LENGTH_SHORT).show();
        }
    }

    // �{�^���N���b�N���X�i�[�̒�`
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClick���\�b�h(�N���b�N�C�x���g)
        @Override
        public void onClick(View v) {
            // �{�^���I�u�W�F�N�g�擾
            Button button = (Button) v;
            // �u���ցv�{�^���̏ꍇ�A���̒P���\��
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
                // �u�O�ցv�{�^���̏ꍇ�A�O�̒P���\��
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
                // �u����Check�v�{�^���̏ꍇ�A�����`�F�b�N��ʂ֑J��
            } else if (button.getTag().equals(BTN_CHECK_PRONOUNCE)) {
                if (list.size() > 0) {
                    Intent intent =
                        new Intent(CheckWordActivity.this,
                            CheckPronounceActivity.class);
                    intent.putExtra("englishword", tvEnglishword.getText()
                        .toString());
                    startActivity(intent);
                }
                // �u�g�b�v�ցv�{�^���̏ꍇ�A�g�b�v��ʂ֑J��
            } else if (button.getTag().equals(BTN_TOP)) {
                // ��ʃN���[�Y
                finish();
            }
        }
    };
}
