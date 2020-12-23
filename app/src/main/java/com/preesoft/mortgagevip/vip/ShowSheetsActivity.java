package com.preesoft.mortgagevip.vip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.preesoft.mortgagevip.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ShowSheetsActivity extends AppCompatActivity implements SheetsAdapter.DownloadFile {
    private RecyclerView recyclerView;
    private SheetsAdapter sheetsAdapter;
    private ArrayList<SheetModelClass> sheetModelClassArrayList = new ArrayList<>();
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sheets);

        sheetModelClassArrayList.add(new SheetModelClass("European Investors", R.raw.european_investors_1_15_2020_sample));
        sheetModelClassArrayList.add(new SheetModelClass("FO Sample", R.raw.fo_sample_updated));
        sheetModelClassArrayList.add(new SheetModelClass("Hard Money", R.raw.hard_money_list_updated_sample));
        sheetModelClassArrayList.add(new SheetModelClass("Investment Firms", R.raw.investment_firms_and_funding_groups_sample));
        sheetModelClassArrayList.add(new SheetModelClass("Investment Trusts", R.raw.investors_trusts_and_executives_sample));
        sheetModelClassArrayList.add(new SheetModelClass("RE American Investors", R.raw.re_american_investors_sample));

        recyclerView = findViewById(R.id.show_sheets_recycler_view);
        backImageView = findViewById(R.id.back_iv);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //download();
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
        }


        sheetsAdapter = new SheetsAdapter(sheetModelClassArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sheetsAdapter);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 99) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    private void copyFiletoExternalStorage(int resourceId, String resourceName) {
        try {
//            InputStream in = getResources().openRawResource(
//                    getResources().getIdentifier(resourceName,
//                            "raw", getPackageName())); // use only file name here, don't use extension
            InputStream in = getResources().openRawResource(resourceId);
            File fileWithinMyDir = new File(checkFolder(), resourceName + ".xlsx"); //Getting a file within the dir.
            Log.e("FILEPATH ", "fileWithinMyDir " + fileWithinMyDir);
            FileOutputStream out = new FileOutputStream(fileWithinMyDir);
            byte[] buff = new byte[1024 * 1024 * 2]; //2MB file
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
            Log.d(TAG, "Download Done ");
            Toast.makeText(this, "Downloaded", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Download Failed " + e.getMessage());
            Toast.makeText(this, "Downloaded Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private File checkFolder() {
        String path;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            path = getExternalFilesDir(null).getAbsolutePath() + "/MortgageVip";
            Log.d(TAG, "checkFolder: Above 29");
        } else {
            path = Environment.getExternalStorageDirectory() + "/MortgageVip";
            Log.d(TAG, "checkFolder: Below 29");
        }
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
            Log.d("Folder", "Created = " + isDirectoryCreated);
        }

        Log.d("Folder", "Created ? " + isDirectoryCreated);
        return dir;
    }

    @Override
    public void download(final SheetModelClass sheetModelClass) {
        copyFiletoExternalStorage(sheetModelClass.getResId(), sheetModelClass.getName());
        /*Log.d(TAG, "download: Downloading Started...");
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/MortgageVIP");
        if (!direct.exists()) {
            direct.mkdirs();
        }
        final ProgressDialog progressBarDialog = new ProgressDialog(this);
        progressBarDialog.setTitle("Downloading Sheet....");
        progressBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBarDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int whichButton) {
                // Toast.makeText(getBaseContext(),
                //       "OK clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        progressBarDialog.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloading = true;
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(getResources().getResourceName(sheetModelClass.getResId()));
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(sheetModelClass.getName())
                        .setDescription("Downloading....")
                        .setDestinationInExternalPublicDir("MortgageVIP", sheetModelClass.getName() + ".pdf");
                Long reference = manager.enqueue(request);
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(reference); //filter by id which you have receieved when reqesting download from download manager
                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                    final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBarDialog.setProgress((int) dl_progress);
                        }
                    });
                    // Log.d(Constants.MAIN_VIEW_ACTIVITY, statusMessage(cursor));
                    cursor.close();
                }
            }
        }).start();
        //show the dialog
        progressBarDialog.show();
*/

    }
}