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
                        OPTION_SELETED = which;
                        notify();
                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }

    public int getOptionSeleted(){
        return OPTION_SELETED;
    }
}
