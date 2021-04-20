package no.kristiania.coinhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import no.kristiania.coinhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}