package com.aramis.library.component.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aramis.library.R;
import com.aramis.library.component.camera.ArCameraHelper;
import com.cbt.yhh.component.dialog.BunnyDialog;


/**
 * PictureDialog
 * Created by Aramis on 2017/5/3.
 */

public class PictureDialog implements BunnyDialog {
    private Dialog dialog;
    private ArCameraHelper arCameraHelper;
    private String cameraPicName;
    private OnPreCameraListener onPreCameraListener;

    public PictureDialog(Activity activity) {
        arCameraHelper = new ArCameraHelper(activity);
        dialog = new Dialog(activity, R.style.new_custom_dialog);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.view_dialog_picture, null);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.DialogAnimation_Bottom);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
        initView(dialogView);
        dialog.setContentView(dialogView);
    }

    public void setCameraPicName(String cameraPicName) {
        this.cameraPicName = cameraPicName;
    }

    private void initView(View dialogView) {
        //拍照
        TextView text_dialog_camera = (TextView) dialogView.findViewById(R.id.text_dialog_camera);
        text_dialog_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPreCameraListener != null) onPreCameraListener.onPreCamera();
                arCameraHelper.openCamera(cameraPicName);
                dismiss();
            }
        });
        //相册
        TextView text_dialog_gallery = (TextView) dialogView.findViewById(R.id.text_dialog_gallery);
        text_dialog_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arCameraHelper.openGallery();
                dismiss();
            }
        });
        //取消
        TextView text_dialog_cancel = (TextView) dialogView.findViewById(R.id.text_dialog_cancel);
        text_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnPreCameraListener {
        void onPreCamera();
    }

    public void setOnPreCameraListener(OnPreCameraListener onPreCameraListener) {
        this.onPreCameraListener = onPreCameraListener;
    }

    public ArCameraHelper getArCameraHelper() {
        return arCameraHelper;
    }


    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }
}
