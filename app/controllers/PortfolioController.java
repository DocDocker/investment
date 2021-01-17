package controllers;

import models.Currency;
import models.Portfolio;
import play.data.validation.Valid;
import play.mvc.*;

public class PortfolioController extends Controller {

    public static void showPortfolio(Long id) {
        Portfolio portfolio = Portfolio.findById(id);
        notFoundIfNull(portfolio);
        render(portfolio);
    }

    public static void editPortfolio(@Valid Long id) {
        Portfolio portfolio;
        Currency portfolioCurrency;
        if (id == 0L) {
            portfolio = new Portfolio();
            portfolioCurrency = new Currency();
        } else {
            portfolio = Portfolio.findById(id);
            notFoundIfNull(portfolio);
            portfolioCurrency = portfolio.currency;
        }
        render(portfolio, portfolioCurrency);
    }

    public static void deletePortfolio(Long id) {
        Portfolio portfolio = Portfolio.findById(id);
        notFoundIfNull(portfolio);
        portfolio.delete();
        Application.index();
    }

    public static void savePortfolio(@Valid Portfolio portfolio) {

        if(validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            Long portfolioId = (portfolio.id == null) ? 0L : portfolio.id;
            editPortfolio(portfolioId);
        }
        portfolio.save();
        Application.index();
    }
}