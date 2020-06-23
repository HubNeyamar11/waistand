package com.example.waistand;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

public class DialogPagerAdapter extends PagerAdapter {
    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private Context mContext = null ;


    public DialogPagerAdapter(Context context){
        mContext =null;

    }

    public DialogPagerAdapter(CustomDialog customDialog) {
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if(mContext !=null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_dialog,container,false);

            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText("TEXT" + position);
        }
        // 뷰페이저에 추가.
        container.addView(view) ;

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
