package presenter.map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clientserver.ClientCommunicator;
import clientserver.ServerProxy;
import model.ClientModel;
import model.CommandFacade;
import model.MockServerProxy;
import model.UIFacade;
import modelclasses.Atlas;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.Route;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class MapPresenterTest {
    @Mock
    private static MockMapFragment view;
    private static MapPresenter presenter;
    private static MockServerProxy mockServerProxy = new MockServerProxy();

    private Route calgary = new Route(Atlas.SLC, Atlas.CALGARY, TrainCardColor.BLACK, 1);
    private Route la = new Route(Atlas.SLC, Atlas.LOS_ANGELES, TrainCardColor.WILD, 1);
    private Route portland = new Route(Atlas.SLC, Atlas.PORTLAND, TrainCardColor.BLACK, 10);
    private Route denver = new Route(Atlas.SLC, Atlas.DENVER, TrainCardColor.RED, 5);

    private Player player;
    private Player other;

    @BeforeClass
    public static void setupClass() {
        view = mock(MockMapFragment.class);
        presenter = new MapPresenter(view);
        UIFacade.getInstance().setAuthToken("player");
        uiFacade.setServerProxy(mockServerProxy);
    }

    @Before
    public void setup() {
        ClientModel.getInstance().reset();

        GameInfo game = new GameInfo(new GameName("game"), new ArrayList<Player>(), 2);
        ClientModel.getInstance().setCurrentGame(game);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(calgary);
        routes.add(la);
        routes.add(portland);
        ClientModel.getInstance().getCurrentGame().setUnclaimedRoutes(routes);

        other = new Player("other", PlayerColor.BLUE);
        other.addRoute(denver);
        ClientModel.getInstance().getCurrentGame().addPlayer(other);

        this.player = new Player("player", PlayerColor.GREEN);
        player.addTrainCardToHand(new TrainCard(TrainCardColor.BLACK));
        player.addTrainCardToHand(new TrainCard(TrainCardColor.RED));
        ClientModel.getInstance().getCurrentGame().addPlayer(player);

    }

    @Test
    public void claimableRouteClicked() {
        assertThat(player.getRoutes(), not(hasItem(this.calgary)));
        presenter.routeClicked(calgary);
        assertThat(player.getRoutes(), hasItem(this.calgary));
    }

    @Test
    public void alreadyClaimedRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
        presenter.routeClicked(denver);
        assertThat(player.getRoutes(), not(hasItem(this.calgary)));
        verify(view).displayMessage("Route already claimed");
    }

    @Test
    public void cantAffordRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
        presenter.routeClicked(portland);
        assertThat(player.getRoutes(), not(hasItem(portland)));
        verify(view, times(1)).displayMessage(anyString());
    }

    @Test
    public void wildRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
        assertThat(player.getRoutes(), not(hasItem(this.la)));
        presenter.routeClicked(la);
        verify(view).queryUserForClaimColor(la);
        assertThat(player.getRoutes(), hasItem(this.la));

    }

    @Test
    public void disabledStateRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
        presenter.routeClicked(portland);
        assertThat(player.getRoutes(), not(hasItem(this.portland)));
    }

    @Test
    public void turnStart() {
        ClientModel.getInstance().getCurrentGame().setCurrentPlayer(this.player);
        assertEquals(ClaimingEnabledState.getInstance(), presenter.getState());
    }

    @Test
    public void turnEnd() {
        ClientModel.getInstance().getCurrentGame().setCurrentPlayer(this.other);
        assertEquals(ClaimingDisabledState.getInstance(), presenter.getState());
    }

    @Test
    public void drawTrainCardDisables() {
        ClientModel.getInstance().drawTrainCardToHand(new TrainCard(TrainCardColor.BLACK), player);
        assertEquals(ClaimingDisabledState.getInstance(), presenter.getState());
    }

    @Test
    public void drawDestinationTicketDisables() {
        ClientModel.getInstance().getCurrentGame().setCurrentPlayer(this.player);
        CommandFacade._selectDestinationCards(new GameName("game"), new ArrayList<DestinationCard>(), player);
        assertEquals(ClaimingDisabledState.getInstance(), presenter.getState());
    }
}
