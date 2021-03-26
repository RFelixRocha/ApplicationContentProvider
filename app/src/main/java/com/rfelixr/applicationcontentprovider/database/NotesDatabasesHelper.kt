package com.rfelixr.applicationcontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotesDatabasesHelper (context: Context):SQLiteOpenHelper(context, NAME_BANCO, null, VERSAO_ATUAL){

    companion object{
        private val NAME_BANCO = "databaseNotes.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "Notes"

    val COLUMNS_TITLE = "title"
    val COLUMNS_DESCRIPTION = "description"

    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$_ID INTEGER NOT NULL PRIMARY KEY," +
            "$COLUMNS_TITLE TEXT NOT NULL," +
            "$COLUMNS_DESCRIPTION TEXT NOT NULL)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if (oldVersion != newVersion){
            db?.execSQL(DROP_TABLE)
        }

        onCreate(db)
    }

}