/*
 * Copyright (c) 2020.
 *
 *                             * __author__     : DataHome S. de R.L. de C.V.
 *                             * __copyright__  : DataHome S. de R.L. de C.V.
 *                             * __version__    : 1.0.0
 *
 */

package mx.ctrlpg.util
import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper{

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "params"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun read(key: String, value: Long): Long? {
        return prefs.getLong(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }
}