package com.example.ducnguyen.demoandroidadvanceapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextName.setText(mContacts.get(position).getName());
        holder.mTextPhone.setText(mContacts.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextName, mTextPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_contact_name);
            mTextPhone = itemView.findViewById(R.id.text_contact_phone);
        }
    }
}
