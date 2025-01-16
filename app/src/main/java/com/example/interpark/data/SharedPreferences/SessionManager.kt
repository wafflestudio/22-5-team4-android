import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionManager(context: Context) {
    val isLoggedIn: Boolean = context.getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE) == null
    val nickName: String? = context.getSharedPreferences("nickName", Context.MODE_PRIVATE).toString()

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_TOKEN = "user_token"
    }

    fun signIn(): Unit{
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

}
