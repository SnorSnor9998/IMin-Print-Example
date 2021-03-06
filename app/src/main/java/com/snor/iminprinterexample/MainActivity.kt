package com.snor.iminprinterexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.viewbinding.library.activity.viewBinding
import com.imin.printerlib.IminPrintUtils
import com.snor.iminprinterexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by viewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val print = PrintUtil(this)

        binding.btnPrint.setOnClickListener {
            print.printExample()
        }


    }


}