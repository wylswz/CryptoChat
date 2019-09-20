package com.example.CryptoChat.common.data.adapters;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

public class DialogAdapter extends DialogsListAdapter {
    public DialogAdapter(ImageLoader imageLoader) {
        super(imageLoader);
    }

    public DialogAdapter(int itemLayoutId, ImageLoader imageLoader) {
        super(itemLayoutId, imageLoader);
    }

    public DialogAdapter(int itemLayoutId, Class holderClass, ImageLoader imageLoader) {
        super(itemLayoutId, holderClass, imageLoader);
    }
}
