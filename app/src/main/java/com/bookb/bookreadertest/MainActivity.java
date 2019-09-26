package com.bookb.bookreadertest;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<BookItem> bookList = new ArrayList<BookItem>();
    LinkItem linkItem;
    ListView bookLV;
    TextView title;
    TextView titleLink;
    BookListAdapter bLAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitialData();
//        fetchData("http://host1715416.hostland.pro/test/test.txt");

        // Setup subviews
//        title = (TextView) findViewById(R.id.title_a);
//        titleLink = (TextView) findViewById(R.id.webS);
        bookLV = (ListView) findViewById(R.id.book_list);

        // Setup book list adapter
        bLAdapter = new BookListAdapter(this, R.layout.list_item, bookList);
        bookLV.setAdapter(bLAdapter);

//        titleLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (linkItem != null) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkItem.link));
//                    startActivity(browserIntent);
//                }
//            }
//        });

        bookLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showBook = new Intent(MainActivity.this, BookReadActivity.class);
                showBook.putExtra("book", bookList.get(position));
                startActivity(showBook);
            }
        });
    }

    // Fetch data from url
//    private void fetchData(String url) {
//        StringRequest request = new StringRequest(url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                parseJsonData(response);
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Some error!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
//        rQueue.add(request);
//    }

    // Sets initial array of books
    private void setInitialData() {
//        bookList.add(new BookItem("А. Грин, <<Алые паруса>>", R.drawable.alyeparusa, "alye_parusa.epub"));
//        bookList.add(new BookItem("А. Пушкин, <<Пиковая Дама>>", R.drawable.pikovayadama, "pikovayadama.epub"));
//        bookList.add(new BookItem("Н. Гоголь, <<Ревизор>>", R.drawable.revisor, "revisor.epub"));
        bookList.add(new BookItem("Библия", R.drawable.bible, "bible.epub"));
        bookList.add(new BookItem("Коран", R.drawable.koran, "Koran.epub"));
        bookList.add(new BookItem("Тора", R.drawable.tora, "TORA.epub"));

        for (int i=0; i < bookList.size(); i++) {
            List<File> files = getListFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
            File sampleFile = getFileFromAssets(bookList.get(i).bookFileName);
            files.add(0, sampleFile);

            for (File file : files) {
                bookList.get(i).filePath = file.getPath();
            }
        }
    }

    // Find file in assest
    public File getFileFromAssets(String fileName) {
        File file = new File(getCacheDir() + "/" + fileName);
        if (!file.exists()) try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListFiles(file));
                } else {
                    if (file.getName().endsWith(".epub")) {
                        inFiles.add(file);
                    }
                }
            }
        }
        return inFiles;
    }

//    private void parseJsonData(String jsonString) {
//        try {
//            JSONArray arr = new JSONArray(jsonString);
//            JSONObject obj = arr.getJSONObject(0);
//            Iterator<String> k = obj.keys();
//            LinkItem item = new LinkItem();
//            item.text = obj.getString("text");
//            item.link = obj.getString("link");
//            linkItem = item;
//
//            title.setText(item.text);
//            titleLink.setText(item.link);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
