package org.aff.tattoosDB.view.fragments;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import org.aff.tattoosDB.R;
import org.aff.tattoosDB.model.entity.Tattoo;
import org.aff.tattoosDB.model.entity.Type;
import org.aff.tattoosDB.viewmodel.TattooViewModel;

import java.util.Calendar;


public class AddTattooFragment extends Fragment {

    private final Calendar c = Calendar.getInstance();
    private int day = c.get(Calendar.DAY_OF_MONTH);
    private int month = c.get(Calendar.MONTH);
    private int year = c.get(Calendar.YEAR);

    private ActivityResultLauncher<String> mGetContent;

    private Spinner spType;
    private TextInputLayout tlColor, tlUbi, tlDuracion, tlHoras, tlFecha;
    private EditText etColor, etUbi, etDuracion, etHoras, etFecha;
    private ImageView ivAddTattoo;
    private Button btAdd, btCancel;
    private Uri selectedUri;
    private static boolean firstType = false;

    private Bundle bundle;

    private Tattoo tattoo;

    private TattooViewModel tattooViewModel;

    public AddTattooFragment() {
        // constructor requerido vacio
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_tattoo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        init(view);
        try{
            tattoo = bundle.getParcelable("tattoo");
        }catch (Exception e){
            adder(view);
        }
    }

    private void cargarSp() {
        tattooViewModel.getTypes().observe(getViewLifecycleOwner(), types -> {
            if(firstType){
                Type type = new Type();
                type.id = 0;
                type.name = getString(R.string.default_select);
                types.add(0, type);
                firstType = false;
            }
            ArrayAdapter<Type> adapter =
                    new ArrayAdapter<Type>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
            this.spType.setAdapter(adapter);
            try{
                this.spType.setSelection(tattoo.idtype);
            } catch (Exception e){}

        });
    }

    private void adder(View view){
        Glide.with(this).load(R.drawable.addimg).into(ivAddTattoo);

        etFecha.setOnFocusChangeListener((v, hasFocus)->{
            if(hasFocus){
                fechaPicker();
            }
        });

        btCancel = view.findViewById(R.id.bt_cancel);
        btCancel.setOnClickListener((View v)->{
            NavHostFragment.findNavController(this).popBackStack();
        });

        btAdd = view.findViewById(R.id.bt_Edit);
        btAdd.setOnClickListener((View v)->{
            try {
                Tattoo c = tattooAdder();
                if(tattoValidator(c)){
                    tattooViewModel.insertTattoo(c);
                    wasInserted();
                    NavHostFragment.findNavController(this).popBackStack();
                } else {
                    ifEmpty();
                    Toast.makeText(getContext(), "Error inserting", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                ifEmpty();
            }
        });
        imgadd();
    }

    private Tattoo tattooAdder(){
        String color = etColor.getText().toString();
        String ubicacion = etUbi.getText().toString();
        String year = etDuracion.getText().toString();
        String horas = etHoras.getText().toString();
        String fecha = etFecha.getText().toString();

        Type type = (Type) spType.getSelectedItem();
        Tattoo t = new Tattoo();
        t.color = color;
        t.ubicacion = ubicacion;
        t.idtype = type.id;
        t.duracion = Integer.parseInt(year);
        t.horas = Integer.parseInt(horas);
        try {
            t.url = selectedUri.toString();
        }catch (Exception e){
            t.url = "https://www.lifeder.com/wp-content/uploads/2018/10/question-mark-2123967_640.jpg";
        }
        t.date = fecha;
        return t;
    }

    private boolean tattoValidator(Tattoo tattoo){
        return !(tattoo.color.isEmpty() || tattoo.ubicacion.isEmpty() || tattoo.duracion <= 0 || tattoo.horas <=0 || tattoo.idtype <= 0);
    }

    private void fechaPicker(){
        DatePickerDialog date = new DatePickerDialog(getContext(), (datePicker, year, month, day) -> {
            String Month = (month < 10) ? "0" + month : String.valueOf(month);
            String Day = (day < 10) ? "0" + day : String.valueOf(day);
            etFecha.setText(Day + "/" + Month + "/" + year);
        },year, month, day);
        date.show();
    }

    private void init(View view){
        spType = view.findViewById(R.id.spinner);

        tlColor = view.findViewById(R.id.tlColor);
        tlUbi = view.findViewById(R.id.tlUbi);
        tlDuracion = view.findViewById(R.id.tlDuracion);
        tlHoras = view.findViewById(R.id.tlHoras);
        tlFecha = view.findViewById(R.id.tlFecha);

        etColor = view.findViewById(R.id.etColor);
        etUbi = view.findViewById(R.id.etUbi);
        etDuracion = view.findViewById(R.id.etDuracion);
        etHoras = view.findViewById(R.id.etHoras);
        etFecha = view.findViewById(R.id.etFecha);

        btCancel = view.findViewById(R.id.bt_cancel);
        btAdd = view.findViewById(R.id.bt_Edit);

        ivAddTattoo = view.findViewById(R.id.imgVAddTattoo);

        tattooViewModel = new ViewModelProvider(requireActivity()).get(TattooViewModel.class);
        cargarSp();
    }


    private void imgadd(){
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if(uri != null){
                        Glide.with(getActivity())
                                .load(uri)
                                .into(ivAddTattoo);
                        selectedUri = uri;
                    } else {
                        Glide.with(getActivity())
                                .load(R.drawable.addimg)
                                .into(ivAddTattoo);
                    }
                });
        ivAddTattoo.setOnClickListener(view -> mGetContent.launch("image/*"));
    }

    private void wasInserted(){
        tattooViewModel.getLiveInsertTattoo().observe(getViewLifecycleOwner(), tattoo -> {
            if(tattoo.longValue() > 0){
                Toast.makeText(getContext(), "Tattoo inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Error inserting tattoo", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ifEmpty(){
        if(etColor.getText().toString().isEmpty()){
            tlColor.setError(getString(R.string.rellena));
            tlColor.setErrorEnabled(true);
        } else {
            tlColor.setError("");
            tlColor.setErrorEnabled(false);
        }
        if(etDuracion.getText().toString().isEmpty()){
            tlDuracion.setError(getString(R.string.rellena));
            tlDuracion.setErrorEnabled(true);
        } else {
            tlDuracion.setError("");
            tlDuracion.setErrorEnabled(false);
        }

        if(etHoras.getText().toString().isEmpty()){
            tlHoras.setError(getString(R.string.rellena));
            tlHoras.setErrorEnabled(true);
        } else {
            tlHoras.setError("");
            tlHoras.setErrorEnabled(false);
        }
    }
}