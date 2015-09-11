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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        CharSequence items[] = {"Use Camera", "Use Gallery" /*, "Use PDF" */};

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
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (intent.resolveActivity(context.getPackageManager()) != null) {
                                    getActivity().startActivityForResult(intent, CAMERA_REQUEST_CODE);
                                }
                                break;
                            case 1:
                                intent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                getActivity().startActivityForResult(intent, REQUEST_CODE_GALLERY);
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


}
