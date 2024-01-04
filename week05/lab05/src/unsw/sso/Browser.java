package unsw.sso;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import unsw.sso.providers.Hoogle;

public class Browser {
    private Token currentToken = null;
    private String currentPage = null;
    private String previousPage = null;
    private ClientApp currentApp = null;
    private List<ClientApp> cacheApps = new ArrayList<>();


    public void visit(ClientApp app) {
        currentApp = app;
        if (app.getCurrentPage() == null) {
            app.addCurrentPage("Select a Provider");
        }
        currentToken = app.getCurrentToken();
        currentPage = app.getCurrentPage();
        previousPage = app.getPreviousPage();
    }

    public String getCurrentPageName() {
        return this.currentPage;
    }

    public void clearCache() {
        for (ClientApp e : cacheApps) {
            e.clearCache();
        }
    }

    public void interact(Object using) {
        if (using == null) {
            this.currentPage = this.previousPage;
            if (this.currentPage == null) {
                this.visit(this.currentApp);
            }
            
            return;
        }

        cacheApps.add(currentApp);
        switch (currentPage) {
            case "Select a Provider": {
                // if the currentApp doesn't have hoogle
                // then it has no providers, which just will prevent
                // transition.
                if (using instanceof Hoogle && currentApp.hasHoogle()) {
                    this.previousPage = currentPage;
                    this.currentPage = "Hoogle Login";
                    currentApp.addCurrentPage(currentPage);
                } else {
                    // do nothing...
                }
                break;
            }
            case "Hoogle Login": {
                if (using instanceof Token) {
                    this.previousPage = currentPage;
                    this.currentPage = "Home";

                    // tell client application about us
                    this.currentToken = (Token)using;
                    this.currentApp.registerUser((Token)using);
                    currentApp.addCurrentToken(currentToken);
                    currentApp.addCurrentPage(currentPage);

                } else {
                }
                break;
            }
            case "Home": {
                currentApp.addCurrentPage(currentPage);
                break;
            }
        }
    }

}
