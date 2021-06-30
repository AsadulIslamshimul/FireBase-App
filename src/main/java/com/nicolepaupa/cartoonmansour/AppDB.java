package com.nicolepaupa.cartoonmansour;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nicolepaupa.cartoonmansour.dao.VideoDao;
import com.nicolepaupa.cartoonmansour.entity.Video;

import org.jetbrains.annotations.NotNull;


@Database(
        entities = {Video.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDB extends RoomDatabase {
    @NotNull
    public abstract VideoDao pigeonDao();
}
