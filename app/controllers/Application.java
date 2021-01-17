package controllers;

import play.mvc.*;

import java.util.*;

import models.Portfolio;

public class Application extends Controller {

    public static void index() {
        List<Portfolio> portfolios = Portfolio.findAll();
        renderArgs.put("portfolios", portfolios);
        render();
    }

}