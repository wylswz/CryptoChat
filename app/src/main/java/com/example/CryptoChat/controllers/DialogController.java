package com.example.CryptoChat.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.fake.DialogsFixtures;
import com.example.CryptoChat.common.data.models.Dialog;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialogController.OnFragmentInteractionListener} interface
 * to handle interaction events.

 */
public class DialogController extends Fragment implements
        DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private DialogsListAdapter<Dialog> adapter;

    private DialogsList dialogs;
    private ImageLoader imageLoader = new ImageLoader() {
        @Override
        public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
            Picasso.get().load(url).into(imageView);
        }
    };
    private OnFragmentInteractionListener mListener;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_controller, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        this.adapter = new DialogsListAdapter<>(this.imageLoader);
        dialogs = (DialogsList) view.findViewById(R.id.dialogsList);
        this.adapter.setItems(DialogsFixtures.getDialogs());
        adapter.setOnDialogClickListener(this);
        adapter.setOnDialogLongClickListener(this);

        Log.e("tag", adapter.toString() + dialogs.toString());

        dialogs.setAdapter(this.adapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDialogClick(Dialog dialog) {
        //DefaultMessagesActivity.open(this);
        MessageController.open(this.getContext());
    }


    @Override
    public void onDialogLongClick(Dialog dialog) {
        //AppUtils.showToast(this.getContext(),dialog.getDialogName(),false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.delete_dialog_confirm)).setTitle("Delete");

        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> adapter.deleteById(dialog.getId()));
        builder.setNegativeButton(R.string.no, (dialogInterface, i) -> {

        });

        AlertDialog delWarn = builder.create();
        delWarn.show();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}