package br.com.chicorialabs.astranovos.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.chicorialabs.astranovos.R

/**
 * A MainActivity serve essencialmente para hospedar os
 * fragmentos.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}