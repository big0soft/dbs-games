package com.games.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gameButton = findViewById(R.id.gameButton);
        LinearLayout gameContainer = findViewById(R.id.gameContainer);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

// Create a WebView with layout parameters


// Initially hide the game container
        gameContainer.setVisibility(View.GONE);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with your actual game URL
//                C:\Users\Amal\AndroidStudioProjects\MyApplication2\app\src\main\assets\spin-wheel-main\examples\themes
//                C:\xampp\htdocs\spin-wheel-main\examples\themes
                String gameUrl = "http://192.168.68.107/spin-wheel-main/examples/themes/index.html";

                // Create a WebView to display the game
                webView = new WebView(getApplicationContext());
//                webView.getSettings().setAllowFileAccess(true);
//                webView.addJavascriptInterface();
                webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript for game interaction
//                webView.setWebViewClient(new MyWebViewClient()); // Optional: Custom WebViewClient
                webView.getSettings().setDomStorageEnabled(true); // Enable DOM storage for JavaScript
                WebView.setWebContentsDebuggingEnabled(true);


                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        super.onReceivedSslError(view, handler, error);
                        Log.e("WebView", "SSL Error: " + error.toString());
                        handler.proceed();
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        view.loadUrl(gameUrl);
                        return true;
                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                        Log.d("onReceivedError", error.getDescription().toString());
                        Log.d("onReceivedError", String.valueOf(error.getErrorCode()));
                    }

                });
                // Load the game URL
                webView.loadUrl(gameUrl);

                webView.setLayoutParams(layoutParams);
                // Add the WebView to the container and show it
                gameContainer.addView(webView);
                gameContainer.setVisibility(View.VISIBLE);

                // (Optional) Add JavaScript bridge code if needed
            }
        });
    }


    // Optional: Custom WebViewClient for error handling and communication
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Handle internal links within the game (optional)
            // return true to override and handle in-app, false to let WebView handle
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // Handle loading errors (optional)
            Log.e("WebView", "Error loading game: " + description);
        }
    }

    @Override
    public void onBackPressed() {

        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}