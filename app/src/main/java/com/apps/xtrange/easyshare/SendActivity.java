package com.apps.xtrange.easyshare;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by Oscar on 9/10/2015.
 */
public class SendActivity extends FragmentActivity  {
    public static final String EXTRA_FILE_URI = "extra-file-uri";
    private static final String TAG = SendActivity.class.getSimpleName();

    private Uri mFileUri;

    private ViewPager mSendMethodsPager;
    private TitlePageIndicator mTitlesIndicator;

    private String[] mTitles;

    private Fragment mSendWithQrFragment;
    private Fragment mSendWithNfcFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_activity);
        mFileUri = getIntent().getParcelableExtra(EXTRA_FILE_URI);

        mTitles = getResources().getStringArray(R.array.receiving_options);

        if (mFileUri == null) {
            Log.d(TAG, "Make sure to include the uri in the intent");
            finish();
        }

        mSendWithQrFragment = SendWithQrFragment.newInstance(mFileUri);
        mSendWithNfcFragment = SendWithNfcFragment.newInstance(mFileUri);

        mSendMethodsPager = (ViewPager)findViewById(R.id.send_methods_pager);
        mSendMethodsPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mSendWithQrFragment;
                    case 1:
                        return mSendWithNfcFragment;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        mTitlesIndicator = (TitlePageIndicator)findViewById(R.id.titles);
        mTitlesIndicator.setViewPager(mSendMethodsPager);
    }
}
