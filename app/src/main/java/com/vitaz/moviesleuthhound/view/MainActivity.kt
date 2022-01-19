package com.vitaz.moviesleuthhound.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.vitaz.moviesleuthhound.databinding.ActivityMainBinding
import com.vitaz.moviesleuthhound.misc.FrescoLoadingHelper
import com.vitaz.moviesleuthhound.model.Movie
import com.vitaz.moviesleuthhound.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val  viewModel : MovieViewModel by viewModels()

    private lateinit var loadingHelper: FrescoLoadingHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadingHelper = FrescoLoadingHelper(applicationContext)

        observeViewModel()
        bindButtons()
        showSearchDialog()
    }

    private fun bindButtons() {
        binding.showDialogBtn.setOnClickListener {
            showSearchDialog()
        }

        binding.buttonDoingNothing.setOnClickListener {
            Toast.makeText(this, "This is a button doing nothing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { value: MovieViewModel.State ->
                when(value){
                    is MovieViewModel.State.Success ->{
                        binding.progressBar.visibility = View.GONE
                        binding.movieHolder.visibility = View.VISIBLE
                        binding.messageHolder.visibility = View.GONE
                        bindNewMovie(value.movie)
                    }
                    is MovieViewModel.State.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.movieHolder.visibility = View.GONE
                        binding.messageHolder.visibility = View.VISIBLE
                        binding.movie = Movie(
                            response = false,
                            error = value.errorText,
                            title = null,
                            released = null,
                            poster = null,
                            genre = null,
                            plot = null
                        )
                    }
                    is MovieViewModel.State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.movieHolder.visibility = View.GONE
                        binding.messageHolder.visibility = View.GONE
                    }
                    is MovieViewModel.State.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        binding.movieHolder.visibility = View.GONE
                        binding.messageHolder.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun showSearchDialog() {
        SearchDialog.showDialog(supportFragmentManager, viewModel)
    }

    private fun bindNewMovie (movie: Movie) {
        if (movie.poster == "N/A") {
            binding.moviePoster.visibility = View.GONE
        } else {
            binding.moviePoster.visibility = View.VISIBLE
            val uri = Uri.parse(movie.poster)
            loadingHelper.setUri(binding.moviePoster, uri, false)
            binding.moviePoster.hierarchy.setProgressBarImage(loadingHelper.getFrescoProgressBarLoadable())
        }
        binding.movie = movie
    }
}
