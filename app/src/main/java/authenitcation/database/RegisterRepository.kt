package authenitcation.database

import android.util.Log

class RegisterRepository(private val dao: RegisterDatabaseDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userEmail: String):RegisterEntity?{
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return dao.getUsername(userEmail)
    }

//    suspend fun deleteAll(): Int {
//        return dao.deleteAll()
//    }

}