package program;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Projekat2 extends Application {
    public static final int TCP_PORT = 9000;
    private boolean checkPrijavljen = false;
    public static void main(String[] args) {
        launch(args);
    }
    TextField txtFizicki;
    TextField txtIntelektualni;
    TextField txtEmocionalni;
    Client client3 = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root,650,650, Color.WHITE);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Zadatak 1.
        Tab zadatak1 = new Tab();
        zadatak1.setText("Zadatak 1.");
        VBox vBoxGlavni1 = new VBox(30);
        vBoxGlavni1.setPadding(new Insets(40));

        HBox hBox1 = new HBox(10);
        hBox1.setAlignment(Pos.TOP_CENTER);
        Label naslov = new Label("Izračunajte bioritam na osnovu datuma rođenja");
        naslov.setFont(new Font(18));
        hBox1.getChildren().add(naslov);

        HBox hbox2 = new HBox(10);
        Label lblDatum = new Label("Odaberite željeni datum:");
        DatePicker dpZeljeniDatum = new DatePicker(LocalDate.now());
        Button btnIzracunaj = new Button();
        btnIzracunaj.setText("Izračunaj bioritam");
        btnIzracunaj.setOnAction(e -> {
            Client client1 = new Client();
            long brojDana = ChronoUnit.DAYS.between(dpZeljeniDatum.getValue(),LocalDate.now());
            String response = client1.racunajBioritam(brojDana);
            String[] arrResponse = response.split(":");
            txtFizicki.setText(arrResponse[0] + "%");
            txtEmocionalni.setText(arrResponse[1] + "%");
            txtIntelektualni.setText(arrResponse[2] + "%");
        });
        Button btnReset = new Button();
        btnReset.setText("Resetuj polja");
        btnReset.setOnAction(e -> {
            dpZeljeniDatum.setValue(LocalDate.now());
            txtFizicki.setText("");
            txtEmocionalni.setText("");
            txtIntelektualni.setText("");
        });
        hbox2.getChildren().addAll(lblDatum,dpZeljeniDatum,btnIzracunaj,btnReset);

        VBox vBox1 = new VBox(10);
        HBox hBox31 = new HBox(53);
        Label lblFizicki = new Label("Fizički: ");
        txtFizicki = new TextField();
        txtFizicki.setMaxWidth(100);
        txtFizicki.setMinWidth(100);
        txtFizicki.setEditable(false);
        txtFizicki.setStyle("-fx-background-color: #51b62f; -fx-text-fill: white;-fx-border-style: solid;\n" +
                "  -fx-border-color: lightgray;\n" +
                "  -fx-border-width: 1;");
        hBox31.getChildren().addAll(lblFizicki,txtFizicki);

        HBox hBox32 = new HBox(20);
        Label lblEmocionalni = new Label("Emocionalni: ");
        txtEmocionalni = new TextField();
        txtEmocionalni.setMaxWidth(100);
        txtEmocionalni.setMinWidth(100);
        txtEmocionalni.setEditable(false);
        txtEmocionalni.setStyle("-fx-background-color: #d04949; -fx-text-fill: white;-fx-border-style: solid;\n" +
                "  -fx-border-color: lightgray;\n" +
                "  -fx-border-width: 1;");
        hBox32.getChildren().addAll(lblEmocionalni,txtEmocionalni);

        HBox hBox33 = new HBox(20);
        Label lblIntelekutalni = new Label("Intelektualni: ");
        txtIntelektualni = new TextField();
        txtIntelektualni.setMaxWidth(100);
        txtIntelektualni.setMinWidth(100);
        txtIntelektualni.setEditable(false);
        txtIntelektualni.setStyle("-fx-background-color: #5d99d5; -fx-text-fill: white;-fx-border-style: solid;\n" +
                "  -fx-border-color: lightgray;\n" +
                "  -fx-border-width: 1;");
        hBox33.getChildren().addAll(lblIntelekutalni,txtIntelektualni);

        vBox1.setPadding(new Insets(30));
        vBox1.getChildren().addAll(hBox31,hBox32,hBox33);


        vBoxGlavni1.getChildren().addAll(hBox1,hbox2,vBox1);
        zadatak1.setContent(vBoxGlavni1);
        tabPane.getTabs().add(zadatak1);



        //Zadatak 2.
        Tab zadatak2 = new Tab();
        zadatak2.setText("Zadatak 2.");

        VBox vBoxGlavni2 = new VBox(30);
        vBoxGlavni2.setPadding(new Insets(40));
        HBox hBox4 = new HBox(10);
        hBox4.setAlignment(Pos.TOP_CENTER);
        Label naslovZad2 = new Label("Pošaljite datoteku iz foldera DATA_KLIENT na server");
        naslovZad2.setFont(new Font(18));
        hBox4.getChildren().add(naslovZad2);

        VBox vBox2 = new VBox(10);
        Label lblDataKlient = new Label("Prikaz foldera DATA_KLIJENT:");
        ObservableList<String> listfiles = FXCollections.observableArrayList();
        ListView<String> listViewFiles = new ListView<String>(listfiles);
        listViewFiles.setMinWidth(350);
        listViewFiles.setMaxWidth(350);
        listViewFiles.setMinHeight(300);
        listViewFiles.setMaxHeight(300);
        zadatak2.setOnSelectionChanged(e -> {
            if(zadatak2.isSelected()){
                listfiles.clear();
                File file = new File("DATA_KLIJENT");
                if (file.exists()) {
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++)
                        listfiles.add(files[i].getName());
                }
                else {
                    MessageBox.show("Folder DATA_KLIJENT ne postoji!","Greška");
                }
            }
        });
        HBox hBox5 = new HBox(20);
        Label lblSelektuj = new Label("Selektuj željeni fajl i pošalji na server:");
        Button btnPosaljiFajl = new Button();
        btnPosaljiFajl.setText("Pošalji datoteku");
        btnPosaljiFajl.setOnAction(e -> {
            String item = listViewFiles.getSelectionModel().getSelectedItem();
            if (item != null) {
                String fileForSend = item;
                Client client2 = new Client();
                if(client2.posaljiFajlNaServer(fileForSend)){
                    MessageBox.show("Uspešno ste prebacili datoteku '" + fileForSend + "' na server!","Uspešno");
                }
            }
            else {
                MessageBox.show("Niste odabrali datoteku za slanje!","Greška");
            }
        });
        hBox5.getChildren().addAll(lblSelektuj,btnPosaljiFajl);

        vBox2.getChildren().addAll(lblDataKlient,listViewFiles,hBox5);


        vBoxGlavni2.getChildren().addAll(hBox4,vBox2);
        zadatak2.setContent(vBoxGlavni2);
        tabPane.getTabs().add(zadatak2);



        //Zadatak 3.
        Tab zadatak3 = new Tab();
        zadatak3.setText("Zadatak 3.");
        zadatak3.setOnSelectionChanged(e -> {
            if(zadatak3.isSelected()){

            }
        });
        VBox vBoxGlavni3 = new VBox(30);
        vBoxGlavni3.setPadding(new Insets(40));
        HBox hBox6 = new HBox(10);
        hBox6.setAlignment(Pos.TOP_CENTER);

        Label naslovZad3 = new Label("Grupni čet");
        naslovZad3.setFont(new Font(18));
        hBox6.getChildren().add(naslovZad3);
        VBox vBox3 = new VBox(10);

        Label lblPoruka = new Label("Unesite željenu poruku:");
        lblPoruka.setVisible(false);
        HBox hBox8 = new HBox(15);
        hBox8.setVisible(false);
        TextField txtPoruka = new TextField();
        txtPoruka.setPromptText("Vaša poruka");
        txtPoruka.setMinWidth(250);
        txtPoruka.setMaxWidth(250);
        Button btnPosaljiPoruku = new Button();
        btnPosaljiPoruku.setText("Pošalji poruku");
        btnPosaljiPoruku.setStyle("-fx-background-color: #51b62f; -fx-text-fill: white;-fx-border-style: solid;\n" +
                "  -fx-border-color: lightgray;\n" +
                "  -fx-border-width: 1; -fx-border-radius: 3px;-fx-background-radius: 3px;");
        ObservableList<String> listaPoruka = FXCollections.observableArrayList();
        ListView<String> listaPrikazPoruka = new ListView<String>(listaPoruka);
        listaPrikazPoruka.setVisible(false);
        listaPrikazPoruka.setMinWidth(450);
        listaPrikazPoruka.setMaxWidth(450);
        listaPrikazPoruka.setMinHeight(340);
        listaPrikazPoruka.setMaxHeight(340);
        Button btnOcistiCet = new Button();
        btnOcistiCet.setText("Očisti čet");
        btnOcistiCet.setVisible(false);
        btnOcistiCet.setStyle("-fx-background-color: #5d99d5; -fx-text-fill: white;-fx-border-style: solid;\n" +
                "  -fx-border-color: lightgray;\n" +
                "  -fx-border-width: 1; -fx-border-radius: 3px;-fx-background-radius: 3px;");


        HBox hbox7 = new HBox(10);
        TextField txtImeKlijenta = new TextField();
        txtImeKlijenta.setPromptText("Unesite Vaše ime za prijavu");
        txtImeKlijenta.setMinWidth(250);
        txtImeKlijenta.setMaxWidth(250);
        Button btnOdjava = new Button();
        btnOdjava.setText("Odjavite se");
        btnOdjava.setVisible(false);
        btnOdjava.setStyle("-fx-background-color: #d04949; -fx-text-fill: white;-fx-border-style: solid;\n" +
                "  -fx-border-color: lightgray;\n" +
                "  -fx-border-width: 1; -fx-border-radius: 3px;-fx-background-radius: 3px;");


        Button btnUlogujSe = new Button();
        btnUlogujSe.setText("Prijavite se");
        btnUlogujSe.setOnAction(e -> {
            if(txtImeKlijenta.getText().equals("")){
                MessageBox.show("Da bi ste se prijavili morate da unesete ime!","Greška");
            }
            else {
                client3 = new Client(listaPoruka);
                String odgovor = client3.posaljiPoruku("Prijava:" + txtImeKlijenta.getText());
                if(odgovor.equals("PunaGrupa")){
                    MessageBox.show("Grupa je trenutno puna! Pokušajte malo kasnije.","Greška");
                }
                else {
                    client3.start();
                    listaPoruka.clear();
                    txtPoruka.setText("");
                    lblPoruka.setVisible(true);
                    hBox8.setVisible(true);
                    listaPrikazPoruka.setVisible(true);
                    btnOdjava.setVisible(true);
                    btnOcistiCet.setVisible(true);
                    txtImeKlijenta.setDisable(true);
                    btnUlogujSe.setDisable(true);
                    checkPrijavljen = true;
                }
            }
        });

        btnPosaljiPoruku.setOnAction(e -> {
            client3.posaljiPoruku(txtPoruka.getText() + ":" + txtImeKlijenta.getText());
            txtPoruka.setText("");
        });

        btnOdjava.setOnAction(e -> {
            lblPoruka.setVisible(false);
            hBox8.setVisible(false);
            listaPrikazPoruka.setVisible(false);
            btnOdjava.setVisible(false);
            btnOcistiCet.setVisible(false);
            txtImeKlijenta.setDisable(false);
            btnUlogujSe.setDisable(false);
            client3.posaljiPoruku("Odjava:" + txtImeKlijenta.getText());
        });

        primaryStage.setOnCloseRequest(e -> {
            if(checkPrijavljen) {
                lblPoruka.setVisible(false);
                hBox8.setVisible(false);
                listaPrikazPoruka.setVisible(false);
                btnOdjava.setVisible(false);
                btnOcistiCet.setVisible(false);
                txtImeKlijenta.setDisable(false);
                btnUlogujSe.setDisable(false);
                client3.posaljiPoruku("Odjava:" + txtImeKlijenta.getText());
            }
        });

        btnOcistiCet.setOnAction(e -> {
            listaPoruka.clear();
        });

        hbox7.getChildren().addAll(txtImeKlijenta,btnUlogujSe);
        hBox8.getChildren().addAll(txtPoruka,btnPosaljiPoruku,btnOdjava);
        vBox3.getChildren().addAll(hbox7,lblPoruka,hBox8,listaPrikazPoruka,btnOcistiCet);



        vBoxGlavni3.getChildren().addAll(hBox6,vBox3);
        zadatak3.setContent(vBoxGlavni3);
        tabPane.getTabs().add(zadatak3);

        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        primaryStage.setTitle("Projekat OP2");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(650);
        primaryStage.setMaxWidth(650);
        primaryStage.setMinHeight(650);
        primaryStage.setMaxHeight(650);
        primaryStage.show();

    }
}
