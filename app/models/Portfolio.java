package models;

import play.data.validation.*;
import play.db.jpa.Model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Portfolio extends Model {

    @Required
    public String name;

    @Required
    @Min(0)
    public BigDecimal goal;

    @Required
    @ManyToOne
    public Currency currency;

    public String toString() {
        return name;
    }
}
