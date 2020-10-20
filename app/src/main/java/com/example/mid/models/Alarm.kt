package com.example.mid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.math.min

@Entity (tableName = "alarms")
class Alarm (
    var hours: Int,
    var mins: Int,
    var type: Boolean
    ) {

        @PrimaryKey (autoGenerate = true)
        var id: Long = 0
        var amPm: String = "AM"
        var hoursFormat: String = ""

        init {
            refHour(hours, mins)
        }

        private fun refHour(hoursInt: Int, minInt: Int) {

            var newMins: String = minInt.toString()

            if ( minInt < 10 ) newMins = "0" + minInt

            if ( hoursInt > 12) {
                val hours12 = hoursInt - 12

                var newHour: String = hours12.toString()

                if ( hours12 < 10 )
                    newHour = "0" + hours12

                hoursFormat = "${ newHour }:${ newMins }"
                amPm = "PM"

            } else {
                var newHour: String = hoursInt.toString()

                if ( hoursInt < 10 )
                    newHour = "0" + hoursInt

                hoursFormat = "${ newHour }:${ newMins }"
                amPm = "AM"
            }
        }

    }