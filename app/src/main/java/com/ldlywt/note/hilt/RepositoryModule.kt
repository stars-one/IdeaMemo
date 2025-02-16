package com.ldlywt.note.hilt

import com.ldlywt.note.db.AppDatabase
import com.ldlywt.note.db.repo.TagNoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        appDatabase: AppDatabase,
    ) = TagNoteRepo(appDatabase.getNoteDao(), appDatabase.getTagNote(), appDatabase.getTagDao(), appDatabase.getNoteTagCrossRefDao())
}