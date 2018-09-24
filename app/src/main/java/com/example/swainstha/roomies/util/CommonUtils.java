/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.example.swainstha.roomies.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;


import com.example.swainstha.roomies.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    /**
     * Method to get the device id
     *
     * @param activity
     */
    public static String getDeviceId(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * @param context get context of the activity
     * @return the hash key
     */
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    //expands the content of animation
    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        Log.d("height", String.valueOf(targetHeight));

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(2 * (int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    //collapse the content with animation
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(2 * (int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static Point getDisplaySize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }

    //returns current system date
    public static Date getCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy");
        String currentFormattedDate = displayDate.format(currentDate);
        Date currentReturnDate = null;
        try {
            currentReturnDate = displayDate.parse(currentFormattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentReturnDate;
    }

    public static Date getCurrentDate(String date) {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat displayDate = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
        String currentFormattedDate = date + ", " + displayDate.format(currentDate);
        Date currentReturnDate = null;
        try {
            currentReturnDate = dateTimeFormat.parse(currentFormattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentReturnDate;
    }


    //returns current system date in String
    public static String getCurrentDateString() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy");
        String currentFormattedDate = displayDate.format(currentDate);
        return currentFormattedDate;
    }

    //get current date and time
    public static String getCurrentDateTimeString() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy, HH:mm z");
        String currentFormattedDate = displayDate.format(currentDate);
        return currentFormattedDate;
    }

    public static String convertDateToServerFormat(String date) {
        SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFirst = null;
        Date dateSecond = null;
        try {
            dateFirst = displayDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String serverDateString = serverDate.format(dateFirst);
        return serverDateString;
    }

    public static Date getCurrentDateFromServer(String date) {
        SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");
        Date firstDate = null;
        Date currentReturnDate = null;
        try {
            firstDate = serverDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String serverDateString = dateTimeFormat.format(firstDate);
        try {
            currentReturnDate = dateTimeFormat.parse(serverDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentReturnDate;
    }

    public static String getCurrentDateToServer(Date date) {
        SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");

        String serverDateString = serverDate.format(date);

        return serverDateString;
    }

    public static String convertDateToServiceHistoryFormat(String date) {
        SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat serviceHistory = new SimpleDateFormat("dd/MM/yyyy");
        Date dateFirst = null;
        Date dateSecond = null;
        try {
            dateFirst = displayDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String serviceHistoryString = serviceHistory.format(dateFirst);
        return serviceHistoryString;
    }

    public static String convertDateLocalFormat(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateFirst = null;
            Date dateSecond = null;
            try {
                dateFirst = serverDate.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String serverDateString = displayDate.format(dateFirst);
            return serverDateString;
        } else {
            return date;
        }
    }

    public static boolean compareDates(String date1, String date2) {
        SimpleDateFormat displayDate = new SimpleDateFormat("dd MMM yyyy");
        Date dateFirst = null;
        Date dateSecond = null;
        try {
            dateFirst = displayDate.parse(date1);
            dateSecond = displayDate.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateFirst.after(dateSecond)) {
            return true;
        } else if (dateFirst.equals(dateSecond)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareDates(Date date1, Date date2) {
        Date dateFirst = date1;
        Date dateSecond = date2;

        if (dateFirst.after(dateSecond)) {
            return true;
        } else if (dateFirst.equals(dateSecond)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDateFromTimeStamp(Context context, Long timeStamp) {
        String date  = "";
        if (timeStamp > 0) {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(timeStamp * 1000);
            date = DateFormat.format("dd MMM yyyy, hh:mm:ss aa", cal).toString();
        } else {
            //date = context.getResources().getString(R.string.not_synced);
        }
        return date;
    }
}
