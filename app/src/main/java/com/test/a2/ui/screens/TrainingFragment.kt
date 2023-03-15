package com.test.a2.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.a2.R
import com.test.a2.data.network.data.response.TrainingResponse
import com.test.a2.databinding.FragmentTrainingBinding
import com.test.a2.databinding.TrainingItemBinding
import com.test.a2.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TrainingFragment : Fragment() {

    private val viewModel by viewModels<AppViewModel>()
    private var _binding: FragmentTrainingBinding? = null
    private val binding: FragmentTrainingBinding
        get() = _binding ?: throw RuntimeException("FragmentTrainingBinding == null")

    private val adapter by lazy {
        TrainingAdapter(LayoutInflater.from(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTraining(dayOfWeek())
        initRecycler()
        initObserver()
        binding.imBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initRecycler() {
        binding.rvNews.adapter = adapter
    }

    private fun initObserver() {
        viewModel.trainingListLD.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    private fun dayOfWeek(): String {
        val calendar: Calendar = Calendar.getInstance()

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> getString(R.string.sunday)
            Calendar.MONDAY -> getString(R.string.monday)
            Calendar.TUESDAY -> getString(R.string.tuesday)
            Calendar.WEDNESDAY -> getString(R.string.wednesday)
            Calendar.THURSDAY -> getString(R.string.thursday)
            Calendar.FRIDAY -> getString(R.string.friday)
            Calendar.SATURDAY -> getString(R.string.saturday)
            else -> getString(R.string.monday)
        }
    }

}

class TrainingAdapter(
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>() {

    private var items = mutableListOf<TrainingResponse>()

    fun updateList(newList: List<TrainingResponse>) {
        items.clear()
        items.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        return TrainingViewHolder(TrainingItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class TrainingViewHolder(private val binding: TrainingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(training: TrainingResponse) {
            if (training.img.isNotEmpty()) {
                Picasso.get().load(training.img).into(binding.imTraining)
            }
            binding.tvTrainingText.text = training.text
        }
    }
}

