package aaaaa.p.eventmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import aaaaa.p.eventmanager.Modelos.ListaGrupos;
import aaaaa.p.eventmanager.R;

public class GruposAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ListaGrupos lista;

    public GruposAdapter(Context context, int layout, ListaGrupos lista) {
        this.context = context;
        this.layout = layout;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return this.lista.grupos.size();
    }

    @Override
    public Object getItem(int position){
        return this.lista.grupos.get(position);
    }

    @Override
    public long getItemId(int id){
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        View view;

        LayoutInflater inflater = LayoutInflater.from(this.context);
        view = inflater.inflate(R.layout.items_grupos, null);

        String currentName = lista.grupos.get(position).getName();

        TextView textView = (TextView) view.findViewById(R.id.textViewItemName);
        textView.setText(currentName);

        return view;
    }

}
