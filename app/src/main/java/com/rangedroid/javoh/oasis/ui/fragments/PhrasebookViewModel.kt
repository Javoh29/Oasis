package com.rangedroid.javoh.oasis.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.rangedroid.javoh.oasis.data.models.PhbookModel
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class PhrasebookViewModel(): ViewModel() {

    fun listModel(context: Context): List<PhbookModel>{
        val list: ArrayList<PhbookModel> = ArrayList()
        var jsonText = ""
        try {
            val input: InputStream? = context.assets?.open("phrasebook.json")
            val size: Int? = input?.available()
            val buffer = ByteArray(size!!)
            input.read(buffer)
            input.close()
            jsonText = String(buffer, charset("UTF-8"))
        }catch (e: IOException){
        }
        val jsonArray = JSONArray(jsonText)
        for (i in 0 until jsonArray.length()){
            val model: PhbookModel = Gson().fromJson(jsonArray.getString(i), PhbookModel::class.java)
            list.add(model)
        }
        return list
    }
}