package com.nicolepaupa.cartoonmansour.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nicolepaupa.cartoonmansour.entity.Video

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: Video)
    @Query("SELECT * FROM Video WHERE category=:b")
    fun getAllVideo(b:String):LiveData<List<Video>>
}