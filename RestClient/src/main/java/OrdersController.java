import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import pojo.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class OrdersController implements Initializable {

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private TableColumn<Order, Long> idCol;

    @FXML
    private TableColumn<Order, Long> userIdCol;

    @FXML
    private TableColumn<Order, OrderStatuses> orderStatus;

    @FXML
    private Button orderSentBtn;

    @FXML
    private Button orderDoneBtn;
    ObservableList<Order> orderList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderSentBtn.setDisable(true);
        orderDoneBtn.setDisable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<Order, Long>("id"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<Order, Long>("userId"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<Order, OrderStatuses>("status"));
        orderTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orderSentBtn.setDisable(false);
                orderDoneBtn.setDisable(false);
            }
        });
    }

    public void loadOrders(){
        orderList = FXCollections.observableArrayList();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
//                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet("http://localhost:8080/rest/orders");

                    String auth = User.getUsername()+":"+User.getPassword();
                    byte[] encodedAuth = Base64.encodeBase64(
                            auth.getBytes(Charset.forName("ISO-8859-1")));
                    String authHeader = "Basic " + new String(encodedAuth);
                    httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

                    HttpClient httpclient = HttpClientBuilder.create().build();


                    HttpResponse response1 = httpclient.execute(httpGet);
                    int statusCode = response1.getStatusLine().getStatusCode();
                    if(statusCode != 200){
                        logout();
                    }
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
                    StringBuffer result = new StringBuffer();
                    result.append("{\"orders\":");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    result.append("}");
//                    JSONObject o = new JSONObject(result.toString().substring(1, result.length()-1));
                    JSONObject ob = new JSONObject(result.toString());
                    JSONArray arr = ob.getJSONArray("orders");
                    List<Order> orders = new ArrayList<Order>();
                    for (int i = 0; i < arr.length(); i++)
                    {
                        Order o = new Order();
                        o.setStatus(arr.getJSONObject(i).getString("status"));
                        o.setId(arr.getJSONObject(i).getLong("id"));
                        JSONObject userObj = arr.getJSONObject(i).getJSONObject("user");
                        o.setUserId(userObj.getLong("id"));
                        List<Product> products = new ArrayList<Product>();
                        JSONArray prodInOrd = arr.getJSONObject(i).getJSONArray("productInOrders");
                        for (int j = 0; j < prodInOrd.length(); j++){
                            Product p = new Product();
                            JSONObject jprod = prodInOrd.getJSONObject(j);
                            p.setAmount(jprod.getInt("amount"));
                            JSONObject jprod2 = jprod.getJSONObject("product");
                            p.setId(jprod2.getLong("id"));
                            p.setName(jprod2.getString("name"));
                            p.setCost(jprod2.getInt("price"));
//                            JSONArray jarr = arr.getJSONObject(j).getJSONArray("productsOnWarehouses");
//                            p.setAmount(prodInOrd.getInt("amount"));
                            products.add(p);
                        }
                        o.setProducts(products);
                        orders.add(o);
                    }
                    orderList.addAll(orders);
                    orderTableView.setItems(orderList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void doOrderSent(){
        Order o = orderTableView.getSelectionModel().getSelectedItem();
        o.setStatus("ORDER_SENT");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    String params = "id=" + o.getId() + "&status=ORDER_SENT";
                    HttpGet httpGet = new HttpGet("http://localhost:8080/rest/changeStatus?"+params);
                    String auth = User.getUsername()+":"+User.getPassword();
                    byte[] encodedAuth = Base64.encodeBase64(
                            auth.getBytes(Charset.forName("ISO-8859-1")));
                    String authHeader = "Basic " + new String(encodedAuth);
                    httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
                    CloseableHttpResponse response1 = httpclient.execute(httpGet);
                    int statusCode = response1.getStatusLine().getStatusCode();
                    if(statusCode != 200){
                        logout();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void doOrderDone(){
        Order o = orderTableView.getSelectionModel().getSelectedItem();
        o.setStatus("ORDER_DONE");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    String params = "id=" + o.getId() + "&status=ORDER_DONE";
                    HttpGet httpGet = new HttpGet("http://localhost:8080/rest/changeStatus?"+params);
                    String auth = User.getUsername()+":"+User.getPassword();
                    byte[] encodedAuth = Base64.encodeBase64(
                            auth.getBytes(Charset.forName("ISO-8859-1")));
                    String authHeader = "Basic " + new String(encodedAuth);
                    httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
                    CloseableHttpResponse response1 = httpclient.execute(httpGet);
                    int statusCode = response1.getStatusLine().getStatusCode();
                    if(statusCode != 200){
                        logout();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void logout() {
        User.logout();
        try {
            Stage st = new Stage();
            String xmlFile = "/fxml/login.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(xmlFile));
            Scene scene = new Scene(rootNode, 400, 400);
            st.setScene(scene);
            st.setTitle("Login");
            st.show();
            ((Stage) orderDoneBtn.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}