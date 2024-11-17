module org.jgp2425.unit.finalactivity_v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.persistence;


    opens org.jgp2425.unit.finalactivity_v1 to javafx.fxml;
    opens org.jgp2425.unit.finalactivity_v1.entities to org.hibernate.orm.core;
    exports org.jgp2425.unit.finalactivity_v1;
}