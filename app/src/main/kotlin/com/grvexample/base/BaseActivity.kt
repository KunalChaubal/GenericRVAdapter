package com.grvexample.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 *  Base Activity for the entire app
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     *  Provides layout id to be inflated
     */
    @LayoutRes
    abstract fun layoutId(): Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        //Uncomment this line to have font enabled textviews
        //LayoutInflaterCompat.setFactory2(layoutInflater, LillyLayoutInflater(delegate))
        super.onCreate(savedInstanceState)
        title = ""
        setContentView(layoutId())
    }
}