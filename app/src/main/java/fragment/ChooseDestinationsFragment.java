package fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;

import modelclasses.DestinationCard;

public class ChooseDestinationsFragment extends DialogFragment implements IChooseDestinationsView {

    ArrayList<DestinationCard> tickets;
    ArrayList<DestinationCard> selected;
    RecyclerView ticketList;
    Button popupButton;
    boolean startingCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_choose_destinations, new ConstraintLayout(getActivity()), false);
        ticketList = view.findViewById(R.id.ticketList);
        RecyclerView.LayoutManager gameListManager = new LinearLayoutManager(ChooseDestinationsFragment.this.getActivity());
        ticketList.setLayoutManager(gameListManager);
        Button button = view.findViewById(R.id.selectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    @Override
    public void displayTickets(ArrayList<DestinationCard> cards) {
        //TODO: Implement this function
    }

    public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketHolder> {
        private ArrayList<DestinationCard> tickets;
        TicketListAdapter(ArrayList<DestinationCard> tickets) {
            this.tickets = tickets;
        }
        @Override
        @NonNull
        public TicketListAdapter.TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(ChooseDestinationsFragment.this.getActivity());
            return new TicketHolder(inflater.inflate(R.layout.item_player_list, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull TicketListAdapter.TicketHolder holder, int position) {
            holder.bind(tickets.get(position));
        }

        @Override
        public int getItemCount() {
            return tickets.size();
        }
        class TicketHolder extends RecyclerView.ViewHolder {
            private TextView ticketDesc;
            private CheckBox ticketCheck;
            private DestinationCard ticket;
            TicketHolder(View view) {
                super(view);
                ticketDesc = view.findViewById(R.id.itemDesc);
                ticketCheck = view.findViewById(R.id.itemCheck);
                ticketCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            selected.add(ticket);
                        } else {
                            selected.remove(ticket);
                        }
                    }
                });
            }
            void bind(DestinationCard card) {
                ticket = card;
                StringBuilder description = new StringBuilder();
                String cities[] = card.getCities();
                int points = card.getPoints();
                description.append(cities[0]).append(" to ").append(cities[1]).append(", ").append(points).append(" points");
                ticketDesc.setText(description);
            }
        }
    }
}
