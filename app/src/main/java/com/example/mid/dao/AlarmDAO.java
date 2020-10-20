package com.example.mid.dao;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mid.models.Alarm;

@Dao
public interface AlarmDAO {
    @Insert
    fun insert(alarm:Alarm): Long

    @Query("SELECT * FROM alarms")
    fun getAll(): MutableList<Alarm>

    @Query("UPDATE alarms SET type = :type WHERE id = :idAlarm")
    fun actualType(idAlarm: Long, type: Boolean)

    @Delete
    fun delete(alarm: Alarm)
}
