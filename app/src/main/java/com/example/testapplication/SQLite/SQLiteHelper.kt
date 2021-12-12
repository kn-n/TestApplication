package com.example.testapplication.SQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testapplication.models.Owner
import com.example.testapplication.models.Repository
import com.example.testapplication.models.SavedRepository
import com.example.testapplication.models.User

const val DB_NAME = "UsersSavedRepositories"
const val TABLE_NAME_USERS = "Users"
const val USERS_COL_ID = "Id"
const val TABLE_NAME_SAVED_REPOSITORIES = "SavedRepositories"
const val SAVED_REPOSITORIES_COL_ID = "Id"
const val SAVED_REPOSITORIES_COL_USER_ID = "UserId"
const val SAVED_REPOSITORIES_COL_FULL_NAME = "FullName"
const val SAVED_REPOSITORIES_COL_DESCRIPTION = "Description"
const val SAVED_REPOSITORIES_COL_FORKS = "Forks"
const val SAVED_REPOSITORIES_COL_WATCHERS = "Watchers"
const val SAVED_REPOSITORIES_COL_CREATED_AT = "CreatedAt"
const val SAVED_REPOSITORIES_COL_OWNER = "Owner"
const val SAVED_REPOSITORIES_COL_IMG_URL = "ImgURL"

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUsers = "CREATE TABLE " + TABLE_NAME_USERS + " (" +
                USERS_COL_ID + " VARCHAR(256) PRIMARY KEY)"
        db?.execSQL(createTableUsers)

        val createTableSavedRepositories = "CREATE TABLE " + TABLE_NAME_SAVED_REPOSITORIES + " (" +
                SAVED_REPOSITORIES_COL_ID + " VARCHAR(256) PRIMARY KEY, " +
                SAVED_REPOSITORIES_COL_USER_ID + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_FULL_NAME + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_DESCRIPTION + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_FORKS + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_WATCHERS + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_CREATED_AT + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_OWNER + " VARCHAR(256), " +
                SAVED_REPOSITORIES_COL_IMG_URL + " VARCHAR(256))"
        db?.execSQL(createTableSavedRepositories)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUser(user: User): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(USERS_COL_ID, user.id)
        val success = db.insert(TABLE_NAME_USERS, null, cv)
        db.close()
        Log.d("SQL", "$success insertUser")
        return "$success".toInt() != -1
    }

    fun insertSavedRepository(savedRepository: SavedRepository) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(SAVED_REPOSITORIES_COL_ID, savedRepository.fullName)
        cv.put(SAVED_REPOSITORIES_COL_USER_ID, savedRepository.userId)
        cv.put(SAVED_REPOSITORIES_COL_FULL_NAME, savedRepository.fullName)
        cv.put(SAVED_REPOSITORIES_COL_DESCRIPTION, savedRepository.description)
        cv.put(SAVED_REPOSITORIES_COL_FORKS, savedRepository.forks)
        cv.put(SAVED_REPOSITORIES_COL_WATCHERS, savedRepository.watchers)
        cv.put(SAVED_REPOSITORIES_COL_CREATED_AT, savedRepository.createdAt)
        cv.put(SAVED_REPOSITORIES_COL_OWNER, savedRepository.owner)
        cv.put(SAVED_REPOSITORIES_COL_IMG_URL, savedRepository.imgUrl)
        val success = db.insert(TABLE_NAME_SAVED_REPOSITORIES, null, cv)
        db.close()
        Log.d("SQL", "$success insertSavedRepository")
    }

    @SuppressLint("Recycle", "Range")
    fun getUsersSavedRepositories(id: String): ArrayList<Repository> {
        val list: ArrayList<Repository> = ArrayList()
        val db = this.readableDatabase
        val query =
            "SELECT  * FROM $TABLE_NAME_SAVED_REPOSITORIES WHERE $SAVED_REPOSITORIES_COL_USER_ID = ?"

        val value = arrayOf(id)
        val result = db.rawQuery(query, value)

        if (result.moveToFirst()) {
            do {
                val owner = Owner(result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_OWNER))
                    .toString(), result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_IMG_URL)).toString())

                val rep = Repository(
                    result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_FULL_NAME))
                        .toString(),
                    result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_DESCRIPTION))
                        .toString(),
                    result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_FORKS))
                        .toString(),
                    result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_WATCHERS))
                        .toString(),
                    result.getString(result.getColumnIndex(SAVED_REPOSITORIES_COL_CREATED_AT)),
                    owner
                )
                list.add(rep)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        Log.d("SQL", "${list.size} getUsersSavedRepositories")
        return list
    }

    @SuppressLint("Recycle")
    fun checkRepository(id: String, name: String):Boolean {
        val db = this.readableDatabase
        val values = arrayOf(id, name)
        val query =
            "SELECT  * FROM $TABLE_NAME_SAVED_REPOSITORIES WHERE $SAVED_REPOSITORIES_COL_USER_ID = ? AND $SAVED_REPOSITORIES_COL_FULL_NAME = ?"
        val result = db.rawQuery(query, values)
        val count = result.count
        result.close()
        db.close()
        return count > 0
    }

    fun deleteUsersSavedRepository(id: String, name: String) {
        val db = this.writableDatabase
        val values = arrayOf(id, name)
        val success = db.delete(
            TABLE_NAME_SAVED_REPOSITORIES,
            "$SAVED_REPOSITORIES_COL_USER_ID = ? AND $SAVED_REPOSITORIES_COL_FULL_NAME = ?",
            values
        ).toLong()
        db.close()
        Log.d("SQL", "$success deleteUsersSavedRepository")
    }

    fun deleteAllUsersSavedRepository(id: String) {
        val db = this.writableDatabase
        val value = arrayOf(id)
        val success = db.delete(
            TABLE_NAME_SAVED_REPOSITORIES,
            "$SAVED_REPOSITORIES_COL_USER_ID = ?",
            value
        ).toLong()
        db.close()
        Log.d("SQL", "$success deleteAllUsersSavedRepository")
    }

}