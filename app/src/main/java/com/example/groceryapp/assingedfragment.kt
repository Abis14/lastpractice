package com.example.groceryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.addtolist
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_addelement.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class assingedfragment :BottomSheetDialogFragment(),assingedadapter.Adaptercallbacks{
//    lateinit var assinged:A
    // TODO: Rename and change types of parameters
lateinit var assignlist:ArrayList<String>
lateinit var imges:ArrayList<Any>
    lateinit var categor:ArrayList<String>
    lateinit var pics:ArrayList<Any>
    lateinit var uids:ArrayList<String>
lateinit var recy:RecyclerView
lateinit var uid:ArrayList<String>
lateinit var adapter: assingedadapter
 var assinged=""
var title:String?=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_assingedfragment, container, false)
        recy = view.findViewById(R.id.dynamicrec)
        var bundle = Bundle()
        assignlist = ArrayList()
        imges = ArrayList()
        uid = ArrayList()
        bundle = requireArguments()!!

        title = bundle.get("title") as String?
        var cat: String = ""
        cat = (bundle.get("state") as String?).toString()


            assignlist = ArrayList()
            imges = ArrayList()
            uid = ArrayList()
            recy = view.findViewById(R.id.dynamicrec)



            gettingmemberlist()


        return view
        }

        override fun onclick(text: String, uid: String) {
            val fragmentmanager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentmanager?.beginTransaction()
            val addtolist = addtolist()
            fragmentTransaction?.replace(R.id.details, addtolist)
            val bundle = Bundle()
            bundle.putString("selected", text)
            bundle.putString("uid", uid)
            bundle.putString("title",title)
            assinged=title.toString()
            addtolist.arguments = bundle
            val assinged = assingedfragment()
            fragmentTransaction?.remove(assinged)
            fragmentTransaction?.commit()


        }

        fun gettingmemberlist() {
            FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo")
                .orderByChild("title").equalTo(title).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.children.forEach { children ->
                        if (children.child("title").value == title) {
                            children.child("members").children.forEach { childa ->

                                Log.d("uid", childa.child("uid").value.toString())

                                FirebaseDatabase.getInstance().getReference("Users")
                                    .addValueEventListener(object :
                                        ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            for (item in snapshot.children) {
                                                if (item.child("Id").value == childa.child("uid").value) {
                                                    assignlist.add(item.child("Name").value.toString())
                                                    imges.add((item.child("img").value.toString()))
                                                    uid.add(childa.child("uid").value.toString())
                                                    adapter = assingedadapter(
                                                        assignlist,
                                                        imges,
                                                        requireActivity(),
                                                        this@assingedfragment,
                                                        uid
                                                    )
                                                    recy.layoutManager = LinearLayoutManager(
                                                        requireActivity(),
                                                        LinearLayoutManager.VERTICAL,
                                                        false
                                                    )
                                                    recy.adapter = adapter
                                                } else {
                                                    Log.d("no", "nochild")
                                                }
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                    })
                            }
                        }
                    }
                }
            }
        }


    override fun onStart() {
        super.onStart()
        val addtolist=addtolist()
        addtolist.isassinged=true
    }

    override fun onPause() {
        super.onPause()
        val addtolist=addtolist()
        addtolist.isassinged=true
    }

}