package com.example.basicactivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.basicactivity.databinding.ActivityMainBinding;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.serial) {
            downloadImagesSerially();
            return true;
        } else if (id == R.id.parallel) {
            downloadImagesInParallel();
            return true;
        } else if (id == R.id.clear) {
            clearImages();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void downloadImagesSerially() {
        String[] imageUrls = {"https://upload.wikimedia.org/wikipedia/commons/f/f8/1804_dollar_obverse.PNG",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/1879S_Morgan_Dollar_NGC_MS67plus_Obverse.png/800px-1879S_Morgan_Dollar_NGC_MS67plus_Obverse.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/1974S_Eisenhower_Obverse.jpg/800px-1974S_Eisenhower_Obverse.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/1976S_Type1_Eisenhower_Reverse.jpg/800px-1976S_Type1_Eisenhower_Reverse.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/5/54/2003_Sacagawea_Rev.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/2009NativeAmericanRev.jpg/800px-2009NativeAmericanRev.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/George_Washington_Presidential_%241_Coin_obverse.png/800px-George_Washington_Presidential_%241_Coin_obverse.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Presidential_dollar_coin_reverse.png/800px-Presidential_dollar_coin_reverse.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Presidential_dollar_coin_reverse.png/800px-Presidential_dollar_coin_reverse.png",

        };

        for (int i = 0; i < imageUrls.length; i++) {
            ImageView imageView = getImageViewByIndex(i);
            new DownloadImageTask(imageView).execute(imageUrls[i]);
        }
    }

    private void downloadImagesInParallel() {
        String[] imageUrls = {"https://upload.wikimedia.org/wikipedia/commons/f/f8/1804_dollar_obverse.PNG",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/1879S_Morgan_Dollar_NGC_MS67plus_Obverse.png/800px-1879S_Morgan_Dollar_NGC_MS67plus_Obverse.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/1974S_Eisenhower_Obverse.jpg/800px-1974S_Eisenhower_Obverse.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/1976S_Type1_Eisenhower_Reverse.jpg/800px-1976S_Type1_Eisenhower_Reverse.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/5/54/2003_Sacagawea_Rev.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/2009NativeAmericanRev.jpg/800px-2009NativeAmericanRev.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/George_Washington_Presidential_%241_Coin_obverse.png/800px-George_Washington_Presidential_%241_Coin_obverse.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Presidential_dollar_coin_reverse.png/800px-Presidential_dollar_coin_reverse.png",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Presidential_dollar_coin_reverse.png/800px-Presidential_dollar_coin_reverse.png",

        };

        for (int i = 0; i < imageUrls.length; i++) {
            ImageView imageView = getImageViewByIndex(i);
            new DownloadImageTask(imageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageUrls[i]);
        }
    }

    private void clearImages() {
        ImageView[] imageViews = {imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        for (ImageView imageView : imageViews) {
            imageView.setImageDrawable(null);
        }
    }

    private ImageView getImageViewByIndex(int index) {
        ImageView[] imageViews = {imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        return imageViews[index];
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);

                // Resize the image to fit the ImageView
                int newWidth = imageView.getWidth();
                int newHeight = imageView.getHeight();
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(mIcon, newWidth, newHeight, true);

                // Assign the resized bitmap to mIcon
                mIcon = resizedBitmap;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}

