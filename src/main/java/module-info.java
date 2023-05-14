module com.rec.recsys {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.rec.recsys to javafx.fxml;
    exports com.rec.recsys;
    exports Vista.scenes;
    opens Vista.scenes to javafx.fxml;
}
