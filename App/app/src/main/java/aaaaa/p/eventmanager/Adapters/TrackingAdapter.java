package aaaaa.p.eventmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import aaaaa.p.eventmanager.Modelos.ListaGrupos;
import aaaaa.p.eventmanager.Modelos.Tracking;
import aaaaa.p.eventmanager.R;

public class TrackingAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Tracking> lista;

    public TrackingAdapter(Context context, int layout, List<Tracking> lista) {
        this.context = context;
        this.layout = layout;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return this.lista.size();
    }

    @Override
    public Object getItem(int position){
        return this.lista.get(position);
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

        String currentName = lista.get(position).getName();

        TextView textView = (TextView) view.findViewById(R.id.textViewItemName);
        textView.setText(currentName);

        return view;
    }

}
