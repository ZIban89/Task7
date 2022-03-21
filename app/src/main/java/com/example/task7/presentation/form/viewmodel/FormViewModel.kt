package com.example.task7.presentation.form.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task7.domain.Resource
import com.example.task7.domain.model.ClevertecForm
import com.example.task7.domain.model.DropdownField
import com.example.task7.domain.model.Field
import com.example.task7.domain.usecase.GetFormUseCase
import com.example.task7.domain.usecase.SendFormUseCase
import com.example.task7.presentation.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FormViewModel @Inject constructor(
    val getFormUseCase: GetFormUseCase,
    val sendFormUseCase: SendFormUseCase
) : ViewModel() {

    private val _formUiState: MutableLiveData<UiState<ClevertecForm>> = MutableLiveData()
    val formUiState: LiveData<UiState<ClevertecForm>> = _formUiState

    private val _resultUiState: MutableLiveData<UiState<String>> = MutableLiveData()
    val resultUiState: LiveData<UiState<String>> = _resultUiState

    private val formValues = hashMapOf<String, String>()

    init {
        loadForm()
    }

    fun loadForm() {
        _resultUiState
        viewModelScope.launch(Dispatchers.IO) {
            getFormUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _formUiState.postValue(UiState.Loading)
                    }
                    is Resource.Success -> {
                        resource.data?.let { form ->
                            formValues.clear()
                            form.fields.forEach {
                                if (it is DropdownField) saveValue(it.id, it.values[0].id)
                                else saveValue(it.id, "")
                            }
                            _formUiState.postValue(UiState.Success(form))
                        }
                    }
                    is Resource.Error -> {
                        _formUiState.postValue(UiState.Error(resource.message))
                    }
                }
            }
        }
    }

    fun submitForm() {
        viewModelScope.launch {
            sendFormUseCase(formValues).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _resultUiState.postValue(UiState.Loading)
                    }
                    is Resource.Error -> {
                        _resultUiState.postValue(UiState.Error(resource.message))
                    }
                    is Resource.Success -> {
                        resource.data?.let { _resultUiState.postValue(UiState.Success(it)) }
                    }
                }
            }
        }
    }

    fun saveValue(fieldId: String, value: String) = formValues.put(fieldId, value)

    // Можно было вернуть null, а во viewHolder его обработать и засетать
    // дефолтное значение, или пустую строку, но, я очень люблю ексепшены :)
    fun getValue(fieldId: String) = formValues[fieldId] ?: throw IllegalStateException()

    fun clearResult() {
        _resultUiState.value = UiState.Idle
    }

}
