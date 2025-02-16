package com.ldlywt.note.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ldlywt.note.bean.NoteTagCrossRef

@Dao
interface NoteTagCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNoteTagCrossRef(entity: NoteTagCrossRef)

    @Delete
    fun deleteCrossRef(entity: NoteTagCrossRef)

}