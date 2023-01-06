package com.example.climaapp.ui.fragments

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
import com.example.climaapp.data.remote.apiclima.repositories.WeatherRepository
import com.example.climaapp.databinding.FragmentClimaCapitalBinding
import com.example.climaapp.ui.viewModels.ClimaLocalViewModel


class ClimaCapitalFragment : Fragment() {

    private var capital: String? = null
    private lateinit var viewModel: ClimaLocalViewModel
    private lateinit var binding: FragmentClimaCapitalBinding
    private var cityName: String? = null


    private fun getExtrasFromGlobalFragment() {


        Log.d("testebundlecall", "to no extras da global")
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey("CAPITAL")) {
                val capital = requireArguments().getString("CAPITAL")
                if (capital != null) {
                    Log.d("testebundlecall", "capital é diferente de nulo")
                    Log.d("testandosucessocapital", "capital: ${capital}, deu certo!")
                    this.capital = capital
                    Log.d("testebundlecall", "to dentro do result listener")
                    viewModel.getLocationFromActivity(capital)
                } else {
                    Log.d("testebundlecall", "capital é nula")
                    setFragmentResultListener("fromMainActivity") {
                            _, bundle ->


                        if (bundle != null) {
                            Log.d("testebundlecall", "bundle não é nulo")

                            Log.d(
                                "testandosucessocapital",
                                "capital: ${bundle.getString("NOMECAPITAL")}, deu certo!"
                            )

                            viewModel.getLocationFromActivity(bundle.getString("NOMECAPITAL"))
                        } else {
                            Log.d("testebundlecall", "bundle é nulo")
                        }
                    }

                }

            }

        }


    }


    private fun initViewModel() {
        Log.d("nomecapitalfragment", "initViewModel")
        viewModel = ViewModelProvider(requireActivity())[ClimaLocalViewModel::class.java]
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

        Log.d("nomecapitalfragment", "onViewCreated")
        val capitalIsChosen = true
        Log.d("testemile1", "" + capital)
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

        viewModel.getWeatherFromViewModel()
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
        viewModel.getStateFromViewModel().observe(viewLifecycleOwner) { state ->
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
