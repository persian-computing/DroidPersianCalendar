package com.byagowi.persiancalendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetProvider;

import com.byagowi.persiancalendar.R;

import calendar.CivilDate;
import calendar.DateConverter;
import calendar.PersianDate;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

/**
 * Program widget on Android launcher.
 * 
 * @author ebraminio
 * 
 */
public class PersianCalendarWidget1x1 extends AppWidgetProvider {
	static private IntentFilter intentFilter = null;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		if (intentFilter == null) {
			intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
			context.getApplicationContext().registerReceiver(
					new BroadcastReceiver() {
						@Override
						public void onReceive(Context context, Intent intent) {
							updateTime(context);
						}
					}, intentFilter);
		}
		
		updateTime(context);
	}

	static public void updateTime(Context context) {
		AppWidgetManager manager = AppWidgetManager.getInstance(context);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget1x1);

		CivilDate civil = new CivilDate();
		PersianDate persian = DateConverter.civilToPersian(civil);

		remoteViews.setTextViewText(R.id.monthText,
				persian.getMonthName());

		remoteViews
				.setTextViewText(
						R.id.dayOfMonthText,
						PersianUtils.getPersianNumber(persian.getDayOfMonth()));

		Intent launchAppIntent = new Intent(context,
				PersianCalendarActivity.class);
		PendingIntent launchAppPendingIntent = PendingIntent.getActivity(
				context, 0, launchAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.widget_layout1x1,
				launchAppPendingIntent);
		
		ComponentName widget = new ComponentName(context,
				PersianCalendarWidget1x1.class);
		manager.updateAppWidget(widget, remoteViews);
	}
}
