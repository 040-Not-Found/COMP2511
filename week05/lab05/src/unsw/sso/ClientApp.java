package unsw.sso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unsw.sso.providers.Hoogle;

public class ClientApp {
    private List<String> visits = new ArrayList<>();
    private List<Token> tokens = new ArrayList<>();
    private boolean hasHoogle = false;

    // HINT: Don't overcomplicate this
    //       for Task 2) you'll want some sort of object
    //       but don't go overboard, even in Task 4)
    //       this object can be relatively simple.
    private Map<String, Boolean> usersExist = new HashMap<>();
    private final String name;

    public ClientApp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void registerProvider(Object o) {
        if (o instanceof Hoogle) {
            hasHoogle = true;
        }
    }

    public boolean hasHoogle() {
        return hasHoogle;
    }

    public void registerUser(Token token) {
        // only hoogle is supported right now!  So we presume hoogle on user
        usersExist.put(token.getUserEmail(), true);
    }

    public boolean hasUserForProvider(String email, Object provider) {
        return provider instanceof Hoogle && this.hasHoogle && this.usersExist.getOrDefault(email, false);
    }

    public boolean hasHoogleUser(String email) {
        return usersExist.getOrDefault(email, false);
    }

    public void addCurrentToken(Token token) {
        tokens.add(token);
    }

    public void addCurrentPage(String page) {
        visits.add(page);
    }

    public Token getCurrentToken() {
        if (tokens.size() < 1) return null;
        return tokens.get(tokens.size()-1);
    }

    public String getCurrentPage() {
        if (visits.size() < 1) return null;
        return visits.get(visits.size()-1);
    }

    public String getPreviousPage() {
        if (visits.size() < 2) return null;
        return visits.get(visits.size()-2);
    }

    public void clearCache() {
        visits.clear();
        tokens.clear();
    }

}
