package jp.co.techfun.englishwordbook;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// �f�[�^�x�[�X�֘A�����N���X
public class EnglishWordBookDbUtil {

    // �w���p�[�C���X�^���X
    private SQLiteOpenHelper helper;

    // DB�C���X�^���X
    private SQLiteDatabase db;

    // DB��
    private static final String DB_NAME = "wordbook";

    // �e�[�u����
    private static final String TABLE_NAME = "wordbook";

    // �J������(�ʔ�)
    private static final String C_ID = "_id";
    // �J������(�p�P��)
    private static final String C_ENGLISH_WORD = "englishword";
    // �J������(���{���)
    private static final String C_JAPANESE_WORD = "japaneseword";
    // �J�������i�⑫�j
    private static final String C_EXTRAS = "extras";

    // �R���X�g���N�^
    public EnglishWordBookDbUtil(Context con) {
        // DB�쐬
        helper = new SQLiteOpenHelper(con, DB_NAME, null, 1) {
            @Override
            public void onUpgrade(SQLiteDatabase database, int oldVersion,
                int newVersion) {
                // �����Ȃ�
            }

            @Override
            public void onCreate(SQLiteDatabase database) {
                // �����Ȃ�
            }
        };

        // �e�[�u���쐬
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // SQL����
            StringBuilder sql = new StringBuilder();
            sql.append("create table " + TABLE_NAME + "(");
            // �ʔ�(�����̔�)
            sql.append(C_ID + " integer primary key autoincrement,");
            // �p�P��
            sql.append(C_ENGLISH_WORD + " text not null,");
            // ���{���
            sql.append(C_JAPANESE_WORD + " text not null,");
            // �⑫
            sql.append(C_EXTRAS + " text not null");
            sql.append(")");

            // SQL���s
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�e�[�u���̍쐬�Ɏ��s���܂����B", th);
        } finally {
            db.close();
        }
    }

    // addWord���\�b�h�i�P����o�^�����j
    public void addWord(WordBean wbn) {
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // SQL����
            StringBuilder sql = new StringBuilder();
            sql.append("insert into " + TABLE_NAME + " values (");
            // �ʔ�(�����̔�)
            sql.append("null,");
            // �p�P��
            sql.append("'" + wbn.getEnglishword() + "',");
            // ���{���
            sql.append("'" + wbn.getJapaneseword() + "',");
            // �⑫
            sql.append("'" + wbn.getExtras() + "'");
            
            sql.append(")");

            // SQL���s
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�e�[�u���ւ̃f�[�^�o�^�Ɏ��s���܂����B", th);
        } finally {
            db.close();
        }
    }

    // getWordList���\�b�h�i�P����擾�����j
    public List<WordBean> getWordList() {
        // �擾�����P��i�[�p���X�g
        List<WordBean> wordList = new ArrayList<WordBean>();

        // �f�[�^�擾�p�J�[�\��
        Cursor cursor = null;
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // �e�[�u������擾����񖼂��`
            String[] columns = { C_ENGLISH_WORD, C_JAPANESE_WORD, C_EXTRAS };

            // �f�[�^�擾
            cursor =
                db.query(TABLE_NAME, columns, null, null, null, null,
                    C_ENGLISH_WORD);

            // �擾�����f�[�^�����X�g�Ɋi�[
            while (cursor.moveToNext()) {
                WordBean wbn =
                    new WordBean(cursor.getString(0), cursor.getString(1),cursor.getString(2));
                wordList.add(wbn);
            }
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�e�[�u���f�[�^�̎擾�Ɏ��s���܂����B", th);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // �P�ꃊ�X�g��Ԃ�
        return wordList;
    }
    
}
