package com.example.barang_hilang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barang_hilang.Model.model_lihat_data;
import com.example.barang_hilang.R;

import org.w3c.dom.Text;

import java.util.List;

public class lihat_data_adapter extends RecyclerView.Adapter<lihat_data_adapter.ViewHolder> {
    private Context context;
    private List<model_lihat_data> data;

    public lihat_data_adapter(Context context, List<model_lihat_data> data){
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_data, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model_lihat_data lihat_data = data.get(position);
        holder.nis.setText(lihat_data.getNis());
        holder.nama.setText(lihat_data.getNama());
        holder.rombel.setText(lihat_data.getRombel());
        holder.rayon.setText(lihat_data.getRayon());
        holder.item_name.setText(lihat_data.getItem_name());
        holder.item_size.setText(lihat_data.getItem_size());
        holder.merek.setText(lihat_data.getMerek());
        holder.tempat.setText(lihat_data.getTempat());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView nis,nama,rombel,rayon,item_name,item_size,merek,tempat;

        public ViewHolder(View view) {
            super(view);
            nis = view.findViewById(R.id.nis);
            nama = view.findViewById(R.id.nama);
            rombel = view.findViewById(R.id.rombel);
            rayon = view.findViewById(R.id.rayon);
            item_name = view.findViewById(R.id.item);
            item_size = view.findViewById(R.id.size);
            merek = view.findViewById(R.id.merek);
            tempat = view.findViewById(R.id.tempat);
        }
    }
}
