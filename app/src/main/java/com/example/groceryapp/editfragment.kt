package com.example.groceryapp

import android.content.Context
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_grocerylist.*
import kotlinx.android.synthetic.main.fragment_addtolist.*


/**
 * A simple [Fragment] subclass.
 * Use the [addtolist.newInstance] factory method to
 * create an instance of this fragment.
 */
class editfragment:BottomSheetDialogFragment() {

    lateinit var assignlists:TextView
    lateinit var item:EditText
    var selected=""
    lateinit var categorylists:TextView
    var assinged=""
    var category=""
    var selecteds=""
var titley=""
    var isassinged:Boolean=false
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
    lateinit var assingcancel:ImageView
    lateinit var catcanc:ImageView
    lateinit var const:ConstraintLayout
    lateinit var const2:ConstraintLayout
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //val view=inflater.inflate(R.layout.fragment_menu, container, false)
    ): View? {
        // Inflate the layout for this fragme

        //savedInstanceState?.putString("assinged",arguments?.get("selected").toString())
        val view=inflater.inflate(R.layout.fragment_editfragment, container, false)
        assignlists=view.findViewById(R.id.tt)
        categorylists=view.findViewById(R.id.textView17)
        assignlist= ArrayList()

//        assignlist.add("Assingedtoall")
        //assignlist.add("Assigntoabis")
        categorylist= ArrayList()

        assingcancel=view.findViewById(R.id.imageView15)
        catcanc=view.findViewById(R.id.imageView13)
        item=view.findViewById(R.id.itemname)

        val bundle=arguments
        titles=bundle!!.getString("title").toString()
        val itemname=bundle!!.get("item").toString()
        val cate=bundle!!.get("category").toString()
        val assing=bundle!!.get("selected").toString()
        item.setText(itemname)
        categorylists.text=cate
        assignlists.text=assing
        Log.d("title",titles.toString())
        const=view.findViewById(R.id.constr)
        assinged=arguments?.get("uid").toString()
        Log.d("btn",assinged.toString())

        selecteds=arguments?.get("selected").toString()

        const2=view.findViewById(R.id.constc)
        category=arguments?.get("category").toString()
        categorylist.add("Uncategorized")
        categorylist.add("Diary")
        categorylist.add("Banana")
        categorylist.add("Beverges")
        categorylist.add("vegetable")
        categorylist.add("Fruits")
        addbtn=view.findViewById(R.id.button7)

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

titley=bundle.getString("title").toString()
        selecteds=arguments?.getString("uid").toString()


        assignlists.setOnClickListener {

            val assigned =groceryeditassign()
            var bundley = Bundle()
            isassinged=true
            bundley.putString("title", titles)
            assigned.arguments = bundley
            val support = activity?.supportFragmentManager
            if (support != null) {
                assigned.show(support, assigned.tag)
            }
            const.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.roundbut));

        }

        assignlists.text=arguments?.getString("selected")
        assignlists.text=selected
        categorylists.text = arguments?.getString("category")
        //spin2.setBackgroundColor(Color.parseColor("#000000"))
        const.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.roundbut));


        assingcancel.visibility=View.VISIBLE
        assingcancel.setOnClickListener {
            val assigned =groceryeditassign()
            var bundley = Bundle()
            isassinged=true
            bundley.putString("title", titles)
            assigned.arguments = bundley
            val support = activity?.supportFragmentManager
            if (support != null) {
                assigned.show(support, assigned.tag)
            }
        }
        categorylists.setOnClickListener {

            Log.d("spin","spin")
            val category= groceryeditcategory()

            var bundley = Bundle()



            bundley.putString("selected",arguments?.getString("selected"))
            bundley.putString("uid",arguments?.getString("uid"))
            bundley.putString("title",arguments?.getString("title"))

            category.arguments = bundley
            val support = activity?.supportFragmentManager
            if (support != null) {
                category.show(support, category.tag)
            }
            // const.background.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.ADD);
            const.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.roundbut));

        }
        val ok=false
            categorylists.text = arguments?.getString("category")
assignlists.text=arguments?.getString("selected")
        //  spin.setBackgroundColor(Color.parseColor("#000000"))
        catcanc.visibility = View.VISIBLE
        const2.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.roundbut
            )
        );


        //  canc.setBackgroundColor(Color.parseColor("#000000"))
        // const.setBackgroundColor(Color.parseColor("#000000"))
        // const.background.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.ADD);
        catcanc.setOnClickListener {   Log.d("spin","spin")
            val category= groceryeditcategory()

            var bundley = Bundle()
            bundley.putString("state", "category")


            bundley.putString("selected",arguments?.getString("selected"))
            bundley.putString("uid",arguments?.getString("uid"))
            bundley.putString("title",arguments?.getString("title"))

            category.arguments = bundley
            val support = activity?.supportFragmentManager
            if (support != null) {
                category.show(support, category.tag)
            } }


        addbtn.setOnClickListener {
            var listdetailss=listdetails2(item.text.toString(),categorylists.text.toString(),assignlists.text.toString())
            FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").get().addOnCompleteListener {
                if(it.isSuccessful)
                {

                    it.result.children.forEach { childs->
                        Log.d("true","truee")
                        if(childs.child("title").value==titles)
                        {
                            val id=childs.key.toString()

                                childs.child("listdetails").children.forEach { category->
                                    if(category.child("category").value==categorylists.text.toString())
                                    {
                                       val ids=category.key.toString()
                                        FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").child(id).child("listdetails").child(ids).setValue(listdetailss).addOnSuccessListener {
                                            Toast.makeText(activity, "save change successfully", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                        }
                    }
                }
            }
        }

        return view
    }




}

