package com.example.mid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mid.R
import com.example.mid.models.Alarm

class AlarmAdapter (alarms: MutableList<Alarm>) : RecyclerView.Adapter<AlarmAdapter.AlarmsViewHolder>() {
    var alarms: MutableList<Alarm> = alarms
        set(value) {
            field = value;
            notifyDataSetChanged()
        }

    interface OnHabilitarAlarmaListener {
        fun onTypeAlarm(alarma: Alarm, posicion: Int)
    }

    interface OnLongClickAlarmaListener {
        fun onLongClickAlarma(alarma: Alarm, posicion: Int)
    }

    var onTypeAlarmListener: OnHabilitarAlarmaListener? = null
    var onLongClickAlarmaListener: OnLongClickAlarmaListener? = null

    inner class AlarmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtHora: TextView = itemView.findViewById(R.id.item_txt_hour)
        val txtAmPm: TextView = itemView.findViewById(R.id.item_txt_am_pm)
        val switchOnOff: Switch = itemView.findViewById(R.id.item_switch)

        init {
            switchOnOff.setOnCheckedChangeListener {_, isChecked ->

                alarms[adapterPosition].type = isChecked

                onTypeAlarmListener?.onTypeAlarm(alarms[adapterPosition], adapterPosition)

            }

            itemView.setOnLongClickListener {_ ->

                onLongClickAlarmaListener?.onLongClickAlarma(alarms[adapterPosition], adapterPosition)

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmsViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm, parent, false)

        return AlarmsViewHolder(item)
    }

    override fun onBindViewHolder(holder: AlarmsViewHolder, position: Int) {
        val listenerAux = onTypeAlarmListener;
        onTypeAlarmListener = null

        val alarma = alarms[position];

        holder.txtHora.text = alarma.hoursFormat;
        holder.txtAmPm.text = alarma.amPm
        holder.switchOnOff.isChecked = alarm.type

        onTypeAlarmListener = listenerAux
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun addAlarm(alarm: Alarm) {
        alarms.add(alarm)
        notifyItemInserted( alarms.size - 1 )
    }

    fun deleteAlarm(alarm: Alarm) {
        val indice = alarms.indexOf(alarm)

        alarms.remove(alarm)

        notifyItemRemoved(indice)
    }
}