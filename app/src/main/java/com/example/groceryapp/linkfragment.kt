package com.example.groceryapp

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class linkfragment :BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters


   lateinit var title:TextView
   lateinit var linktext:TextView
   lateinit var copy: Button
   lateinit var sharebutton:Button
   var link:String=""

    @SuppressLint("ServiceCast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_linkfragment, container, false)
        var bundle=Bundle()
        bundle=requireArguments()
         link=bundle.get("link").toString()

        title=view.findViewById(R.id.textView24)
        linktext=view.findViewById(R.id.linktext)
        linktext.text=link.toString()
        copy=view.findViewById(R.id.button8)
        copy.setOnClickListener {
val clipboard=activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val mydata:ClipData=ClipData.newPlainText("text",link)
            clipboard.setPrimaryClip(mydata)

        }
        sharebutton=view.findViewById(R.id.sharelink)
        sharebutton.setOnClickListener {
            val mydata = "hi please bring these grocery ".toString()


            val action = Intent()
            action.action = Intent.ACTION_SEND
            action.putExtra(Intent.EXTRA_SUBJECT, mydata)
            action.putExtra(Intent.EXTRA_TEXT, link.toString())
            action.type = "text/plain"

          startActivity(action)

        }
        return view
    }


}