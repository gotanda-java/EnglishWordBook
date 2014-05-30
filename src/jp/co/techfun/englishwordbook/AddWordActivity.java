package jp.co.techfun.englishwordbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// �P��o�^���Activity
public class AddWordActivity extends Activity {
    // �P����ۑ��pDB�C���X�^���X
    private EnglishWordBookDbUtil dbUtil;

    // �{�^���̃^�O�������`
    // �u�P���o�^�v�{�^���^�O
    static final String BTN_SAVE = "btnSave";
    // �u�g�b�v�ցv�{�^���^�O
    static final String BTN_TOP = "btnTop";
    // �u�S�č폜�v�{�^���^�O
    static final String BTN_DELETE = "btndelete";

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.addword);

        // �u�P���o�^�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setTag(BTN_SAVE);
        btnSave.setOnClickListener(buttonClickListener);

        // �u�g�b�v�ցv�{�^���Ƀ��X�i�[�ݒ�
        Button btnTopPage = (Button) findViewById(R.id.btn_toppage);
        btnTopPage.setTag(BTN_TOP);
        btnTopPage.setOnClickListener(buttonClickListener);
        
        // �u�S�č폜�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnDelete = (Button) findViewById(R.id.btn_deleteall);
        btnDelete.setTag(BTN_DELETE);
        btnDelete.setOnClickListener(buttonClickListener);

        // DBUtil�C���X�^���X����
        dbUtil = new EnglishWordBookDbUtil(this);
    }

    // �{�^���N���b�N���X�i�[�̒�`
    private OnClickListener buttonClickListener = new OnClickListener() {
        // onClick���\�b�h(�N���b�N�C�x���g)
        @Override
        public void onClick(View v) {
            // �{�^���I�u�W�F�N�g�擾
            Button button = (Button) v;
            // �u�P���o�^�v�{�^���̏ꍇ�A�P��o�^
            if (button.getTag().equals(BTN_SAVE)) {
                // �m�F�_�C�A���O�\��
                AddWordActivity.this.showDialog();
                // �u�g�b�v�ցv�{�^���̏ꍇ�A�g�b�v��ʂ֑J��
            } else if (button.getTag().equals(BTN_TOP)) {
                // ��ʃN���[�Y
                finish();
                // �u�S�č폜�v�{�^���̏ꍇ�ADB�폜
            } else if (button.getTag().equals(BTN_DELETE)){
            	
            }
        }
    };

    // showDialog���\�b�h(�m�F�_�C�A���O�̕\��)
    private void showDialog() {
        // �_�C�A���O����
        AlertDialog.Builder dialog =
            new AlertDialog.Builder(AddWordActivity.this);
        // �_�C�A���O�^�C�g���ݒ�
        dialog.setTitle(R.string.add_confirm_text);
        // �_�C�A���O���b�Z�[�W�ݒ�
        dialog.setMessage(R.string.confirm_message_text);
        // �u�͂��v�{�^���ݒ�
        dialog.setPositiveButton(R.string.yes_text,
            dialogPositiveButtonClickListener);
        // �u�������v�{�^���ݒ�
        dialog.setNegativeButton(R.string.no_text,
            dialogNegativeButtonClickListener);
        // �_�C�A���O�\��
        dialog.show();
    }

    // �_�C�A���O�{�^���N���b�N���X�i�[�̒�`(�u�͂��v�{�^��)
    private DialogInterface.OnClickListener dialogPositiveButtonClickListener =
        new DialogInterface.OnClickListener() {
            // onClick���\�b�h(�_�C�A���O�̃{�^���N���b�N�C�x���g)
            public void onClick(DialogInterface dialog, int whichButton) {
                // �e�L�X�g���͏��擾
                EditText etEnglishword =
                    (EditText) findViewById(R.id.et_englishword);
                EditText etJapaneseword =
                    (EditText) findViewById(R.id.et_japaneseword);
                EditText etExtras = 
                	(EditText) findViewById(R.id.et_extras);
                // �����͂̏ꍇ�A�G���[���b�Z�[�W���g�[�X�g�ŕ\��
                if (etEnglishword.getText().toString().equals("")
                    || etJapaneseword.getText().toString().equals("")) {
                    Toast.makeText(AddWordActivity.this,
                        R.string.input_words_text, Toast.LENGTH_LONG).show();
                    return;
                    // ���͂���Ă���ꍇ�A�f�[�^�x�[�X�ɓo�^
                }

                // WordBean�C���X�^���X����
                WordBean wbn =
                    new WordBean(etEnglishword.getText().toString(),
                        etJapaneseword.getText().toString(),
                        etExtras.getText().toString());

                // DB�֒P�����o�^
                dbUtil.addWord(wbn);
                
                // �o�^�������b�Z�[�W
                Toast.makeText(AddWordActivity.this, "�o�^���܂����I", Toast.LENGTH_SHORT).show();
            }
        };

    // �_�C�A���O�{�^���N���b�N���X�i�[�̒�`(�u�������v�{�^��)
    private DialogInterface.OnClickListener dialogNegativeButtonClickListener =
        new DialogInterface.OnClickListener() {
            // onClick���\�b�h(�_�C�A���O�̃{�^���N���b�N�C�x���g)
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        };
}
