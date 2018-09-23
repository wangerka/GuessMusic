package com.example.administrator.guessmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.guessmusic.model.WordButton;
import com.example.administrator.guessmusic.util.Const;
import com.example.administrator.guessmusic.util.IWordClickListener;
import com.example.administrator.guessmusic.widget.WordsGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IWordClickListener{

    private int mCurrentStage = 0;
    private String mCurrentSongName;
    private int mCurrentSongLength;

    private LinearLayout mSelectedWordsContainer;
    private WordsGridView mWordGrid;

    private List<WordButton> mAllWors = new ArrayList<WordButton>();
    private List<WordButton> mSelects = new ArrayList<WordButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSelectedWordsContainer = (LinearLayout)findViewById(R.id.selected_words);
        mWordGrid = (WordsGridView)findViewById(R.id.all_words);
        mWordGrid.setListener(this);

        mCurrentSongName = Const.musics[mCurrentStage][1];
        mCurrentSongLength = mCurrentSongName.length();

        initSelectedUI();

        fillAllWords();
    }


    private void initSelectedUI(){
        mSelectedWordsContainer.removeAllViews();
        for(int i =0;i<mCurrentSongLength;i++){
            final int j = i;
            WordButton wb = new WordButton();
            Button btn = new Button(this);
            btn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    mSelects.get(j).setText("");
                    mSelects.get(j).getBtn().setText("");

                    mAllWors.get(mSelects.get(j).getIndex()).getBtn().setVisibility(View.VISIBLE);
                }
            });
            btn.setBackgroundResource(R.drawable.game_wordblank);
            wb.setBtn(btn);
            mSelects.add(wb);
            mSelectedWordsContainer.addView(btn,140,140);
        }
    }

        private void fillAllWords(){
            mAllWors.clear();
            for(int j=0;j<mCurrentSongLength;j++){
                WordButton wb = new WordButton();
                wb.setText(mCurrentSongName.toCharArray()[j]+"");
                mAllWors.add(wb);
            }

            for(int i =0;i <Const.ALL_WORDS_COUNTS-mCurrentSongLength;i++){
                WordButton wb = new WordButton();
                wb.setText(Const.getRandomChar()+"");
                mAllWors.add(wb);
            }

        mWordGrid.setAllWorsList(mAllWors);
    }

    @Override
    public void onClick(int index) {
        if(mSelects.get(mCurrentSongLength-1).getText().length()!=0){
            Toast.makeText(this, "已填满！", Toast.LENGTH_SHORT).show();
            return;
        }

        mAllWors.get(index).setVisible(false);
        mAllWors.get(index).getBtn().setVisibility(View.INVISIBLE);

        fillSelectWords(index);
    }

    private void fillSelectWords(int index){
        for(int i=0;i<mCurrentSongLength;i++){
            if(mSelects.get(i).getText().length()==0){
                mSelects.get(i).getBtn().setText(mAllWors.get(index).getText());
                mSelects.get(i).getBtn().setTextColor(getResources().getColor(R.color.white));
                mSelects.get(i).getBtn().setVisibility(View.VISIBLE);
                mSelects.get(i).setText(mAllWors.get(index).getText());
                mSelects.get(i).setIndex(index);
                break;
            }
        }
    }
}
