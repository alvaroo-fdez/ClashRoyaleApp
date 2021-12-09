package org.izv.clashroyale.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import org.izv.card.R;
import org.izv.clashroyale.model.entity.Card;
import org.izv.clashroyale.model.entity.Type;
import org.izv.clashroyale.viewmodel.CardViewModel;
import org.izv.clashroyale.viewmodel.TypeViewModel;

public class EditCard extends AppCompatActivity {
    private EditText etName, etLevel, etElixir, etUrl;
    private Spinner spType;
    private ImageView ivImage;
    private Card card;
    private CardViewModel pvm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        initialize();
    }

    private void initialize() {
        card = getIntent().getExtras().getParcelable("card");

        spType = findViewById(R.id.spType);
        etName = findViewById(R.id.etName);
        etElixir = findViewById(R.id.etElixir);
        etLevel = findViewById(R.id.etLevel);
        etUrl = findViewById(R.id.etUrl);
        ivImage = findViewById(R.id.ivImage);

        etName.setText(card.name);
        etElixir.setText(card.elixir + "");
        etLevel.setText(card.level + "");
        etUrl.setText(card.url);
        Glide.with(this).load(card.url).into(ivImage);

        CardViewModel pvm = new ViewModelProvider(this).get(CardViewModel.class);
        TypeViewModel tvm = new ViewModelProvider(this).get(TypeViewModel.class);
        tvm.getTypes().observe(this, types -> {
            Type type = new Type();
            type.id = 0;
            type.name = getString(R.string.default_type);
            types.add(0, type);
            ArrayAdapter<Type> adapter =
                    new ArrayAdapter<Type>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
            spType.setAdapter(adapter);
            spType.setSelection((int) card.idtype);
        });

        Button btEdit = findViewById(R.id.btEdit);
        btEdit.setOnClickListener(v -> {
            Card p = getCartas();
            if (p.isValid()){
                pvm.updateCard(p);
                finish();
            }
        });

        Button btCancel = findViewById(R.id.btCancel);
        btCancel.setOnClickListener(v -> {
                finish();
        });

        ImageButton btDelete = findViewById(R.id.imB_delete);
        btDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder  = new AlertDialog.Builder(this);
            builder.setTitle("¿Está seguro que desea borrar la carta?")
                    .setMessage("Si continua con la acción, se borrará la carta indicada")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //SI NO DESEA BORRAR LA CARTA
                        }
                    })
                    .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Card c = getCartas();
                            if (c.isValid()){
                                pvm.deleteCards(c);
                                finish();
                            }
                        }
                    });
            builder.create().show();
        });

    }

    private Card getCartas() {
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
        card.id = this.card.id;
        return card;
    }
}