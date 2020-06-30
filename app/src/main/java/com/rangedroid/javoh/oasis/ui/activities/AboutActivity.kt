package com.rangedroid.javoh.oasis.ui.activities

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.rangedroid.javoh.oasis.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.ThemeAbout)
        setContentView(R.layout.activity_about)

        val anim: Animation = AnimationUtils.makeInAnimation(this,true)
        anim.duration = 2000

        img_line_cer.startAnimation(anim)
        img_line_mail.startAnimation(anim)
        img_line_telegram.startAnimation(anim)
    }

//    override fun attachBaseContext(newBase: Context?) {
//        val locale: Locale = if (localChange == null) {
//            Locale.getDefault()
//        }else{
//            localChange!!
//        }
//        val context: Context = ContextWrapper.wrap(newBase!!, locale)
//        super.attachBaseContext(context)
//    }
}