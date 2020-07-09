package com.fyp.dronelmfao

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.database.DataSnapshot

class DataListAdapter(private val context: Context, val resourceId: Int, private val snapshotList: ArrayList<DataSnapshot>):BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(resourceId, parent, false)
        val snapshot = snapshotList[position]
        if(snapshot!=null){
            val title = view.findViewById<TextView>(R.id.title)
            val description = view.findViewById<TextView>(R.id.description)
            title.text = snapshot.key.toString() + ":"
            description.text = snapshot.value.toString()
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return snapshotList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return snapshotList.size
    }

}