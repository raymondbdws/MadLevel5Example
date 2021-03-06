package com.rayray.madlevel5example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_reminders.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RemindersFragment : Fragment() {

    //private lateinit var reminderRepository: ReminderRepository

    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders)

    private val viewModel: ReminderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeAddReminderResult();
    }

    private fun observeAddReminderResult() {
        viewModel.reminders.observe(viewLifecycleOwner, Observer { reminders ->
            this@RemindersFragment.reminders.clear()
            this@RemindersFragment.reminders.addAll(reminders)
            reminderAdapter.notifyDataSetChanged()
        })
    }
//
//    private fun getRemindersFromDatabase() {
//        CoroutineScope(Dispatchers.Main).launch {
//            val reminders = withContext(Dispatchers.IO){
//                reminderRepository.getAllReminders()
//            }
//            this@RemindersFragment.reminders.clear()
//            this@RemindersFragment.reminders.addAll(reminders)
//            reminderAdapter.notifyDataSetChanged()
//        }
//
//    }

    private fun initViews() {
        rvReminders.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvReminders.adapter = reminderAdapter

        createItemTouchHelper().attachToRecyclerView(rvReminders)
    }

//    private fun observeAddReminderResult() {
//        setFragmentResultListener(REQ_REMINDER_KEY) { key, bundle ->
//            bundle.getString(BUNDLE_REMINDER_KEY)?.let {
//                val reminder = Reminder(it)
//
//                CoroutineScope(Dispatchers.Main).launch {
//                    withContext(Dispatchers.IO){
//                        reminderRepository.insertReminder(reminder)
//                    }
//                }
//                getRemindersFromDatabase()
//            }?: Log.e("ReminderFragment", "Request triggered, but empty reminder text!")
//        }
//    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val reminderToDelete = reminders[position]

//                CoroutineScope(Dispatchers.Main).launch {
//                    withContext(Dispatchers.IO){
//                        reminderRepository.deleteReminder(reminderToDelete)
//                    }
//                }
//                getRemindersFromDatabase()
                viewModel.deleteReminder(reminderToDelete)
            }

        }
        return ItemTouchHelper(callback)
    }
}