package com.example.CryptoChat.common.data.adapters;

import androidx.annotation.Nullable;

import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DialogAdapter<DIALOG extends IDialog> extends DialogsListAdapter<DIALOG> {
    private List<DIALOG> items;
    private SQLiteDialogProvider dp;
    public DialogAdapter(ImageLoader imageLoader, SQLiteDialogProvider dp) {
        super(imageLoader);
        this.items = new ArrayList<>();
        this.dp = dp;
        this.setItems((List<DIALOG>) dp.getDialogs());
    }


    @Override
    public void addItem(DIALOG dialog) {
        dp.addDialog((Dialog) dialog);
        super.addItem(dialog);

    }

    public void addItem(int position, DIALOG dialog) {
        dp.addDialog((Dialog) dialog);
        super.addItem(position,dialog);

    }

    /**
     * Move an item
     * @param fromPosition the actual position of the item
     * @param toPosition the new position of the item
     */
    public void moveItem(int fromPosition, int toPosition) {
        super.moveItem(fromPosition, toPosition);
    }

    /**
     * Update dialog by position in dialogs list
     *
     * @param position position in dialogs list
     * @param item     new dialog item
     */
    public void updateItem(int position, DIALOG item) {
        super.updateItem(position, item);
        dp.updateDialog((Dialog)item);
    }

    /**
     * Update dialog by dialog id
     *
     * @param item new dialog item
     */
    public void updateItemById(DIALOG item) {
        super.updateItemById(item);
        dp.updateDialog((Dialog)item);
    }

    /**
     * Upsert dialog in dialogs list or add it to then end of dialogs list
     *
     * @param item dialog item
     */
    public void upsertItem(DIALOG item) {
        super.upsertItem(item);
    }

    /**
     * Find an item by its id
     *
     * @param id the wanted item's id
     * @return the found item, or null
     */
    @Nullable
    public DIALOG getItemById(String id) {
        return super.getItemById(id);
    }

    /**
     * Update last message in dialog and swap item to top of list.
     *
     * @param dialogId Dialog ID
     * @param message  New message
     * @return false if dialog doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public boolean updateDialogWithMessage(String dialogId, IMessage message) {
        boolean dialogExist = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(dialogId)) {
                items.get(i).setLastMessage(message);
                dp.updateDialog((Dialog)items.get(i));
                notifyItemChanged(i);
                if (i != 0) {
                    Collections.swap(items, i, 0);
                    notifyItemMoved(i, 0);
                }
                dialogExist = true;
                break;
            }
        }
        return dialogExist;
    }

    /**
     * Sort dialog by last message date
     */
    public void sortByLastMessageDate() {
        Collections.sort(items, new Comparator<DIALOG>() {
            @Override
            public int compare(DIALOG o1, DIALOG o2) {
                if (o1.getLastMessage().getCreatedAt().after(o2.getLastMessage().getCreatedAt())) {
                    return -1;
                } else if (o1.getLastMessage().getCreatedAt().before(o2.getLastMessage().getCreatedAt())) {
                    return 1;
                } else return 0;
            }
        });
        notifyDataSetChanged();
    }

    /**
     * Sort items with rules of comparator
     *
     * @param comparator Comparator
     */
    public void sort(Comparator<DIALOG> comparator) {
        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }



    }

