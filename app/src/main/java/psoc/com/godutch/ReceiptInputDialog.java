package psoc.com.godutch;

import android.annotation.TargetApi;
import android.app.Activity;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        CharSequence items[] = {"Use Gallery", "Use Camera"/*, "Use PDF" */};

        builder.setTitle(R.string.bill_input);
        builder.setItems(items,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {

                    ((Home) getActivity()).selectFromGallery();

                }
                else if (which == 1){

                    ((Home) getActivity()).selectFromCamera();

                }

            }
        });


        return builder.create();
    }
}
