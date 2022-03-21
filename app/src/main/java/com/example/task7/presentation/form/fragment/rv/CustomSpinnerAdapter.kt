package com.example.task7.presentation.form.fragment.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.task7.databinding.ItemSpinnerOptionBinding
import com.example.task7.domain.model.Option

//Можно было просто переопределить toString в Option. Я решил сделать так.
class CustomSpinnerAdapter(
    context: Context,
    resource: Int,
    items: List<Option>
) : ArrayAdapter<Option>(context, resource, items) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(position, convertView, parent)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            if (convertView == null) ItemSpinnerOptionBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            else ItemSpinnerOptionBinding.bind(convertView)
        val item = getItem(position)
        item?.let { binding.textView.text = it.value }
        return binding.root;
    }
}
