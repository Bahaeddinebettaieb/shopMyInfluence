package com.smi.test.presentation.utils

import android.text.TextUtils
import java.util.regex.Pattern

class StringValidatorsUtils {

    companion object{
        val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,2}" +
                    ")+"
        )
        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && EMAIL_ADDRESS_PATTERN.matcher(target).matches()
        }


    }

}
