package com.vitaz.moviesleuthhound.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.vitaz.moviesleuthhound.databinding.ActivityMainBinding
import com.vitaz.moviesleuthhound.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val  viewModel : MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { value: MovieViewModel.State ->
                when(value){
                    is MovieViewModel.State.Success ->{
                        binding.progressBar.visibility = View.GONE
                        binding.titleName.text = (value.movie.title + value.movie.released)
                    }
                    is MovieViewModel.State.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.titleName.text = (value.errorText)
                    }
                    is MovieViewModel.State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MovieViewModel.State.Empty -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        binding.showDialogBtn.setOnClickListener {
            SearchDialog.showDialog(supportFragmentManager, viewModel)
        }

    }
}
