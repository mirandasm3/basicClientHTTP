
package clientehttp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author miran
 */
public class FXMLAppController implements Initializable {

    @FXML
    private ComboBox<String> cbOptions;
    @FXML
    private TextField tfURL;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private RadioButton rbRaw;
    @FXML
    private RadioButton rbPretty;
    @FXML
    private Text txtResponse;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Text txtContentType;
    @FXML
    private Text txt1;
    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt1.setVisible(false);
        rectangle.setVisible(false);
        
    }    

    @FXML
    private void consultarURL(ActionEvent event) {
    }

    @FXML
    private void guardarRespuesta(ActionEvent event) {
    }
    
}
