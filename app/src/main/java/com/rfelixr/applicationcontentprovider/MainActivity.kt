package com.rfelixr.applicationcontentprovider

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rfelixr.applicationcontentprovider.adapter.NotesAdapter
import com.rfelixr.applicationcontentprovider.database.NotesDatabasesHelper.Companion.COLUMNS_TITLE
import com.rfelixr.applicationcontentprovider.database.NotesProvider.Companion.URI_NOTES
import com.rfelixr.applicationcontentprovider.details.NotesDetailFragment
import com.rfelixr.applicationcontentprovider.interfaces.NoteClickedListener

class MainActivity : AppCompatActivity() , LoaderManager.LoaderCallbacks<Cursor>{

    lateinit var noteRecyclerView: RecyclerView
    lateinit var noteAdd: FloatingActionButton
    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAdd = findViewById(R.id.note_add)
        noteAdd.setOnClickListener{
            NotesDetailFragment().show(supportFragmentManager,"dialog")
        }

        adapter = NotesAdapter(object : NoteClickedListener{
            override fun noteClickedItem(cursor: Cursor) {
                val id = cursor.getLong(cursor.getColumnIndex(_ID))
                val fragment = NotesDetailFragment.newInstance(id)
                fragment.show(supportFragmentManager,"dialog")
            }

            override fun noteRemoveItem(cursor: Cursor) {
                val id = cursor?.getLong(cursor.getColumnIndex(_ID))
                contentResolver.delete(Uri.withAppendedPath(URI_NOTES,id.toString()),null,null)
            }

        })

        adapter.setHasStableIds(true)

        noteRecyclerView = findViewById(R.id.notes_recycle)

        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        noteRecyclerView.adapter = adapter

        LoaderManager.getInstance(this).initLoader(0,null,this)

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(this,URI_NOTES,null,null,null,COLUMNS_TITLE)

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null){
            adapter.setCursor(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.setCursor(null)
    }
}