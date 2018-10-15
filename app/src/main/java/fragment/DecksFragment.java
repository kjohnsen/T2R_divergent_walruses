package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.emilyhales.tickettoride.R;

public class DecksFragment extends Fragment implements IDecksView{

    Button popupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_decks, container, false);
        popupButton = v.findViewById(R.id.popup);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pops up a dialog. The input from the dialog isn't attached to anything, currently
                ChooseDestinationsFragment cd = new ChooseDestinationsFragment();
                cd.show(DecksFragment.this.getActivity().getSupportFragmentManager(), "example");
            }
        });
        return v;
    }
}
