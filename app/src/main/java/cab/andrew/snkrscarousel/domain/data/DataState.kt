package cab.andrew.snkrscarousel.domain.data

import android.provider.ContactsContract

data class DataState<out T>(
    val data: T? = null,
    val error : String? = null,
    val loading: Boolean = false
) {
    companion object {
        fun <T> success(
            data: T
        ): DataState<T> {
            return DataState<T> (data = data)
        }

        fun <T> error(
            message: String
        ): DataState<T> {
            return DataState(error = message)
        }

        fun <T> loading(): DataState<T> = DataState(loading = true)
    }
}
