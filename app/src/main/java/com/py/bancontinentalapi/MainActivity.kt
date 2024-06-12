package com.py.bancontinentalapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.py.contiappapi.Api

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val api = Api("https://pokeapi.co/api/v2")
        api.get("/pokemon/ditto")
    }
}