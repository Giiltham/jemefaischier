package io.fairflix.jemefaischier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.fairflix.jemefaischier.databinding.MainActivityBinding
import io.fairflix.jemefaischier.viewmodels.activities.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding : MainActivityBinding
    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
