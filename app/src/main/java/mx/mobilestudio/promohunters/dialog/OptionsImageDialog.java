package mx.mobilestudio.promohunters.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.mobilestudio.promohunters.R;

/**
 * Created by mobile on 12/17/16.
 */

public class OptionsImageDialog extends DialogFragment {

    public  String mCurrentPhotoPath;

    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 2;





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_name)
                .setItems(R.array.array_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item

                        Toast.makeText(getActivity(),"La opcion es:"+which,Toast.LENGTH_SHORT).show();

                        //dispatchPictureIntent();

                        takePhoto(null);

                    }
                });
        return builder.create();
    }

    public void dispatchPictureIntent(){
        // Para tomar una foto.

        File file = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try{

            file = createImageFile();
            mCurrentPhotoPath = file.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        }catch (IOException e){
            e.printStackTrace();
            file = null;
            mCurrentPhotoPath = null;
        }

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    1);

        startActivityForResult(takePictureIntent,0);

    }




    private File createImageFile() throws IOException{
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_"+timeStamp+"_";
        File albumF = getPictureDir();
        File imageF = File.createTempFile(imageFileName, ".jpg", albumF);
        return imageF;
    }


    private File getPictureDir(){
        File storageDir = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "PromoAlbum");
            if (storageDir != null) {

                if(! storageDir.mkdirs()){

                    if(! storageDir.exists()){
                        Log.e("PromoHunters","failed to create directory");
                        return null;
                    }
                }
            }
        }else{
            Log.v("PromoHunters", "External storage is not mounted");
        }

        return storageDir;

    }





    public boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = getActivity().getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }

    public void takePhoto(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(),
                            "External storage permission required to save images",
                            Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE_RESULT);
            }
        } else {
            callCameraApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_EXTERNAL_STORAGE_RESULT) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                Toast.makeText(getActivity(),
                        "External write permission has not been granted, cannot saved images",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void callCameraApp() {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
            mCurrentPhotoPath = photoFile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(callCameraApplicationIntent, 0);
    }

}
