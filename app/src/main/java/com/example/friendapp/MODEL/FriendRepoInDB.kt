package com.example.friendapp.MODEL

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import java.util.concurrent.Executors

class FriendRepoInDB {

    private val database: FriendDatabase

    private val personDao : FriendDAO

    private lateinit var cache: List<BEFriend>

    private constructor(context: Context) {

        database = Room.databaseBuilder(context.applicationContext,
            FriendDatabase::class.java,
            "person-database").fallbackToDestructiveMigration().build()

        personDao = database.personDao()

        val updateCacheObserver = Observer<List<BEFriend>>{ persons ->
            cache = persons;

        }
        getAllLiveData().observe(context as LifecycleOwner, updateCacheObserver)
    }

    fun getAllLiveData(): LiveData<List<BEFriend>> = personDao.getAll()


    fun getById(id: Int): BEFriend? {
        cache.forEach { p -> if (p.id == id) return p; }
        return null;
    }

    fun getByPos(pos: Int): BEFriend? {
        if (pos < cache.size)
            return cache[pos]
        return null
    }
    fun filter(text: String): List<BEFriend>?{
        cache.forEach { p -> if (p.name.contains(text)) return p as List<BEFriend> ; }
        return null;
    }


    private val executor = Executors.newSingleThreadExecutor()

    fun insert(p: BEFriend) {
        executor.execute{ personDao.insert(p) }
    }

    fun update(p: BEFriend) {
        executor.execute { personDao.update(p) }
    }

    fun delete(p: BEFriend) {
        executor.execute { personDao.delete(p) }
    }

    fun clear() {
        executor.execute { personDao.deleteAll() }
    }

    fun addPicPath(id: Int, picPath: String) {
        executor.execute { personDao.addPicPath(id, picPath)}
    }


    companion object {
        private var Instance: FriendRepoInDB? = null

        fun initialize(context: Context) {
            if (Instance == null)
                Instance = FriendRepoInDB(context)
        }

        fun get(): FriendRepoInDB {
            if (Instance != null) return Instance!!
            throw IllegalStateException("Person repo not initialized")
        }
    }

}