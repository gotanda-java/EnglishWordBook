package jp.co.techfun.englishwordbook;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// データベース関連処理クラス
public class EnglishWordBookDbUtil {

    // ヘルパーインスタンス
    private SQLiteOpenHelper helper;

    // DBインスタンス
    private SQLiteDatabase db;

    // DB名
    private static final String DB_NAME = "wordbook";

    // テーブル名
    private static final String TABLE_NAME = "wordbook";

    // カラム名(通番)
    private static final String C_ID = "_id";
    // カラム名(英単語)
    private static final String C_ENGLISH_WORD = "englishword";
    // カラム名(日本語訳)
    private static final String C_JAPANESE_WORD = "japaneseword";
    // カラム名（補足）
    private static final String C_EXTRAS = "extras";

    // コンストラクタ
    public EnglishWordBookDbUtil(Context con) {
        // DB作成
        helper = new SQLiteOpenHelper(con, DB_NAME, null, 1) {
            @Override
            public void onUpgrade(SQLiteDatabase database, int oldVersion,
                int newVersion) {
                // 処理なし
            }

            @Override
            public void onCreate(SQLiteDatabase database) {
                // 処理なし
            }
        };

        // テーブル作成
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // SQL生成
            StringBuilder sql = new StringBuilder();
            sql.append("create table " + TABLE_NAME + "(");
            // 通番(自動採番)
            sql.append(C_ID + " integer primary key autoincrement,");
            // 英単語
            sql.append(C_ENGLISH_WORD + " text not null,");
            // 日本語訳
            sql.append(C_JAPANESE_WORD + " text not null,");
            // 補足
            sql.append(C_EXTRAS + " text not null");
            sql.append(")");

            // SQL実行
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "テーブルの作成に失敗しました。", th);
        } finally {
            db.close();
        }
    }

    // addWordメソッド（単語情報登録処理）
    public void addWord(WordBean wbn) {
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // SQL生成
            StringBuilder sql = new StringBuilder();
            sql.append("insert into " + TABLE_NAME + " values (");
            // 通番(自動採番)
            sql.append("null,");
            // 英単語
            sql.append("'" + wbn.getEnglishword() + "',");
            // 日本語訳
            sql.append("'" + wbn.getJapaneseword() + "',");
            // 補足
            sql.append("'" + wbn.getExtras() + "'");
            
            sql.append(")");

            // SQL実行
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "テーブルへのデータ登録に失敗しました。", th);
        } finally {
            db.close();
        }
    }

    // getWordListメソッド（単語情報取得処理）
    public List<WordBean> getWordList() {
        // 取得した単語格納用リスト
        List<WordBean> wordList = new ArrayList<WordBean>();

        // データ取得用カーソル
        Cursor cursor = null;
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // テーブルから取得する列名を定義
            String[] columns = { C_ENGLISH_WORD, C_JAPANESE_WORD, C_EXTRAS };

            // データ取得
            cursor =
                db.query(TABLE_NAME, columns, null, null, null, null,
                    C_ENGLISH_WORD);

            // 取得したデータをリストに格納
            while (cursor.moveToNext()) {
                WordBean wbn =
                    new WordBean(cursor.getString(0), cursor.getString(1),cursor.getString(2));
                wordList.add(wbn);
            }
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "テーブルデータの取得に失敗しました。", th);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // 単語リストを返す
        return wordList;
    }
    
}
