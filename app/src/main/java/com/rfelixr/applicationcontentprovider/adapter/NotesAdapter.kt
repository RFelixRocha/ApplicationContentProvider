package com.rfelixr.applicationcontentprovider.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rfelixr.applicationcontentprovider.R
import com.rfelixr.applicationcontentprovider.database.NotesDatabasesHelper.Companion.COLUMNS_DESCRIPTION
import com.rfelixr.applicationcontentprovider.database.NotesDatabasesHelper.Companion.COLUMNS_TITLE
import com.rfelixr.applicationcontentprovider.interfaces.NoteClickedListener

class NotesAdapter(private val listener: NoteClickedListener): RecyclerView.Adapter<NotesViewHolder>(){

    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder = NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false))


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        mCursor?.moveToPosition(position)

        holder.noteTile.text = mCursor?.getString(mCursor?.getColumnIndex(COLUMNS_TITLE) as Int)
        holder.noteDescription.text = mCursor?.getString(mCursor?.getColumnIndex(COLUMNS_DESCRIPTION) as Int)

        holder.notesButtonRemove.setOnClickListener{
            mCursor?.moveToPosition(position)
            listener.noteRemoveItem(mCursor as Cursor)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener{
            listener.noteClickedItem(mCursor as Cursor)
        }
    }

    override fun getItemCount(): Int  = if (mCursor != null)mCursor?.count as Int else 0

    fun setCursor(newCursor: Cursor?){
        mCursor = newCursor
        notifyDataSetChanged()
    }

}

class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val noteTile  = itemView.findViewById(R.id.note_title) as TextView
    val noteDescription = itemView.findViewById(R.id.note_description) as TextView
    val notesButtonRemove = itemView.findViewById(R.id.note_button_remove) as Button
}