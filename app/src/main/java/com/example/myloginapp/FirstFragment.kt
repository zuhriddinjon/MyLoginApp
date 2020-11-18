package com.example.myloginapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*

private const val REQ_CODE = 1001
private const val PASS = "password"

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_user.setOnClickListener { showDialog() }
        tv_pr_name.setOnClickListener { showDialog() }

        btn_login.setOnClickListener {
            if (tv_pr_name.text == "Не выбран") {
                Snackbar.make(view, "пожалуйста, выберите аккаунт!", Snackbar.LENGTH_SHORT)
                    .setAction("выбрать", { showDialog() })
                    .show()
            } else {
                val fragment = LockFragment.newInstance(1234)
                activity?.supportFragmentManager?.beginTransaction()!!
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    private fun showDialog() {
        var list = arrayListOf("Murodbek", "Alisa Elnina", "Jaloliddin Manguberdi", "Ivan")
        val fragment = ItemFragment.newInstance(list)
        fragment.setTargetFragment(this, REQ_CODE)
        fragment.show(parentFragmentManager, ItemFragment.TAG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    val bundle = data?.extras
                    val name = bundle?.getString(NAME)
                    tv_pr_name.text = name
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    /***/
                }
        }
    }
}