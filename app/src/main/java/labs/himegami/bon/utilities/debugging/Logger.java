package labs.himegami.bon.utilities.debugging;

import android.content.Context;
import android.util.Log;


import java.lang.reflect.Field;

import labs.himegami.bon.R;


/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 7, 2016
 */
public class Logger {

    //region STANDARD LOGGERS
    public static void v(Class processClass, String message) {
        Log.v(getProcessName(processClass), message);
    }

    public static void v(Class processClass, String message, Exception e) {
        Log.v(getProcessName(processClass), message, e);
    }

    public static void d(Class processClass, String message) {
        Log.d(getProcessName(processClass), message);
    }

    public static void d(Class processClass, String message, Exception e) {
        Log.d(getProcessName(processClass), message, e);
    }

    public static void i(Class processClass, String message) {
        Log.i(getProcessName(processClass), message);
    }

    public static void i(Class processClass, String message, Exception e) {
        Log.i(getProcessName(processClass), message, e);
    }

    public static void w(Class processClass, String message) {
        Log.w(getProcessName(processClass), message);
    }

    public static void w(Class processClass, String message, Exception e) {
        Log.w(getProcessName(processClass), message, e);
    }

    public static void e(Class processClass, String message) {
        Log.e(getProcessName(processClass), message);
    }

    public static void e(Class processClass, String message, Exception e) {
        Log.e(getProcessName(processClass), message, e);
    }
    //endregion

    //region HELPERS
    public static String getProcessName(Class processClass) {
        return processClass.getSimpleName();
    }

    protected static Field findThisField(Class processClass, String fieldName) {
        for(Field field : processClass.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        if (processClass.getSuperclass() != null) {
            return findThisField(processClass.getSuperclass(), fieldName);
        }
        return null;
    }
    //endregion

    //region NULL CHECKERS
    public static void isNull(Context context, Class processClass, Object object, String fieldName) {
        Field field = findThisField(processClass, fieldName);
        if (field != null) {
            Logger.i(processClass, context.getString(R.string.debug_message_validate_object)
                    .replace("{0}", field.getName())
                    .replace("{1}", field.getType().getSimpleName())
                    .replace("{2}", object == null ? "NULL" : "NOT NULL"));
        } else {
            Logger.e(processClass, context.getString(R.string.debug_message_object_does_not_exist)
                    .replace("{0}", fieldName));
        }
    }

    public static String oIsNull(Context context, Class processClass, Object object, String fieldName) {
        Field field = findThisField(processClass, fieldName);
        if (field != null) {
            return context.getString(R.string.debug_message_validate_object)
                    .replace("{0}", field.getName())
                    .replace("{1}", field.getType().getSimpleName())
                    .replace("{2}", object == null ? "NULL" : "NOT NULL");
        } else {
            return context.getString(R.string.debug_message_object_does_not_exist)
                    .replace("{0}", fieldName);
        }
    }
    //endregion

}

