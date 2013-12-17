package com.seven.viewpager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tutor.viewpager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ViewPager实现中间大，两边小图，左右滑动变大变小的效果
 * 
 * @author seven
 */
public class ViewPagerMulTiFragmentDemo extends Activity {

    private static int     TOTAL_COUNT = 3;

    private RelativeLayout viewPagerContainer;
    private CoverFlowViewPager      viewPager;
    private TextView       indexText;
    private static final int ALBUM_RES[] = {
		R.drawable.test1,R.drawable.test2,R.drawable.test3,
		R.drawable.test4,R.drawable.test5,R.drawable.test6
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_multi_fragment_demo);
        JSONArray mJsonArray = new JSONArray();
    	for(int i = 0;i<10; i++){
    		JSONObject object = new JSONObject();
    		try {
				object.put("resid", ALBUM_RES[i % ALBUM_RES.length]);
				object.put("name", "Album " + i);
	    		mJsonArray.put(object);
			} catch (JSONException e) {
				e.printStackTrace();
			}
    		
    	} 
        viewPager = (CoverFlowViewPager)findViewById(R.id.view_pager);
        indexText = (TextView)findViewById(R.id.view_pager_index);
        viewPagerContainer = (RelativeLayout)findViewById(R.id.pager_layout);
        viewPager.setAdapter(new ViewPagerAdapter(this,mJsonArray));
        // to cache all page, or we will see the right item delayed
        viewPager.setOffscreenPageLimit(TOTAL_COUNT);
        viewPager.setPageMargin(10);
        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);

        viewPagerContainer.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });
        indexText.setText(new StringBuilder().append("1/").append(TOTAL_COUNT));
    }


    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            indexText.setText(new StringBuilder().append(position + 1).append("/").append(TOTAL_COUNT));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}