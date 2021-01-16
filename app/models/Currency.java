package models;

import play.data.validation.*;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Currency extends Model {

    @Required
    public String name;

    @Required
    public String symbol;

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
