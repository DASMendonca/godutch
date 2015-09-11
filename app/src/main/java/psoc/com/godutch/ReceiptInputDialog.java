package psoc.com.godutch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by vmineiro on 11/09/15.
 */
public class ReceiptInputDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        CharSequence items[] = {"Use Gallery", "Use Camera", "Use Test" };

        builder.setTitle(R.string.bill_input);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {

                    ((Home) getActivity()).selectFromGallery();

                } else if (which == 1) {

                    ((Home) getActivity()).selectFromCamera();

                } else {

                    ((Home) getActivity()).selectTest();
                }

            }
        });


        return builder.create();
    }
}
