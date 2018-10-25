package fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import java.util.List;

import adapter.TicketListAdapter;
import modelclasses.City;
import modelclasses.DestinationCard;
import presenter.ChooseDestinationsPresenter;
import presenter.IChooseDestinationsPresenter;

public class ChooseDestinationsFragment extends DialogFragment implements IChooseDestinationsView {

    RecyclerView ticketList;
    Button selectButton;
    IChooseDestinationsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_choose_destinations, new ConstraintLayout(getActivity()), false);
        presenter = new ChooseDestinationsPresenter(this);
        if (presenter.isGameStart()) {
            displayTickets(presenter.getPlayerCards());
        }
        ticketList = view.findViewById(R.id.ticketList);
        RecyclerView.LayoutManager gameListManager = new LinearLayoutManager(ChooseDestinationsFragment.this.getActivity());
        ticketList.setLayoutManager(gameListManager);
        selectButton = view.findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectTicketsTask s = new SelectTicketsTask();
                s.execute();
            }
        });


        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    @Override
    public void setSelectEnabled(final boolean enabled) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectButton.setEnabled(enabled);
            }
        });
    }

    @Override
    public void displayTickets(List<DestinationCard> cards) {
        TicketListAdapter adapter = new TicketListAdapter(cards, getContext(), presenter);
        ticketList.setAdapter(adapter);
    }

    public class SelectTicketsTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... s) {
            return presenter.selectCards();
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(ChooseDestinationsFragment.this.getActivity(), param, Toast.LENGTH_LONG).show();
            } else {
                presenter.onSwitchView();
                ChooseDestinationsFragment.this.dismiss();
            }
        }
    }
}
