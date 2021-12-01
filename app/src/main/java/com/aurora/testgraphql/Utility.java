package com.aurora.testgraphql;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.Window;

public  class  Utility {

   static Dialog  dialogPleaseWait;
    public static void openWebSite(Context context,String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void showDialogPleaseWait(Context context) {

        dialogPleaseWait = new Dialog(context);
        dialogPleaseWait.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogPleaseWait.setContentView(R.layout.dialog_loading);
        dialogPleaseWait.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogPleaseWait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogPleaseWait.show();

    }
    public static void dismissDialogPleaseWait() {

     try {
         dialogPleaseWait.dismiss();
     }
     catch (Exception e){}

    }
}
