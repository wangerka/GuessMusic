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

    private Context context;

    private IWordClickListener listener;
    public void setListener(IWordClickListener l){
        listener = l;
    }

    public void setAllWorsList(List<WordButton> allWorsList) {
        Log.i("gejun","set DATA");
        this.allWorsList = allWorsList;
        if(!mFirstInit){
            setAdapter(adapter);
        } else {
            mFirstInit = false;
        }
    }

    private List<WordButton> allWorsList = new ArrayList<WordButton>();

    public WordsGridView(Context context) {
        super(context);
        this.context = context;
    }

    private WordsAdapter adapter;
    private boolean mFirstInit = true;

    public WordsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        adapter = new WordsAdapter(context);
        setAdapter(adapter);
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
            Log.i("gejun","getCount  = " + allWorsList.size());
            return allWorsList.size();
        }

        @Override
        public Object getItem(int i) {

            return allWorsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            Log.i("gejun","getItemId i = " + i);
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Log.i("gejun","getview i = " + i);
            ViewHolder viewHoler;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.word_layout, null);
                viewHoler = new ViewHolder();
                viewHoler.mButton = (Button)view.findViewById(R.id.word);
                viewHoler.mButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(i);
                    }
                });
                view.setTag(viewHoler);
            } else {
                viewHoler = (ViewHolder) view.getTag();
            }
            viewHoler.mButton.setText(allWorsList.get(i).getText());
            viewHoler.index = i;

            allWorsList.get(i).setIndex(viewHoler.index);
            allWorsList.get(i).setBtn(viewHoler.mButton);

            return view;

        }
    }

    public class ViewHolder{
        public Button mButton;
        public int index;
    }

}
