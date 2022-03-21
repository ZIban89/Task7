package com.example.task7.presentation.resultdialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.task7.R
import com.example.task7.common.exception.ResultDialogInitializationException

class ResultDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val message =
                arguments?.getString(RESULT_MESSAGE_ID)
                    ?: throw ResultDialogInitializationException(
                        getString(R.string.empty_result_dialog_bundle_message)
                    )
            AlertDialog.Builder(it)
                .setMessage(message)
                .setNeutralButton(getString(R.string.close_dialog_btn_label)) { dialog, _ ->
                    sendResultAction(dialog, RESULT_ACTION_CLOSE)
                }
                .setNegativeButton(getString(R.string.reload_dialog_btn_label)) { dialog, _ ->
                    sendResultAction(dialog, RESULT_ACTION_RELOAD)
                }
                .create()
        }
            ?: throw ResultDialogInitializationException(
                getString(R.string.activity_not_found_exception_message)
            )
    }

    private fun sendResultAction(dialog: DialogInterface, action: String) {
        parentFragmentManager.setFragmentResult(
            RESULT_REQUEST_KEY, bundleOf(
                RESULT_ACTION_KEY to action
            )
        )
        dialog.cancel()
    }

    companion object {
        const val RESULT_MESSAGE_ID = "RESULT_MESSAGE_ID"
        const val RESULT_REQUEST_KEY = "RESULT_REQUEST_KEY"
        const val RESULT_ACTION_CLOSE = "RESULT_ACTION_CLOSE"
        const val RESULT_ACTION_RELOAD = "RESULT_ACTION_RELOAD"
        const val RESULT_ACTION_KEY = "RESULT_ACTION_KEY"

        fun getInstance(result: String) =
            ResultDialog().apply {
                arguments = Bundle().apply {
                    putString(
                        RESULT_MESSAGE_ID, result
                    )
                }
            }
    }
}
