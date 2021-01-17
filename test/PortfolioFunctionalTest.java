import models.Currency;
import org.junit.*;
import org.junit.Before;
import play.Logger;
import play.db.jpa.JPA;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.Portfolio;

import javax.persistence.EntityTransaction;
import java.util.*;

public class PortfolioFunctionalTest extends FunctionalTest {

    @Before
    public void beforeTest() {
        Fixtures.deleteDatabase();
        EntityTransaction transaction = JPA.em().getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

    @Test
    public void testShouldOpenPageNewPortfolio() {
        Response response = GET("/portfolios/new");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertContentMatch("type=\"submit\"", response);
    }

    @Test
    public void testShouldCreateNewPortfolio() {

        Fixtures.loadModels("currencies.yml");
        JPA.em().getTransaction().commit();

        Currency currency = Currency.all().first();

        Map<String, String> params = new HashMap<>();
        params.put("portfolio.name", "Dream");
        params.put("portfolio.goal", "999");
        params.put("portfolio.currency.id", currency.id.toString());

        Response response = POST(Router.reverse("PortfolioController.savePortfolio").url, params);
        assertStatus( 302, response);

        String location = response.headers.get("Location").value();
        response = GET(location);
        assertIsOk(response);

        Assert.assertEquals("portfolios count", Portfolio.count(), 1);

    }

    @Test
    public void testShouldCheckPortfolioValidationBeforeCreation() {

        Map<String, String> params = new HashMap<>();
        params.put("portfolio.name", "Dream");
        params.put("portfolio.goal", "999");
        params.put("portfolio.currency.id", "1");

        Response response = POST(Router.reverse("PortfolioController.savePortfolio").url, params);
        assertStatus( 302, response);

        String location = response.headers.get("Location").value();
        response = GET(location);
        assertIsOk(response);
        assertContentMatch("Required", response);

        Assert.assertEquals("portfolios count", Portfolio.count(), 0);

    }

    @Test
    public void testShouldDeleteThePortfolio() {

        Fixtures.deleteDatabase();
        Fixtures.loadModels("portfolios.yml");
        JPA.em().getTransaction().commit();

        assertEquals("portfolios count before deleting", Portfolio.count(), 1);
        Portfolio portfolio = Portfolio.all().first();

        String url = "/portfolios/" + portfolio.id;

        Response response = DELETE(url);
        assertStatus( 302, response);

        String location = response.headers.get("Location").value();
        response = GET(location);

        assertEquals("portfolios count after deleting", Portfolio.count(), 0);

    }
}