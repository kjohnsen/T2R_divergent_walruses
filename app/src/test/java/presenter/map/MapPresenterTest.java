package presenter.map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clientserver.ClientCommunicator;
import clientserver.ServerProxy;
import model.ClientModel;
import model.CommandFacade;
import model.IUIFacade;
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
import modelclasses.User;
import presenter.MockUIFacade;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MapPresenterTest {
    @Mock
    private static MockMapFragment view;
    private static MapPresenter presenter;
    private static ClientModel clientModel = ClientModel.getInstance();

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
        IUIFacade uiFacade = new MockUIFacade();
        uiFacade.setAuthToken("player");
        presenter.setUiFacade(uiFacade);
    }

    @Before
    public void setup() {
        clientModel.reset();

        GameInfo game = new GameInfo(new GameName("game"), new ArrayList<Player>(), 2);
        clientModel.setCurrentGame(game);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(calgary);
        routes.add(la);
        routes.add(portland);
        clientModel.getCurrentGame().setUnclaimedRoutes(routes);

        other = new Player("other", PlayerColor.BLUE);
        other.addRoute(denver);
        clientModel.getCurrentGame().addPlayer(other);

        this.player = new Player("player", PlayerColor.GREEN);
        player.addTrainCardToHand(new TrainCard(TrainCardColor.BLACK));
        player.addTrainCardToHand(new TrainCard(TrainCardColor.RED));
        clientModel.setCurrentUser(new User("player", "password"));
        clientModel.getCurrentGame().addPlayer(player);
        clientModel.getCurrentGame().setCurrentPlayer(player);
        clientModel.setPlayerTrainCards(player.getTrainCards());

    }

    @Test
    public void claimableRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
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
        verify(view, times(1)).displayMessage("Can't afford route");
    }

    @Test
    public void wildRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
        assertThat(player.getRoutes(), not(hasItem(this.la)));
        TrainCard redCard = new TrainCard(TrainCardColor.RED);
        assertTrue(clientModel.getPlayerTrainCards().contains(redCard));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                invocation.callRealMethod();
                return null;
            }
        }).when(view).queryUserForClaimColor(la, ClaimingEnabledState.getInstance());
        presenter.routeClicked(la);
        assertThat(player.getRoutes(), hasItem(this.la));
        assertFalse(clientModel.getPlayerTrainCards().contains(redCard));
    }

    @Test
    public void disabledStateRouteClicked() {
        presenter.setState(ClaimingEnabledState.getInstance());
        presenter.routeClicked(portland);
        assertThat(player.getRoutes(), not(hasItem(this.portland)));
    }

    @Test
    public void turnStart() {
        clientModel.getCurrentGame().setCurrentPlayer(this.player);
        clientModel.notifyObservers(this.player);
        assertEquals(ClaimingEnabledState.getInstance(), presenter.getState());
    }

    @Test
    public void turnEnd() {
        clientModel.getCurrentGame().setCurrentPlayer(this.other);
        clientModel.notifyObservers(this.other);
        assertEquals(ClaimingDisabledState.getInstance(), presenter.getState());
    }

    @Test
    public void drawTrainCardDisables() {
        clientModel.drawTrainCardToHand(new TrainCard(TrainCardColor.BLACK), player);
        assertEquals(ClaimingDisabledState.getInstance(), presenter.getState());
    }

    @Test
    public void drawDestinationTicketDisables() {
        clientModel.getCurrentGame().setCurrentPlayer(this.player);
        CommandFacade._selectDestinationCards(new GameName("game"), new ArrayList<DestinationCard>(), player);
        assertEquals(ClaimingDisabledState.getInstance(), presenter.getState());
    }
}
