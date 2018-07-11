package huitca1212.tiempoourense.ui.utils

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.showToast(@StringRes text: Int, lenght: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(text), lenght).show()
}
