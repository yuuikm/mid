package com.example.mid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mid.dao.AlarmDAO
import com.example.mid.models.Alarm

@Database(entities = arrayOf(Alarm::class), version = 1)
abstract class AlarmDB : RoomDatabase() {

    abstract fun alarmDAO(): AlarmDAO

}