package com.glory.imageuploadusingretrofit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button pickImage, upload;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMEERA_PICK_REQUEST =1111;
    private static final String  TAG = ImageActivity.class.getSimpleName();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private String mediaPath;
    private Button btnCapturePicture;
    private String mImageFileLocation ="";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    AlertDialog  pDialog;
    private String postPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.preview);
        pickImage = findViewById(R.id.pickImage);
        upload = findViewById(R.id.upload);

        pickImage.setOnClickListener(this);
        upload.setOnClickListener(this);
        initDialog();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.pickImage:
            new AlertDialog.Builder(this)
                    .setTitle("my dialog")
                    .setView(R.array.uploadImages)
//                    .setItems("R.array.uploadImages)
                    .setItems(new AlertDialog.Builder() {
                        @Override
                        public void onSelection(AlertDialog dialog, View view, int which, CharSequence text) {
                            switch (which){
                                case 0:
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                    break;
                                case 1:
                                    captureImage();
                                    break;
                                case 2:
                                    imageView.setImageResource(R.drawable.ic_launcher_background);
                                    break;
                            }
                        }
                    }).show();
                    break;

            case R.id.upload:
                uploadFile();
                break;
        }
    }

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if (requestCode ==REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO){
                if(data != null){
                    // get the image from data
                    Uri selectedImage = data.getData();
                    String [] filePathColumn = { MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null, null, null );
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);

                    // set the image in imageview for previewing the media
                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();
                    postPath = mediaPath;
                }
            }else if(requestCode == CAMEERA_PICK_REQUEST){
                if(Build.VERSION.SDK_INT > 21){
                    Glide.with(this).load(mImageFileLocation).into(imageView);
                    postPath = mImageFileLocation;
                }else{
                    Glide.with(this).load(fileUri).into(imageView);
                    postPath = fileUri.getPath();
                }
            }
        }
        else if(resultCode != RESULT_CANCELED){
            Toast.makeText(this, "Sorry there was an error", Toast.LENGTH_LONG).show();
        }
    }


    // Checking  device has camera hardware or not
    private boolean isDeviceSupportCamera(){
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        }else{
            //this device does not have a camera
            return false;
        }
    }

    protected void initDialog(){
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("loading..");
//        pDialog.setMessage("Loading...");
        pDialog.setCancelable(true);
    }

    protected void showpDialog(){
        if(!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog(){
        if(pDialog.isShowing()) pDialog.dismiss();
    }

    // Launching camera app to capture Image

    private void captureImage(){
        if(Build.VERSION.SDK_INT > 21){ // use this if api 22 or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // we give some instruction to the intent to save the image
            File photoFile = null;
            try{
                // If the image will be successful the photofile will have the address
                photoFile =createImageFile();
                // here we call the function that will try to catch the exeception made
            }catch (IOException e){
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // here we add an extra file to the intent to put the address unto

            Uri outputUri = FileProvider.getUriForFile(
                    this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Logger.getAnonymousLogger().info("Calling the camera app by intent");

            // the following string calls the camera app and wait for his file in return
            startActivityForResult(callCameraApplicationIntent, CAMEERA_PICK_REQUEST);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // Start the image capture intent
            startActivityForResult(intent, CAMEERA_PICK_REQUEST);
        }
    }

    File createImageFile() throws IOException{
        Logger.getAnonymousLogger().info("Generating the image method started");

        // here we create a unique file

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;

        // here we specify the environment location and the exact path where we want to save the
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app ");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if(!storageDirectory.exists()) storageDirectory.mkdir();

        // here we create the file using a prefix, a suffice and  a directory
         File image  = new File (storageDirectory, imageFileName + ".jpg");

         //File image  = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // here the location is save into the string mimageFileLocation
        Logger.getAnonymousLogger().info("file name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri  = Uri.parse(mImageFileLocation);
        //The file is returned to the previous intent across the camera application

        return image;

    }

    //here we store the file url as it wil be null after returning from camera

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save the file url in bundle as it will be null on screen orientation changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    // creating File Uri to store images or video

    public  Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    //returning image/video

    private static File getOutputMediaFile(int type){
        // External SD card loaction

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // create the storage directory if it does not exist

        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdir()){
                Log.d(TAG, "opps failed to create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // create a mediafile name

        String timeStamp  = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + " IMG_" + ".jpg");
        }else {
            return null;
        }
        return mediaFile;
    }

    // uploading image/video

    private void uploadFile(){
        if(postPath == null || postPath.equals("")){
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        }else{
            showpDialog();
            // Map is used to multipart the file using Okhttp3.RequestBody
            Map<String, RequestBody> map  = new HashMap<>();
            File file  = new File(postPath);

            //Parsing any media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("file\"; filename=\"" + file.getName() +  "\"", requestBody);
            ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
            Call<ServerResponse> call  = getResponse.upload("token", map);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null){
                            hidepDialog();
                            ServerResponse serverResponse = response.body();
                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Problem uploading image", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                    hidepDialog();
                    Log.d("response gotten is: ", t.getMessage() );
                }
            });
        }
    }


}
