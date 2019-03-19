package com.glory.bottomdialogfragmentexample;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BottomDialog extends BottomSheetDialogFragment{

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.bottom_dialog_layout, null);
        dialog.setContentView(contentView);

        ImageView imageView = contentView.findViewById(R.id.dummy_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "the dialog image is working Properly", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
