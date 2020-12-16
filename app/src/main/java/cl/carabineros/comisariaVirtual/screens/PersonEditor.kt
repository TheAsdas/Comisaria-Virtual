package cl.carabineros.comisariaVirtual.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cl.carabineros.comisariaVirtual.api.RegionsApi
import cl.carabineros.comisariaVirtual.tabFragments.TabAddressAndPerson2
import cl.carabineros.comisariaVirtual.tabFragments.TabPerson1
import cl.carabineros.comisariaVirtual.model.*
import cl.carabineros.comisariaVirtual.utils.DatabaseHandler
import cl.carabineros.comisariaVirtual.utils.TabMethods
import cl.example.comisariaVirtual.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_person_editor.*
import kotlinx.android.synthetic.main.tab_address_1.view.*
import kotlinx.android.synthetic.main.tab_person_1.*
import kotlinx.android.synthetic.main.tab_person_1.view.*
import kotlinx.android.synthetic.main.tab_person_and_address_2.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class PersonEditor : AppCompatActivity()
{
    //tabs
    private lateinit var dataTab: Fragment;
    private lateinit var addressTab: Fragment;

    //Variables for the API
    private lateinit var retrofit: Retrofit;
    private lateinit var service: RegionsApi;
    private lateinit var context: Context;

    //Variables for a Person
    private var names: String? = null;
    private var lastNames: String? = null;
    private var rut: String? = null;
    private var rutId: String? = null;
    private var gender: Int? = null;
    private var address: String? = null;
    private var mail: String? = null;
    private var selectedRegion: Region? = null;
    private var selectedCommune: Comuna? = null;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_editor);

        configureActivity();
    }

    private fun configureActivity()
    {
        //init context
        context = this;

        configureIncomingPerson();
        configureApi();
        configureToolbar();
        configureViewPager();
        configureRegionsMenu();

    }



    private fun configureApi()
    {
        //init retrofit
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.cofralit.cl/desarrollo/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //init service
        service = this.retrofit.create(RegionsApi::class.java);
    }

    private fun configureToolbar()
    {
        toolbar.setNavigationOnClickListener{finish()};
    }

    private fun configureViewPager()
    {
        val tabs = TabMethods(supportFragmentManager);
        dataTab = TabPerson1();
        addressTab = TabAddressAndPerson2();

        tabs.addTab(
            Pair(
                getString(R.string.data_tab),
                dataTab
            )
        );
        tabs.addTab(
            Pair(
                getString(R.string.address_tab),
                addressTab
            )
        );

        viewPager.adapter = tabs;
        tabLayout.setupWithViewPager(viewPager);
    }

    private fun configureRegionsMenu()
    {
        val regionCall = service.getRegions();

        regionCall.enqueue(object : Callback<Regiones> {
            override fun onFailure(
                call: Call<Regiones>,
                t: Throwable
            ) {
                Toast.makeText(
                    context,
                    "Error al listar las regiones.",
                    Toast.LENGTH_LONG
                ).show();

                t.printStackTrace();
            }

            override fun onResponse(
                call: Call<Regiones>,
                response: Response<Regiones>)
            {
                //get the list with regions from response
                val regionList = response.body()!!.Regiones;
                //delete last item from ArrayList
                regionList.removeAt(regionList.size - 1);
                //create ArrayAdapter for the list
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    regionList
                );
                //set ArrayAdapter
                selectRegion.setAdapter(adapter);

                /* When a Region from the list is selected, it will load the Communes of said
                Region. */
                selectRegion.setOnItemClickListener {
                        adapterView: AdapterView<*>,
                        _: View,
                        index: Int,
                        _: Long ->
                    selectedRegion = adapterView.getItemAtPosition(index) as Region;

                    //debug message:
                    println("Seleccionaste $selectedRegion, cuya ID es ${selectedRegion?.IdRegion}");

                    //clear previously selected Commune
                    selectCommune.clearListSelection();
                    selectCommune.setText("");
                    selectedCommune = null;

                    loadCommunes(selectedRegion!!);
                }
            }
        });
    }

    /**
     * Load communes from the region selected by the user.
     *
     * @param region Region selected by the user.
     */
    private fun loadCommunes(region: Region)
    {
        val communeCall = service.getCommunesOfRegion(region.IdRegion);

        communeCall.enqueue(object : Callback<ArrayList<Comuna>> {
            override fun onFailure(
                call: Call<ArrayList<Comuna>>,
                t: Throwable)
            {
                //show error Toast
                Toast.makeText(
                    context, "Error al cargar las comunas de $region",
                    Toast.LENGTH_SHORT
                ).show();

                //set an empty ArrayList
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    ArrayList<Regiones>());
                selectCommune.setAdapter(adapter);

                //clear previously selected Commune
                selectCommune.clearListSelection();
                selectCommune.setText("");
                selectedCommune = null;

                //print error message in console
                t.printStackTrace();
            }

            override fun onResponse(
                call: Call<ArrayList<Comuna>>,
                response: Response<ArrayList<Comuna>>)
            {
                //extract commune list from response
                val communesList = response.body()!!;

                //create ArrayAdapter for the list
                val adapter = ArrayAdapter(
                    context,
                    R.layout.layout_list_item,
                    communesList
                );

                //set ArrayAdapter
                selectCommune.setAdapter(adapter);

                //When an item from the list is selected, this item is saved in memory.
                selectCommune.setOnItemClickListener{
                        adapterView: AdapterView<*>,
                        _: View,
                        i: Int,
                        _: Long ->
                    selectedCommune = adapterView.getItemAtPosition(i) as Comuna;
                }

            }
        });
    }

    fun addPerson(view: View)
    {
        val person: Person;

        if (inputsAreCorrect())
        {
            person = createPerson();
            DatabaseHandler(this).insertPersona(person);

            Toast.makeText(
                this,
                "Persona agregada.",
                Toast.LENGTH_SHORT
            ).show();

            this.finish();
        }
        else
        {
            Toast.makeText(
                this,
                "¡Hay campos incorrectos!",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    private fun inputsAreCorrect(): Boolean
    {
        names = checkAndGetTextInput(
            inputNames,
            Regex(
                "^[a-z]+\\s[a-z]+\$",
                RegexOption.IGNORE_CASE
            ),
            "Ingrese su nombre y apellido separado por un espacio."
        )

        lastNames = checkAndGetTextInput(
            inputLastNames,
            Regex(
                "^[a-z]+\\s[a-z]+\$",
                RegexOption.IGNORE_CASE
            ),
            "Ingrese sus apellidos separados por un espacio."
        )

        rut = checkAndGetTextInput(
            inputRut,
            Regex(
                "^[0-9]+-[0-9k]+\$",
                RegexOption.IGNORE_CASE
            ),
            "Ingrese su rut sin puntos y con guión."
        );

        rutId = checkAndGetTextInput(
            inputRutId,
            Regex(
                "^[0-9]+\$",
            ),
            "Ingrese su número de documento sin puntos."
        );
        gender = getGender();

        address = checkAndGetTextInput(
            inputAddress,
            Regex(
                "^[a-z ]+\\s[0-9]+(,*\\s[a-z]*)?\$",
                RegexOption.IGNORE_CASE
            ),
            "Ingrese el nombre de la calle y el número del edificio."
        );

        mail = checkAndGetTextInput(
            inputMail,
            Regex(
                "^[a-z0-9._\\-]+@[a-z0-9]+\\.[a-z]+\$",
                RegexOption.IGNORE_CASE
            ),
            "Ingrese su mail con @ y dominio (.cl - .com - etc...)."
        )
        if (selectedCommune == null) selectCommune.error = "Eliga una comuna.";
        else selectCommune.error = null;

        if (selectedRegion == null) selectRegion.error = "Eliga una región."
        else selectRegion.error = null;

        //debug message
        println("Atributos:");
        println("nombres: $names");
        println("apellidos: $lastNames");
        println("rut: $rut");
        println("num documento: $rutId");
        println("sexo: ${if(gender == 0) "mujer" else if (gender == 1) "hombre" else "null"}");
        println("dirección: $address");
        println("e-mail: $mail");
        println("region: ${selectedRegion?.NombreRegion}");
        println("comuna: ${selectedCommune?.Ciudad}");

        if (names == null ||
            lastNames == null ||
            rut == null ||
            rutId == null ||
            gender == null ||
            address == null ||
            mail == null ||
            selectedRegion == null ||
            selectedCommune == null
        ) return false;

        return true;
    }

    private fun checkAndGetTextInput(
        textInput: TextInputEditText,
        regex: Regex,
        errorMessage: String)
    : String?
    {
        val text = textInput.text.toString();

        if (text.matches(regex))
        {
            textInput.error = null;
            return text;
        }
        else
            textInput.error = errorMessage;

        return null;
    }

    private fun getGender(): Int?
    {
        if (radioMale.isChecked)
            return 1;
        else if (radioFemale.isChecked)
            return 0;

        return null;
    }

    private fun createPerson(): Person
    {
        return Person(
            0,
            names!!.split(" ")[0].toUpperCase(Locale.ROOT),
            names!!.split(" ")[1].toUpperCase(Locale.ROOT),
            lastNames!!.split(" ")[0].toUpperCase(Locale.ROOT),
            lastNames!!.split(" ")[1].toUpperCase(Locale.ROOT),
            rut!!.toUpperCase(Locale.ROOT),
            rutId!!,
            gender!!,
            selectedRegion!!.IdRegion,
            selectedCommune!!.IdCiudad,
            address!!.toUpperCase(Locale.ROOT),
            mail!!.toUpperCase(Locale.ROOT)
        );
    }

    private fun configureIncomingPerson()
    {
        val incomingPerson: Person? = try
        {
            intent.getSerializableExtra("person") as Person;
        }
        catch (e: Exception)
        {
            null;
        }

        if (incomingPerson != null) fillFields(incomingPerson);
    }

    private fun fillFields(p: Person)
    {
        val names = "${p.nombre} ${p.segundoNombre}";
        val lastNames = "${p.apellidoPaterno} ${p.apellidoMaterno}";
        val rut = p.rut;
        val rutId = p.numeroSerie;
        val gender = p.genero;
        val address = p.direccion;
        val mail = p.correo;

        println(names);
        println(lastNames);

        dataTab.inputNames!!.setText(names);
        inputLastNames!!.setText(lastNames);
        inputRut!!.setText(rut);
        inputRutId!!.setText(rutId);
        if (gender == 1) radioMale!!.isSelected = true;
        else radioFemale?.isSelected = true;
        inputAddress!!.setText(address);
        inputMail!!.setText(mail);
    }

}


