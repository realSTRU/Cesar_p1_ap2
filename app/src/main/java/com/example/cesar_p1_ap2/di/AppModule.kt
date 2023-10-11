package com.example.cesar_p1_ap2.di

import android.content.Context
import androidx.room.Room
import com.example.cesar_p1_ap2.data.OperationsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun providesOperationsDatabase(@ApplicationContext appContext: Context): OperationsDb =
        Room.databaseBuilder(
            appContext,
            OperationsDb::class.java,
            "Operations.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesOperationDao(db: OperationsDb) = db.operationDao()

}