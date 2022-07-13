package badvilbel.ws20st.frontend

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        private val decimalFormat = DecimalFormat("0.0")
        private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        private val dateFormatJson = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")

        fun formatDate(date: Date): String {
            return dateFormat.format(date)
        }

        fun dateToJson(date: Date): String {
            return dateFormatJson.format(date)
        }

        fun formatKilometers(kilometers: Double): String {
            return decimalFormat.format(kilometers)
        }
    }
}