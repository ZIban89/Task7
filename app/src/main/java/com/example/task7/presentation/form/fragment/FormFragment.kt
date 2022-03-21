package com.example.task7.presentation.form.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.task7.common.appComponent
import com.example.task7.common.loadImage
import com.example.task7.databinding.FragmentFormBinding
import com.example.task7.presentation.UiState
import com.example.task7.presentation.form.fragment.rv.FieldAdapter
import com.example.task7.presentation.form.viewmodel.FormViewModel
import com.example.task7.presentation.form.viewmodel.ViewModelFactory
import com.example.task7.presentation.resultdialog.ResultDialog
import com.example.task7.presentation.resultdialog.ResultDialog.Companion.RESULT_ACTION_KEY
import com.example.task7.presentation.resultdialog.ResultDialog.Companion.RESULT_ACTION_RELOAD
import com.example.task7.presentation.resultdialog.ResultDialog.Companion.RESULT_REQUEST_KEY
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class FormFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FormViewModel by viewModels { factory }
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    private val saveValueCallback: (String, String) -> Unit =
        { id, value -> viewModel.saveValue(id, value) }
    private val getValueCallback = { id: String -> viewModel.getValue(id) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleForm()
        handleResult()

        binding.btnSendForm.setOnClickListener {
            viewModel.submitForm()
        }

        binding.btnRefreshForm.setOnClickListener { viewModel.loadForm() }

        childFragmentManager.setFragmentResultListener(
            RESULT_REQUEST_KEY,
            viewLifecycleOwner
        ) { key, bundle ->
            if (key == RESULT_REQUEST_KEY) {
                if (bundle.getString(RESULT_ACTION_KEY) == RESULT_ACTION_RELOAD) {
                    viewModel.loadForm()
                }
            }
        }
    }

    private fun handleResult() {
        viewModel.resultUiState.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is UiState.Success -> {
                    ResultDialog.getInstance(resultState.data).show(childFragmentManager, "")
                    setViewsVisibility(isProgressBarVisible = false, isFormVisible = true)
                    viewModel.clearResult()
                }
                is UiState.Loading -> {
                    setViewsVisibility(isProgressBarVisible = true, isFormVisible = true)
                }
                is UiState.Error -> {
                    setViewsVisibility(isProgressBarVisible = false, isFormVisible = true)
                    makeSnackbar(resultState.message)
                }
            }
        }
    }

    private fun handleForm() {
        val adapter = FieldAdapter(saveValueCallback, getValueCallback)
        binding.rvForm.adapter = adapter
        viewModel.formUiState.observe(viewLifecycleOwner) { formState ->
            when (formState) {
                is UiState.Success -> {
                    setViewsVisibility()
                    with(formState) {
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                            binding.ivImage.loadImage(
                                data.imageUri
                            )
                        }
                        requireActivity().title = data.title
                        adapter.items = data.fields
                    }
                }
                is UiState.Loading -> {
                    setViewsVisibility(isProgressBarVisible = true, isFormVisible = false)
                }
                is UiState.Error -> {
                    setViewsVisibility(isFormVisible = false, isRefreshBtnVisible = true)
                    makeSnackbar(formState.message)
                }
            }
        }
    }

    private fun makeSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun setViewsVisibility(
        isProgressBarVisible: Boolean = false,
        isFormVisible: Boolean = true,
        isRefreshBtnVisible: Boolean = false
    ) {
        binding.groupForm.visibility = getVisibility(isFormVisible)
        binding.progressCircular.visibility = getVisibility(isProgressBarVisible)
        binding.btnRefreshForm.visibility = getVisibility(isRefreshBtnVisible)
    }

    private fun getVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
