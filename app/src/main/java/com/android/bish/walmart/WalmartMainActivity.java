package com.android.bish.walmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.android.bish.walmart.search.SearchResultsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalmartMainActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH_TERM = "extra.search.term";

    @BindView(R.id.search_edit_text)
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    startSearchResultsActivity(searchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void startSearchResultsActivity(String searchTerm) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(EXTRA_SEARCH_TERM, searchTerm);
        startActivity(intent);
    }
}
