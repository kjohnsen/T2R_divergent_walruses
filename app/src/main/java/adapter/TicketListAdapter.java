package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import modelclasses.City;
import modelclasses.DestinationCard;
import presenter.IChooseDestinationsPresenter;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketHolder> {

    private List<DestinationCard> tickets;
    private Context context;
    private IChooseDestinationsPresenter presenter;

    public TicketListAdapter(List<DestinationCard> tickets, Context context, IChooseDestinationsPresenter presenter) {
        this.tickets = tickets;
        this.context = context;
        this.presenter = presenter;
    }
    @Override
    @NonNull
    public TicketListAdapter.TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
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
                    presenter.setCardSelected(ticket, b);
                }
            });
        }
        void bind(DestinationCard card) {
            ticket = card;
            StringBuilder description = new StringBuilder();
            City cities[] = card.getCities();
            int points = card.getPoints();
            description.append(cities[0].getName()).append(" to ").append(cities[1].getName()).append(", ").append(points).append(" points");
            ticketDesc.setText(description);
        }
    }
}
