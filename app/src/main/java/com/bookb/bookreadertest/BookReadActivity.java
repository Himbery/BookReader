package com.bookb.bookreadertest;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mertakdut.BookSection;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;


public class BookReadActivity extends AppCompatActivity implements PageFragment.OnFragmentReadyListener {

    private Reader bookReader = new Reader();
    private ViewPager bookVP;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private int pageCount = Integer.MAX_VALUE;
    private int pxScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_activity);

        pxScreenWidth = getResources().getDisplayMetrics().widthPixels;
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        bookVP = (ViewPager) findViewById(R.id.container);
        bookVP.setOffscreenPageLimit(0);
        bookVP.setAdapter(sectionsPagerAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            BookItem book = getIntent().getExtras().getParcelable("book");

            try {
                bookReader.setMaxContentPerSection(1250);
                bookReader.setIsIncludingTextContent(true);
                bookReader.setIsOmittingTitleTag(true);
                bookReader.setFullContent(book.filePath);

                if (bookReader.isSavedProgressFound()) {
                    int lastSavedPage = bookReader.loadProgress();
                    bookVP.setCurrentItem(lastSavedPage);
                }

            } catch (ReadingException e) {
                Toast.makeText(BookReadActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public View onFragmentReady(int position) {

        BookSection bookSection = null;

        try {
            bookSection = bookReader.readSection(position);
        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(BookReadActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            this.pageCount = e.getPageCount();
        }

        sectionsPagerAdapter.notifyDataSetChanged();

    if (bookSection != null) {
        return setFragmentView (bookSection.getSectionContent(), "text/html", "UTF-8");
    }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            bookReader.saveProgress(bookVP.getCurrentItem());
            Toast.makeText(BookReadActivity.this, "Saved page: " + bookVP.getCurrentItem() + "...", Toast.LENGTH_LONG).show();
        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(BookReadActivity.this, "Progress is not saved: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            Toast.makeText(BookReadActivity.this, "Progress is not saved. Out of Bounds. Page Count: " + e.getPageCount(), Toast.LENGTH_LONG).show();
        }
    }


    private View setFragmentView(String data, String mimeType, String encoding) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WebView webView = new WebView(BookReadActivity.this);
        webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
        webView.setLayoutParams(layoutParams);
        return webView;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }
    }
}