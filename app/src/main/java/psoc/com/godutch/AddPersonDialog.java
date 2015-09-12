package psoc.com.godutch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import psoc.com.godutch.model.Contacts;
import psoc.com.godutch.model.Person;

/**
 * Created by vmineiro on 11/09/15.
 */
public class AddPersonDialog extends DialogFragment {

    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> mSelectedItems;
    ArrayList checks;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        persons = ((BillActivity) getActivity()).bill.getPersons();

        mSelectedItems = new ArrayList<Person>();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        checks = new ArrayList<>();

        boolean bs[] = new boolean[Contacts.allContacts().size()];
        Arrays.fill(bs, Boolean.FALSE);

        for (int i = 0; i<Contacts.allContacts().size(); i++) {
            for (Person p: persons) {
                if (Contacts.getAllNames().get(i).equals(p.getName())){
                    bs[i] = Boolean.TRUE;
                    mSelectedItems.add(p);
                    break;
                }
            }
        }

        CharSequence cs[] = new CharSequence[Contacts.allContacts().size()];
        cs = Contacts.getAllNames().toArray(cs);


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
                                    mSelectedItems.add(Contacts.allContacts().get(which));
                                } else {
                                    Person person = null;
                                    for (Person p : mSelectedItems) {
                                        if (Contacts.getAllNames().get(which).equals(p.getName())) {
                                            // Else, if the item is already in the array, remove it
                                            person = p;
                                            break;
                                        }
                                    }

                                    if (person != null) mSelectedItems.remove(person);
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        ((BillActivity) getActivity()).updatePersons(mSelectedItems);

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
