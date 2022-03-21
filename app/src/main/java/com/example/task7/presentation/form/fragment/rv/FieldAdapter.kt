package com.example.task7.presentation.form.fragment.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task7.R
import com.example.task7.common.exception.UnknownViewTypeException
import com.example.task7.databinding.ItemListFieldBinding
import com.example.task7.databinding.ItemTextFieldBinding
import com.example.task7.domain.model.DropdownField
import com.example.task7.domain.model.Field
import com.example.task7.domain.model.FieldType

class FieldAdapter(
    private val saveValueCallback: (String, String) -> Unit,
    private val getValueCallback: (String) -> String
) :
    RecyclerView.Adapter<FieldViewHolder>() {

    var items: List<Field> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            FieldType.LIST -> {
                R.layout.item_list_field
            }
            else -> {
                R.layout.item_text_field
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        return when (viewType) {
            R.layout.item_text_field -> {
                FieldViewHolder.EditTextFieldViewHolder(
                    ItemTextFieldBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    saveValueCallback,
                    getValueCallback
                )
            }
            R.layout.item_list_field -> {
                FieldViewHolder.SpinnerFieldViewHolder(
                    ItemListFieldBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    saveValueCallback,
                    getValueCallback
                )
            }
            else -> throw UnknownViewTypeException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        when (holder) {
            is FieldViewHolder.EditTextFieldViewHolder -> {

                holder.bind(
                    items[position],
                    items[position].type == FieldType.NUMERIC
                )
            }
            is FieldViewHolder.SpinnerFieldViewHolder -> {
                holder.bind(items[position] as DropdownField)
            }
        }
    }

    override fun getItemCount() = items.size
}
