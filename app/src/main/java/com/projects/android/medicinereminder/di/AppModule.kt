package com.projects.android.medicinereminder.di

import android.content.Context
import androidx.room.Room
import com.projects.android.medicinereminder.database.MedicineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideYourDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, MedicineDatabase::class.java, "medicine_db.db")
            .build()

    @Singleton
    @Provides
    fun provideYourDao(db: MedicineDatabase) = db.getMedicineDao()
}