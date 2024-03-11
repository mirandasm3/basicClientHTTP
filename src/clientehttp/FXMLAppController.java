
package clientehttp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private Button btnGuardar;
    private ToggleGroup radioButtons;
    @FXML
    private Tab tabPaneHeaders;
    @FXML
    private Tab tabPaneBody;
    @FXML
    private WebView webViewHeaders;
    @FXML
    private WebView webViewBody;
    @FXML
    private Text txtAreaRawResponse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rectangle.setVisible(false);
        cbOptions.getItems().addAll("GET", "PUT", "OPTIONS");
        
        radioButtons = new ToggleGroup();
        rbRaw.setToggleGroup(radioButtons);
        rbPretty.setToggleGroup(radioButtons);
        rbRaw.setSelected(true);
        
    }
    
    @FXML
    private void consultarURL(ActionEvent event) {
        String url = tfURL.getText();
        String method = cbOptions.getValue();

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpRequestBase httpRequest = null;

            switch (method) {
                case "GET":
                    httpRequest = new HttpGet(url);
                    break;
                case "PUT":
                    // Implementa lógica para los otros métodos si los necesitas
                    break;
                case "OPTIONS":
                    // Implementa lógica para los otros métodos si los necesitas
                    break;
                default:
                    txtResponse.setText("Método HTTP no soportado.");
                    return;
            }
            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                // Obtener el código de estado de la respuesta
                int statusCode = response.getStatusLine().getStatusCode();
                txtResponse.setText("Código de Estado: " + statusCode);

                // Obtener el tipo de contenido MIME de la respuesta
                String contentType = response.getEntity().getContentType().getValue();
                txtContentType.setText("Tipo de Contenido: " + contentType);

                // Leer el cuerpo de la respuesta
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                if (rbRaw.isSelected()) {
                    txtAreaRawResponse.setText(responseBody);
                } else {
                    // Si la respuesta es HTML, mostrarla en el WebView
                    Document document = Jsoup.parse(responseBody);
                    webViewBody.getEngine().loadContent(document.outerHtml());
                }

                // Mostrar las cabeceras de la respuesta en el WebView
                StringBuilder headers = new StringBuilder();
                for (org.apache.http.Header header : response.getAllHeaders()) {
                    headers.append(header.getName()).append(": ").append(header.getValue()).append("\n");
                }
                webViewHeaders.getEngine().loadContent("<pre>" + headers.toString() + "</pre>");
            }
        } catch (IOException e) {
            txtResponse.setText("Error al realizar la solicitud HTTP: " + e.getMessage());
        }
    }
           

    @FXML
    private void guardarRespuesta(ActionEvent event) {
        String responseContent = rbRaw.isSelected() ? txtAreaRawResponse.getText() : "";
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de texto", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(responseContent);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
