package fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

import com.example.emilyhales.tickettoride.R;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.

import modelclasses.TrainCardColor;
import static modelclasses.TrainCardColor.*;
import presenter.ChooseClaimColorPresenter;
import presenter.IChooseClaimColorPresenter;

public class ChooseClaimColorFragment extends DialogFragment implements IChooseClaimColorView {
    private IChooseClaimColorPresenter presenter;
    private Button red;
    private Button orange;
    private Button yellow;
    private Button green;
    private Button blue;
    private Button purple;
    private Button black;
    private Button white;

    private BiMap<Button, TrainCardColor> buttonColorBiMap = HashBiMap.create();

    public void setPresenter(IChooseClaimColorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayPossibleColors(Set<TrainCardColor> possibleColors) {
        for (TrainCardColor color : TrainCardColor.values()) {
            Button button = buttonColorBiMap.inverse().get(color);
            if (possibleColors.contains(color)) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_choose_claim_color,
                new ConstraintLayout(getActivity()), false);
        red = view.findViewById(R.id.ccButtonRed);
        orange = view.findViewById(R.id.ccButtonOrange);
        yellow = view.findViewById(R.id.ccButtonYellow);
        green = view.findViewById(R.id.ccButtonGreen);
        blue = view.findViewById(R.id.ccButtonBlue);
        purple = view.findViewById(R.id.ccButtonPurple);
        black = view.findViewById(R.id.ccButtonBlack);
        white = view.findViewById(R.id.ccButtonWhite);
        buttonColorBiMap.put(red, RED);
        buttonColorBiMap.put(orange, ORANGE);
        buttonColorBiMap.put(yellow, YELLOW);
        buttonColorBiMap.put(green, GREEN);
        buttonColorBiMap.put(blue, BLUE);
        buttonColorBiMap.put(purple, PURPLE);
        buttonColorBiMap.put(black, BLACK);
        buttonColorBiMap.put(white, WHITE);

        for (Button button : buttonColorBiMap.keySet()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.chooseClaimColor(buttonColorBiMap.get(view));
                }
            });
        }

    }
}
