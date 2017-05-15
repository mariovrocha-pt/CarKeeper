package com.mariovrocha.carkeeper;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(message);
        }

        mProgressDialog.show();
    }

    public ProgressDialog makeRawProgressDialog(String message) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
