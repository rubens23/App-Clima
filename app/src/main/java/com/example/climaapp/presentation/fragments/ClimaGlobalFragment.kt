package com.example.climaapp.presentation.fragments




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climaapp.R
import com.example.climaapp.framework.data.remote.apicountries.repositories.CountryRepository
import com.example.climaapp.databinding.FragmentClimaGlobalBinding
import com.example.climaapp.presentation.adapters.CountryAdapter
import com.example.climaapp.framework.viewModels.ClimaGlobalViewModel
import com.example.climaapp.framework.viewModels.ClimaGlobalViewModelFactory
import java.util.*


class ClimaGlobalFragment : Fragment() {

    /**
     * para melhorar a arquitetura nessa parte
     * eu tenho que ver quais metodos daqui
     * não pertencem a view. Se n pertencerem a view,
     * eles podem ir para outra camada como a viewModel
     * ou até mesmo a model.
     *
     * por incrível que pareça eu acho que essa view ta bem
     * desacoplada.
     *
     * mas uma coisa ainda me preocupa.. eu ainda não estou usando
     * o outro modulo que eu criei anteriormente.
     *
     * Mas antes de resolver isso, eu vou ver se o fragmentLocalWeather
     * ta bem desacoplado
     */

    private lateinit var viewModel: ClimaGlobalViewModel
    private lateinit var binding: FragmentClimaGlobalBinding
    private var countryAdapter = CountryAdapter(Collections.emptyList())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val capitalIsChosen = false
        setFragmentResult("GLOBALOPEN", bundleOf("ISOPEN" to capitalIsChosen))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        initViewModel()


        binding = FragmentClimaGlobalBinding.inflate(inflater, container, false)
        val root = binding.root
        setAdapterSettings()

        countryObservers()
        return root
    }

    private fun setAdapterSettings() {
        binding.recyclerViewPaises.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPaises.adapter = countryAdapter
    }

    private fun initViewModel() {
        val viewModelFactory = ClimaGlobalViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ClimaGlobalViewModel::class.java)
    }

    private fun countryObservers() {
        viewModel.getCountryFromViewModel()?.observe(viewLifecycleOwner) { listCountries ->
            val adapter = CountryAdapter(listCountries)
            adapter.getCapital()?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                val bundle = bundleOf(
                    "CAPITAL" to it
                )

                val capitalIsChosen = true
                setFragmentResult(
                    "requestKey",
                    bundleOf("CAPITAL" to capitalIsChosen, "NOMECAPITAL" to it)
                )


                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<ClimaCapitalFragment>(R.id.fragment_container_view, args = bundle)
                }


            })
            binding.recyclerViewPaises.adapter = adapter

        }
        viewModel.getStateFromViewModel().observe(viewLifecycleOwner) { state ->
            setupState(state)
        }


    }


    private fun setupState(state: CountryRepository.State?) {
        when(state){
            CountryRepository.State.DOING ->{
                binding.loadingClimaglobal.visibility = View.VISIBLE
            }
            CountryRepository.State.DONE ->{
                binding.loadingClimaglobal.visibility = View.GONE
            }
            CountryRepository.State.ERROR -> {
                binding.loadingClimaglobal.visibility = View.GONE
                Toast.makeText(requireActivity(), "Ocorreu um erro ao carregar o clima", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(requireActivity(), "Erro: unknown state", Toast.LENGTH_LONG).show()
            }
        }

    }

}
