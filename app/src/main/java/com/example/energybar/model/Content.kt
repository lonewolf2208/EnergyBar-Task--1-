package com.example.energybar.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_table")
data class Content(@PrimaryKey() val start:Int, var End:Int,var color:String)
