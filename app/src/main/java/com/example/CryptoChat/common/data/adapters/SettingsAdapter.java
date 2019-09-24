package com.example.CryptoChat.common.data.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoChat.R;

public class SettingsAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View cell;
        public MyViewHolder(View v) {
            super(v);
            cell = v;

        }
        public void init(String name) {
            TextView nameText = (TextView) cell.findViewById(R.id.settings_cell_name);
            nameText.setText(name);
        }
    }
}
