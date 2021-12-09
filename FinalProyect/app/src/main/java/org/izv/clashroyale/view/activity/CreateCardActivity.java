package org.izv.clashroyale.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.izv.card.R;
import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.Type;
import org.izv.clashroyale.viewmodel.CardViewModel;
import org.izv.clashroyale.viewmodel.TypeViewModel;

public class CreateCardActivity extends AppCompatActivity {

    private EditText etName, etLevel, etElixir, etUrl;
    private Spinner spType;
    private ImageView ivImage;
    private Card card;
    private CardViewModel pvm;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        initialize();
    }

    private void initialize() {
        spType = findViewById(R.id.spType);
        etName = findViewById(R.id.etName);
        etElixir = findViewById(R.id.etElixir);
        etLevel = findViewById(R.id.etLevel);
        etUrl = findViewById(R.id.etUrl);
        ivImage = findViewById(R.id.ivImage);
        Button bt_Cancel = findViewById(R.id.btCancel);

        bt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etName.setOnFocusChangeListener((v, hasFocus) -> {
            String url;
            if(!hasFocus) {
                if(!etName.getText().toString().isEmpty()) {
                    url = pvm.getUrl(etName.getText().toString());
                    Glide.with(this).load(url).into(ivImage);
                    etUrl.setText(url);
                }
            }
        });
        getViewModel();
        defineAddListener();
    }

    private void defineAddListener() {
        Button btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(v -> {
            Card card = getCard();
            if(card.isValid()) {
                addCard(card);
            } else {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Card getCard() {
        String name = etName.getText().toString().trim();
        String level = etLevel.getText().toString().trim();
        String elixir = etElixir.getText().toString().trim();
        String url = etUrl.getText().toString().trim();
        Type type = (Type) spType.getSelectedItem();
        Card card = new Card();
        card.name = name;
        card.elixir = Double.parseDouble(elixir);
        card.level = Integer.parseInt(level);
        card.url = url;
        card.idtype = type.id;
        return card;
    }

    private void addCard(Card card) {
        pvm.insertCard(card);
    }

    private void getViewModel() {
        pvm = new ViewModelProvider(this).get(CardViewModel.class);

        pvm.getInsertResults().observe(this, list -> {
            if(list.get(0) > 0) {
                if(firstTime) {
                    firstTime = false;
                    alert();
                } else {
                    cleanFields();
                }
            } else {
                Toast.makeText(this, "Esa carta ya existe en el sistema", Toast.LENGTH_LONG).show();
            }
        });

        TypeViewModel tvm = new ViewModelProvider(this).get(TypeViewModel.class);
        tvm.getTypes().observe(this, types -> {
            Type type = new Type();
            type.id = 0;
            type.name = getString(R.string.default_type);
            types.add(0, type);
            ArrayAdapter<Type> adapter =
                    new ArrayAdapter<Type>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
            spType.setAdapter(adapter);
        });
    }

    private void alert() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setTitle("¿Desea continuar?")
                .setMessage("La carta se ha insertado correctamente, ¿desea seguir agregando cartas?")
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanFields();
                    }
                });
        builder.create().show();
    }

    private void cleanFields() {
        etUrl.setText("");
        etElixir.setText("");
        etLevel.setText("");
        etName.setText("");
        spType.setSelection(0);
    }
}