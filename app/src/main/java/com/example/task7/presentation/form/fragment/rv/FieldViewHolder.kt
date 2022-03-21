package com.example.task7.presentation.form.fragment.rv

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.task7.databinding.ItemListFieldBinding
import com.example.task7.databinding.ItemTextFieldBinding
import com.example.task7.domain.model.DropdownField
import com.example.task7.domain.model.Field

sealed class FieldViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    /*
    * После принятия запрещенных в РБ веществ я удалил третий вьюхолдер.
    * Теперь TEXT и NUMERIC отображаются одним. Сделано это было из-за того, что в вьюхолдерах
    * дублировались поля field и textWatcher, а выносить в родителя не хотелось, т.к. вьюхолдеру
    * для LIST эти поля не нужны.
    * */
    class EditTextFieldViewHolder(
        private val binding: ItemTextFieldBinding,
        private val saveValueCallback: (String, String) -> Unit,
        private val getValueCallback: (String) -> String
    ) : FieldViewHolder(binding) {

        private var field: Field? = null
        private val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                field?.let { saveValueCallback(it.id, s.toString()) }
            }
        }

        fun bind(item: Field, isNumeric: Boolean) {
            binding.etFieldValue.removeTextChangedListener(textWatcher)
            field = item
            binding.etFieldValue.inputType =
                if (isNumeric) {
                    InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
                } else {
                    InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT
                }

            binding.tvFieldTitle.text = item.title
            binding.etFieldValue.setText(getValueCallback(item.id))
            binding.etFieldValue.addTextChangedListener(textWatcher)
        }
    }

    class SpinnerFieldViewHolder(
        private val binding: ItemListFieldBinding,
        private val saveValueCallback: (String, String) -> Unit,
        private val getValueCallback: (String) -> String
    ) : FieldViewHolder(binding) {

        fun bind(item: DropdownField) {
            binding.tvFieldTitle.text = item.title
            // Создаю новый адаптер, т.к. у разных полей может отличаться набор опций.
            binding.spinnerFieldValue.adapter = CustomSpinnerAdapter(
                binding.root.context, android.R.layout.simple_spinner_item, item.values
            )

            val currentPosition = item.values.map { it.id }.indexOf(getValueCallback(item.id))
            binding.spinnerFieldValue.setSelection(currentPosition)

            binding.spinnerFieldValue.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    saveValueCallback(item.id, item.values[position].id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}
