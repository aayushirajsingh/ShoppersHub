package com.example.shoppershub.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.shoppershub.Constants;
import com.example.shoppershub.Model.CartModel;
import com.example.shoppershub.R;
import com.example.shoppershub.Retrofit.ApiInterface;
import com.example.shoppershub.Retrofit.RetrofitClient;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<CartModel> arrCart;

    public interface OnItemDeleteListener {
        void onItemDeleted(CartModel deletedItem);
    }

    private OnItemDeleteListener itemDeleteListener;

    public CartAdapter() {
    }

    public CartAdapter(Context context, List<CartModel> arrCart, OnItemDeleteListener itemDeleteListener) {
        this.context = context;
        this.arrCart = arrCart;
        this.itemDeleteListener = itemDeleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recy_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.txtCartName.getContext()).load(Constants.BASE_URL_IP+"images/"+arrCart.get(position).getImage()).centerCrop().into(holder.imgCartItem);
        holder.txtCartName.setText(arrCart.get(position).getName());
        holder.txtCartPrice.setText("₹ "+arrCart.get(position).getPrice());
        if (arrCart.get(position).getColour() != null) {
            int productColour = Color.parseColor(arrCart.get(position).getColour());
            holder.circleImageView.setColorFilter(productColour);
        } else {
            holder.circleImageView.setColorFilter(Color.WHITE);
        }
        holder.txtCartSize.setText(arrCart.get(position).getSize());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();

                itemDeleteListener.onItemDeleted(arrCart.get(pos));

                deleteProduct(arrCart.get(pos).getCart_id());
                arrCart.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, arrCart.size());

            }
        });
    }

    public void deleteProduct(int cart_id){
        ApiInterface apiInterface = RetrofitClient.getInstance().getApi();
        Call<CartModel> call = apiInterface.delete_cart(cart_id);

        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if(response.isSuccessful()){
                    CartModel cartModel = response.body();
                    String message = cartModel.getMessage();

                    if ("Success".equals(message)) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Deletion Failed", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "API Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgCartItem;
        CircleImageView circleImageView;
        TextView txtCartName, txtCartPrice, txtCartSize;
        Button btnDelete;

        public ViewHolder(View cartView){
            super(cartView);

            btnDelete = cartView.findViewById(R.id.btn_delete);
            imgCartItem = cartView.findViewById(R.id.img_cartitem);
            circleImageView = cartView.findViewById(R.id.cr_img);
            txtCartName = cartView.findViewById(R.id.tv_cartitemname);
            txtCartPrice = cartView.findViewById(R.id.tv_cartitemprice);
            txtCartSize = cartView.findViewById(R.id.tv_size);
        }
    }
}