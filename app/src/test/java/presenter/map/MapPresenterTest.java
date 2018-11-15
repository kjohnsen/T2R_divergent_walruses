package presenter.map;

import org.junit.Before;
import org.junit.Test;

import model.ClientModel;

import static org.junit.Assert.*;

public class MapPresenterTest {
    private MapPresenter presenter = new MapPresenter(new MockMapFragment());

    @Before
    public void setup() {
        ClientModel.getInstance().reset();
//        ClientModel.getInstance().
    }

    @Test
    public void alreadyClaimedRouteClicked() {

    }

    @Test
    public void cantAffordRouteClicked() {

    }

    @Test
    public void wildRouteClicked() {

    }

    @Test
    public void disabledStateRouteClicked() {

    }

    @Test
    public void turnStart() {
    }

    @Test
    public void turnEnd() {
    }

    @Test
    public void drawCardDisabled() {
    }
}