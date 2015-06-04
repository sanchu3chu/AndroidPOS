package com.ricoh.pos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ProductDetailDialogFragment extends DialogFragment {
	public static final String DIALOG_TAG = "PRODUCT_DETAIL_DIALOG";
	public static final String ARG_KEY_IMAGE_BITMAP = "ARG_KEY_IMAGE_BITMAP";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageBitmap((Bitmap)getArguments().getParcelable(ARG_KEY_IMAGE_BITMAP));
		builder.setView(imageView);

		builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});

		return builder.create();
	}
}
