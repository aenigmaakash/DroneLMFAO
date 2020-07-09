package com.fyp.dronelmfao

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_info.*
import java.lang.Exception


class InfoActivity : AppCompatActivity() {

    private var dataSnapshotList: ArrayList<DataSnapshot> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val databaseReference = FirebaseDatabase.getInstance().reference


        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshotList.clear()
                for (snapshot in dataSnapshot.children){
                    dataSnapshotList.add(snapshot)
                }
                val dataListAdapter = DataListAdapter(this@InfoActivity, R.layout.data_layout, dataSnapshotList)
                dataList.adapter = dataListAdapter
                Log.d("dataSnapshot", "$dataSnapshot")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("dataSnapshot", "Failed to read value.", error.toException())
            }
        })

        if(!admin.isChecked){
            add.visibility = View.GONE
            guideText.visibility = View.GONE
        }

        admin.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                add.visibility = View.VISIBLE
                guideText.visibility = View.VISIBLE
            }
            else{
                add.visibility = View.GONE
                guideText.visibility = View.GONE
            }
        }

        add.setOnClickListener {
            if (admin.isChecked){
                val dialog = EditTextDialog
                    .newInstance("Add", true, null, "Title", "Description")
                dialog.onOk = {
                    if (dialog.title.text.toString() != "" && dialog.description.text.toString() != ""){
                        databaseReference.child(dialog.title.text.toString()).setValue(dialog.description.text.toString())
                    }
                }
                dialog.show(supportFragmentManager, "AddTextDialog")
            }
        }

        dataList.setOnItemClickListener { parent, view, position, id ->
            if(admin.isChecked){
                val title = view.findViewById<TextView>(R.id.title).text
                val ftitle = title.subSequence(0, title.lastIndex)
                val description = view.findViewById<TextView>(R.id.description).text
                try {
                    val dialog = EditTextDialog
                        .newInstance("Edit", false, ftitle.toString(), null, description.toString())
                    dialog.onOk = {
                        if(dialog.description.text.toString() != "")
                            databaseReference.child(ftitle.toString()).setValue(dialog.description.text.toString())
                    }
                    dialog.onDelete = {
                        databaseReference.child(ftitle.toString()).setValue(null)
                    }
                    dialog.show(supportFragmentManager, "editTextDialog")
                } catch (e: Exception){
                    e.printStackTrace()
                }

            }
        }

    }
}
