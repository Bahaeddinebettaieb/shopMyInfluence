package com.smi.test.presentation.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import com.smi.test.R
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class GlobalUtils {
    companion object {
        private val TAG = "GlobalUtils"

        fun navigateToActivityServiceWithExtraNoBack(
            activity: Context,
            start: Context,
            destination: Any,
            id: String?,
            name: String
        ) {
            activity.startActivity(
                Intent(start, destination as Class<Object>).putExtra("id", id)
                    .putExtra("name", name)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            )
        }

        fun navigateToActivityRightToLeft(
            activity: Activity,
            start: Context,
            destination: Any,
            id: String?,
            name: String
        ) {
            activity.startActivity(
                Intent(start, destination as Class<Object>).putExtra("id", id)
                    .putExtra("name", name).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        }

        fun navigateToActivityServiceWithExtra(
            activity: Context,
            start: Context,
            destination: Any,
            id: String?,
            name: String
        ) {
            activity.startActivity(
                Intent(start, destination as Class<Object>).putExtra("id", id)
                    .putExtra("name", name).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        fun navigateToActivity(
            activity: Activity,
            start: Context,
            destination: Any
        ) {
            activity.startActivity(Intent(start, destination as Class<Object>))
//            activity.overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit)
//            activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        }

        fun navigateToActivityWithExtra(
            activity: Activity,
            start: Context,
            destination: Any,
            id: String?,
            name: String
        ) {
            activity.startActivity(
                Intent(start, destination as Class<Object>).putExtra("id", id)
                    .putExtra("name", name).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
        }

        fun navigateToActivityWithExtraWithAnimation(
            activity: Activity,
            start: Context,
            destination: Any,
            id: String?,
            name: String
        ) {
            activity.startActivity(
                Intent(start, destination as Class<Object>).putExtra("id", id)
                    .putExtra("name", name).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            activity.overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit)
        }

        fun myLocationChangedOrNot(
            latitude: Double,
            longitude: Double,
            latitude1: Double,
            longitude1: Double,
            arron: Int
        ): Boolean {
            var res = false
            val df = DecimalFormat()
            val df1 = DecimalFormat()
            df.maximumFractionDigits = arron //arrondi à 3 chiffres apres la virgules
            df.isDecimalSeparatorAlwaysShown = true
            df1.maximumFractionDigits = arron //arrondi à 3 chiffres apres la virgules
            df1.isDecimalSeparatorAlwaysShown = true
//            Log.e(TAG, "myLocationChanged: $latitude == $latitude1 <> $longitude == $longitude1")
            if ((df.format(latitude) != df.format(latitude1)) && (df.format(longitude) != df.format(
                    longitude1
                ))
            ) {
                res = true
            }
            return res
        }

        fun toDP(context: Context, value: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value.toFloat(), context.resources.displayMetrics
            ).toInt()
        }

        fun toDPFromFloat(context: Context, value: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value.toFloat(), context.resources.displayMetrics
            )
        }

        fun toSP(context: Context, value: Int): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                value.toFloat(), context.resources.displayMetrics
            ).toInt()
        }

        fun clearCountryCode(number: String): Boolean {
            var s: Boolean
            var s1 = number.substring(0, 1)
            var s2 = number.substring(0, 2)
            s = s2 == "00" || s1 == "+"
            return s
        }

        fun listIntContainsNumber(type: List<Int>, i: Int): Boolean {
            var have = false
            for (Int in type) {
                if (Int == i) {
                    have = true
                    Log.e(TAG, "listIntContainsNumber: $Int == $i")
                }
            }
            return have
        }

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
                .matches()
        }

        fun changeTimestampToDate(timestamp: String?): String {
            Log.e(TAG, "changeTimestampToDate: $timestamp")
            var date = ""
            if (!timestamp.isNullOrEmpty()) {
                val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
                val netDate = Date(timestamp.toLong())
                date = sdf.format(netDate)
                Log.e(TAG, "Formatted Date= $date")
            }
            return date
        }

        fun convertFromBigDate(dateString: String?): Date? {
//            val dateStr=dateString?.substring(0,24)
            val sdf3 = SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z ('GMT'z)", Locale.ENGLISH)
            var d1: Date? = null
            try {
                d1 = sdf3.parse(dateString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return d1
        }
        fun convertToFormatDate(dateString: String?): String {
            val originalFormat: java.text.DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val targetFormatDate: java.text.DateFormat = SimpleDateFormat("dd MMMM yyyy")
            val targetFormatTime: java.text.DateFormat = SimpleDateFormat("hh:MM:ss")
            val date: Date = originalFormat.parse(dateString)
            return "${targetFormatDate.format(date)} a ${targetFormatTime.format(date)}"
        }

        fun convertToFormatDateWithoutSecond(dateString: String?): String {
            val originalFormat: java.text.DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            var targetFormatDate: java.text.DateFormat = SimpleDateFormat("dd/M/yyyy")
            var targetFormatTime: java.text.DateFormat = SimpleDateFormat("hh:MM")
            targetFormatTime.calendar.set(Calendar.SECOND, 0)
            val date: Date = originalFormat.parse(dateString)
//            var retour= "${targetFormatDate.format(date)} a ${targetFormatTime.format(date)}"
//            return retour.substring(0, retour.length - 4)
            return "${targetFormatDate.format(date)} a ${targetFormatTime.format(date)}"
        }

//        fun getDate(time: Long): String? {
//            val cal = Calendar.getInstance(Locale.ENGLISH)
//            cal.timeInMillis = time * 1000
//            return DateFormat.format("dd-MM-yyyy", cal).toString()
//        }

        fun convertToFormatDateWithDay(date: String?): String {
            val originalFormat: java.text.DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val targetFormat: java.text.DateFormat = SimpleDateFormat("EEE, dd MMMM")
            val date: Date = originalFormat.parse(date)
            return targetFormat.format(date)
        }

        fun convertToFormatDateMonth(date: String?): String {
            val originalFormat: java.text.DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val targetFormat: java.text.DateFormat = SimpleDateFormat("dd-mm-yyyy",Locale("en"))
            val date: Date = originalFormat.parse(date)
            return targetFormat.format(date)
        }


        fun convertToTime(date: String?): String {
//            Lingver.getInstance().setLocale(context, "en")
            val originalFormat: java.text.DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en"))
            val targetFormat: java.text.DateFormat = SimpleDateFormat("hh:mm a",Locale("en"))
            val date: Date = originalFormat.parse(date)
            return targetFormat.format(date)
        }

        fun getAddress(lat: Double, lng: Double, context: Context): String {
            val geocoder = Geocoder(context)
            val list = geocoder.getFromLocation(lat, lng, 1)
            Log.e(TAG, "getAddress: " + list[0].countryCode)
            return list[0].countryCode
        }

//        fun getAddressComplete(lat: Double, lng: Double, context: Context): String {
//            val geocoder = Geocoder(getApplicationContext(), Locale.getDefault())
////            val geocoder = Geocoder(context)
//            val list = geocoder.getFromLocation(lat, lng, 1)
////            Log.e(TAG, "getAddressComplete: " + list[0].getAddressLine(0))
//            return list[0].getAddressLine(0)
//        }

        fun distanceBetweenTowLocation(
            latitude: Double,
            longitude: Double,
            latitude1: Double,
            longitude1: Double
        ): Float {
            val locationA = Location("point A")

            locationA.latitude = latitude
            locationA.longitude = longitude

            val locationB = Location("point B")

            locationB.latitude = latitude1
            locationB.longitude = longitude1
            return locationA.distanceTo(locationB)
        }

//        fun showNotification(
//            context: Context,
//            title: String?,
//            message: String?,
//            intent: Intent?,
//            reqCode: Int
//        ) {
//            val pendingIntent =
//                PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT)
//            val CHANNEL_ID =
//                context.getString(R.string.default_notification_channel_id) // The id of the channel.
//            val notificationBuilder: NotificationCompat.Builder =
//                NotificationCompat.Builder(context, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setLargeIcon(
//                        BitmapFactory.decodeResource(
//                            context.resources,
//                            R.drawable.ic_launcher_background
//                        )
//                    )
//                    .setContentTitle(title)
//                    .setContentText(message)
//                    .setAutoCancel(false)
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                    .setContentIntent(pendingIntent)
//                    .setPriority(Notification.PRIORITY_HIGH)
//            val notificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val name: CharSequence =
//                    context.getString(R.string.default_notification_channel_id) // The user-visible name of the channel.
//                val importance = NotificationManager.IMPORTANCE_HIGH
//                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
//                notificationManager.createNotificationChannel(mChannel)
//            }
//            notificationManager.notify(
//                reqCode,
//                notificationBuilder.build()
//            ) // 0 is the request code, it should be unique id
//        }
//
//        fun removeNotification(context: Context, reqCode: Int) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.getSystemService(NotificationManager::class.java)
//                    .cancel(reqCode)
//            } else {
//                NotificationManagerCompat.from(context)
//                    .cancel(reqCode)
//            }
//        }


        fun roundFloat(f: Float): Float {
            val c = (f + 0.5f)
            val n = f + 0.5f
            return if ((n - c) % 2 == 0f) f else c
        }
        fun toDecimalDigitsFloat(d: Float, decimalPlace: Int): Float {
            var bd = BigDecimal(java.lang.Float.toString(d))
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
            Log.e(TAG, "toDecimalDigitsFloat: $bd")
            return bd.toFloat()
        }

        fun convertToString(date: String): String {
            val c = Calendar.getInstance()
            val dateConverter = convertToDateString(date)
            val parts = dateConverter.split("-")
            var dateString = parts[2] + " " + convertMonthToString(parts[1].toInt())
            if (parts[0].toInt() == c.get(Calendar.YEAR))
                if (parts[1].toInt() == c.get(Calendar.MONTH) + 1)
                    if (parts[2].toInt() == c.get(Calendar.DAY_OF_MONTH)) {
                        dateString = "Today"
                    }
            return dateString
        }

        // TODO You need to change it to Date format and then you cast it to month name MMM
        fun convertMonthToString(month: Int): String {
            return when (month) {
                1 -> return "Janvier"
                2 -> return "Fevrier"
                3 -> return "Mars"
                4 -> return "Avril"
                5 -> return "Mai"
                6 -> return "Juin"
                7 -> return "Juillet"
                8 -> return "Aout"
                9 -> return "September"
                10 -> return "October"
                11 -> return "Novembre"
                else -> return "December"
            }
        }
        fun convertToDateString(date: String): String {
            var date2 = date.substring(0..9)
            val parts = date2.split("-")
            Log.i(TAG, "" + date2 + " ==>" + parts[0].toInt() + "/" + parts[1].toInt() + "/" + parts[2].toInt())
            return date2
        }

//        fun postTimeAgo(updatedAt: String): String {
//            GetUnixTime()
//            val currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
//            currentCalendar.add(Calendar.HOUR, -1)
//            Log.e(TAG, "postTimeAgo: "+currentCalendar.time )
//            val oneHourBack = currentCalendar.time
//            val targetCalendar = Calendar.getInstance()
//            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
//            targetCalendar.time = sdf.parse(updatedAt)
//            val diffTs = oneHourBack.time - targetCalendar.timeInMillis
//            val diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffTs)
//            val diffHours = TimeUnit.MILLISECONDS.toHours(diffTs)
//            val diffDays = TimeUnit.MILLISECONDS.toDays(diffTs)
//            val diffWeeks = TimeUnit.MILLISECONDS.toDays(diffTs) / 7
//            val diffMonths = TimeUnit.MILLISECONDS.toDays(diffTs) / 30
//            val diffYears = TimeUnit.MILLISECONDS.toDays(diffTs) / 365
//            Log.e(TAG, "postTimeAgo: $diffTs , $diffMinutes , $diffHours , $diffDays , $diffDays , $diffWeeks , $diffMonths , $diffYears")
//            val newTs = when {
//                diffYears >= 1 -> context.getString(R.string.years_ago).replace(
//                    "$$$",
//                    diffYears.toString()
//                )
//                diffMonths >= 1 -> context.getString(R.string.months_ago).replace(
//                    "$$$",
//                    diffMonths.toString()
//                )
//                diffWeeks >= 1 -> context.getString(R.string.weeks_ago).replace(
//                    "$$$",
//                    diffWeeks.toString()
//                )
//                diffDays >= 1 -> context.getString(R.string.days_ago).replace(
//                    "$$$",
//                    diffDays.toString()
//                )
//                diffHours >= 1 -> context.getString(R.string.hours_ago).replace(
//                    "$$$",
//                    diffHours.toString()
//                )
//                diffMinutes >= 1 -> context.getString(R.string.minutes_ago).replace(
//                    "$$$",
//                    diffMinutes.toString()
//                )
//                else -> context.getString(R.string.now)
//            }
//            return newTs
//        }

        fun GetUnixTime(): Int {
            val calendar = Calendar.getInstance()
            val now = calendar.timeInMillis
            Log.e(TAG, "GetUnixTime: "+(now / 1000).toInt() )
            return (now / 1000).toInt()
        }
    }
}
