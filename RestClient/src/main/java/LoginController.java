import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import pojo.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController implements Initializable{

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(){
        String pass = password.getText();
        String log = login.getText();
        sendPost(log, pass);
    }

    public void sendPost(String log, String pass){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpGet httpGet = new HttpGet("http://localhost:8080/rest/login");
                    String auth =log +  ":" + pass;
                    byte[] encodedAuth = Base64.encodeBase64(
                            auth.getBytes(Charset.forName("ISO-8859-1")));
                    String authHeader = "Basic " + new String(encodedAuth);
                    httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
                    HttpClient httpclient = HttpClientBuilder.create().build();
                    HttpResponse response = httpclient.execute(httpGet);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if(statusCode != 200){
                        message.setText("Access denied");
                    }else {
                        User.setPassword(pass);
                        User.setUsername(log);
                        Stage st = new Stage();
                        try {
                            String xmlFile = "/fxml/warehouses.fxml";
                            FXMLLoader loader = new FXMLLoader();
                            Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(xmlFile));
                            Scene scene = new Scene(rootNode, 400,400);
                            st.setTitle("Choose warehouse for open content products");
                            st.setScene(scene);
                            st.show();
                            ((Stage) login.getScene().getWindow()).close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
