package org.aff.tattoosDB.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.aff.tattoosDB.R;
import org.aff.tattoosDB.model.entity.TattooType;
import org.aff.tattoosDB.view.adapter.TattooAdapter;
import org.aff.tattoosDB.viewmodel.TattooViewModel;

import java.util.List;


public class TattoosListFragment extends Fragment {

    private MaterialCardView card;
    private ActionMode actionMode;
    private TattooViewModel tmv;

    public TattoosListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tattoos_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvTattoo = view.findViewById(R.id.recyclerView);
        rvTattoo.setLayoutManager(new LinearLayoutManager(getContext()));
        tmv = new ViewModelProvider(requireActivity()).get(TattooViewModel.class);
        TattooAdapter tattooAdapter = new TattooAdapter(getContext());

        card = view.findViewById(R.id.card);
        rvTattoo.setAdapter(tattooAdapter);

        LiveData<List<TattooType>> listTattooType = tmv.getAllTattoos();
        listTattooType.observe(getViewLifecycleOwner(), tattos -> {
            tattooAdapter.setTattooList(tattos);
        });

        FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        fab.setOnClickListener((View v) -> {
                NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_SecondFragment);
        });


        }


}