package com.example.administrator.guessmusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.guessmusic.R;
import com.example.administrator.guessmusic.model.WordButton;
import com.example.administrator.guessmusic.util.IWordClickListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordsGridView extends GridView {

    private IWordClickListener listener;
    public void setListener(IWordClickListener l){
        listener = l;
    }

    public void setAllWorsList(List<WordButton> allWorsList) {
        this.allWorsList = allWorsList;
    }

    private List<WordButton> allWorsList = new ArrayList<WordButton>();

    public WordsGridView(Context context) {
        super(context);
    }

    public WordsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setAdapter(new WordsAdapter(context));
    }

    public WordsGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private class WordsAdapter extends BaseAdapter{
        private Context context;
        public WordsAdapter(Context c){
            context = c;
        }

        @Override
        public int getCount() {
            return allWorsList.size();
        }

        @Override
        public Object getItem(int i) {
            return allWorsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.word_layout, null);
                Button btn = (Button)view.findViewById(R.id.word);
                btn.setText(allWorsList.get(i).getText());
                allWorsList.get(i).setIndex(i);
                allWorsList.get(i).setBtn(btn);
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(i);
                    }
                });
            } else {

            }
            return view;

        }
    }

}
