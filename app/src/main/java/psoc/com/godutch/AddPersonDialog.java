package psoc.com.godutch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import psoc.com.godutch.model.Person;

/**
 * Created by vmineiro on 11/09/15.
 */
public class AddPersonDialog extends DialogFragment {

    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Integer> mSelectedItems;
    ArrayList<String> names;
    ArrayList checks;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        persons = ((BillActivity) getActivity()).bill.getPersons();

        mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        names = new ArrayList<String>();
        checks = new ArrayList<>();

        for (Person p: persons) {
            names.add(p.getName());
            checks.add(true);
        }

        CharSequence cs[] = new CharSequence[names.size()];
        cs = names.toArray(cs);

        boolean bs[] = new boolean[checks.size()];
        Arrays.fill(bs, Boolean.TRUE);

        // Set the dialog title
        builder.setTitle(R.string.add_person)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(cs, bs,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        ((BillActivity) getActivity()).addPersons(mSelectedItems);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });


        return builder.create();
    }
}
