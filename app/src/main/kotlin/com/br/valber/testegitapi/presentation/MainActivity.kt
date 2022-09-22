package com.br.valber.testegitapi.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.br.valber.testegitapi.R
import com.br.valber.testegitapi.di.injectProjectMainModule
import com.br.valber.testegitapi.presentation.state.JavaRepoState
import com.br.valber.testegitapi.presentation.viewmodel.JavaRepoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    init {
        injectProjectMainModule()
    }

    private val viewModel: JavaRepoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.run {
            state.observe(this@MainActivity, Observer { state ->
                when(state){
                    is JavaRepoState.ShowJavaRepo -> {
                        Log.d("## DOWNLOAD: ", state.javaRepo.toString())
                        Toast.makeText(this@MainActivity, state.javaRepo.toString(), Toast.LENGTH_LONG).show()
                    }
                    is JavaRepoState.ShowError -> {
                        Log.d("## ERROR DOWNLOAD: ", state.message)
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                    else -> return@Observer
                }
            })
            fetchJavaRepo()
        }
    }
}