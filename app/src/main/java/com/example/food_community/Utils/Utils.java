package com.example.food_community.Utils;

import android.app.AlertDialog;
import android.content.Context;

import com.example.food_community.ViewModel.Client;
import com.example.food_community.ViewModel.FoodAPI;

public class Utils {
    public static FoodAPI getApi() {
        return Client.getFoodClient().create(FoodAPI.class);
    }

    public static void showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
    }
}
