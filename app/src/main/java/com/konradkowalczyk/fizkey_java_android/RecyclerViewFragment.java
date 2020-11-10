package com.konradkowalczyk.fizkey_java_android;


import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;


public class RecyclerViewFragment extends androidx.fragment.app.Fragment {

    private String[] titels;
    private Class[] classes;

    public RecyclerViewFragment(String[] titels, Class[] classes) {
        this.titels=titels;
        this.classes=classes;
    }


    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          android.os.Bundle savedInstanceState) {

        RecyclerView menuView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recycler_view,container,false);

        //stworzenie i ustawienie adaptera
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titels);
        menuView.setAdapter(adapter);

        //dodanie jak ma wygladac layout (cardView - rodzaj wyswietlania)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        menuView.setLayoutManager(layoutManager);

        //onClick - reakcja na przycisniecie cardView
        adapter.setListener(new RecyclerViewAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getActivity(), classes[position] );
                getActivity().startActivity(intent);
            }
        });


        setRetainInstance(true);


        return menuView;
    }

}
