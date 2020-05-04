package com.fyp.dronelmfao

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_info.*
import java.lang.Exception


class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try{
                    disease.text = "Disease: " + dataSnapshot.child("disease").getValue(String::class.java)
                    solution.text = "Solution: " + dataSnapshot.child("solution").getValue(String::class.java)
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
                Log.d("dataSnapshot", "$dataSnapshot")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("dataSnapshot", "Failed to read value.", error.toException())
            }
        })

        if(!admin.isChecked)
            adminLayout.visibility = View.INVISIBLE

        admin.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                adminLayout.visibility = View.VISIBLE
            else
                adminLayout.visibility = View.INVISIBLE
        }

        update.setOnClickListener {
            if(diseaseEdit.text.isNotEmpty() && solutionEdit.text.isNotEmpty()){
                databaseReference.child("disease").setValue(diseaseEdit.text.toString())
                databaseReference.child("solution").setValue(solutionEdit.text.toString())
                diseaseEdit.setText("")
                solutionEdit.setText("")
            }
            else
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
        }

    }
}
