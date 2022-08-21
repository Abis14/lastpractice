package com.example.groceryapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_addtolist.*
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 * Use the [addtolist.newInstance] factory method to
 * create an instance of this fragment.
 */
class groceryadd : BottomSheetDialogFragment() {

    lateinit var spin:TextView
    lateinit var item:EditText
    var selected=""
    lateinit var spin2:TextView
    var assinged=""
    var category=""
    var selecteds=""
lateinit var categoryconstriant:ConstraintLayout
lateinit var assingedconstriant:ConstraintLayout
    var isassinged:Boolean=false
    lateinit var spincancel:ImageView
    lateinit var spin2cancel:ImageView
    lateinit var spinnerad:spinneradapter
    lateinit var categorylist:ArrayList<String>
    lateinit var pic:ArrayList<Any>
    lateinit var spin2ad:spinneradapter
    lateinit var addbtn:Button
    lateinit var assignlist:ArrayList<String>
    lateinit var ref:DatabaseReference
    lateinit var auth:FirebaseAuth
    lateinit var titles:String
    lateinit var imges:ArrayList<Any>
    lateinit var listdetailss:listdetails

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

        //val view=inflater.inflate(R.layout.fragment_menu, container, false)
    ): View? {
        // Inflate the layout for this fragme

        //savedInstanceState?.putString("assinged",arguments?.get("selected").toString())
        val view=inflater.inflate(R.layout.fragment_groceryadd, container, false)
        spincancel=view.findViewById(R.id.spinnercancel)
        spin2cancel=view.findViewById(R.id.spinner2cancel)
        categoryconstriant=view.findViewById(R.id.categoryconstriant)
        assingedconstriant=view.findViewById(R.id.assingedconstriant)
        spin=view.findViewById(R.id.spinner)
        spin2=view.findViewById(R.id.spinner2)
        assignlist= ArrayList()
        spin.text="Assinged to all"
        spin2.text="Uncategorized"
//        assignlist.add("Assingedtoall")
        //assignlist.add("Assigntoabis")
        categorylist= ArrayList()
        item=view.findViewById(R.id.itemdetails)
        val bundle=arguments
        titles=bundle!!.getString("title").toString()
        Log.d("title",titles.toString())

        assinged=arguments?.get("uid").toString()
        Log.d("btn",assinged.toString())

        selecteds=arguments?.get("selected").toString()

        val checkcategory=arguments?.getString("category")
        category=arguments?.get("category").toString()
        categorylist.add("Uncategorized")
        categorylist.add("Diary")
        categorylist.add("Banana")
        categorylist.add("Beverges")
        categorylist.add("vegetable")
        categorylist.add("Fruits")
        addbtn=view.findViewById(R.id.button3)

        pic= ArrayList()
        pic.add(R.drawable.fruit)
        pic.add(R.drawable.veg)
        pic.add(R.drawable.drink)
        pic.add((R.drawable.ic_launcher_background))
        pic.add(R.drawable.veg)


        imges= ArrayList()

        Log.d("before",imges.size.toString())
        var bundles=Bundle()
        bundles= requireArguments()

    arguments?.putString("itemdetails",item.text.toString())



        selecteds=arguments?.getString("uid").toString()


        spin.setOnClickListener {

            val assigned =groceryaddassign()
            var bundley = Bundle()
            isassinged=true
            bundley.putString("title", titles)
            assigned.arguments = bundley
            val support = activity?.supportFragmentManager
            if (support != null) {
                assigned.show(support, assigned.tag)
            }

        }

        spin.text="Assinged to all"
        Log.d("checking item",arguments?.get("itemdetails").toString())
        item.setText(arguments?.get("itemdetails").toString(),TextView.BufferType.EDITABLE)
        val checkassinged=arguments?.getString("selected")
        if(checkassinged!=null) {
            spin.setText(arguments?.getString("selected"))
            spin.setTextColor(Color.parseColor("#ffffff"))
            assingedconstriant.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.roundbut))
        }
        spin2.setOnClickListener {

            Log.d("spin","spin")
            val category= groceryaddcategory()

            var bundley = Bundle()
            bundley.putString("state", "category")


            bundley.putString("selected",arguments?.getString("selected"))
            bundley.putString("uid",arguments?.getString("uid"))
            bundley.putString("title",arguments?.getString("title"))

            category.arguments = bundley
            val support = activity?.supportFragmentManager
            if (support != null) {
                category.show(support, category.tag)
            }
        }
        spin2.text="Uncategorized"

        if(checkcategory!=null) {
            spin2.text = arguments?.getString("category")
categoryconstriant.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.roundbut))
        }
        addbtn.setOnClickListener{
            var it=item.text.toString()

            // val category2=spin.selectedItem.toString()
            // categorym= listdetails(category2.toString())


            assinged=arguments?.getString("uid").toString()

            titles =arguments?.get("title").toString()
            Log.d("btn",titles.toString())

            category=arguments?.getString("category").toString()
            // val listdetails:listdetails2= listdetails2(it,category2,assinged)
            auth=FirebaseAuth.getInstance()
            val user=auth.currentUser?.uid
            var idy:String=""
            var id:String=""

            listdetailss= listdetails(it,category.toString(), assinged.toString())

            FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").orderByChild("title").equalTo(titles)

                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        it.result.children.forEach { children ->
                            Log.d("succes","sucessy")
                            id = children.key.toString()


                            FirebaseDatabase.getInstance().getReference("grocerylist")
                                .child("listbasicinfo").child(id).child("listdetails").push().updateChildren(listdetailss.toMap()).addOnSuccessListener {
                                    Toast.makeText(activity, "List details has been saved", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    else {
                        Log.d("error", "error")
                    }
                }.addOnFailureListener {
                    Log.d("exception",it.toString())
                }




        }

        return view
    }




}

