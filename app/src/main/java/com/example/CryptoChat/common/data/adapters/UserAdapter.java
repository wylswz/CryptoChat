package com.example.CryptoChat.common.data.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.provider.ContactProvider;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ContactProvider cp;
    private Context context;



    public UserAdapter(ContactProvider cp, Context context){
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
        holder.init(cp.getUser(position).getName());
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
        public void init(String name) {
            TextView nameText = (TextView) cell.findViewById(R.id.contact_cell_name);
            nameText.setText(name);
        }
    }

    public void deleteItem(int position){
        this.cp.deleteUser(position);
        Log.v("Remaining", cp.getCount() + "");
        notifyItemRemoved(position);
    }

    public Context getContext(){
        return this.context;
    }

}
