package com.rangedroid.javoh.oasis.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.rangedroid.javoh.oasis.data.models.ForTouristsModel
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class ForTouristsViewModel : ViewModel() {

    fun listForModel(context: Context): List<ForTouristsModel>{
        val list: ArrayList<ForTouristsModel> = ArrayList()
        var jsonText = ""
        try {
            val input: InputStream? = context.assets?.open("for_tourist.json")
            val size: Int? = input?.available()
            val buffer = ByteArray(size!!)
            input.read(buffer)
            input.close()
            jsonText = String(buffer, charset("UTF-8"))
            val jsonArray = JSONArray(jsonText)
            for (i in 0 until jsonArray.length()){
                val model: ForTouristsModel = Gson().fromJson(jsonArray.getString(i), ForTouristsModel::class.java)
                list.add(model)
            }
        }catch (e: IOException){}

        return list
    }
}