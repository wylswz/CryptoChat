package com.example.CryptoChat.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.adapters.MessageAdapter;
import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.models.Message;
import com.example.CryptoChat.common.data.models.User;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.common.data.provider.SQLiteUserProvider;
import com.example.CryptoChat.services.AuthenticationManager;
import com.example.CryptoChat.services.FirebaseAPIs;
import com.example.CryptoChat.utils.AppUtils;
import com.example.CryptoChat.utils.DBUtils;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class MessageController extends AppCompatActivity implements MessagesListAdapter.OnLoadMoreListener,
        MessagesListAdapter.SelectionListener, MessageInput.InputListener, MessageInput.TypingListener, MessageInput.AttachmentsListener,MessageInput.OnFocusChangeListener {
    private static final int TOTAL_MESSAGES_COUNT = 10000;

    protected String senderId;
    protected String receiverId;
    protected ImageLoader imageLoader;
    protected MessageAdapter<Message> messagesAdapter;
    private SQLiteMessageProvider mp;
    private SQLiteDialogProvider dp;
    private SQLiteUserProvider cp;
    private DaoSession ds;


    private Menu menu;
    private int selectionCount;
    private MessagesList messagesList;

    private int offset;
    private int limit;




    private Dialog dialog;

    public static void open(Context context, String receiverId) {
        Intent intent = new Intent(context, MessageController.class);
        intent.putExtra("receiverId", receiverId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.receiverId = getIntent().getStringExtra("receiverId");
        this.senderId = AuthenticationManager.getUid();
        setContentView(R.layout.activity_messages_controller);

        ds = DBUtils.getDaoSession(this);
        mp = SQLiteMessageProvider.getInstance(ds);
        dp = SQLiteDialogProvider.getInstance(ds);
        cp = SQLiteUserProvider.getInstance(ds);
        offset = 0;
        limit = 10;
        try {
            this.dialog = dp.getDialogByReceiverId(receiverId);
        } catch (ObjectNotExistException e) {
            this.dialog = null;
        }

        imageLoader = (imageView, url, payload) -> Picasso.get().load(url).into(imageView);

    }

    private void initAdapter() {

        messagesAdapter = new MessageAdapter<Message>(senderId, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setLoadMoreListener(this);

        this.messagesList.setAdapter(messagesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            User receiver = cp.getUser(receiverId);
            getSupportActionBar().setTitle(receiver.getAlias());

        } catch (Exception e) {
            Log.e("MessageController", "Contact not found when setting toolbar title");
        }

        this.messagesList = findViewById(R.id.messagesList);
        initAdapter();
        MessageInput input = findViewById(R.id.input);
        input.setInputListener(this);
        input.setTypingListener(this);
        input.setAttachmentsListener(this);

        loadMessages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_actions_menu, menu);
        getMenuInflater().inflate(R.menu.toolbar_message, menu);
        onSelectionChanged(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                List<Message> messages = messagesAdapter.getSelectedMessages();
                for (Message m : messages) {
                    mp.dropMessageById(m.getId());
                    messagesAdapter.delete(m);
                }

                break;
            case R.id.action_copy:
                messagesAdapter.copySelectedMessagesText(this, getMessageStringFormatter(), true);
                AppUtils.showToast(this, R.string.copied_message, true);
                break;
            case R.id.edit_contact_in_chat:

                ContactSettingsController.open(this, this.receiverId);


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            messagesAdapter.unselectAllItems();
        }
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages();
        }
    }

    @Override
    public void onSelectionChanged(int count) {
        this.selectionCount = count;
        menu.findItem(R.id.action_delete).setVisible(count > 0);
        menu.findItem(R.id.action_copy).setVisible(count > 0);
    }

    /**
     * Load message sent by/to receiver
     */
    protected void loadMessages() {
        //imitation of internet connection
        new Handler().postDelayed(() -> {

            List<Message> messages = mp.getMessages(receiverId, limit, offset);
            offset = offset + limit;
            messagesAdapter.addToEnd(messages, false);
        }, 100);
    }


    private MessagesListAdapter.Formatter<Message> getMessageStringFormatter() {
        return message -> {
            String createdAt = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                    .format(message.getCreatedAt());

            String text = message.getText();
            if (text == null) text = "[attachment]";

            return String.format(Locale.getDefault(), "%s: %s (%s)",
                    message.getUser().getName(), text, createdAt);
        };
    }


    @Override
    public void onStartTyping() {

    }

    @Override
    public void onStopTyping() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onSubmit(CharSequence input) {
        try {
            Message msg = new Message(UUID.randomUUID().toString(), AuthenticationManager.getMe(), cp.getUser(receiverId), input.toString());
            msg.setReceiverId(receiverId);
            mp.insertMessage(msg);
            messagesAdapter.addToStart(msg,true);
            FirebaseAPIs.writeMsg(msg, senderId, receiverId);

            offset += 1;
            if (this.dialog == null) {
                try {
                    User receiver = cp.getUser(receiverId);
                    this.dialog = new Dialog(receiver.getAlias(), receiver.getAvatar(), receiverId, msg, 0);
                    dp.addDialog(this.dialog);
                } catch (Exception e) {
                    Log.e("MessageController", "Contact not found when creating dialog");
                }

            }
            this.dialog.setLastMessageId(msg.getId());
            dp.updateDialog(this.dialog);
        } catch (Exception e) {
            Log.e("MessageController", "User not exist when sending message");
        }


        // TODO: Send the message to server side along with receiver ID
        return true;
    }

    @Override
    public void onAddAttachments() {

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
