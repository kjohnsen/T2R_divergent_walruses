package fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import modelclasses.ChatMessage;
import presenter.ChatPresenter;
import presenter.IChatPresenter;

public class ChatFragment extends Fragment implements IChatView {
    private RecyclerView chatRecyclerView;
    private EditText chatEditText;
    private Button sendButton;
    private IChatPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        presenter = new ChatPresenter(this);

        chatRecyclerView = v.findViewById(R.id.chatRecylcerView);
        ChatAdapter chatAdapter = new ChatAdapter();
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        chatEditText = v.findViewById(R.id.chatEditText);
        chatEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.chatMessageEditTextDidChange(chatEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendButton = v.findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendChatMessageTask sendChatMessageTask = new SendChatMessageTask();
                sendChatMessageTask.execute(chatEditText.getText().toString());
            }
        });

        sendButton.setEnabled(!chatEditText.getText().toString().isEmpty());
        return v;
    }

    @Override
    public void sendButtonSetEnabled(Boolean enabled) {
        sendButton.setEnabled(enabled);
    }

    @Override
    public void chatViewShouldReloadData() {
        ChatAdapter chatAdapter = (ChatAdapter)chatRecyclerView.getAdapter();
        chatAdapter.setChatMessages(presenter.getChatMessages());
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private class SendChatMessageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return presenter.sendMessageButtonWasPressed(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                showError(s);
            }
        }
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
        private List<ChatMessage> chatMessages;

        public ChatAdapter() {
            chatMessages = presenter.getChatMessages();
        }

        public void setChatMessages(List<ChatMessage> chatMessages) {
            this.chatMessages = chatMessages;
        }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View messageView = layoutInflater.inflate(R.layout.chat_view_holder, parent, false);
            ChatViewHolder chatViewHolder = new ChatViewHolder(messageView);
            return chatViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
            ChatMessage chatMessage = chatMessages.get(position);

            holder.setMessageText(chatMessage.getMessage());
            holder.setUserNameText(chatMessage.getUsername());
        }

        @Override
        public int getItemCount() {
            return chatMessages.size();
        }


        public class ChatViewHolder extends RecyclerView.ViewHolder {
            private TextView usernameTextView;
            private TextView chatMessageTextView;

            public ChatViewHolder(View itemView) {
                super(itemView);
                usernameTextView = itemView.findViewById(R.id.userTextView);
                chatMessageTextView = itemView.findViewById(R.id.messageTextView);
            }

            public void setUserNameText(String username) {
                usernameTextView.setText(username);
            }

            public void setMessageText(String message) {
                chatMessageTextView.setText(message);
            }

        }
    }
}
