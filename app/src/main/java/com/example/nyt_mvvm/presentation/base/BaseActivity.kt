package com.example.nyt_mvvm.presentation.base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    protected fun setupToolbar(toolbar: androidx.appcompat.widget.Toolbar, titleIdRes: Int, showBackButton: Boolean = false){
        toolbar.title = getString(titleIdRes)
        setSupportActionBar(toolbar)
        if (showBackButton){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }
}