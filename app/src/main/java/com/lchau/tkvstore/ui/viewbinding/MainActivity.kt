package com.lchau.tkvstore.ui.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lchau.tkvstore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }

}