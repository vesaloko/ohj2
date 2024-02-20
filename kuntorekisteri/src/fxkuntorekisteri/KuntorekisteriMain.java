package fxkuntorekisteri;

import Kayttaja.Kuntorekisteri;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
/**
 * @author vesal
 * @version 26.1.2023
 *
 */
public class KuntorekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("paavalikko.fxml"));
            final Pane root = ldr.load();
            final paavalikkoController paavalikkoCtrl = (paavalikkoController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kuntorekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("kuntorekisteri");
            primaryStage.show();
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !paavalikkoCtrl.voikoSulkea() ) event.consume();
            });

            Kuntorekisteri kuntorekisteri = new Kuntorekisteri();  
            paavalikkoCtrl.setKuntorekisteri(kuntorekisteri); 

        primaryStage.show();
        Application.Parameters params = getParameters(); 
        if ( params.getRaw().size() > 0 ) 
            paavalikkoCtrl.lueTiedosto(params.getRaw().get(0));  
        else
            if ( !paavalikkoCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}