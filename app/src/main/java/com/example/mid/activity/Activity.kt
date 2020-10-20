package com.example.mid.activity

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mid.R
import com.example.mid.adapters.AlarmAdapter
import com.example.mid.db.AlarmDB
import com.example.mid.models.Alarm
import com.example.mid.utils.AlarmUtils
import java.util.*

const val COD_CREAR_ALARMA = 354

class MainActivity : AppCompatActivity(), AlarmAdapter.OnHabilitarAlarmaListener,
    AlarmAdapter.OnLongClickAlarmaListener {

    var contNoHayAlarms: LinearLayout? = null
    var recyclerAlarms: RecyclerView? = null

    var alarmAdapter: AlarmAdapter? = null

    var db: AlarmDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        enlazarXML();

        db = Room.databaseBuilder(
            applicationContext,
            AlarmDB::class.java,
            getString(R.string.db_name)
        ).build()

        Thread {
            val alarms = db?.alarmDAO()?.getTodas() ?: mutableListOf()

            runOnUiThread {
                alarmsAdapter?.alarms = alarms
                toggleVisibilidadTxtNoHayAlarms()
            }

        }.start()
    }

    private fun enlazarXML() {
        contNoHayAlarms = findViewById(R.id.cont_no_hay_alarms)
        recyclerAlarms = findViewById(R.id.recycler_alarms)

        val lmAlarmas = LinearLayoutManager(this)
        recyclerAlarms?.layoutManager = lmAlarmas

        alarmAdapter = AlarmAdapter(mutableListOf())
        alarmAdapter?.onTypeAlarmListener = this
        alarmAdapter?.onLongClickAlarmaListener = this
        recyclerAlarms?.adapter = alarmAdapter

    }

    private fun toggleVisibilidadTxtNoHayAlarmas() {
        alarmAdapter?.let {
            contNoHayAlarms?.visibility = if ( it.itemCount > 0 ) View.GONE else View.VISIBLE
        }
    }

    fun clickAddAlarm(btn: View) {

        startActivityForResult(
            Intent(this, AlarmActivity::class.java),
            COD_CREAR_ALARMA
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                COD_CREAR_ALARMA ->{

                    val hour = data?.getIntExtra(AlarmActivity.Args.TIME, 0 ) ?: 0
                    val minutes = data?.getIntExtra(AlarmActivity.Args.MINUTES, 0 ) ?: 0

                    val alarm = Alarm(hour, minutes, type = false)

                    Thread {

                        val id = db?.alarmDAO()?.insert(alarm) ?: 0
                        Log.v("TEST", "Inserted: &${ id }")

                        alarm.id = id

                        runOnUiThread {

                            alarmAdapter?.addAlarm(alarm)

                            toggleVisibilidadTxtNoHayAlarms()
                        }

                    }.start()
                }
            }

        }
    }

    override fun onHabilitarAlarma(alarma: Alarm, posicion: Int) {

        Thread {

            db?.alarmDAO()?.actualizarHabilitada(alarma.id, alarma.type)

            runOnUiThread {

                if (alarma.type) {

                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, alarma.hours)
                    calendar.set(Calendar.MINUTE, alarma.mins)
                    calendar.set(Calendar.SECOND, 0)

                    AlarmUtils.habilitarAlarm(this, calendar, alarma.horaFormateada, alarma.id.toInt())

                } else {
                    AlarmUtils.deshabilidatAlarma(this, alarma.id.toInt())
                }
            }
        }.start()
    }

    override fun onLongClickAlarma(alarma: Alarm, posicion: Int) {

        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Seguro que quieres eliminar la alarma de ${ alarms.hourFormat }${ alarms.amPm }?")
            .setPositiveButton("SI") {_, _ ->

                Thread {

                    db?.alarmDAO()?.delete(alarm)

                    runOnUiThread {

                        alarmAdapter?.eliminarAlarma(alarm)

                        toggleVisibilidadTxtNoHayAlarmas()

                    }
                }.start()

            }
            .setNegativeButton("NO", null)
            .create()

        if ( !isFinishing )
            dialog.show()
    }

}
