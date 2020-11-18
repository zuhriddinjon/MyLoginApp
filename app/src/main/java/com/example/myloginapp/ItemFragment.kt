package com.example.myloginapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myloginapp.adapter.MyItemRecyclerViewAdapter
import com.example.myloginapp.adapter.itemClick.ItemClickListener
import com.example.myloginapp.adapter.itemClick.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_item_list.view.*


const val NAME = "name"

class ItemFragment : DialogFragment() {

    private var list: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            list = it.getStringArrayList(ARG_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view.rv is RecyclerView) {
            with(view.rv) {
//                setBackgroundResource(R.drawable.shape_aval2)
                adapter = MyItemRecyclerViewAdapter(list?: arrayListOf())
            }
        }
        // Set transparent background and no title
        if (dialog != null && dialog?.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemClicked()
    }

    fun itemClicked(): String {
        var name = "Выберите профиль"
        rv.addOnItemClickListener(object : ItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                name = list!![position]
                val i: Intent = Intent()
                    .putExtra(NAME, name)
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, i)
                dialog?.dismiss()
            }
        })
        return name
    }


    companion object {
        const val TAG = "ItemFragment"

        const val ARG_LIST = "column-count"

        @JvmStatic
        fun newInstance(list: ArrayList<String>) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_LIST, list)
                }
            }
    }
}