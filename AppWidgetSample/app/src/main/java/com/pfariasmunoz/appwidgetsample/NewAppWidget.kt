package com.pfariasmunoz.appwidgetsample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import java.text.DateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {

        const val mSharedPrefFile = "com.pfariasmunoz.appwidgetsample"
        const val COUNT_KEY = "count"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val prefs = context.getSharedPreferences(mSharedPrefFile, 0)
            var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
            count++
            val dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setTextViewText(R.id.appwidget_update, context.resources.getString(R.string.widget_update, count, dateString))
            views.setTextViewText(R.id.appwidget_id, appWidgetId.toString())

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

            prefs.edit().apply {
                putInt(COUNT_KEY + appWidgetId, count)
                apply()
            }
        }
    }
}

