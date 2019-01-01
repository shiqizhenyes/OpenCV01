package mobi.zack.opencv01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Bitmap bitmap;
    private Bitmap greyBitmap;
    private ImageView imageView;
    private Button mChangeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChangeColor = findViewById(R.id.mChangeColor);
        imageView = findViewById(R.id.imageView);
        mChangeColor.setOnClickListener(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        greyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
    }

//    public native String stringFromJNI();
    public native int[] turnToGrey(int[] buf, int w, int h);

    @Override
    public void onClick(View v) {

        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels( pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int[] greyPixels = turnToGrey(pixels,bitmap.getWidth(), bitmap.getHeight());
        greyBitmap.setPixels(greyPixels,0, greyBitmap.getWidth(), 0, 0, greyBitmap.getWidth(), greyBitmap.getHeight());
        if (mChangeColor.getText().equals(getString(R.string.grey_picture))) {
            imageView.setImageBitmap(greyBitmap);
            mChangeColor.setText(R.string.recovery);
        }else {
            imageView.setImageBitmap(bitmap);
            mChangeColor.setText(R.string.grey_picture);
        }

    }
}
