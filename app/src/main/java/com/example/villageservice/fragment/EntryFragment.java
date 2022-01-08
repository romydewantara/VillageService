package com.example.villageservice.fragment;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.utility.ConstantVariable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryFragment extends Fragment {

    public static String URL = "";
    private Context context;
    private FragmentListener fragmentListener;

    private View view;
    private WebView webView;
    private ImageView backButton;
    private CardView cvMenuLeft;
    private CardView cvMenuRight;
    private String clType;
    private String menuSelected;
    private String previousFragment = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public void addPreviousFragmentTag(String previousFragment) {
        Log.d("PREVIOUS", "previousFragmentTag: " + previousFragment);
        this.previousFragment = previousFragment;
    }

    public EntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntryFragment newInstance(String param1, String param2) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            clType = getArguments().getString(ConstantVariable.KEY_CL_BUNDLE);
        }
        fragmentListener.onFragmentCreated(EntryFragment.this, previousFragment);
        URL = "https://kependudukancapil.jakarta.go.id/kartu-keluarga/";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_entry, container, false);
        backButton = view.findViewById(R.id.backButton);
        cvMenuLeft = view.findViewById(R.id.cvMenuLeft);
        cvMenuRight = view.findViewById(R.id.cvMenuRight);
        webView = view.findViewById(R.id.webView);

        //initContent();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** CLICK LISTENER */
        cvMenuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentPassingData(clType);
                fragmentListener.onFragmentFinish(EntryFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_CL, true);
            }
        });
        cvMenuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentPassingData(clType);
                fragmentListener.onFragmentFinish(EntryFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_FORM_LIST, true);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentListener) {
            fragmentListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void initContent(){
        webView.loadUrl(URL);
    }

    private void setUpWebView() {
        webView.setVisibility(View.VISIBLE);
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(false);
        android.webkit.CookieManager.getInstance().removeAllCookie();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String
                    contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new
                        DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(context, "Downloading File", Toast.LENGTH_LONG).show();
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //mProgressDialog.show();
                view.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //mProgressDialog.dismiss();
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //mProgressDialog.dismiss();
                webView.loadUrl("about:blank");
            }
        });
    }
}