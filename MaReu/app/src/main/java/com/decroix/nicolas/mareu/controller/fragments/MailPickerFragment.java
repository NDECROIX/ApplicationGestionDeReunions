package com.decroix.nicolas.mareu.controller.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.decroix.nicolas.mareu.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MailPickerFragment extends DialogFragment {

    // Use this instance of the interface to deliver action events
    OnMailsSelectedListener callback;

    public MailPickerFragment(OnMailsSelectedListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        List<String> selectedItems = new ArrayList<>();
        List<String> myItems = Arrays.asList(getResources().getStringArray(R.array.mails));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.mails_picker_title))
                .setMultiChoiceItems(R.array.mails, null, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedItems.add(myItems.get(which));
                    } else selectedItems.remove(myItems.get(which));
                })
                .setPositiveButton(R.string.button_ok, (dialog, id) -> {
                    callback.onMailsSelected(selectedItems);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.button_cancel, (dialog, id) -> dialog.cancel());

        return builder.create();
    }

    public interface OnMailsSelectedListener {
        void onMailsSelected(List<String> emails);
    }
}
