package com.example.myloginapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lock.*

private const val MAX_LENGHT = 4

class LockFragment : Fragment() {

    private var TRUE_CODE = ""

    private var pass: Int? = null
    var dots: List<ImageView>? = null
    var btns: List<AppCompatButton>? = null
    private var codeString: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pass = it.getInt(ARG_PASS)
            TRUE_CODE = pass.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dots = listOf(dot_1, dot_2, dot_3, dot_4)
        btns = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)

        btn_clear.setOnClickListener { onClear() }

        btns!!.all { btn ->
            btn.setOnClickListener { onClick(btn) }
            return@all true
        }
    }

    private fun onClear() {
        if (codeString!!.isNotEmpty()) {
            //remove last character of code
            codeString = removeLastChar(codeString)

            //update dots layout
            setDotImagesState()
        }
    }

    private fun onClick(button: AppCompatButton) {
        getStringCode(button.id)
        if (codeString!!.length == MAX_LENGHT) {
            if (codeString == TRUE_CODE) {
                Toast.makeText(requireContext(), "Code is right", Toast.LENGTH_SHORT).show()
                setIsPass()
//                finish()
                activity?.supportFragmentManager?.beginTransaction()!!
                    .replace(R.id.frame_layout, BlankFragment())
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Wrong Pass code", Toast.LENGTH_SHORT).show()
                //vibrate the dots layout
                shakeAnimation()
            }
        } else if (codeString!!.length > MAX_LENGHT) {
            //reset the input code
            codeString = ""
            getStringCode(button.id)
        }
        setDotImagesState()
    }

    private fun shakeAnimation() {
        val shake: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_anim)
        dot_layout.startAnimation(shake)
        Toast.makeText(requireContext(), "Wrong Password", Toast.LENGTH_SHORT).show()
    }

    private fun getStringCode(buttonId: Int) {
        when (buttonId) {
            R.id.btn0 -> codeString += "0"
            R.id.btn1 -> codeString += "1"
            R.id.btn2 -> codeString += "2"
            R.id.btn3 -> codeString += "3"
            R.id.btn4 -> codeString += "4"
            R.id.btn5 -> codeString += "5"
            R.id.btn6 -> codeString += "6"
            R.id.btn7 -> codeString += "7"
            R.id.btn8 -> codeString += "8"
            R.id.btn9 -> codeString += "9"
            else -> {
            }
        }
    }

    private fun setDotImagesState() {
        for (i in 0 until codeString!!.length) {
            dots!![i].setImageResource(R.drawable.ic_enable)
        }
        if (codeString!!.length < 4) {
            for (j in codeString!!.length..3) {
                dots!![j].setImageResource(R.drawable.ic_disable)
            }
        }
    }

    private fun removeLastChar(s: String?): String? {
        return if (s == null || s.isEmpty()) {
            s
        } else s.substring(0, s.length - 1)
    }

    private fun setIsPass() {
        val editor =
            activity?.getSharedPreferences("PASS_CODE", AppCompatActivity.MODE_PRIVATE)?.edit()
        editor?.putBoolean("is_pass", true)
        editor?.apply()
    }


    companion object {
        const val TAG = "LockFragment"

        const val ARG_PASS = "pass"

        @JvmStatic
        fun newInstance(pass: Int) =
            LockFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PASS, pass)
                }
            }
    }

}