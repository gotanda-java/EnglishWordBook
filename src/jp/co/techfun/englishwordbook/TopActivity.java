package jp.co.techfun.englishwordbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// �g�b�v���Activity
public class TopActivity extends Activity {

    // �{�^���̃^�O�������`
    // �u�p�P�꒠���J���v�{�^���^�O
    static final String BTN_CHECK_WORD = "btnCheckWord";
    // �u�P���o�^����v�{�^���^�O
    static final String BTN_ADD_WORD = "btnAddWord";

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.top);

        // �u�p�P�꒠���J���v�{�^���Ƀ��X�i�[�ݒ�
        Button btnCheckWord = (Button) findViewById(R.id.btn_checkword);
        btnCheckWord.setTag(BTN_CHECK_WORD);
        btnCheckWord.setOnClickListener(buttonClickListener);

        // �u�P���o�^����v�{�^���Ƀ��X�i�[�ݒ�
        Button btnAddWord = (Button) findViewById(R.id.btn_addword);
        btnAddWord.setTag(BTN_ADD_WORD);
        btnAddWord.setOnClickListener(buttonClickListener);

    }

    // �{�^���N���b�N���X�i�[�̒�`
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClick���\�b�h(�N���b�N�C�x���g)
        @Override
        public void onClick(View v) {
            // �{�^���I�u�W�F�N�g�擾
            Button button = (Button) v;
            // �u�p�P�꒠���J���v�{�^���̏ꍇ
            if (button.getTag().equals(BTN_CHECK_WORD)) {
                Intent intent =
                    new Intent(TopActivity.this, CheckWordActivity.class);
                startActivity(intent);
                // �u�P���o�^����v�{�^���̏ꍇ
            } else if (button.getTag().equals(BTN_ADD_WORD)) {
                Intent intent =
                    new Intent(TopActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        }
    };
}
