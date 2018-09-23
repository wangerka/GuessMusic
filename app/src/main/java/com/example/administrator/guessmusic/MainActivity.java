package com.example.administrator.guessmusic;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guessmusic.model.WordButton;
import com.example.administrator.guessmusic.util.Const;
import com.example.administrator.guessmusic.util.IWordClickListener;
import com.example.administrator.guessmusic.widget.WordsGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements IWordClickListener{

    private int mCurrentStage = 0;
    private String mCurrentSongName;
    private int mCurrentSongLength;

    private LinearLayout mSelectedWordsContainer;
    private WordsGridView mWordGrid;
    private ImageButton mPlayBtn;
    private ImageView mPanpianBar;
    private TextView mStage;

    private List<WordButton> mAllWors = new ArrayList<WordButton>();
    private List<WordButton> mSelects = new ArrayList<WordButton>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSelectedWordsContainer = (LinearLayout)findViewById(R.id.selected_words);
        mWordGrid = (WordsGridView)findViewById(R.id.all_words);
        mWordGrid.setListener(this);

        mPlayBtn = (ImageButton)findViewById(R.id.btn_play);
        mPanpianBar = (ImageView)findViewById(R.id.panpian_bar);
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation ani = AnimationUtils.loadAnimation(MainActivity.this,R.anim.panpian_in);
                ani.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        playRing();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ani.setFillAfter(true);
                mPanpianBar.startAnimation(ani);
            }
        });

        mStage = (TextView)findViewById(R.id.stage);
        mStage.setText((mCurrentStage+1)+"");

        mCurrentSongName = Const.musics[mCurrentStage][1];
        mCurrentSongLength = mCurrentSongName.length();

        initSelectedUI();

        fillAllWords();
    }


    private void initSelectedUI(){
        mSelectedWordsContainer.removeAllViews();
        mSelects.clear();
        for(int i =0;i<mCurrentSongLength;i++){
            final int j = i;
            WordButton wb = new WordButton();
            wb.setText("");
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

        if(isAllFill()){
            Toast.makeText(this,"已填满！！！",Toast.LENGTH_SHORT).show();
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

        if(isAllFill()){
            StringBuffer sb = new StringBuffer();
            for(int j=0;j<mCurrentSongLength;j++){
                sb.append(mSelects.get(j).getText());
            }
            if(mCurrentSongName.equals(sb.toString())){
                goNextStage();
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    int count;
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                while(++count>7){
                                    return;
                                }
                                for(int i=0;i<mSelects.size();i++){
                                    mSelects.get(i).getBtn().setTextColor(count%2!=0?
                                            getResources().getColor(R.color.red):
                                            getResources().getColor(R.color.white));
                                }
                            }
                        });

                    }
                }, 1,150);
            }
        }
    }

    private void goNextStage(){
        mCurrentStage++;
        mStage.setText((mCurrentStage+1)+"");
        mCurrentSongName = Const.musics[mCurrentStage][1];
        mCurrentSongLength = mCurrentSongName.length();

        initSelectedUI();

        fillAllWords();
    }

    private AssetManager assetManager;
    private MediaPlayer playRing() {
        MediaPlayer player = null;
        try {
            player = new MediaPlayer();
            assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd(mCurrentSongName+".mp3");
            player.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Animation out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.panpian_out);
                    mPanpianBar.startAnimation(out);
                }
            });
            player.prepare();
            player.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }


    private boolean isAllFill(){
        for(int i =0;i<mCurrentSongLength;i++){
            if(mSelects.get(i).getText().length()==0){
                return false;
            }
        }
        return true;
    }

}
