package fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.emilyhales.tickettoride.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelclasses.TrainCardColor;
import static modelclasses.TrainCardColor.*;
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

    private Map<Button, TrainCardColor> buttonColorMap = new HashMap<>();
    private Map<TrainCardColor, Button> colorButtonMap = new HashMap<>();

    @Override
    public void setPresenter(IChooseClaimColorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayPossibleColors(Set<TrainCardColor> possibleColors) {
        for (TrainCardColor color : TrainCardColor.values()) {
            Button button = colorButtonMap.get(color);
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

        for (Button button : buttonColorMap.keySet()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.chooseClaimColor(buttonColorMap.get(view));
                    presenter.onSwitchView();
                    ChooseClaimColorFragment.this.dismiss();
                }
            });
        }
        putInMaps(red, RED);
        putInMaps(orange, ORANGE);
        putInMaps(yellow, YELLOW);
        putInMaps(green, GREEN);
        putInMaps(blue, BLUE);
        putInMaps(purple, PURPLE);
        putInMaps(black, BLACK);
        putInMaps(white, WHITE);

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void putInMaps(Button button, TrainCardColor color) {
        buttonColorMap.put(button, color);
        colorButtonMap.put(color, button);
    }
}
