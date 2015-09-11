package psoc.com.godutch;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import psoc.com.godutch.model.Bill;

/**
 * Created by vmineiro on 11/09/15.
 */
public class ReceiptInputDialog extends DialogFragment {

    private static int CAMERA_REQUEST_CODE = 0;
    private static int REQUEST_CODE_GALLERY = 1;

    private static int OPTION_SELETED = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        CharSequence items[] = {"Use Gallery", "Use Camera"/*, "Use PDF" */};

        builder.setTitle(R.string.bill_input)
                .setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item

                        Dialog dialog  = (Dialog) dialogInterface;
                        Context context = dialog.getContext();
                        Intent intent = null;
                        switch (which) {
                            case 0:
                                intent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                                break;
                            case 1:
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (intent.resolveActivity(context.getPackageManager()) != null) {
                                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                                };
                                break;
                            case 2:
                                /*Intent getPDF = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (getPDF.resolveActivity(getContext().getPackageManager()) != null) {
                                    startActivityForResult(getPDF, CAMERA_REQUEST_CODE);
                                }*/
                                break;
                            default:
                                break;
                        }

                        if (null != intent) {



                        }



                    }
                });

        return builder.create();
    }

    public int getOptionSeleted(){
        return OPTION_SELETED;
    }

    /*protected void onPhotoSelected(Uri uri) {
        Uri selectedImage = uri;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bill = BitmapFactory.decodeFile(picturePath);

        onPhotoTaken(bill);

    }*/

}
