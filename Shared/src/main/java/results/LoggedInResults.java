package results;

import java.io.Serializable;
import java.util.ArrayList;
import modelclasses.GameInfo;

public class LoggedInResults extends Results implements Serializable {

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}