package results;

import java.util.ArrayList;
import modelclasses.GameInfo;

public class LoggedInResults extends Results {

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}