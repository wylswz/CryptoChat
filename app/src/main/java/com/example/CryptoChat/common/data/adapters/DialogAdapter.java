package com.example.CryptoChat.common.data.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.example.CryptoChat.utils.DBUtils;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class extends DialogsListAdapter provided by ChatKit library therefore it
 * must follow the pattern of that library
 *
 * - Adapter maintains a list locally, which is set whenever instantiated
 * - When need to write database, write db first then update local list
 * - Methods like sorting are trivial, our app doesn't sort dialog
 *
 * TODO:
 *  - When new message arrive, following things will be done
 *  - 1. Insert the new message to local database with receiverId set to "0" and get ID
 *  - 2. Find if there's a Dialog with receiverID == newMessage.senderId
 *  - 3. If there exists, then update its latestMessageId with the new message id
 *  - 4. If not, create a new dialog and set latestMessageId with new message id
 *  - 5. Notify the adapter
 *
 * @param <DIALOG>
 */
public class DialogAdapter<DIALOG extends IDialog> extends DialogsListAdapter<DIALOG> {

    private List<DIALOG> items;
    private SQLiteDialogProvider dp;

    public DialogAdapter(ImageLoader imageLoader, Context ctx) {
        super(imageLoader);
        this.items = new ArrayList<>();
        this.dp = SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(ctx));
    }


    @Override
    public void addItem(DIALOG dialog) {
        dp.addDialog((Dialog) dialog);
        super.addItem(dialog);

    }

    @Override
    public void addItem(int position, DIALOG dialog) {
        dp.addDialog((Dialog) dialog);
        super.addItem(position, dialog);

    }

    /**
     * Move an item
     *
     * @param fromPosition the actual position of the item
     * @param toPosition   the new position of the item
     */
    @Override
    public void moveItem(int fromPosition, int toPosition) {
        super.moveItem(fromPosition, toPosition);
    }

    /**
     * Update dialog by position in dialogs list
     *
     * @param position position in dialogs list
     * @param item     new dialog item
     */
    @Override
    public void updateItem(int position, DIALOG item) {
        super.updateItem(position, item);
        dp.updateDialog((Dialog) item);
    }

    /**
     * Update dialog by dialog id
     *
     * @param item new dialog item
     */
    @Override
    public void updateItemById(DIALOG item) {
        super.updateItemById(item);
        dp.updateDialog((Dialog) item);
    }

    /**
     * Upsert dialog in dialogs list or add it to then end of dialogs list
     *
     * @param item dialog item
     */
    @Override
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
    @Override
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
    @Override
    @SuppressWarnings("unchecked")
    public boolean updateDialogWithMessage(String dialogId, IMessage message) {
        boolean dialogExist = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(dialogId)) {
                items.get(i).setLastMessage(message);
                dp.updateDialog((Dialog) items.get(i));
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
    @Override
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
    @Override
    public void sort(Comparator<DIALOG> comparator) {
        Collections.sort(items, comparator);
        notifyDataSetChanged();
    }

    @Override
    public void deleteById(String id) {
        dp.dropDialog(id);
        super.deleteById(id);
    }

    public List<DIALOG> getItems() {
        return items;
    }


}

