package com.example.shoppershub.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shoppershub.Constants;
import com.example.shoppershub.Interface.ItemClick;
import com.example.shoppershub.Model.ProductsModel;
import com.example.shoppershub.R;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final ItemClick itemClick;
    Context context;
    List<ProductsModel> data;

    public ItemsAdapter(ItemClick itemClick, Context context, List<ProductsModel> data) {
        this.itemClick = itemClick;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recy_view_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.txtName.getContext()).load(Constants.BASE_URL_IP+"images/"+data.get(position).getImage()).centerCrop().into(holder.imgItem);
        holder.txtName.setText(data.get(position).getName());
        holder.txtPrice.setText("₹ "+data.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    int pos = holder.getAdapterPosition();
                    itemClick.onItemClick(data.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItem;
        TextView txtName, txtPrice;

        public ViewHolder(View itemView){
            super(itemView);

            imgItem = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.tv_itemname);
            txtPrice = itemView.findViewById(R.id.tv_itemprice);

        }
    }
}