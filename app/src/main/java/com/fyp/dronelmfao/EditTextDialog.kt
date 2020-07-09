package com.fyp.dronelmfao

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class EditTextDialog : DialogFragment() {
    companion object {
        private const val TAG = "EditTextDialog"

        private const val EXTRA_TITLE = "title"
        private const val EXTRA_TITLE_HINT = "hint"
        private const val EXTRA_MULTILINE = "multiline"
        private const val EXTRA_TITLE_TEXT = "text"
        private const val EXTRA_DESCRIPTION_HINT = "description"

        fun newInstance(title: String? = null, isMultiline: Boolean = false, titleText: String? = null, titleHint: String? = null, descriptionHint: String? = null): EditTextDialog {
            val dialog = EditTextDialog()
            val args = Bundle().apply {
                putString(EXTRA_TITLE, title)
                putString(EXTRA_TITLE_HINT, titleHint)
                putString(EXTRA_TITLE_TEXT, titleText)
                putString(EXTRA_DESCRIPTION_HINT, descriptionHint)
                putBoolean(EXTRA_MULTILINE, isMultiline)
            }
            dialog.arguments = args
            return dialog
        }
    }

    lateinit var title: EditText
    lateinit var description: EditText
    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    var onDelete: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(EXTRA_TITLE)
        val titleHint = arguments?.getString(EXTRA_TITLE_HINT)
        val titleText: String? = arguments?.getString(EXTRA_TITLE_TEXT)
        val descriptionHint = arguments?.getString(EXTRA_DESCRIPTION_HINT)
        val isMultiline = arguments?.getBoolean(EXTRA_MULTILINE) ?: false

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_edit_text, null)

        this.title = view.findViewById(R.id.titleText)
        this.title.hint = titleHint

        this.description = view.findViewById(R.id.descriptionText)
        this.description.hint = descriptionHint

        if (!isMultiline)
            this.title.visibility = View.GONE

        if (titleText != null) {
            this.title.append(titleText)
        }

        val builder = AlertDialog.Builder(context!!)
            .setTitle(title)
            .setView(view)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                onOk?.invoke()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                onCancel?.invoke()
            }
            .setNeutralButton("Delete") { _, _ ->
                onDelete?.invoke()
            }
        val dialog = builder.create()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }
}