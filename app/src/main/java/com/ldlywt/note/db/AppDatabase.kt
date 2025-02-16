package com.ldlywt.note.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ldlywt.note.bean.*
import com.ldlywt.note.db.dao.NoteDao
import com.ldlywt.note.db.dao.NoteTagCrossRefDao
import com.ldlywt.note.db.dao.TagDao
import com.ldlywt.note.db.dao.TagNoteDao

@Database(
    entities = [
        Note::class,
        Tag::class,
        NoteTagCrossRef::class,
        Comment::class,
        Reminder::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    //创建DAO的抽象类
    abstract fun getNoteDao(): NoteDao
    abstract fun getTagDao(): TagDao
    abstract fun getTagNote(): TagNoteDao
    abstract fun getNoteTagCrossRefDao(): NoteTagCrossRefDao
}