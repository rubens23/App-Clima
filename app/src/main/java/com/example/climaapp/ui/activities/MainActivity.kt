package com.example.climaapp.ui.activities


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.climaapp.R
import com.example.climaapp.databinding.ActivityMainBinding
import com.example.climaapp.ui.fragments.ClimaCapitalFragment
import com.example.climaapp.ui.fragments.ClimaGlobalFragment
import com.example.climaapp.ui.fragments.ClimaLocalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    //todo ver tutoriais sobre crashlytics


    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var isCapitalChosen = false
    private var nomeCapital: String? = null
    private var bundle: Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        callFragmentClimaLocal()

        navViewOnItemSelected()


    }

    private fun navViewOnItemSelected() {

        binding.navView.setOnItemSelectedListener {
            when (it.title.toString()) {
                "Clima Local" -> {
                    callFragmentClimaLocal()
                    getExtrasFromGlobalFragment()
                    getExtrasFromCapitalFragment()
                    Log.d("nomecapital", "" + nomeCapital)
                }
                "Clima Global" -> {
                    seeIfCapitalIsAlreadyChosen()
                    if (!isCapitalChosen) {
                        callFragmentClimaGlobal()
                    } else {
                        callFragmentClimaCapital()
                    }

                }
            }
            true
        }


    }

    private fun seeIfCapitalIsAlreadyChosen() {
        supportFragmentManager.setFragmentResultListener("GLOBALOPEN", this) { _, bundle ->
            val result = bundle.getBoolean("ISOPEN")
            if (result != null) {
                isCapitalChosen = result
                Log.d("testeisopen", "" + isCapitalChosen)


            }
        }

    }


    private fun callFragmentClimaLocal() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ClimaLocalFragment>(R.id.fragment_container_view)
        }

    }

    private fun getExtrasFromGlobalFragment() {
        supportFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            val result = bundle.getBoolean("CAPITAL")
            if (result != null) {
                this.bundle = bundle
                setBundleToSendToCapitalFragment()
                isCapitalChosen = result
                if (bundle.getString("NOMECAPITAL") != null) {
                    nomeCapital = bundle.getString("NOMECAPITAL")
                }

            }
        }


    }

    private fun getExtrasFromCapitalFragment() {
        supportFragmentManager.setFragmentResultListener(
            "requestKey2",
            this
        ) { _, bundle ->
            val result = bundle.getBoolean("CAPITAL")
            if (result != null) {
                this.bundle = bundle
                setBundleToSendToCapitalFragment()
                isCapitalChosen = result
                if (bundle.getString("NOMECAPITAL") != null) {
                    nomeCapital = bundle.getString("NOMECAPITAL")
                }

            }
        }

    }

    private fun setBundleToSendToCapitalFragment() {
        supportFragmentManager.setFragmentResult(
            "fromMainActivity",
            bundleOf("CAPITAL" to true, "NOMECAPITAL" to nomeCapital)
        )


    }

    private fun callFragmentClimaGlobal() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ClimaGlobalFragment>(R.id.fragment_container_view)
        }

    }

    private fun callFragmentClimaCapital() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ClimaCapitalFragment>(R.id.fragment_container_view, args = bundle)
        }

    }


}

