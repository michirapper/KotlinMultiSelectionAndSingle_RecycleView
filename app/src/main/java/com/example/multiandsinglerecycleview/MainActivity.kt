package com.example.multiandsinglerecycleview

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), ActionMode.Callback {
    private var actionMode: ActionMode? = null
    private var isMultiSelect = false

    //i created List of int type to store id of data, you can create custom class type data according to your need.
    private var selectedIds: MutableList<Int> = ArrayList()
    private var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<View>(R.id.widget_list) as RecyclerView
        adapter = MyAdapter(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                if (isMultiSelect) {
                    multiSelect(position)
                }

            }

            override fun onItemLongClick(view: View?, position: Int) {
                if (!isMultiSelect) {
                    selectedIds = ArrayList()
                    isMultiSelect = true
                    if (actionMode == null) {
                        actionMode = startActionMode(this@MainActivity) //show ActionMode.
                    }
                }
                multiSelect(position)
            }
        }))
    }

    private fun multiSelect(position: Int) {
        val data = adapter!!.getItem(position)
        if (data != null) {
            if (actionMode != null) {
                if (selectedIds.contains(data.id)) selectedIds.remove(Integer.valueOf(data.id)) else selectedIds.add(data.id)
                if (selectedIds.size > 0) actionMode!!.title = selectedIds.size.toString() //show selected item count on action mode.
                else {
                    actionMode!!.title = "" //remove item count from action mode.
                    actionMode!!.finish() //hide action mode.
                }
                adapter!!.setSelectedIds(selectedIds)
            }
        }else {
            Toast.makeText(applicationContext, "hola", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * @see MyData Create dummy List of type MyData.
     *
     * @return list
     */
    private val list: List<MyData>
        private get() {
            val list: MutableList<MyData> = ArrayList()
            list.add(MyData(1, "GridView"))
            list.add(MyData(2, "Switch"))
            list.add(MyData(3, "SeekBar"))
            list.add(MyData(4, "EditText"))
            list.add(MyData(5, "ToggleButton"))
            list.add(MyData(6, "ProgressBar"))
            list.add(MyData(7, "ListView"))
            list.add(MyData(8, "RecyclerView"))
            list.add(MyData(9, "ImageView"))
            list.add(MyData(10, "TextView"))
            list.add(MyData(11, "Button"))
            list.add(MyData(12, "ImageButton"))
            list.add(MyData(13, "Spinner"))
            list.add(MyData(14, "CheckBox"))
            list.add(MyData(15, "RadioButton"))
            return list
        }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.menu_select, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_delete -> {
                //just to show selected items.
                val stringBuilder = StringBuilder()
                for (data in list) {
                    if (selectedIds.contains(data.id)) stringBuilder.append("\n").append(data.title)
                }
                Toast.makeText(this, "Selected items are :$stringBuilder", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        actionMode = null
        isMultiSelect = false
        selectedIds = ArrayList()
        adapter!!.setSelectedIds(ArrayList())
    }
}