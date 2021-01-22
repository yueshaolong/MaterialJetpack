package com.ysl.myapplication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// @Inject tells Hilt how to create instances of this type
// and the dependencies it has.
class UserRepository @Inject constructor(
    private val userService: UserService,
    // Simple in-memory cache. Details omitted for brevity.
    private val userCache : UserCache,
    // Simple in-memory cache. Details omitted for brevity.
    private val executor: Executor,
    private val userDao: UserDao
){
//    private val userService : UserService = TODO()

    fun getUser(userId : String) : LiveData<User>{
        return object : NetworkBoundResource<User,User>(){
            override fun saveCallResult(item: User) {
                userDao.save(item)
            }

            override fun shouldFetch(data: User?): Boolean {
//                return rateLimiter.canFetch(userId) && (data == null || !isFresh(data))
                return userDao.hasUser(FRESH_TIMEOUT)
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao.load(userId)
            }

            override fun createCall(): LiveData<Response<User>> {
                return userService.getUser(userId)
            }

        }.asLiveData()
    }

    /*fun getUser1(userId : String) : LiveData<User>{
        refreshUser(userId)

        val cache : LiveData<User> = userCache.get(userId)
        if (cache != null){
            return cache
        }
        val data = MutableLiveData<User>()
        // The LiveData object is currently empty, but it's okay to add it to the
        // cache here because it will pick up the correct data once the query
        // completes.
        userCache.put(userId, data)
        // This implementation is still suboptimal but better than before.
        // A complete implementation also handles error cases.
        userService.getUser(userId).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                data.value = response.body()
            }
            // Error case is left out for brevity.
            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return data
    }*/

    /*private fun refreshUser(userId: String) {
        // Runs in a background thread.
        executor.execute(Runnable {
            // Check if user data was fetched recently.
            val userExists = userDao.hasUser(FRESH_TIMEOUT)
            if (!userExists) {
                // Refreshes the data.
                val response = userService.getUser(userId).execute()

                // Check for errors here.

                // Updates the database. The LiveData object automatically
                // refreshes, so we don't need to do anything else here.
                userDao.save(response.body()!!)
            }
        })
    }*/

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}