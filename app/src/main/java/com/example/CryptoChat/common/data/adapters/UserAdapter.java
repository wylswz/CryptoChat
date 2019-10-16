package com.example.CryptoChat.common.data.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.PointerIconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.provider.ContactProvider;
import com.example.CryptoChat.controllers.MessageController;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ContactProvider cp;
    private Context context;

    public UserAdapter(ContactProvider cp, Context context) {
        this.cp = cp;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contact = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_contact, parent, false);
        //TextView nameField = (TextView) conatct.findViewById(R.id.contact_cell_name);
        MyViewHolder vh = new MyViewHolder(contact);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.init(cp.getUser(position).getAlias(), cp.getUser(position).getAvatar());
        holder.cell.findViewById(R.id.contact_cell_constraint).setOnClickListener(view -> {
            MessageController.open(getContext(), cp.getUser(position).getId());
            Log.i("ContactListAdapter", "Clicked");
        });

    }

    @Override
    public int getItemCount() {
        return this.cp.getCount();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cell;

        public MyViewHolder(View v) {
            super(v);
            cell = v;

        }

        public void init(String name, String avatar) {
            TextView nameText = (TextView) cell.findViewById(R.id.contact_cell_name);
            CircularImageView imv = cell.findViewById(R.id.contact_cell_avatar);
            nameText.setText(name);
            Picasso.get().load(avatar).into(imv);
        }

    }

    public void deleteItem(int position) {
        this.cp.deleteUser(position);
        Log.v("Remaining", cp.getCount() + "");
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return this.context;
    }

    public void sort(){
        this.cp.sortByAlias();
        this.notifyDataSetChanged();
    }

}
