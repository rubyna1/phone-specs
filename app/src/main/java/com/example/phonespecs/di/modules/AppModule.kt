package com.example.phonespecs.di.modules

import android.content.Context
import androidx.room.Room
import com.example.phonespecs.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "phoneSpecsData")
            .fallbackToDestructiveMigration().build()
}