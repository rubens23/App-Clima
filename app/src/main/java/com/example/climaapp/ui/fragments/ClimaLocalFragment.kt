package com.example.climaapp.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.climaapp.R
import com.example.climaapp.data.remote.apiclima.repositories.WeatherRepository
import com.example.climaapp.databinding.FragmentClimaLocalBinding
import com.example.climaapp.ui.viewModels.ClimaLocalViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

class ClimaLocalFragment : Fragment() {

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var viewModel: ClimaLocalViewModel
    private lateinit var binding: FragmentClimaLocalBinding
    private lateinit var cityName: String
    private var userEscolheuPais = false

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initViewModel()


        if (!userEscolheuPais) {
            initLocationClient()
        }


        binding = FragmentClimaLocalBinding.inflate(inflater, container, false)
        val root = binding.root

        weatherObservers()

        getExtrasFromGlobalFragment()

        return root
    }

    private fun getExtrasFromGlobalFragment() {
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey("CAPITAL")) {
                val capital = requireArguments().getString("CAPITAL")
                Log.d("testandosucesso", "capital: ${capital}, deu certo!")
                userEscolheuPais = true
                if (capital != null) {
                    cityName = capital
                }
                viewModel.getLocationFromActivity(capital)
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun weatherObservers() {
        viewModel.getWeatherFromViewModel()
            ?.observe(viewLifecycleOwner) {
                binding.valorClima.text = it.main.temperature.toString()[0].toString() +
                        it.main.temperature.toString()[1].toString() + "º"
                cityName = it.name
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
            setupState(state)
        }


    }

    private fun setupState(state: WeatherRepository.State?) {
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[ClimaLocalViewModel::class.java]


    }

    private fun initLocationClient() {
        Log.d("trackingcaminho", "to no init location client")
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())


        getNecessaryPermissions()
    }

    private fun getNecessaryPermissions() {
        Log.d("trackingcaminho", "to pegando as permissoes necessarias")

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return

        }
        Log.d("trackingcaminho", "to aqui. Ja tenho permissao")

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                currentLocation = location!!
                Log.d("testelocation", "${location}")
                val nomeCidade = getCityNameByLocation(location)
                if (!userEscolheuPais) {
                    viewModel.getLocationFromActivity(nomeCidade)
                }


            }




    }

    //eu tenho que mostrar alguma coisa quando o usuario n permitir o uso da localização
    //uma text view ja ta de bom tamanho

    //eu posso colocar no proprio fragment aí eu tiro quando ele carregar o clima do lugar

    //aí eu posso colocar tbm algo para o usuario ativar a permissao e começar a ver o clima


    private fun getCityNameByLocation(location: Location): String? {
        val geoCoder = Geocoder(requireActivity(), Locale.getDefault())
        val adresses: MutableList<Address> =
            geoCoder.getFromLocation(location.latitude, location.longitude, 1)!!
        cityName = adresses[0].subAdminArea

        return cityName

    }


}
