package com.example.climaapp.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.example.climaapp.R
import com.example.climaapp.framework.data.remote.apiclima.repositories.WeatherRepository
import com.example.climaapp.databinding.FragmentClimaCapitalBinding
import com.example.climaapp.framework.viewModels.ClimaCapitalViewModel


class ClimaCapitalFragment : Fragment() {

    private var capital: String? = null
    private lateinit var climaCapitalViewModel: ClimaCapitalViewModel
    private lateinit var binding: FragmentClimaCapitalBinding
    private var cityName: String? = null


    private fun getExtrasFromGlobalFragment() {



        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey("CAPITAL")) {
                val capital = requireArguments().getString("CAPITAL")
                if (capital != null) {
                    this.capital = capital
                    climaCapitalViewModel.getCapitalFromGlobalFragment(capital)
                    //viewModel.getLocationFromActivity(capital)
                } else {
                    setFragmentResultListener("fromMainActivity") {
                            _, bundle ->


                        climaCapitalViewModel.getCapitalFromGlobalFragment(bundle.getString("NOMECAPITAL")!!)

                    }

                }

            }

        }


    }


    private fun initViewModel() {
        climaCapitalViewModel = ViewModelProvider(requireActivity())[ClimaCapitalViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentClimaCapitalBinding.inflate(inflater, container, false)
        val root = binding.root


        initViewModel()
        getExtrasFromGlobalFragment()


        weatherObservers()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val capitalIsChosen = true
        setFragmentResult(
            "requestKey2",
            bundleOf("CAPITAL" to capitalIsChosen, "NOMECAPITAL" to this.capital)
        )

        binding.btnCloseCapitalFragment.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ClimaGlobalFragment>(R.id.fragment_container_view)
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun weatherObservers() {

        climaCapitalViewModel.getWeatherFromViewModel()
            ?.observe(viewLifecycleOwner) {
                Log.d("nomecapitalfragment", "dentroweatherObservers")
                binding.valorClima.text = it.main.temperature.toString()[0].toString() +
                        it.main.temperature.toString()[1].toString() + "º"
                cityName = it.name
                Log.d("nomecapitalfragment", "nome da cidade a ser mostrado na tela: ${it.name}")
                binding.cidade.text = cityName
                when (it.weather[0].description) {
                    "clear sky" -> {
                        binding.descClima.text = getString(R.string.res_ceu_limpo)
                    }
                    "broken clouds" -> {
                        binding.descClima.text = getString(R.string.res_nuvens_esparsas)
                    }
                    "few clouds" -> {
                        binding.descClima.text = getString(R.string.res_poucas_nuvens)
                    }
                    "overcast clouds" -> {
                        binding.descClima.text = getString(R.string.res_nuvens_nubladas)
                    }
                    "light rain" -> {
                        binding.descClima.text = getString(R.string.res_chuva_fraca)
                    }
                    "scattered clouds" -> {
                        binding.descClima.text = getString(R.string.res_nuvens_dispersas)

                    }
                    else -> {
                        binding.descClima.text = it.weather[0].description
                    }
                }


            }
        climaCapitalViewModel.getStateFromViewModel().observe(viewLifecycleOwner) { state ->
            Log.d("nomecapitalfragment", "dentrodostateObserver: $state")
            setupState(state)
        }


    }

    private fun setupState(state: WeatherRepository.State?) {
        Log.d("nomecapitalfragment", "dentro do setupstate")
        when (state) {
            WeatherRepository.State.DOING -> {
                binding.loading.visibility = View.VISIBLE
            }
            WeatherRepository.State.DONE -> {
                binding.loading.visibility = View.GONE

            }
            WeatherRepository.State.ERROR -> {
                binding.loading.visibility = View.GONE
                Toast.makeText(
                    requireActivity(),
                    "Ocorreu um erro ao carregar o clima",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(requireActivity(), "Erro: unknown state", Toast.LENGTH_LONG).show()
            }
        }


    }


}
