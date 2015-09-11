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

    private DialogClickListener callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (DialogClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        CharSequence items[] = {"Use Gallery", "Use Camera"/*, "Use PDF" */};

        builder.setTitle(R.string.bill_input)
                .setNeutralButton("Use gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.selectFromGallery();
                    }
                })
        .setNeutralButton("Use Camera",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.selectFromCamera();
            }
        });

        return builder.create();
    }
}
