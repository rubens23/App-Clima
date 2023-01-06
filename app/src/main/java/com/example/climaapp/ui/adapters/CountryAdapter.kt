package com.example.climaapp.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.climaapp.data.remote.apiclima.repositories.WeatherRepository
import com.example.climaapp.data.remote.apicountries.models.CountryMainModel
import com.example.climaapp.databinding.ItemPaisBinding
import com.example.climaapp.ui.fragments.ClimaGlobalFragment
import com.example.climaapp.ui.fragments.ClimaLocalFragment
import com.squareup.picasso.Picasso


class CountryAdapter(private var lista: List<CountryMainModel>):
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var capital: MutableLiveData<String>? = MutableLiveData<String>()


    inner class ViewHolder(private var binding: ItemPaisBinding): RecyclerView.ViewHolder(binding.root) {



        fun bind(country: CountryMainModel) {
            binding.nomePais.text = country.translations.por.common
            Picasso.get().load(country.flags.png).into(binding.bandeira)
            binding.itemPais.setOnClickListener {
                sendCapital(country.capital.get(0))


            }


        }

    }

    private fun sendCapital(capital: String) {
        this.capital?.postValue(capital)

    }

    fun getCapital(): MutableLiveData<String>? = this.capital

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPaisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country: CountryMainModel = lista[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int = lista.size




}
