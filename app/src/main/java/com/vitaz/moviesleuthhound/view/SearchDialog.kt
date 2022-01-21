package com.vitaz.moviesleuthhound.view

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vitaz.moviesleuthhound.R
import com.vitaz.moviesleuthhound.databinding.SearchDialogBinding
import com.vitaz.moviesleuthhound.viewmodel.MovieViewModel
import com.vitaz.moviesleuthhound.viewmodel.obtainParentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchDialog(
): BottomSheetDialogFragment() {

    private lateinit var searchDialogBinding: SearchDialogBinding
    private val viewModel: MovieViewModel by lazy {
        obtainParentViewModel(requireActivity(), MovieViewModel::class.java, defaultViewModelProviderFactory)
    }

    companion object {
        fun showDialog(
            parentFragmentManager: FragmentManager,
        ) {
            val fragmentA: Fragment? = parentFragmentManager.findFragmentByTag("SearchDialog")
            if (fragmentA == null) {
                val bottomDialogFragment = newInstance()
                parentFragmentManager.beginTransaction()
                bottomDialogFragment.show(parentFragmentManager, "SearchDialog")
            }
        }

        private fun newInstance(): SearchDialog {
            return SearchDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchDialogBinding = SearchDialogBinding.inflate(layoutInflater)
        val view = searchDialogBinding.root
        bindTextViews()
        bindButton()
        return view
    }

    private fun bindTextViews() {
        searchDialogBinding.movieNameField.editText?.setText(viewModel.movieName.value)
        searchDialogBinding.movieYearField.editText?.setText(viewModel.movieYear.value)

        searchDialogBinding.movieNameField.editText?.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setNewMovieName(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {}
        } )

        searchDialogBinding.movieYearField.editText?.addTextChangedListener ( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setNewMovieYear(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {}
        } )
    }

    private fun bindButton() {
        searchDialogBinding.searchBtn.setOnClickListener {
            if (viewModel.movieName.value == "") {
                searchDialogBinding.movieNameField.error = getString(R.string.enter_movie_name)
                searchDialogBinding.movieNameField.requestFocus()
            } else {
                viewModel.getMovie()
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }
}
