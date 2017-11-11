/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import static java.util.Spliterators.iterator;
import static java.util.Spliterators.iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javax.swing.JFileChooser;

/**
 *
 * @author root
 */
public class InterfaceController implements Initializable {

    /* Seçao de variaveis da interface */
    @FXML
    public Canvas CanvasXY = new Canvas();
    @FXML
    public Canvas CanvasXZ = new Canvas();
    @FXML
    public Canvas CanvasZY = new Canvas();
    @FXML
    public Canvas CanvasP = new Canvas();
    @FXML
    public Spinner SpinnerRaio = new Spinner();
    @FXML
    public Spinner SpinnerVertices = new Spinner();
    @FXML
    public ColorPicker CPBorda = new ColorPicker(Color.BLACK);
    @FXML
    public ColorPicker CPObjeto = new ColorPicker(null);
    @FXML
    public AnchorPane AnchorPaneMain = new AnchorPane();
    @FXML
    public MenuBar BarraMenu = new MenuBar();
    @FXML
    public TabPane PainelLateral_A = new TabPane();
    @FXML
    public TabPane PainelLateral_B = new TabPane();
    @FXML
    public TabPane PainelLateral_C = new TabPane();
    @FXML
    public Pane PainelPropriedades = new Pane();
    @FXML
    public Button TamanhoDefinido = new Button();
    @FXML
    public Button MaoLivre = new Button();
    @FXML
    public Button Revolucao = new Button();
    @FXML
    public Button Extrusao = new Button();
    @FXML
    public Button Rotacao = new Button();
    @FXML
    public Button Translacao = new Button();
    @FXML
    public Button Escala = new Button();
    @FXML
    public Button Cisalhamento = new Button();
    @FXML
    public Button SelecionarPoligono = new Button();
    @FXML
    public Button ExcluirPoligono = new Button();
    @FXML
    public Button CorBorda = new Button();
    @FXML
    public Button CorPreenchimento = new Button();
    @FXML
    ChoiceBox EixoSelecionado = new ChoiceBox();
    @FXML
    public Separator SeparatorHorizontal = new Separator();
    @FXML
    public Separator SeparatorVertical = new Separator();
    @FXML
    public Button MaximizeXY = new Button();
    @FXML
    public Button MaximizeXZ = new Button();
    @FXML
    public Button MaximizeZY = new Button();
    @FXML
    public Button MaximizeP = new Button();
    @FXML
    public Label labelEixo = new Label("Eixo: ");
    @FXML
    public Label labelRaio = new Label("Raio: ");
    @FXML
    public Label labelVertices = new Label("Vertices: ");
    @FXML
    public Label labelBorda = new Label("Borda");
    @FXML
    public Label labelFill = new Label("Fill");
    @FXML
    public Label labelXY = new Label("Fill");
    @FXML
    public Label labelXZ = new Label("Fill");
    @FXML
    public Label labelZY = new Label("Fill");
    @FXML
    public Label labelP = new Label("Fill");

    /* Seçao de variáveis de processamento */
    ArrayList<Solido> PolyList;
    int erro;
    Color SystemEdgeColor, SystemFillColor;
    Node P, VRP;
    double zvp, zprp;
    int eixo; // 1 = X, 2 = Y, 3 = Z;
    boolean focoXY, focoXZ, focoZY, focoP;

    Node mouse_posicao_ant;
    boolean drag, ocultacaoFaces, wireFrame;
    Hashtable<Integer, Solido> SelectedPoly = new Hashtable<>();
    int op;
    Rectangle2D limiteTela = Screen.getPrimary().getVisualBounds();
    int larguraTabMenu = 100;
    int alturaMenuBar = 30;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CanvasXY.setVisible(true);
        CanvasXY.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
        CanvasXY.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
        CanvasXY.setLayoutX(0);
        CanvasXY.setLayoutY(0);
        CanvasXZ.setVisible(true);
        CanvasXZ.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
        CanvasXZ.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
        CanvasXZ.setLayoutX(0);
        CanvasXZ.setLayoutY((limiteTela.getHeight() - alturaMenuBar) / 2);
        CanvasZY.setVisible(true);
        CanvasZY.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
        CanvasZY.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
        CanvasZY.setLayoutX((limiteTela.getWidth() - larguraTabMenu) / 2);
        CanvasZY.setLayoutY(0);
        CanvasP.setVisible(true);
        CanvasP.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
        CanvasP.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
        CanvasP.setLayoutX((limiteTela.getWidth() - larguraTabMenu) / 2);
        CanvasP.setLayoutY((limiteTela.getHeight() - alturaMenuBar) / 2);

        SpinnerRaio.setVisible(false);
        SpinnerVertices.setVisible(false);
        EixoSelecionado.setVisible(false);

        CPBorda.setValue(Color.BLACK);
        CPBorda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                SystemEdgeColor = CPBorda.getValue();
            }
        });
        CPObjeto.setValue(null);
        CPObjeto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                SystemFillColor = CPObjeto.getValue();
            }
        });

        EixoSelecionado.setItems(FXCollections.observableArrayList("X", "Y", "Z"));
        EixoSelecionado.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                eixo = newValue.intValue();
            }
        });
        EixoSelecionado.setValue("Y");

        SeparatorHorizontal.setOrientation(Orientation.HORIZONTAL);
        SeparatorHorizontal.setPrefWidth((limiteTela.getWidth() - larguraTabMenu));
        SeparatorHorizontal.setLayoutX(0);
        SeparatorHorizontal.setLayoutY((limiteTela.getHeight() - alturaMenuBar) / 2);
        SeparatorVertical.setOrientation(Orientation.VERTICAL);
        SeparatorVertical.setPrefHeight((limiteTela.getHeight() - alturaMenuBar));
        SeparatorVertical.setLayoutY(alturaMenuBar);
        SeparatorVertical.setLayoutX((limiteTela.getWidth() - larguraTabMenu) / 2);

        PainelLateral_A.setPrefSize(larguraTabMenu, (limiteTela.getHeight() - alturaMenuBar) / 2);
        PainelLateral_A.setLayoutX((limiteTela.getWidth() - larguraTabMenu));
        PainelLateral_A.setLayoutY(alturaMenuBar);
        PainelLateral_A.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));

        PainelLateral_B.setPrefSize(larguraTabMenu, (limiteTela.getHeight() - alturaMenuBar) / 2);
        PainelLateral_B.setLayoutX((limiteTela.getWidth() - larguraTabMenu));
        PainelLateral_B.setLayoutY(alturaMenuBar + 90);
        PainelLateral_B.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));

        PainelLateral_C.setPrefSize(larguraTabMenu, (limiteTela.getHeight() - alturaMenuBar) / 2);
        PainelLateral_C.setLayoutX((limiteTela.getWidth() - larguraTabMenu));
        PainelLateral_C.setLayoutY(alturaMenuBar + 180);
        PainelLateral_C.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));

        PainelPropriedades.setPrefSize(larguraTabMenu, (limiteTela.getHeight() - alturaMenuBar) / 2);
        PainelPropriedades.setLayoutX((limiteTela.getWidth() - larguraTabMenu));
        PainelPropriedades.setLayoutY((limiteTela.getHeight() - alturaMenuBar) / 2);
        PainelPropriedades.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));

        BarraMenu.setPrefSize(limiteTela.getWidth(), alturaMenuBar);
        BarraMenu.setLayoutX(0);
        BarraMenu.setLayoutY(0);

        /* Botoes */
        TamanhoDefinido.setPrefSize(40, 40);
        TamanhoDefinido.setLayoutX(10);
        TamanhoDefinido.setLayoutY(10);

        MaoLivre.setPrefSize(40, 40);
        MaoLivre.setLayoutX(55);
        MaoLivre.setLayoutY(10);

        Revolucao.setPrefSize(40, 40);
        Revolucao.setLayoutX(10);
        Revolucao.setLayoutY(10);

        Extrusao.setPrefSize(40, 40);
        Extrusao.setLayoutX(55);
        Extrusao.setLayoutY(10);

        Rotacao.setPrefSize(40, 40);
        Rotacao.setLayoutX(10);
        Rotacao.setLayoutY(10);

        Translacao.setPrefSize(40, 40);
        Translacao.setLayoutX(55);
        Translacao.setLayoutY(10);

        Escala.setPrefSize(40, 40);
        Escala.setLayoutX(10);
        Escala.setLayoutY(55);

        Cisalhamento.setPrefSize(40, 40);
        Cisalhamento.setLayoutX(55);
        Cisalhamento.setLayoutY(55);

        EixoSelecionado.setPrefSize(80, 30);
        EixoSelecionado.setLayoutX(10);
        EixoSelecionado.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 310);

        SpinnerRaio.setPrefSize(80, 30);
        SpinnerRaio.setLayoutX(10);
        SpinnerRaio.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 265);

        SpinnerVertices.setPrefSize(80, 30);
        SpinnerVertices.setLayoutX(10);
        SpinnerVertices.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 210);

        SelecionarPoligono.setPrefSize(40, 40);
        SelecionarPoligono.setLayoutX(10);
        SelecionarPoligono.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 170);

        ExcluirPoligono.setPrefSize(40, 40);
        ExcluirPoligono.setLayoutX(55);
        ExcluirPoligono.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 170);

        CorBorda.setPrefSize(40, 40);
        CorBorda.setLayoutX(10);
        CorBorda.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 120);

        CorPreenchimento.setPrefSize(40, 40);
        CorPreenchimento.setLayoutX(55);
        CorPreenchimento.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 120);

        CPBorda.setPrefSize(40, 40);
        CPBorda.setLayoutX(10);
        CPBorda.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 50);

        CPObjeto.setPrefSize(40, 40);
        CPObjeto.setLayoutX(55);
        CPObjeto.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 50);

        MaximizeXY.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 30px; "
                + "-fx-min-height: 30px; "
                + "-fx-max-width: 30px; "
                + "-fx-max-height: 30px;"
        );
        MaximizeXY.setLayoutX(CanvasXY.getWidth() - 35);
        MaximizeXY.setLayoutY(CanvasXY.getLayoutY() + 35);
        MaximizeXY.setOpacity(0.5);
        MaximizeXY.setVisible(true);

        MaximizeXZ.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 30px; "
                + "-fx-min-height: 30px; "
                + "-fx-max-width: 30px; "
                + "-fx-max-height: 30px;"
        );
        MaximizeXZ.setLayoutX(CanvasXZ.getWidth() - 35);
        MaximizeXZ.setLayoutY(CanvasXZ.getLayoutY() + 5);
        MaximizeXZ.setOpacity(0.5);
        MaximizeXZ.setVisible(true);

        MaximizeZY.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 30px; "
                + "-fx-min-height: 30px; "
                + "-fx-max-width: 30px; "
                + "-fx-max-height: 30px;"
        );
        MaximizeZY.setLayoutX(CanvasZY.getWidth() + CanvasZY.getLayoutX() - 35);
        MaximizeZY.setLayoutY(CanvasZY.getLayoutY() + 35);
        MaximizeZY.setOpacity(0.5);
        MaximizeZY.setVisible(true);

        MaximizeP.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 30px; "
                + "-fx-min-height: 30px; "
                + "-fx-max-width: 30px; "
                + "-fx-max-height: 30px;"
        );
        MaximizeP.setLayoutX(CanvasP.getWidth() + CanvasP.getLayoutX() - 35);
        MaximizeP.setLayoutY(CanvasP.getLayoutY() + 5);
        MaximizeP.setOpacity(0.5);
        MaximizeP.setVisible(true);

        labelXY.setVisible(true);
        labelXY.setPrefSize(70, 30);
        labelXY.setTextFill(Color.BLACK);
        labelXY.setText("XY");
        labelXY.setLayoutX(CanvasXY.getLayoutX() + 5);
        labelXY.setLayoutY(CanvasXY.getLayoutY() + 23);

        labelXZ.setVisible(true);
        labelXZ.setPrefSize(70, 30);
        labelXZ.setTextFill(Color.BLACK);
        labelXZ.setText("XZ");
        labelXZ.setLayoutX(CanvasXZ.getLayoutX() + 5);
        labelXZ.setLayoutY(CanvasXZ.getLayoutY());

        labelZY.setVisible(true);
        labelZY.setPrefSize(70, 30);
        labelZY.setTextFill(Color.BLACK);
        labelZY.setText("ZY");
        labelZY.setLayoutX(CanvasZY.getLayoutX() + 5);
        labelZY.setLayoutY(CanvasZY.getLayoutY() + 23);

        labelP.setVisible(true);
        labelP.setPrefSize(100, 30);
        labelP.setTextFill(Color.BLACK);
        labelP.setText("Perspectiva");
        labelP.setLayoutX(CanvasP.getLayoutX() + 5);
        labelP.setLayoutY(CanvasP.getLayoutY());

        labelEixo.setVisible(false);
        labelEixo.setPrefSize(70, 50);
        labelEixo.setTextFill(Color.BLACK);
        labelEixo.setText("Eixo: ");
        labelEixo.setLayoutX(10);
        labelEixo.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 345);

        labelRaio.setVisible(false);
        labelRaio.setPrefSize(70, 50);
        labelRaio.setTextFill(Color.BLACK);
        labelRaio.setText("Raio: ");
        labelRaio.setLayoutX(10);
        labelRaio.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 300);

        labelVertices.setVisible(false);
        labelVertices.setPrefSize(70, 50);
        labelVertices.setTextFill(Color.BLACK);
//        labelVertices.setText("Borda");
        labelVertices.setLayoutX(10);
        labelVertices.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 245);

        labelBorda.setVisible(true);
        labelBorda.setPrefSize(70, 50);
        labelBorda.setTextFill(Color.BLACK);
        labelBorda.setText("Borda");
        labelBorda.setLayoutX(10);
        labelBorda.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 90);

        labelFill.setVisible(true);
        labelFill.setPrefSize(70, 50);
        labelFill.setTextFill(Color.BLACK);
        labelFill.setText("Fill");
        labelFill.setLayoutX(65);
        labelFill.setLayoutY(((limiteTela.getHeight() - alturaMenuBar) / 2) - 90);

        // Icones
        Image img = new Image(getClass().getResource("/Images/MaoLivre.png").toExternalForm(), 25, 30, false, false);
        MaoLivre.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/TamanhoDefinido.png").toExternalForm(), 25, 30, false, false);
        TamanhoDefinido.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Revolucao.png").toExternalForm(), 25, 30, false, false);
        Revolucao.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Extrusao.png").toExternalForm(), 25, 30, false, false);
        Extrusao.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Rotacao.png").toExternalForm(), 25, 30, false, false);
        Rotacao.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Translacao.png").toExternalForm(), 25, 30, false, false);
        Translacao.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Escala.png").toExternalForm(), 25, 30, false, false);
        Escala.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Cisalhamento.png").toExternalForm(), 25, 30, false, false);
        Cisalhamento.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Selecionar.png").toExternalForm(), 25, 30, false, false);
        SelecionarPoligono.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/Excluir.png").toExternalForm(), 25, 30, false, false);
        ExcluirPoligono.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/CorBorda.png").toExternalForm(), 25, 30, false, false);
        CorBorda.setGraphic(new ImageView(img));
        img = new Image(getClass().getResource("/Images/CorFill.png").toExternalForm(), 25, 30, false, false);
        CorPreenchimento.setGraphic(new ImageView(img));
        /**/

        focoXY = focoXZ = focoZY = focoP = false;
        erro = 5;
        PolyList = new ArrayList();
        SystemEdgeColor = Color.BLACK;
        SystemFillColor = null;

        P = new Node(0, 0, 0);
        VRP = new Node(0, 0, 500);
        zvp = 0;
        zprp = 100;

        ocultacaoFaces = false;
        wireFrame = false;
        inicializaPerspectiva();

    }

    @FXML
    void TamanhoDefinidoActionPerformed(ActionEvent event) throws IOException {
        CleanSection(false);

        EventHandler TamanhoDefinidoL_XY = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                int raio = (int) SpinnerRaio.getValue();
                int vertices = (int) SpinnerVertices.getValue();
                Node Origem = new Node(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 0);
                double teta = (2 * Math.PI / vertices);

                Node PontoInicial = new Node(0, -raio, 0);
                if (vertices == 4) {
                    PontoInicial.x = (int) Math.round((PontoInicial.y * ((double) Math.sin(Math.PI / 4))));
                    PontoInicial.y = (int) Math.round((PontoInicial.y * ((double) Math.cos(Math.PI / 4))));
                }
//                Node anterior = new Node(PontoInicial.x, PontoInicial.y);
                double antX = PontoInicial.x, antY = PontoInicial.y;
                /**/
                ArrayList<Edge> teste = new ArrayList();
                /**/
                for (int i = 1; i < vertices; i++) {
                    double proxX = (antX * ((double) Math.cos(teta))) - (antY * ((double) Math.sin(teta)));
                    double proxY = (antX * ((double) Math.sin(teta))) + (antY * ((double) Math.cos(teta)));

                    antX += Origem.x;
                    antY += Origem.y;
                    proxX += Origem.x;
                    proxY += Origem.y;

                    Edge ui = new Edge(new Node(antX, antY, 0), new Node(proxX, proxY, 0));

                    teste.add(ui);
                    antX = proxX - Origem.x;
                    antY = proxY - Origem.y;
                }

                PontoInicial.x += Origem.x;
                PontoInicial.y += Origem.y;
                Edge ui = new Edge(new Node((int) Math.round(antX) + Origem.x, (int) Math.round(antY) + Origem.y, 0), PontoInicial);

                teste.add(ui);

                Solido novo = new Solido();
                novo.AddFace(new Face(teste, SystemEdgeColor, SystemFillColor));
                novo.faceAtIndex(0).inverteOrientacao();
                PolyList.add(novo);

                PaintFunction();
            }

        };

        EventHandler TamanhoDefinidoL_XZ = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                int raio = (int) SpinnerRaio.getValue();
                int vertices = (int) SpinnerVertices.getValue();
                Node Origem = new Node((t.getX() - (CanvasZY.getWidth() / 2)), 0, -(t.getY() - (CanvasZY.getHeight() / 2)));
                double teta = (2 * Math.PI / vertices);

                Node PontoInicial = new Node(0, 0, -raio);
                if (vertices == 4) {
                    PontoInicial.x = (int) Math.round((PontoInicial.z * ((double) Math.sin(Math.PI / 4))));
                    PontoInicial.z = (int) Math.round((PontoInicial.z * ((double) Math.cos(Math.PI / 4))));
                }

//                Node anterior = new Node(PontoInicial.x, PontoInicial.y);
                double antX = PontoInicial.x, antZ = PontoInicial.z;
                /**/
                ArrayList<Edge> teste = new ArrayList();
                /**/
                for (int i = 1; i < vertices; i++) {
                    double proxX = (antX * ((double) Math.cos(teta))) - (antZ * ((double) Math.sin(teta)));
                    double proxZ = (antX * ((double) Math.sin(teta))) + (antZ * ((double) Math.cos(teta)));

                    antX += Origem.x;
                    antZ += Origem.z;
                    proxX += Origem.x;
                    proxZ += Origem.z;

                    Edge ui = new Edge(new Node(antX, 0, antZ), new Node(proxX, 0, proxZ));

                    teste.add(ui);
                    antX = proxX - Origem.x;
                    antZ = proxZ - Origem.z;
                }

                PontoInicial.x += Origem.x;
                PontoInicial.z += Origem.z;
                Edge ui = new Edge(new Node((int) Math.round(antX) + Origem.x, 0, (int) Math.round(antZ) + Origem.z), PontoInicial);

                teste.add(ui);

                Solido novo = new Solido();
                novo.AddFace(new Face(teste, SystemEdgeColor, SystemFillColor));
                PolyList.add(novo);

                PaintFunction();
            }

        };

        EventHandler TamanhoDefinidoL_YZ = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                int raio = (int) SpinnerRaio.getValue();
                int vertices = (int) SpinnerVertices.getValue();
                Node Origem = new Node(0, t.getY() - (CanvasZY.getHeight() / 2), -(t.getX() - (CanvasZY.getWidth() / 2)));
                double teta = (2 * Math.PI / vertices);

                Node PontoInicial = new Node(0, 0, -raio);
                if (vertices == 4) {
                    PontoInicial.y = (int) Math.round((PontoInicial.z * ((double) Math.sin(Math.PI / 4))));
                    PontoInicial.z = (int) Math.round((PontoInicial.z * ((double) Math.cos(Math.PI / 4))));
                }
//                Node anterior = new Node(PontoInicial.x, PontoInicial.y);
                double antY = PontoInicial.y, antZ = PontoInicial.z;
                /**/
                ArrayList<Edge> teste = new ArrayList();
                /**/
                for (int i = 1; i < vertices; i++) {
                    double proxY = (antY * ((double) Math.cos(teta))) - (antZ * ((double) Math.sin(teta)));
                    double proxZ = (antY * ((double) Math.sin(teta))) + (antZ * ((double) Math.cos(teta)));

                    antY += Origem.y;
                    antZ += Origem.z;
                    proxY += Origem.y;
                    proxZ += Origem.z;

                    Edge ui = new Edge(new Node(0, antY, antZ), new Node(0, proxY, proxZ));

                    teste.add(ui);
                    antY = proxY - Origem.y;
                    antZ = proxZ - Origem.z;
                }

                PontoInicial.y += Origem.y;
                PontoInicial.z += Origem.z;
                Edge ui = new Edge(new Node(0, (int) Math.round(antY) + Origem.y, (int) Math.round(antZ) + Origem.z), PontoInicial);

                teste.add(ui);

                Solido novo = new Solido();
                novo.AddFace(new Face(teste, SystemEdgeColor, SystemFillColor));
                PolyList.add(novo);

                PaintFunction();
            }

        };

        CanvasXY.setCursor(Cursor.OPEN_HAND);
        CanvasXZ.setCursor(Cursor.OPEN_HAND);
        CanvasZY.setCursor(Cursor.OPEN_HAND);
        SpinnerRaio.setVisible(true);
        SpinnerVertices.setVisible(true);
        labelRaio.setVisible(true);
        labelVertices.setVisible(true);
        labelVertices.setText("Vértices: ");

        CanvasXY.setOnMousePressed(TamanhoDefinidoL_XY);
        CanvasXZ.setOnMousePressed(TamanhoDefinidoL_XZ);
        CanvasZY.setOnMousePressed(TamanhoDefinidoL_YZ);

    }

    @FXML
    void MaoLivreActionPerformed(ActionEvent event) throws IOException {
        CleanSection(false);

        EventHandler MaoLivreL_XY = new EventHandler<MouseEvent>() {
            boolean check = false;
            ArrayList<Node> Pontos = new ArrayList();
            Node Origem = new Node(-1, -1, 0);

            @Override
            public void handle(MouseEvent t) {
                if (!PolyList.isEmpty() && check) {
                    PolyList.remove(PolyList.size() - 1);
                }
                if (Origem.x == -1 && Origem.y == -1) {
                    Origem.setX(t.getX() - (CanvasXY.getWidth() / 2));
                    Origem.setY(t.getY() - (CanvasXY.getHeight() / 2));
                } else if (Math.hypot(Math.abs(Origem.x - (t.getX() - (CanvasXY.getWidth() / 2))), Math.abs(Origem.y - (t.getY() - (CanvasXY.getHeight() / 2)))) < 8) {
                    Solido novo = new Solido();
                    novo.AddFace(new Face(Pontos, Origem, true, SystemEdgeColor, SystemFillColor));
                    
                    
                    PolyList.add(novo);

                    check = true;
                    CanvasXY.setCursor(Cursor.DEFAULT);
                    PaintFunction();
                    CanvasXY.setOnMousePressed(null);
                } else {
                    Node ponto = new Node(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 0);
                    Pontos.add(ponto);
                    Solido novo = new Solido();
                    novo.complete = false;
                    novo.AddFace(new Face(Pontos, Origem, false, Color.BLUE, null));
                    PolyList.add(novo);
                    check = true;
                    PaintFunction();
                }
            }
        };

        EventHandler MaoLivreL_XZ = new EventHandler<MouseEvent>() {
            boolean check = false;
            ArrayList<Node> Pontos = new ArrayList();
            Node Origem = new Node(-1, 0, -1);

            @Override
            public void handle(MouseEvent t) {
                if (!PolyList.isEmpty() && check) {
                    PolyList.remove(PolyList.size() - 1);
                }
                if (Origem.x == -1 && Origem.z == -1) {
                    Origem.setX(t.getX() - (CanvasXZ.getWidth() / 2));
                    Origem.setZ(-(t.getY() - (CanvasXZ.getHeight() / 2)));
                } else if (Math.hypot(Math.abs(Origem.x - ((t.getX() - (CanvasXZ.getWidth() / 2)))), Math.abs(Origem.z - (-(t.getY() - (CanvasXZ.getHeight() / 2))))) < 8) {
                    Solido novo = new Solido();
                    novo.AddFace(new Face(Pontos, Origem, true, SystemEdgeColor, SystemFillColor));
                    PolyList.add(novo);

                    check = true;
                    CanvasXZ.setCursor(Cursor.DEFAULT);
                    PaintFunction();
                    CanvasXZ.setOnMousePressed(null);
                } else {
                    Node ponto = new Node(t.getX() - (CanvasXZ.getWidth() / 2), 0, -(t.getY() - (CanvasXZ.getHeight() / 2)));
                    Pontos.add(ponto);
                    Solido novo = new Solido();
                    novo.complete = false;
                    novo.AddFace(new Face(Pontos, Origem, false, Color.BLUE, null));
                    PolyList.add(novo);
                    check = true;
                    PaintFunction();
                }
            }
        };

        EventHandler MaoLivreL_YZ = new EventHandler<MouseEvent>() {
            boolean check = false;
            ArrayList<Node> Pontos = new ArrayList();
            Node Origem = new Node(0, -1, -1);

            @Override
            public void handle(MouseEvent t) {
                if (!PolyList.isEmpty() && check) {
                    PolyList.remove(PolyList.size() - 1);
                }
                if (Origem.y == -1 && Origem.z == -1) {
                    Origem.setY((t.getY() - (CanvasZY.getHeight() / 2)));
                    Origem.setZ(-(t.getX() - (CanvasZY.getWidth() / 2)));
                } else if (Math.hypot(Math.abs(Origem.y - ((t.getY() - (CanvasZY.getHeight() / 2)))), Math.abs(Origem.z - (-(t.getX() - (CanvasZY.getWidth() / 2))))) < 8) {
                    Solido novo = new Solido();
                    novo.AddFace(new Face(Pontos, Origem, true, SystemEdgeColor, SystemFillColor));
                    PolyList.add(novo);

                    check = true;
                    CanvasZY.setCursor(Cursor.DEFAULT);
                    PaintFunction();
                    CanvasZY.setOnMousePressed(null);
                } else {
                    Node ponto = new Node(0, (t.getY() - (CanvasZY.getHeight() / 2)), -(t.getX() - (CanvasZY.getWidth() / 2)));
                    Pontos.add(ponto);
                    Solido novo = new Solido();
                    novo.AddFace(new Face(Pontos, Origem, false, Color.BLUE, null));
                    novo.complete = false;
                    PolyList.add(novo);
                    check = true;
                    PaintFunction();
                }
            }
        };

        CanvasXY.setCursor(Cursor.CROSSHAIR);
        CanvasXZ.setCursor(Cursor.CROSSHAIR);
        CanvasZY.setCursor(Cursor.CROSSHAIR);

        CanvasXY.setOnMousePressed(MaoLivreL_XY);
        CanvasXZ.setOnMousePressed(MaoLivreL_XZ);
        CanvasZY.setOnMousePressed(MaoLivreL_YZ);

    }

    @FXML
    void SelecionarPoligonoActionPerformed(ActionEvent event) throws IOException {
        CleanSection(false);

        EventHandler SelecionarPoligonoMP_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int index = mouseSobreAresta(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 1);
                if (index != -1) {
                    PolyList.get(index).selected = true;
                } else {
                    for (int i = 0; i < PolyList.size(); i++) {
                        PolyList.get(i).selected = false;
                    }
                }
                PaintFunction();
            }
        };

        EventHandler SelecionarPoligonoMM_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseSobreAresta(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 1) != -1) {
                    CanvasXY.setCursor(Cursor.HAND);
                } else {
                    CanvasXY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler SelecionarPoligonoMP_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int index = mouseSobreAresta(t.getX() - (CanvasXZ.getWidth() / 2), -(t.getY() - (CanvasXZ.getHeight() / 2)), 2);
                if (index != -1) {
                    PolyList.get(index).selected = true;
                } else {
                    for (int i = 0; i < PolyList.size(); i++) {
                        PolyList.get(i).selected = false;
                    }
                }
                PaintFunction();
            }
        };

        EventHandler SelecionarPoligonoMM_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseSobreAresta((t.getX() - (CanvasXZ.getWidth() / 2)), -(t.getY() - (CanvasXZ.getHeight() / 2)), 2) != -1) {
                    CanvasXZ.setCursor(Cursor.HAND);
                } else {
                    CanvasXZ.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler SelecionarPoligonoMP_YZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                int index = mouseSobreAresta(-(t.getX() - (CanvasZY.getWidth() / 2)), (t.getY() - (CanvasZY.getHeight() / 2)), 3);
                if (index != -1) {
                    PolyList.get(index).selected = true;
                } else {
                    for (int i = 0; i < PolyList.size(); i++) {
                        PolyList.get(i).selected = false;
                    }
                }
                PaintFunction();
            }
        };

        EventHandler SelecionarPoligonoMM_YZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseSobreAresta(-(t.getX() - (CanvasZY.getWidth() / 2)), (t.getY() - (CanvasZY.getHeight() / 2)), 3) != -1) {
                    CanvasZY.setCursor(Cursor.HAND);
                } else {
                    CanvasZY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        CanvasXY.setOnMousePressed(SelecionarPoligonoMP_XY);
        CanvasXY.setOnMouseMoved(SelecionarPoligonoMM_XY);
        CanvasXZ.setOnMousePressed(SelecionarPoligonoMP_XZ);
        CanvasXZ.setOnMouseMoved(SelecionarPoligonoMM_XZ);
        CanvasZY.setOnMousePressed(SelecionarPoligonoMP_YZ);
        CanvasZY.setOnMouseMoved(SelecionarPoligonoMM_YZ);
    }

    @FXML
    void ExcluirPoligonoActionPerformed(ActionEvent event) throws IOException {
        for (int i = 0; i < PolyList.size(); i++) {
            if (PolyList.get(i).selected) {
                PolyList.remove(i);
                i = -1;
            }
        }

        PaintFunction();
    }

    @FXML
    void RevolucaoActionPerformed(ActionEvent event) {
        CleanSection(true);

        EventHandler RevolucaoMP = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse_posicao_ant = new Node(event.getX(), event.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected && PolyList.get(i).numFaces == 1) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));
                    }
                }
            }
        };

        EventHandler RevolucaoMD = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node dif = new Node(event.getX() - mouse_posicao_ant.x, event.getY() - mouse_posicao_ant.y, 0);
                int secoes = (int) SpinnerVertices.getValue();
                double angulo_total = (dif.x < 360) ? dif.x : 360;
                angulo_total = (angulo_total > -360) ? angulo_total : -360;

                if (angulo_total != 0) {
                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    double angulo_entre_secoes = (angulo_total * (Math.PI / 180)) / secoes;
                    while (itr.hasNext()) {
                        int key = itr.next();
                        Solido bufferRevolucao = new Solido(SelectedPoly.get(key).Faces);
                        Face ultima_secao = new Face(SelectedPoly.get(key).faceAtIndex(0).Edges, SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                        Face intermediaria = new Face(SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                        for (int l = 0; l < secoes; l++) {
                            // Calcula Proxima Secao
                            intermediaria = new Face(SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                            for (int i = 0; i < ultima_secao.numArestas; i++) {
                                double xa = ultima_secao.nodeCoordinatesA(i).x, ya = ultima_secao.nodeCoordinatesA(i).y, za = ultima_secao.nodeCoordinatesA(i).z;
                                double xb = ultima_secao.nodeCoordinatesB(i).x, yb = ultima_secao.nodeCoordinatesB(i).y, zb = ultima_secao.nodeCoordinatesB(i).z;
                                if (eixo == 0) {
                                    intermediaria.AddEdge(new Node(xa, ya * Math.cos(angulo_entre_secoes) - za * Math.sin(angulo_entre_secoes), ya * Math.sin(angulo_entre_secoes) + za * Math.cos(angulo_entre_secoes)),
                                            new Node(xb, yb * Math.cos(angulo_entre_secoes) - zb * Math.sin(angulo_entre_secoes), yb * Math.sin(angulo_entre_secoes) + zb * Math.cos(angulo_entre_secoes)));
                                } else if (eixo == 1) {
                                    intermediaria.AddEdge(new Node(xa * Math.cos(angulo_entre_secoes) - za * Math.sin(angulo_entre_secoes), ya, xa * Math.sin(angulo_entre_secoes) + za * Math.cos(angulo_entre_secoes)),
                                            new Node(xb * Math.cos(angulo_entre_secoes) - zb * Math.sin(angulo_entre_secoes), yb, xb * Math.sin(angulo_entre_secoes) + zb * Math.cos(angulo_entre_secoes)));
                                } else if (eixo == 2) {
                                    intermediaria.AddEdge(new Node(xa * Math.cos(angulo_entre_secoes) - ya * Math.sin(angulo_entre_secoes), xa * Math.sin(angulo_entre_secoes) + ya * Math.cos(angulo_entre_secoes), za),
                                            new Node(xb * Math.cos(angulo_entre_secoes) - yb * Math.sin(angulo_entre_secoes), xb * Math.sin(angulo_entre_secoes) + yb * Math.cos(angulo_entre_secoes), zb));
                                }
                            }

                            for (int i = 0; i < ultima_secao.numArestas; i++) {
                                Face bufferFace = new Face(SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                                bufferFace.AddEdge(new Node(ultima_secao.nodeCoordinatesA(i)), new Node(intermediaria.nodeCoordinatesA(i)));
                                bufferFace.AddEdge(new Node(intermediaria.nodeCoordinatesA(i)), new Node(intermediaria.nodeCoordinatesB(i)));
                                bufferFace.AddEdge(new Node(intermediaria.nodeCoordinatesB(i)), new Node(ultima_secao.nodeCoordinatesB(i)));
                                bufferFace.AddEdge(new Node(ultima_secao.nodeCoordinatesB(i)), new Node(ultima_secao.nodeCoordinatesA(i)));
                                if (angulo_entre_secoes < 0) {
                                    bufferFace.inverteOrientacao();
                                }
                                bufferRevolucao.AddFace(new Face(bufferFace.Edges, bufferFace.edgesColor, bufferFace.PolyColor));
                            }
                            ultima_secao = new Face(intermediaria.Edges, intermediaria.edgesColor, intermediaria.PolyColor);
                        }

                        if (angulo_entre_secoes > 0) {
                            ultima_secao.inverteOrientacao();
                        } else {
                            bufferRevolucao.faceAtIndex(0).inverteOrientacao();
                        }

                        bufferRevolucao.AddFace(new Face(ultima_secao.Edges, ultima_secao.edgesColor, ultima_secao.PolyColor));

                        PolyList.set(key, bufferRevolucao);
                        PolyList.get(key).selected = true;
                        PolyList.get(key).construirRectangulo();
                    }

                    PaintFunction();
                }
            }
        };

        SpinnerVertices.setVisible(true);
        EixoSelecionado.setVisible(true);
        labelVertices.setVisible(true);
        labelVertices.setText("Seções: ");
        labelEixo.setVisible(true);

        CanvasXY.setCursor(Cursor.H_RESIZE);
        CanvasXZ.setCursor(Cursor.H_RESIZE);
        CanvasZY.setCursor(Cursor.H_RESIZE);
        CanvasXY.setOnMousePressed(RevolucaoMP);
        CanvasXY.setOnMouseDragged(RevolucaoMD);
        CanvasXZ.setOnMousePressed(RevolucaoMP);
        CanvasXZ.setOnMouseDragged(RevolucaoMD);
        CanvasZY.setOnMousePressed(RevolucaoMP);
        CanvasZY.setOnMouseDragged(RevolucaoMD);
    }

    @FXML
    void ExtrusaoActionPerformed(ActionEvent event) {
        CleanSection(true);

        EventHandler ExtrusaoMP = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse_posicao_ant = new Node(event.getX(), event.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected && PolyList.get(i).numFaces == 1) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));
                    }
                }
            }
        };

        EventHandler ExtrusaoMD = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node dif = new Node(event.getX() - mouse_posicao_ant.x, event.getY() - mouse_posicao_ant.y, 0);
                int secoes = (int) SpinnerVertices.getValue();
                double diferenca = dif.x / secoes;
                if (dif.x != 0) {
                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        Solido bufferRevolucao = new Solido(SelectedPoly.get(key).Faces);
                        Face ultima_secao = new Face(SelectedPoly.get(key).faceAtIndex(0).Edges, SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                        Face intermediaria = new Face(SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                        for (int l = 0; l < secoes; l++) {
                            // Calcula Proxima Secao
                            intermediaria = new Face(SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                            for (int i = 0; i < ultima_secao.numArestas; i++) {
                                double xa = ultima_secao.nodeCoordinatesA(i).x, ya = ultima_secao.nodeCoordinatesA(i).y, za = ultima_secao.nodeCoordinatesA(i).z;
                                double xb = ultima_secao.nodeCoordinatesB(i).x, yb = ultima_secao.nodeCoordinatesB(i).y, zb = ultima_secao.nodeCoordinatesB(i).z;
                                if (eixo == 0) {
                                    intermediaria.AddEdge(new Node(xa + diferenca, ya, za), new Node(xb + diferenca, yb, zb));
                                } else if (eixo == 1) {
                                    intermediaria.AddEdge(new Node(xa, ya + diferenca, za), new Node(xb, yb + diferenca, zb));
                                } else if (eixo == 2) {
                                    intermediaria.AddEdge(new Node(xa, ya, za + diferenca), new Node(xb, yb, zb + diferenca));
                                }
                            }

                            for (int i = 0; i < ultima_secao.numArestas; i++) {
                                Face bufferFace = new Face(SelectedPoly.get(key).faceAtIndex(0).edgesColor, SelectedPoly.get(key).faceAtIndex(0).PolyColor);
                                bufferFace.AddEdge(new Node(ultima_secao.nodeCoordinatesA(i)), new Node(intermediaria.nodeCoordinatesA(i)));
                                bufferFace.AddEdge(new Node(intermediaria.nodeCoordinatesA(i)), new Node(intermediaria.nodeCoordinatesB(i)));
                                bufferFace.AddEdge(new Node(intermediaria.nodeCoordinatesB(i)), new Node(ultima_secao.nodeCoordinatesB(i)));
                                bufferFace.AddEdge(new Node(ultima_secao.nodeCoordinatesB(i)), new Node(ultima_secao.nodeCoordinatesA(i)));
                                if (diferenca > 0) {
                                    bufferFace.inverteOrientacao();
                                }
                                bufferRevolucao.AddFace(new Face(bufferFace.Edges, bufferFace.edgesColor, bufferFace.PolyColor));
                            }
                            ultima_secao = new Face(intermediaria.Edges, intermediaria.edgesColor, intermediaria.PolyColor);
                        }

                        if (diferenca < 0) {
                            ultima_secao.inverteOrientacao();
                        } else {
                            bufferRevolucao.faceAtIndex(0).inverteOrientacao();
                        }
                        
                        bufferRevolucao.AddFace(new Face(ultima_secao.Edges, ultima_secao.edgesColor, ultima_secao.PolyColor));

                        PolyList.set(key, bufferRevolucao);
                        PolyList.get(key).selected = true;
                        PolyList.get(key).construirRectangulo();
                    }

                    PaintFunction();
                }
            }
        };

        SpinnerVertices.setVisible(true);
        EixoSelecionado.setVisible(true);
        labelVertices.setVisible(true);
        labelVertices.setText("Seções: ");
        labelEixo.setVisible(true);

        CanvasXY.setCursor(Cursor.H_RESIZE);
        CanvasXZ.setCursor(Cursor.H_RESIZE);
        CanvasZY.setCursor(Cursor.H_RESIZE);
        CanvasXY.setOnMousePressed(ExtrusaoMP);
        CanvasXY.setOnMouseDragged(ExtrusaoMD);
        CanvasXZ.setOnMousePressed(ExtrusaoMP);
        CanvasXZ.setOnMouseDragged(ExtrusaoMD);
        CanvasZY.setOnMousePressed(ExtrusaoMP);
        CanvasZY.setOnMouseDragged(ExtrusaoMD);
    }

    @FXML
    void RotacaoActionPerformed(ActionEvent event) throws IOException {
        CleanSection(true);

        EventHandler RotacaoMP = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);
                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));
                    }
                }

                drag = true;
            }
        };

        EventHandler RotacaoMR = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = null;
                drag = false;
            }
        };

        EventHandler RotacaoMM_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseInsidePolyRectangle(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 1)) {
                    CanvasXY.setCursor(Cursor.WAIT);
                } else {
                    CanvasXY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler RotacaoMD_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double angle = (t.getX() - mouse_posicao_ant.x) / 80.0;
//                double angleY = (t.getY() - mouse_posicao_ant.y) / 80.0;
//                double angleZ = 0;

                if (drag && angle != 0) {

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int i = 0; i < SelectedPoly.get(key).numFaces; i++) {
                            for (int j = 0; j < SelectedPoly.get(key).faceAtIndex(i).numArestas; j++) {
                                SelectedPoly.get(key).construirRectangulo();
                                double x = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).x - SelectedPoly.get(key).centroX;
                                double y = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).y - SelectedPoly.get(key).centroY;
//                                double z = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).z - SelectedPoly.get(key).centroZ;
//                                double nx = x*Math.cos(angleY)*Math.cos(angleZ) + y*Math.cos(angleY)*-(Math.sin(angleZ))+z*Math.sin(angleY);
//                                double ny = x*((-Math.sin(angleX))*(-Math.sin(angleY))*Math.cos(angleY)+Math.cos(angleX)*Math.sin(angleZ)) +
//                                        y*((-Math.sin(angleX))*(-Math.sin(angleY))*(-Math.sin(angleZ)) + (Math.cos(angleX)*Math.cos(angleZ))) +
//                                        z*((-Math.sin(angleX))*Math.cos(angleY));
//                                double nz = x*( (Math.cos(angleX)*(-Math.sin(angleY))*Math.cos(angleZ)) + (Math.sin(angleX)*Math.sin(angleZ))) +
//                                        y*((Math.cos(angleX)*(-Math.sin(angleY))*(-Math.sin(angleZ))) + (Math.sin(angleX)*Math.cos(angleZ) ) ) +
//                                        z*Math.cos(angleX)*Math.cos(angleY);
//                                
//                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setX(nx + SelectedPoly.get(key).centroX);
//                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setY(ny + SelectedPoly.get(key).centroY);
//                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setZ(nz + SelectedPoly.get(key).centroZ);

                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setX(x * Math.cos(angle) - y * Math.sin(angle) + SelectedPoly.get(key).centroX);
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setY(x * Math.sin(angle) + y * Math.cos(angle) + SelectedPoly.get(key).centroY);

                                x = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesB(j).x - SelectedPoly.get(key).centroX;
                                y = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesB(j).y - SelectedPoly.get(key).centroY;
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesB(j).setX(x * Math.cos(angle) - y * Math.sin(angle) + SelectedPoly.get(key).centroX);
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesB(j).setY(x * Math.sin(angle) + y * Math.cos(angle) + SelectedPoly.get(key).centroY);
                            }
                        }
                        PolyList.get(key).construirRectangulo();
                    }

                }
                PaintFunction();

            }
        };

        EventHandler RotacaoMM_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseInsidePolyRectangle(t.getX() - (CanvasXZ.getWidth() / 2), -(t.getY() - (CanvasXZ.getHeight() / 2)), 2)) {
                    CanvasXZ.setCursor(Cursor.WAIT);
                } else {
                    CanvasXZ.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler RotacaoMD_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double angle = -(t.getX() - mouse_posicao_ant.x) / 80.0;
                if (drag && angle != 0) {

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int i = 0; i < SelectedPoly.get(key).numFaces; i++) {
                            for (int j = 0; j < SelectedPoly.get(key).faceAtIndex(i).numArestas; j++) {
                                double x = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).x - SelectedPoly.get(key).centroX;
                                double y = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).z - SelectedPoly.get(key).centroZ;
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setX(x * Math.cos(angle) - y * Math.sin(angle) + SelectedPoly.get(key).centroX);
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setZ(x * Math.sin(angle) + y * Math.cos(angle) + SelectedPoly.get(key).centroZ);

                                x = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesB(j).x - SelectedPoly.get(key).centroX;
                                y = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesB(j).z - SelectedPoly.get(key).centroZ;
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesB(j).setX(x * Math.cos(angle) - y * Math.sin(angle) + SelectedPoly.get(key).centroX);
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesB(j).setZ(x * Math.sin(angle) + y * Math.cos(angle) + SelectedPoly.get(key).centroZ);
                            }
                        }
                        PolyList.get(key).construirRectangulo();
                    }

                }
                PaintFunction();

            }
        };

        EventHandler RotacaoMM_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseInsidePolyRectangle(-(t.getX() - (CanvasXY.getWidth() / 2)), (t.getY() - (CanvasXY.getHeight() / 2)), 3)) {
                    CanvasZY.setCursor(Cursor.WAIT);
                } else {
                    CanvasZY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler RotacaoMD_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                double angle = -(t.getX() - mouse_posicao_ant.x) / 80.0;
                if (drag && angle != 0) {

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int i = 0; i < SelectedPoly.get(key).numFaces; i++) {
                            for (int j = 0; j < SelectedPoly.get(key).faceAtIndex(i).numArestas; j++) {
                                double x = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).z - SelectedPoly.get(key).centroZ;
                                double y = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesA(j).y - SelectedPoly.get(key).centroY;
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setZ(x * Math.cos(angle) - y * Math.sin(angle) + SelectedPoly.get(key).centroZ);
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesA(j).setY(x * Math.sin(angle) + y * Math.cos(angle) + SelectedPoly.get(key).centroY);

                                x = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesB(j).z - SelectedPoly.get(key).centroZ;
                                y = SelectedPoly.get(key).faceAtIndex(i).nodeCoordinatesB(j).y - SelectedPoly.get(key).centroY;
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesB(j).setZ(x * Math.cos(angle) - y * Math.sin(angle) + SelectedPoly.get(key).centroZ);
                                PolyList.get(key).faceAtIndex(i).nodeCoordinatesB(j).setY(x * Math.sin(angle) + y * Math.cos(angle) + SelectedPoly.get(key).centroY);
                            }
                        }
                        PolyList.get(key).construirRectangulo();
                    }

                }
                PaintFunction();

            }
        };

        CanvasXY.setOnMousePressed(RotacaoMP);
        CanvasXY.setOnMouseReleased(RotacaoMR);
        CanvasXY.setOnMouseMoved(RotacaoMM_XY);
        CanvasXY.setOnMouseDragged(RotacaoMD_XY);

        CanvasXZ.setOnMousePressed(RotacaoMP);
        CanvasXZ.setOnMouseReleased(RotacaoMR);
        CanvasXZ.setOnMouseMoved(RotacaoMM_XZ);
        CanvasXZ.setOnMouseDragged(RotacaoMD_XZ);

        CanvasZY.setOnMousePressed(RotacaoMP);
        CanvasZY.setOnMouseReleased(RotacaoMR);
        CanvasZY.setOnMouseMoved(RotacaoMM_ZY);
        CanvasZY.setOnMouseDragged(RotacaoMD_ZY);

    }

    @FXML
    void TranslacaoActionPerformed(ActionEvent event) throws IOException {
        CleanSection(true);
        drag = false;

        EventHandler TranslacaoMP_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                if (mouseInsidePolyRectangle(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 1)) {
                    drag = true;
                } else {
                    drag = false;
                }
            }
        };

        EventHandler TranslacaoMM_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseInsidePolyRectangle(t.getX() - (CanvasXY.getWidth() / 2), t.getY() - (CanvasXY.getHeight() / 2), 1)) {
                    CanvasXY.setCursor(Cursor.MOVE);
                } else {
                    CanvasXY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler TranslacaoMD_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (drag) {
                    for (int i = 0; i < PolyList.size(); i++) {
                        if (PolyList.get(i).selected) {
                            for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                                for (int k = 0; k < PolyList.get(i).faceAtIndex(j).numArestas; k++) {
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).x += t.getX() - mouse_posicao_ant.x;
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).y += t.getY() - mouse_posicao_ant.y;

                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).x += t.getX() - mouse_posicao_ant.x;
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).y += t.getY() - mouse_posicao_ant.y;
                                }
                                PolyList.get(i).faceAtIndex(j).construirRectangulo();
                                PolyList.get(i).faceAtIndex(j).calcularIntersec();
                            }
                        }
                        PolyList.get(i).construirRectangulo();
                    }

                    mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                    PaintFunction();
                }
            }
        };

        EventHandler TranslacaoMP_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                if (mouseInsidePolyRectangle(t.getX() - (CanvasXY.getWidth() / 2), -(t.getY() - (CanvasXY.getHeight() / 2)), 2)) {
                    drag = true;
                } else {
                    drag = false;
                }
            }
        };

        EventHandler TranslacaoMM_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseInsidePolyRectangle(t.getX() - (CanvasXY.getWidth() / 2), -(t.getY() - (CanvasXY.getHeight() / 2)), 2)) {
                    CanvasXZ.setCursor(Cursor.MOVE);
                } else {
                    CanvasXZ.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler TranslacaoMD_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (drag) {
                    for (int i = 0; i < PolyList.size(); i++) {
                        if (PolyList.get(i).selected) {
                            for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                                for (int k = 0; k < PolyList.get(i).faceAtIndex(j).numArestas; k++) {
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).x += t.getX() - mouse_posicao_ant.x;
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).z += -(t.getY() - mouse_posicao_ant.y);

                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).x += t.getX() - mouse_posicao_ant.x;
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).z += -(t.getY() - mouse_posicao_ant.y);
                                }
                                PolyList.get(i).faceAtIndex(j).construirRectangulo();
                                PolyList.get(i).faceAtIndex(j).calcularIntersec();
                            }
                        }
                        PolyList.get(i).construirRectangulo();
                    }

                    mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                    PaintFunction();
                }
            }
        };

        EventHandler TranslacaoMP_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                if (mouseInsidePolyRectangle(-(t.getX() - (CanvasZY.getWidth() / 2)), t.getY() - (CanvasZY.getHeight() / 2), 3)) {
                    drag = true;
                } else {
                    drag = false;
                }
            }
        };

        EventHandler TranslacaoMM_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mouseInsidePolyRectangle(-(t.getX() - (CanvasZY.getWidth() / 2)), t.getY() - (CanvasZY.getHeight() / 2), 3)) {
                    CanvasZY.setCursor(Cursor.MOVE);
                } else {
                    CanvasZY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler TranslacaoMD_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (drag) {
                    for (int i = 0; i < PolyList.size(); i++) {
                        if (PolyList.get(i).selected) {
                            for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                                for (int k = 0; k < PolyList.get(i).faceAtIndex(j).numArestas; k++) {
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).z += -(t.getX() - mouse_posicao_ant.x);
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).y += (t.getY() - mouse_posicao_ant.y);

                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).z += -(t.getX() - mouse_posicao_ant.x);
                                    PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).y += t.getY() - mouse_posicao_ant.y;
                                }
                                PolyList.get(i).faceAtIndex(j).construirRectangulo();
                                PolyList.get(i).faceAtIndex(j).calcularIntersec();
                            }
                        }
                        PolyList.get(i).construirRectangulo();
                    }

                    mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                    PaintFunction();
                }
            }
        };

        CanvasXY.setOnMousePressed(TranslacaoMP_XY);
        CanvasXY.setOnMouseMoved(TranslacaoMM_XY);
        CanvasXY.setOnMouseDragged(TranslacaoMD_XY);
        CanvasXZ.setOnMousePressed(TranslacaoMP_XZ);
        CanvasXZ.setOnMouseMoved(TranslacaoMM_XZ);
        CanvasXZ.setOnMouseDragged(TranslacaoMD_XZ);
        CanvasZY.setOnMousePressed(TranslacaoMP_ZY);
        CanvasZY.setOnMouseMoved(TranslacaoMM_ZY);
        CanvasZY.setOnMouseDragged(TranslacaoMD_ZY);
    }

    @FXML
    void EscalaActionPerformed(ActionEvent event) throws IOException {
        CleanSection(true);
        op = -1;

        EventHandler EscalaMP_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));

                        if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            op = 1;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            op = 2;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            op = 3;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            op = 4;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            op = 5;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            op = 6;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            op = 7;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            op = 8;
                        }

                    }

                }
            }
        };

        EventHandler EscalaMM_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean check = false;

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {

                        if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            CanvasXY.setCursor(Cursor.NW_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            CanvasXY.setCursor(Cursor.NE_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            CanvasXY.setCursor(Cursor.SW_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            CanvasXY.setCursor(Cursor.SE_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            CanvasXY.setCursor(Cursor.N_RESIZE);
                            check = true;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            CanvasXY.setCursor(Cursor.W_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            CanvasXY.setCursor(Cursor.S_RESIZE);
                            check = true;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            CanvasXY.setCursor(Cursor.E_RESIZE);
                            check = true;
                        }
                    }

                }

                if (!check) {
                    CanvasXY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler EscalaMD_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (op != -1) {

                    double deslocX = (t.getX() - mouse_posicao_ant.x) / 80.0;
                    double deslocY = (t.getY() - mouse_posicao_ant.y) / 80.0;
//                    double deslocMAX = Math.abs(deslocX) >= Math.abs( deslocY) ? deslocX:deslocY;

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int j = 0; j < SelectedPoly.get(key).numFaces; j++) {
                            for (int k = 0; k < SelectedPoly.get(key).faceAtIndex(j).numArestas; k++) {
                                SelectedPoly.get(key).faceAtIndex(j).construirRectangulo();
                                switch (op) {

                                    case 1: {
                                        deslocX = Math.min(1, deslocX);
                                        deslocY = Math.min(1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 2: {
                                        deslocX = Math.max(-1, deslocX);
                                        deslocY = Math.min(1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;

                                    }
                                    case 3: {
                                        deslocX = Math.min(1, deslocX);
                                        deslocY = Math.max(-1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;

                                    }
                                    case 4: {
                                        deslocX = Math.max(-1, deslocX);
                                        deslocY = Math.max(-1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;

                                    }
                                    case 5: {
                                        deslocY = Math.min(1, deslocY);
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 6: {
                                        deslocX = Math.min(1, deslocX);
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }
                                    case 7: {
                                        deslocY = Math.max(-1, deslocY);
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 8: {
                                        deslocX = Math.max(-1, deslocX);
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }

                                }
                            }
                            PolyList.get(key).faceAtIndex(j).construirRectangulo();
                            PolyList.get(key).faceAtIndex(j).calcularIntersec();
                        }
                        PolyList.get(key).construirRectangulo();

                    }
                }

                PaintFunction();
            }
        };

        EventHandler EscalaMP_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));

                        if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            op = 1;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            op = 2;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            op = 3;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            op = 4;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            op = 5;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            op = 6;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            op = 7;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            op = 8;
                        }

                    }

                }
            }
        };

        EventHandler EscalaMM_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean check = false;

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {

                        if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            CanvasXZ.setCursor(Cursor.SW_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            CanvasXZ.setCursor(Cursor.SE_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            CanvasXZ.setCursor(Cursor.NW_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            CanvasXZ.setCursor(Cursor.NE_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            CanvasXZ.setCursor(Cursor.N_RESIZE);
                            check = true;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            CanvasXZ.setCursor(Cursor.W_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            CanvasXZ.setCursor(Cursor.S_RESIZE);
                            check = true;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            CanvasXZ.setCursor(Cursor.E_RESIZE);
                            check = true;
                        }
                    }

                }

                if (!check) {
                    CanvasXZ.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler EscalaMD_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (op != -1) {

                    double deslocX = (t.getX() - mouse_posicao_ant.x) / 80.0;
                    double deslocY = -(t.getY() - mouse_posicao_ant.y) / 80.0;
//                    double deslocMAX = Math.abs(deslocX) >= Math.abs( deslocY) ? deslocX:deslocY;

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int j = 0; j < SelectedPoly.get(key).numFaces; j++) {
                            for (int k = 0; k < SelectedPoly.get(key).faceAtIndex(j).numArestas; k++) {
                                SelectedPoly.get(key).faceAtIndex(j).construirRectangulo();
                                switch (op) {

                                    case 1: {
                                        deslocX = Math.min(1, deslocX);
                                        deslocY = Math.min(1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 2: {
                                        deslocX = Math.max(-1, deslocX);
                                        deslocY = Math.min(1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;

                                    }
                                    case 3: {
                                        deslocX = Math.min(1, deslocX);
                                        deslocY = Math.max(-1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;

                                    }
                                    case 4: {
                                        deslocX = Math.max(-1, deslocX);
                                        deslocY = Math.max(-1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;

                                    }
                                    case 5: {
                                        deslocY = Math.min(1, deslocY);
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 6: {
                                        deslocX = Math.min(1, deslocX);
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }
                                    case 7: {
                                        deslocY = Math.max(-1, deslocY);
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 8: {
                                        deslocX = Math.max(-1, deslocX);
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }

                                }
                            }
                            PolyList.get(key).faceAtIndex(j).construirRectangulo();
                            PolyList.get(key).faceAtIndex(j).calcularIntersec();
                        }
                        PolyList.get(key).construirRectangulo();

                    }
                }

                PaintFunction();
            }
        };

        EventHandler EscalaMP_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));
                        double eventY = t.getY() - (CanvasZY.getHeight() / 2);
                        double eventX = -(t.getX() - (CanvasZY.getWidth() / 2));

                        if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            op = 1;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            op = 2;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            op = 3;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            op = 4;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            op = 5;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)) {
                            op = 6;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            op = 7;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)) {
                            op = 8;
                        }

                    }

                }
            }
        };

        EventHandler EscalaMM_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean check = false;

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {

                        double eventY = t.getY() - (CanvasZY.getHeight() / 2);
                        double eventX = -(t.getX() - (CanvasZY.getWidth() / 2));

                        if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            CanvasZY.setCursor(Cursor.NE_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            CanvasZY.setCursor(Cursor.NW_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            CanvasZY.setCursor(Cursor.SE_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            CanvasZY.setCursor(Cursor.SW_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            CanvasZY.setCursor(Cursor.N_RESIZE);
                            check = true;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)) {
                            CanvasZY.setCursor(Cursor.W_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            CanvasZY.setCursor(Cursor.S_RESIZE);
                            check = true;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)) {
                            CanvasZY.setCursor(Cursor.E_RESIZE);
                            check = true;
                        }
                    }

                }

                if (!check) {
                    CanvasZY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler EscalaMD_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (op != -1) {

                    double deslocX = -(t.getX() - mouse_posicao_ant.x) / 80.0;
                    double deslocY = (t.getY() - mouse_posicao_ant.y) / 80.0;
//                    double deslocMAX = Math.abs(deslocX) >= Math.abs( deslocY) ? deslocX:deslocY;

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int j = 0; j < SelectedPoly.get(key).numFaces; j++) {
                            for (int k = 0; k < SelectedPoly.get(key).faceAtIndex(j).numArestas; k++) {
                                SelectedPoly.get(key).faceAtIndex(j).construirRectangulo();
                                switch (op) {

                                    case 1: {
                                        deslocX = Math.min(1, deslocX);
                                        deslocY = Math.min(1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 2: {
                                        deslocX = Math.max(-1, deslocX);
                                        deslocY = Math.min(1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;

                                    }
                                    case 3: {
                                        deslocX = Math.min(1, deslocX);
                                        deslocY = Math.max(-1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;

                                    }
                                    case 4: {
                                        deslocX = Math.max(-1, deslocX);
                                        deslocY = Math.max(-1, deslocY);

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;

                                    }
                                    case 5: {
                                        deslocY = Math.min(1, deslocY);
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y - (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 6: {
                                        deslocX = Math.min(1, deslocX);
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x - (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 7: {
                                        deslocY = Math.max(-1, deslocY);
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(y * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 8: {
                                        deslocX = Math.max(-1, deslocX);
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x + (int) Math.round(x * deslocX)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }

                                }
                            }
                            PolyList.get(key).faceAtIndex(j).construirRectangulo();
                            PolyList.get(key).faceAtIndex(j).calcularIntersec();
                        }
                        PolyList.get(key).construirRectangulo();

                    }
                }

                PaintFunction();
            }
        };

        CanvasXY.setOnMousePressed(EscalaMP_XY);
        CanvasXY.setOnMouseMoved(EscalaMM_XY);
        CanvasXY.setOnMouseDragged(EscalaMD_XY);
        CanvasXZ.setOnMousePressed(EscalaMP_XZ);
        CanvasXZ.setOnMouseMoved(EscalaMM_XZ);
        CanvasXZ.setOnMouseDragged(EscalaMD_XZ);
        CanvasZY.setOnMousePressed(EscalaMP_ZY);
        CanvasZY.setOnMouseMoved(EscalaMM_ZY);
        CanvasZY.setOnMouseDragged(EscalaMD_ZY);
    }

    @FXML
    void CisalhamentoActionPerformed(ActionEvent event) throws IOException {
        CleanSection(true);
        op = -1;

        EventHandler CisalhamentoMP_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));

                        if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            op = 5;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            op = 6;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            op = 7;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            op = 8;
                        }

                    }

                }
            }
        };

        EventHandler CisalhamentoMM_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean check = false;

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {

                        if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).minY + erro)) {
                            CanvasXY.setCursor(Cursor.N_RESIZE);
                            check = true;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            CanvasXY.setCursor(Cursor.W_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).maxY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)) {
                            CanvasXY.setCursor(Cursor.S_RESIZE);
                            check = true;
                        } else if ((t.getY() - (CanvasXY.getHeight() / 2) >= PolyList.get(i).minY - erro && t.getY() - (CanvasXY.getHeight() / 2) <= PolyList.get(i).maxY + erro)
                                && (t.getX() - (CanvasXY.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXY.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            CanvasXY.setCursor(Cursor.E_RESIZE);
                            check = true;
                        }
                    }

                }

                if (!check) {
                    CanvasXY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler CisalhamentoMD_XY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (op != -1) {

                    double deslocX = (t.getX() - mouse_posicao_ant.x) / 80.0;
                    double deslocY = (t.getY() - mouse_posicao_ant.y) / 80.0;
//                    double deslocMAX = Math.abs(deslocX) >= Math.abs( deslocY) ? deslocX:deslocY;

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int j = 0; j < SelectedPoly.get(key).numFaces; j++) {
                            for (int k = 0; k < SelectedPoly.get(key).faceAtIndex(j).numArestas; k++) {

                                switch (op) {

                                    case 5: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 6: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }
                                    case 7: {
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 8: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }

                                }
                            }
                            PolyList.get(key).faceAtIndex(j).construirRectangulo();
                            PolyList.get(key).faceAtIndex(j).calcularIntersec();
                        }
                        PolyList.get(key).construirRectangulo();

                    }
                }

                PaintFunction();
            }
        };

        EventHandler CisalhamentoMP_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));

                        if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            op = 1;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            op = 2;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            op = 3;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            op = 4;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            op = 5;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            op = 6;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            op = 7;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            op = 8;
                        }

                    }

                }
            }
        };

        EventHandler CisalhamentoMM_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean check = false;

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {

                        if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            CanvasXZ.setCursor(Cursor.SW_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            CanvasXZ.setCursor(Cursor.SE_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            CanvasXZ.setCursor(Cursor.NW_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            CanvasXZ.setCursor(Cursor.NE_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).minZ + erro)) {
                            CanvasXZ.setCursor(Cursor.N_RESIZE);
                            check = true;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).minX + erro)) {
                            CanvasXZ.setCursor(Cursor.W_RESIZE);
                            check = true;
                        } else if ((t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).minX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)
                                && (-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).maxZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)) {
                            CanvasXZ.setCursor(Cursor.S_RESIZE);
                            check = true;
                        } else if ((-(t.getY() - (CanvasXZ.getHeight() / 2)) >= PolyList.get(i).minZ - erro && -(t.getY() - (CanvasXZ.getHeight() / 2)) <= PolyList.get(i).maxZ + erro)
                                && (t.getX() - (CanvasXZ.getWidth() / 2) >= PolyList.get(i).maxX - erro && t.getX() - (CanvasXZ.getWidth() / 2) <= PolyList.get(i).maxX + erro)) {
                            CanvasXZ.setCursor(Cursor.E_RESIZE);
                            check = true;
                        }
                    }

                }

                if (!check) {
                    CanvasXZ.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler CisalhamentoMD_XZ = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (op != -1) {

                    double deslocX = (t.getX() - mouse_posicao_ant.x) / 80.0;
                    double deslocY = -(t.getY() - mouse_posicao_ant.y) / 80.0;
//                    double deslocMAX = Math.abs(deslocX) >= Math.abs( deslocY) ? deslocX:deslocY;

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int j = 0; j < SelectedPoly.get(key).numFaces; j++) {
                            for (int k = 0; k < SelectedPoly.get(key).faceAtIndex(j).numArestas; k++) {

                                switch (op) {

                                    case 5: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 6: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }
                                    case 7: {
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 8: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).x - SelectedPoly.get(key).centroX;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).x - SelectedPoly.get(key).centroX;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).x = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroX;

                                        break;
                                    }

                                }
                            }
                            PolyList.get(key).faceAtIndex(j).construirRectangulo();
                            PolyList.get(key).faceAtIndex(j).calcularIntersec();
                        }
                        PolyList.get(key).construirRectangulo();

                    }
                }

                PaintFunction();
            }
        };

        EventHandler CisalhamentoMP_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouse_posicao_ant = new Node(t.getX(), t.getY(), 0);

                SelectedPoly = new Hashtable<>();

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {
                        SelectedPoly.put(i, new Solido(PolyList.get(i).Faces));
                        double eventY = t.getY() - (CanvasZY.getHeight() / 2);
                        double eventX = -(t.getX() - (CanvasZY.getWidth() / 2));

                        if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            op = 1;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            op = 2;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            op = 3;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            op = 4;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            op = 5;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)) {
                            op = 6;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            op = 7;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)) {
                            op = 8;
                        }

                    }

                }
            }
        };

        EventHandler CisalhamentoMM_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                boolean check = false;

                for (int i = 0; i < PolyList.size(); i++) {
                    if (PolyList.get(i).selected) {

                        double eventY = t.getY() - (CanvasZY.getHeight() / 2);
                        double eventX = -(t.getX() - (CanvasZY.getWidth() / 2));

                        if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            CanvasZY.setCursor(Cursor.NE_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            CanvasZY.setCursor(Cursor.NW_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            CanvasZY.setCursor(Cursor.SE_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            CanvasZY.setCursor(Cursor.SW_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).minY + erro)) {
                            CanvasZY.setCursor(Cursor.N_RESIZE);
                            check = true;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).minZ + erro)) {
                            CanvasZY.setCursor(Cursor.W_RESIZE);
                            check = true;
                        } else if ((eventX >= PolyList.get(i).minZ - erro && eventX <= PolyList.get(i).maxZ + erro)
                                && (eventY >= PolyList.get(i).maxY - erro && eventY <= PolyList.get(i).maxY + erro)) {
                            CanvasZY.setCursor(Cursor.S_RESIZE);
                            check = true;
                        } else if ((eventY >= PolyList.get(i).minY - erro && eventY <= PolyList.get(i).maxY + erro)
                                && (eventX >= PolyList.get(i).maxZ - erro && eventX <= PolyList.get(i).maxZ + erro)) {
                            CanvasZY.setCursor(Cursor.E_RESIZE);
                            check = true;
                        }
                    }

                }

                if (!check) {
                    CanvasZY.setCursor(Cursor.DEFAULT);
                }
            }
        };

        EventHandler CisalhamentoMD_ZY = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (op != -1) {

                    double deslocX = -(t.getX() - mouse_posicao_ant.x) / 80.0;
                    double deslocY = (t.getY() - mouse_posicao_ant.y) / 80.0;
//                    double deslocMAX = Math.abs(deslocX) >= Math.abs( deslocY) ? deslocX:deslocY;

                    Set<Integer> KeysSet = SelectedPoly.keySet();
                    Iterator<Integer> itr = KeysSet.iterator();
                    while (itr.hasNext()) {
                        int key = itr.next();
                        for (int j = 0; j < SelectedPoly.get(key).numFaces; j++) {
                            for (int k = 0; k < SelectedPoly.get(key).faceAtIndex(j).numArestas; k++) {

                                switch (op) {

                                    case 5: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 6: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }
                                    case 7: {
                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).y = (y + (int) Math.round(x * deslocY)) + SelectedPoly.get(key).centroY;

                                        break;
                                    }
                                    case 8: {

                                        double x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).z - SelectedPoly.get(key).centroZ;
                                        double y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesA(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesA(k).z = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroZ;

                                        x = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).z - SelectedPoly.get(key).centroZ;
                                        y = SelectedPoly.get(key).faceAtIndex(j).nodeCoordinatesB(k).y - SelectedPoly.get(key).centroY;
                                        PolyList.get(key).faceAtIndex(j).nodeCoordinatesB(k).z = (x + (int) Math.round(y * deslocX)) + SelectedPoly.get(key).centroZ;

                                        break;
                                    }

                                }
                            }
                            PolyList.get(key).faceAtIndex(j).construirRectangulo();
                            PolyList.get(key).faceAtIndex(j).calcularIntersec();
                        }
                        PolyList.get(key).construirRectangulo();

                    }
                }

                PaintFunction();
            }
        };

        CanvasXY.setOnMousePressed(CisalhamentoMP_XY);
        CanvasXY.setOnMouseMoved(CisalhamentoMM_XY);
        CanvasXY.setOnMouseDragged(CisalhamentoMD_XY);
        CanvasXZ.setOnMousePressed(CisalhamentoMP_XZ);
        CanvasXZ.setOnMouseMoved(CisalhamentoMM_XZ);
        CanvasXZ.setOnMouseDragged(CisalhamentoMD_XZ);
        CanvasZY.setOnMousePressed(CisalhamentoMP_ZY);
        CanvasZY.setOnMouseMoved(CisalhamentoMM_ZY);
        CanvasZY.setOnMouseDragged(CisalhamentoMD_ZY);
    }

    @FXML
    void CorBordaActionPerformed(ActionEvent event) throws IOException {

        for (int i = 0; i < PolyList.size(); i++) {
            if (PolyList.get(i).selected) {
                for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                    PolyList.get(i).faceAtIndex(j).setEdgeColor(SystemEdgeColor);
                }
            }
        }

        PaintFunction();
    }

    @FXML
    void CorPreenchimentoActionPerformed(ActionEvent event) throws IOException {
        for (int i = 0; i < PolyList.size(); i++) {
            if (PolyList.get(i).selected) {
                for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                    PolyList.get(i).faceAtIndex(j).setFillColor(SystemFillColor);
                }
            }
        }

        PaintFunction();

    }

    @FXML
    void NovoActionPerformed(ActionEvent event) throws IOException {
        CleanSection(false);
        PolyList.clear();
        PaintFunction();
    }

    @FXML
    void CarregarActionPerformed(ActionEvent event) throws IOException {
        CleanSection(false);

        JFileChooser j = new JFileChooser();
        if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = j.getSelectedFile();
            FileInputStream fos = null;
            try {
                fos = new FileInputStream(f);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ObjectInputStream oos = null;
            try {
                oos = new ObjectInputStream(fos);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                try {
                    PolyList.clear();
                    ArrayList<ArquivoSolido> tempList = new ArrayList<>();

                    tempList = (ArrayList<ArquivoSolido>) oos.readObject();

                    for (int i = 0; i < tempList.size(); i++) {
                        Solido tempSolido = new Solido();
                        for (int k = 0; k < tempList.get(i).numFaces; k++) {
                            Face tempFace = new Face(tempList.get(i).faceAtIndex(k).Edges, Color.valueOf(tempList.get(i).faceAtIndex(k).edgesColor), Color.valueOf(tempList.get(i).faceAtIndex(k).edgesColor));
                            tempSolido.AddFace(tempFace);
                        }
                        PolyList.add(tempSolido);
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        PaintFunction();
    }

    @FXML
    void SalvarActionPerformed(ActionEvent event) throws IOException {
        CleanSection(false);

        JFileChooser j = new JFileChooser();
        if (j.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = j.getSelectedFile();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                ArrayList<ArquivoSolido> tempList = new ArrayList<>();
                for (int i = 0; i < PolyList.size(); i++) {
                    ArquivoSolido tempSolido = new ArquivoSolido();
                    for (int k = 0; k < PolyList.get(i).numFaces; k++) {
                        ArquivoFace tempFace = new ArquivoFace(PolyList.get(i).faceAtIndex(k).Edges, PolyList.get(i).faceAtIndex(k).edgesColor, PolyList.get(i).faceAtIndex(k).edgesColor);
                        tempSolido.AddFace(tempFace);
                    }
                    tempList.add(tempSolido);
                }
                oos.writeObject(tempList);
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        PaintFunction();
    }

    @FXML
    void SairActionPerformed(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void OcultacaoActionPerformed(ActionEvent event) {
        ocultacaoFaces = !ocultacaoFaces;
        PaintFunction();
    }

    @FXML
    void WireframeActionPerformed(ActionEvent event) {
        wireFrame = !wireFrame;
        PaintFunction();
    }

    @FXML
    void MaximizeXYActionPerformed(ActionEvent event) {
        CleanSection(false);
        if (!focoXY) {
            CanvasXY.setVisible(true);
            CanvasXY.setWidth((limiteTela.getWidth() - larguraTabMenu));
            CanvasXY.setHeight((limiteTela.getHeight() - alturaMenuBar));
            CanvasXY.setLayoutX(0);
            CanvasXY.setLayoutY(alturaMenuBar);

            CanvasXZ.setVisible(false);
            CanvasZY.setVisible(false);
            CanvasP.setVisible(false);
            SeparatorHorizontal.setVisible(false);
            SeparatorVertical.setVisible(false);

            MaximizeXY.setLayoutX(CanvasXY.getWidth() - 35);
            MaximizeXY.setLayoutY(CanvasXY.getLayoutY() + 5);
            MaximizeXZ.setVisible(false);
            MaximizeZY.setVisible(false);
            MaximizeP.setVisible(false);

            labelXY.setLayoutX(CanvasXY.getLayoutX() + 5);
            labelXY.setLayoutY(CanvasXY.getLayoutY());
            labelXZ.setVisible(false);
            labelZY.setVisible(false);
            labelP.setVisible(false);

            PaintFunction();
            focoXY = true;
        } else {
            CanvasXY.setVisible(true);
            CanvasXY.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
            CanvasXY.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
            CanvasXY.setLayoutX(0);
            CanvasXY.setLayoutY(0);

            CanvasXZ.setVisible(true);
            CanvasZY.setVisible(true);
            CanvasP.setVisible(true);
            SeparatorHorizontal.setVisible(true);
            SeparatorVertical.setVisible(true);
            MaximizeXZ.setVisible(true);
            MaximizeZY.setVisible(true);
            MaximizeP.setVisible(true);

            labelXY.setLayoutX(CanvasXY.getLayoutX() + 5);
            labelXY.setLayoutY(CanvasXY.getLayoutY() + 23);
            labelXZ.setVisible(true);
            labelZY.setVisible(true);
            labelP.setVisible(true);

            MaximizeXY.setLayoutX(CanvasXY.getWidth() - 35);
            MaximizeXY.setLayoutY(CanvasXY.getLayoutY() + 35);

            PaintFunction();
            focoXY = false;
        }
    }

    @FXML
    void MaximizeXZActionPerformed(ActionEvent event) {
        CleanSection(false);
        if (!focoXZ) {
            CanvasXZ.setVisible(true);
            CanvasXZ.setWidth((limiteTela.getWidth() - larguraTabMenu));
            CanvasXZ.setHeight((limiteTela.getHeight() - alturaMenuBar));
            CanvasXZ.setLayoutX(0);
            CanvasXZ.setLayoutY(alturaMenuBar);

            CanvasXY.setVisible(false);
            CanvasZY.setVisible(false);
            CanvasP.setVisible(false);
            SeparatorHorizontal.setVisible(false);
            SeparatorVertical.setVisible(false);
            MaximizeXY.setVisible(false);
            MaximizeZY.setVisible(false);
            MaximizeP.setVisible(false);

            labelXZ.setLayoutX(CanvasXZ.getLayoutX() + 5);
            labelXZ.setLayoutY(CanvasXZ.getLayoutY());
            labelXY.setVisible(false);
            labelZY.setVisible(false);
            labelP.setVisible(false);

            MaximizeXZ.setLayoutX(CanvasXZ.getWidth() - 35);
            MaximizeXZ.setLayoutY(CanvasXZ.getLayoutY() + 5);

            PaintFunction();
            focoXZ = true;
        } else {
            CanvasXZ.setVisible(true);
            CanvasXZ.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
            CanvasXZ.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
            CanvasXZ.setLayoutX(0);
            CanvasXZ.setLayoutY((limiteTela.getHeight() - alturaMenuBar) / 2);

            CanvasXY.setVisible(true);
            CanvasZY.setVisible(true);
            CanvasP.setVisible(true);
            SeparatorHorizontal.setVisible(true);
            SeparatorVertical.setVisible(true);
            MaximizeXY.setVisible(true);
            MaximizeZY.setVisible(true);
            MaximizeP.setVisible(true);

            labelXZ.setLayoutX(CanvasXZ.getLayoutX() + 5);
            labelXZ.setLayoutY(CanvasXZ.getLayoutY());
            labelXY.setVisible(true);
            labelZY.setVisible(true);
            labelP.setVisible(true);

            MaximizeXZ.setLayoutX(CanvasXZ.getWidth() - 35);
            MaximizeXZ.setLayoutY(CanvasXZ.getLayoutY() + 5);

            PaintFunction();
            focoXZ = false;
        }
    }

    @FXML
    void MaximizeZYActionPerformed(ActionEvent event) {
        CleanSection(false);
        if (!focoZY) {
            CanvasZY.setVisible(true);
            CanvasZY.setWidth((limiteTela.getWidth() - larguraTabMenu));
            CanvasZY.setHeight((limiteTela.getHeight() - alturaMenuBar));
            CanvasZY.setLayoutX(0);
            CanvasZY.setLayoutY(alturaMenuBar);

            CanvasXZ.setVisible(false);
            CanvasXY.setVisible(false);
            CanvasP.setVisible(false);
            SeparatorHorizontal.setVisible(false);
            SeparatorVertical.setVisible(false);
            MaximizeXZ.setVisible(false);
            MaximizeXY.setVisible(false);
            MaximizeP.setVisible(false);

            labelZY.setLayoutX(CanvasZY.getLayoutX() + 5);
            labelZY.setLayoutY(CanvasZY.getLayoutY());
            labelXY.setVisible(false);
            labelXZ.setVisible(false);
            labelP.setVisible(false);

            MaximizeZY.setLayoutX(CanvasZY.getWidth() - 35);
            MaximizeZY.setLayoutY(CanvasZY.getLayoutY() + 5);

            PaintFunction();
            focoZY = true;
        } else {
            CanvasZY.setVisible(true);
            CanvasZY.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
            CanvasZY.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
            CanvasZY.setLayoutX((limiteTela.getWidth() - larguraTabMenu) / 2);
            CanvasZY.setLayoutY(0);

            CanvasXZ.setVisible(true);
            CanvasXY.setVisible(true);
            CanvasP.setVisible(true);
            SeparatorHorizontal.setVisible(true);
            SeparatorVertical.setVisible(true);
            MaximizeXZ.setVisible(true);
            MaximizeXY.setVisible(true);
            MaximizeP.setVisible(true);

            labelZY.setLayoutX(CanvasZY.getLayoutX() + 5);
            labelZY.setLayoutY(CanvasZY.getLayoutY() + 23);
            labelXY.setVisible(true);
            labelXZ.setVisible(true);
            labelP.setVisible(true);

            MaximizeZY.setLayoutX(CanvasZY.getWidth() + CanvasZY.getLayoutX() - 35);
            MaximizeZY.setLayoutY(CanvasZY.getLayoutY() + 35);

            PaintFunction();
            focoZY = false;
        }
    }

    @FXML
    void MaximizePActionPerformed(ActionEvent event) {
        CleanSection(false);
        if (!focoP) {
            CanvasP.setVisible(true);
            CanvasP.setWidth((limiteTela.getWidth() - larguraTabMenu));
            CanvasP.setHeight((limiteTela.getHeight() - alturaMenuBar));
            CanvasP.setLayoutX(0);
            CanvasP.setLayoutY(alturaMenuBar);

            CanvasXZ.setVisible(false);
            CanvasZY.setVisible(false);
            CanvasXY.setVisible(false);
            SeparatorHorizontal.setVisible(false);
            SeparatorVertical.setVisible(false);
            MaximizeXZ.setVisible(false);
            MaximizeZY.setVisible(false);
            MaximizeXY.setVisible(false);

            labelP.setLayoutX(CanvasP.getLayoutX() + 5);
            labelP.setLayoutY(CanvasP.getLayoutY());
            labelXY.setVisible(false);
            labelXZ.setVisible(false);
            labelZY.setVisible(false);

            MaximizeP.setLayoutX(CanvasP.getWidth() - 35);
            MaximizeP.setLayoutY(CanvasP.getLayoutY() + 5);

            PaintFunction();
            focoP = true;
        } else {
            CanvasP.setVisible(true);
            CanvasP.setWidth((limiteTela.getWidth() - larguraTabMenu) / 2);
            CanvasP.setHeight((limiteTela.getHeight() - alturaMenuBar) / 2);
            CanvasP.setLayoutX((limiteTela.getWidth() - larguraTabMenu) / 2);
            CanvasP.setLayoutY((limiteTela.getHeight() - alturaMenuBar) / 2);

            CanvasXZ.setVisible(true);
            CanvasZY.setVisible(true);
            CanvasXY.setVisible(true);
            SeparatorHorizontal.setVisible(true);
            SeparatorVertical.setVisible(true);
            MaximizeXZ.setVisible(true);
            MaximizeZY.setVisible(true);
            MaximizeXY.setVisible(true);

            labelP.setLayoutX(CanvasP.getLayoutX() + 5);
            labelP.setLayoutY(CanvasP.getLayoutY());
            labelXY.setVisible(true);
            labelXZ.setVisible(true);
            labelZY.setVisible(true);

            MaximizeP.setLayoutX(CanvasP.getWidth() + CanvasP.getLayoutX() - 35);
            MaximizeP.setLayoutY(CanvasP.getLayoutY() + 5);

            PaintFunction();
            focoP = false;
        }
    }

    public void inicializaPerspectiva() {
        mouse_posicao_ant = new Node(0, 0, 0);

        EventHandler telaPerspectivaL_MP = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CanvasP.setCursor(Cursor.MOVE);
                mouse_posicao_ant = new Node(event.getX(), event.getY(), 0);
            }
        };

        EventHandler telaPerspectivaL_MD = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node dif = new Node((event.getX() - mouse_posicao_ant.x), (event.getY() - mouse_posicao_ant.y), 0);
                Node NewVRP = new Node(VRP.x, VRP.y, VRP.z);
                NewVRP.setX(VRP.x * Math.cos(dif.x / 80.0) - VRP.z * Math.sin(dif.x / 80.0));
                NewVRP.setZ(VRP.x * Math.sin(dif.x / 80.0) + VRP.z * Math.cos(dif.x / 80.0));

                VRP = NewVRP;
                NewVRP.setX(VRP.x * Math.cos(dif.y / 80.0) - VRP.y * Math.sin(dif.y / 80.0));
                NewVRP.setY(VRP.x * Math.sin(dif.y / 80.0) + VRP.y * Math.cos(dif.y / 80.0));

                VRP = NewVRP;
                mouse_posicao_ant.setX(event.getX());
                mouse_posicao_ant.setY(event.getY());
                PaintFunction();
            }
        };

        EventHandler telaPerspectivaL_MR = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CanvasP.setCursor(Cursor.HAND);
            }
        };

        CanvasP.setCursor(Cursor.HAND);
        CanvasP.setOnMousePressed(telaPerspectivaL_MP);
        CanvasP.setOnMouseDragged(telaPerspectivaL_MD);
        CanvasP.setOnMouseReleased(telaPerspectivaL_MR);
    }

    public void deleteIncompletePolygons(boolean MaintainSelection) {
        for (int i = 0; i < PolyList.size(); i++) {
            if (!PolyList.get(i).complete) {
                PolyList.remove(i);
                i--;
                continue;
            }
            if (!MaintainSelection) {
                PolyList.get(i).selected = false;
            }
        }

        PaintFunction();

    }

    void CleanSection(boolean MaintainSelection) {
        deleteIncompletePolygons(MaintainSelection);

        EixoSelecionado.setVisible(false);
        SpinnerRaio.setVisible(false);
        SpinnerVertices.setVisible(false);
        labelRaio.setVisible(false);
        labelVertices.setVisible(false);
        labelEixo.setVisible(false);

        CanvasXY.setOnMousePressed(null);
        CanvasXY.setOnMouseClicked(null);
        CanvasXY.setOnMouseMoved(null);
        CanvasXY.setOnMouseDragged(null);
        CanvasXY.setOnMouseReleased(null);
        CanvasXZ.setOnMousePressed(null);
        CanvasXZ.setOnMouseClicked(null);
        CanvasXZ.setOnMouseMoved(null);
        CanvasXZ.setOnMouseDragged(null);
        CanvasXZ.setOnMouseReleased(null);
        CanvasZY.setOnMousePressed(null);
        CanvasZY.setOnMouseClicked(null);
        CanvasZY.setOnMouseMoved(null);
        CanvasZY.setOnMouseDragged(null);
        CanvasZY.setOnMouseReleased(null);

        CanvasXY.setCursor(Cursor.DEFAULT);
        CanvasXZ.setCursor(Cursor.DEFAULT);
        CanvasZY.setCursor(Cursor.DEFAULT);
    }

    public int mouseDentroSolido(int mX, int mY) {

        for (int i = PolyList.size() - 1; i >= 0; i--) {
            for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                PolyList.get(i).faceAtIndex(j).construirRectangulo();
                PolyList.get(i).faceAtIndex(j).calcularIntersec();
                if (mY >= PolyList.get(i).faceAtIndex(j).minY && mY <= PolyList.get(i).faceAtIndex(j).maxY) {
                    int cont = 0;
                    for (Iterator<Double> iterator = PolyList.get(i).faceAtIndex(j).intersectionPoints.get(mY - PolyList.get(i).faceAtIndex(j).minY).iterator(); iterator.hasNext();) {
                        Double next = iterator.next();

                        if (next > mX) {
                            if (cont % 2 == 0) {
                                break;
                            } else {
                                return i;
                            }
                        }

                        cont++;
                    }

                }
            }
        }

        return -1;
    }

    public boolean mouseInsidePolyRectangle(double x, double y, int op) {

        for (int i = 0; i < PolyList.size(); i++) {

            if (PolyList.get(i).selected) {
                for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                    if (op == 1) {
                        if ((x >= PolyList.get(i).minX && y >= PolyList.get(i).minY)
                                && (x <= PolyList.get(i).maxX && y <= PolyList.get(i).maxY)) {
                            return true;
                        }
                    } else if (op == 2) {
                        if ((x >= PolyList.get(i).minX && y >= PolyList.get(i).minZ)
                                && (x <= PolyList.get(i).maxX && y <= PolyList.get(i).maxZ)) {
                            return true;
                        }
                    } else if (op == 3) {
                        if ((x >= PolyList.get(i).minZ && y >= PolyList.get(i).minY)
                                && (x <= PolyList.get(i).maxZ && y <= PolyList.get(i).maxY)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public int mouseSobreAresta(double mouseX, double mouseY, int op) {
//        int r, s, t;
//        double dist;
        for (int i = 0; i < PolyList.size(); i++) {

            if (!(PolyList.get(i).selected)) {
                for (int j = 0; j < PolyList.get(i).numFaces; j++) {
                    for (int k = 0; k < PolyList.get(i).faceAtIndex(j).numArestas; k++) {

                        double ax = 0, ay = 0, bx = 0, by = 0;

                        // Olha, nao estou orgulhoso.
                        if (op == 1) {
                            ax = PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).x;
                            ay = PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).y;
                            bx = PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).x;
                            by = PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).y;
                        } else if (op == 2) {
                            ax = PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).x;
                            ay = PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).z;
                            bx = PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).x;
                            by = PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).z;
                        } else if (op == 3) {
                            ax = PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).z;
                            ay = PolyList.get(i).faceAtIndex(j).nodeCoordinatesA(k).y;
                            bx = PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).z;
                            by = PolyList.get(i).faceAtIndex(j).nodeCoordinatesB(k).y;
                        } else {
                            return -1;
                        }

                        double cx = mouseX;
                        double cy = mouseY;
                        double r_numerator = (cx - ax) * (bx - ax) + (cy - ay) * (by - ay);
                        double r_denomenator = (bx - ax) * (bx - ax) + (by - ay) * (by - ay);
                        double r = r_numerator / r_denomenator;

                        double px = ax + r * (bx - ax);
                        double py = ay + r * (by - ay);

                        double s = ((ay - cy) * (bx - ax) - (ax - cx) * (by - ay)) / r_denomenator;

                        double distanceLine = Math.abs(s) * Math.sqrt(r_denomenator);
                        double distanceSegment;

                        double xx = px;
                        double yy = py;

                        if ((r >= 0) && (r <= 1)) {
                            distanceSegment = distanceLine;
                        } else {
                            double dist1 = (cx - ax) * (cx - ax) + (cy - ay) * (cy - ay);
                            double dist2 = (cx - bx) * (cx - bx) + (cy - by) * (cy - by);
                            if (dist1 < dist2) {
                                xx = ax;
                                yy = ay;
                                distanceSegment = Math.sqrt(dist1);
                            } else {
                                xx = bx;
                                yy = by;
                                distanceSegment = Math.sqrt(dist2);
                            }

                        }

                        if (distanceSegment <= erro) {
                            return i;
                        }

                    }
                }
            }
        }

        return -1;
    }

    public ArrayList<Solido> ConversaoSRUSRC(Node localVRP, Node localP) {
        Node n = new Node(localVRP.x - localP.x, localVRP.y - localP.y, localVRP.z - localP.z);
        n.dividirPorNorma();
        Node v = new Node(0 - (n.y * n.x), 1 - (n.y * n.y), 0 - (n.y * n.z));
        v.dividirPorNorma();
        Node u = v.produtoVetorial(n);

        double localVRPn = n.produtoEscalar(localVRP.inverterPonto());
        double localVRPv = v.produtoEscalar(localVRP.inverterPonto());
        double localVRPu = u.produtoEscalar(localVRP.inverterPonto());

        zvp = localP.calculaSRUSRC(u, v, n, localVRPu, localVRPv, localVRPn).z;
        zprp = localVRP.calculaSRUSRC(u, v, n, localVRPu, localVRPv, localVRPn).z;
        ArrayList<Solido> SRC = new ArrayList<>();
        for (int i = 0; i < PolyList.size(); i++) {
            SRC.add(new Solido(PolyList.get(i).Faces));
        }

        for (int i = 0; i < SRC.size(); i++) {
            for (int j = 0; j < SRC.get(i).numFaces; j++) {
                for (int k = 0; k < SRC.get(i).faceAtIndex(j).numArestas; k++) {
                    SRC.get(i).faceAtIndex(j).edgeAtIndex(k).setNodeA(SRC.get(i).faceAtIndex(j).nodeCoordinatesA(k).calculaSRUSRC(u, v, n, localVRPu, localVRPv, localVRPn));
                    SRC.get(i).faceAtIndex(j).edgeAtIndex(k).setNodeB(SRC.get(i).faceAtIndex(j).nodeCoordinatesB(k).calculaSRUSRC(u, v, n, localVRPu, localVRPv, localVRPn));
                }
            }
            SRC.get(i).construirRectangulo();
        }

        return SRC;
    }

    public ArrayList<Solido> conversaoPerspectiva(ArrayList<Solido> SRC, double localzprp, double localzvp) {
        double dp = localzprp - localzvp;
        for (int i = 0; i < SRC.size(); i++) {
            for (int j = 0; j < SRC.get(i).numFaces; j++) {
                for (int k = 0; k < SRC.get(i).faceAtIndex(j).numArestas; k++) {
                    SRC.get(i).faceAtIndex(j).edgeAtIndex(k).setNodeA(SRC.get(i).faceAtIndex(j).nodeCoordinatesA(k).calculaSRCPers(zvp, zprp, dp));
                    SRC.get(i).faceAtIndex(j).edgeAtIndex(k).setNodeB(SRC.get(i).faceAtIndex(j).nodeCoordinatesB(k).calculaSRCPers(zvp, zprp, dp));
                }
            }
            SRC.get(i).construirRectangulo();
        }

        return SRC;
    }

    public void PaintFunction() {

        Node tempVRP = new Node(0, 100, 1);
        Node tempP = new Node(0, 0, 0);
        PaintP(CanvasXZ, ConversaoSRUSRC(new Node(0, 100, 1), new Node(0, 0, 0)), tempVRP.ConversaoSRUSRC_Ponto(new Node(0, 100, 1), new Node(0, 0, 0)), tempP.ConversaoSRUSRC_Ponto(new Node(0, 100, 1), new Node(0, 0, 0)));
        tempVRP = new Node(0, 0, 100);
        tempP = new Node(0, 0, 0);
        PaintP(CanvasXY, ConversaoSRUSRC(new Node(0, 0, 100), new Node(0, 0, 0)), tempVRP.ConversaoSRUSRC_Ponto(new Node(0, 0, 100), new Node(0, 0, 0)), tempP.ConversaoSRUSRC_Ponto(new Node(0, 0, 100), new Node(0, 0, 0)));
        tempVRP = new Node(100, 0, 0);
        tempP = new Node(0, 0, 0);
        PaintP(CanvasZY, ConversaoSRUSRC(new Node(100, 0, 0), new Node(0, 0, 0)), tempVRP.ConversaoSRUSRC_Ponto(new Node(100, 0, 0), new Node(0, 0, 0)), tempP.ConversaoSRUSRC_Ponto(new Node(100, 0, 0), new Node(0, 0, 0)));
        PaintP(CanvasP, conversaoPerspectiva(ConversaoSRUSRC(VRP, P), zprp, zvp), VRP.ConversaoSRUSRC_Ponto(VRP, P), P.ConversaoSRUSRC_Ponto(VRP, P));
    }

    public void PaintP(Canvas c, ArrayList<Solido> Pers, Node localVRP, Node localP) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        for (int i = 0; i < Pers.size(); i++) {

//            Ocultação de Faces
            for (int j = 0; j < Pers.get(i).numFaces; j++) {
                Pers.get(i).faceAtIndex(j).calculaAlgoritmoPintor(localVRP);
            }
            Collections.sort(Pers.get(i).Faces, (new Comparator<Face>() {
                @Override
                public int compare(Face o1, Face o2) {
                    if (o1.distance > o2.distance) {
                        return 1;
                    } else if (o1.distance == o2.distance) {
                        return 0;
                    } else {
                        return -1;
                    }

                }
            }));

            for (int j = 0; j < Pers.get(i).numFaces; j++) {
                Pers.get(i).faceAtIndex(j).calculaVisibilidadeNormal(localVRP, localP);
            }

//                 Fill
            for (int j = 0; j < Pers.get(i).numFaces; j++) {
                gc.setLineDashes(0.0);
                gc.setLineWidth(2);
                if ((Pers.get(i).faceAtIndex(j).visivel || !ocultacaoFaces || (Pers.get(i).numFaces == 1)) && !wireFrame) {

                    if (Pers.get(i).faceAtIndex(j).complete && Pers.get(i).faceAtIndex(j).PolyColor != null) {
                        gc.setStroke(PolyList.get(i).faceAtIndex(j).PolyColor);

                        Pers.get(i).faceAtIndex(j).calcularIntersec();
                        for (int l = 0; l < Pers.get(i).faceAtIndex(j).intersectionPoints.size(); l++) {
                            double xa = 0, xb = 0, ya = 0, yb = 0, cont = 0;
                            for (int k = 0; k < Pers.get(i).faceAtIndex(j).intersectionPoints.get(l).size(); k++) {
                                Double next = Pers.get(i).faceAtIndex(j).intersectionPoints.get(l).get(k);
                                if (cont % 2 == 0) {
                                    xa = next;
                                    ya = l;
                                } else {
                                    xb = next;
                                    yb = l;
                                    if (!(xa - xb < 0.0001 && xa - xb > -0.0001)) {
                                        gc.strokeLine((xa + 1) + (c.getWidth() / 2), (ya + Pers.get(i).faceAtIndex(j).minY) + (c.getHeight() / 2), (xb - 1) + (c.getWidth() / 2), (yb + Pers.get(i).faceAtIndex(j).minY) + (c.getHeight() / 2));
                                    }

                                }
                                cont++;
                            }
                        }
                    }
                }

//                Arestas
                gc.setLineWidth(1);
                if (Pers.get(i).faceAtIndex(j).visivel && ocultacaoFaces || (Pers.get(i).numFaces == 1)) {
                    for (int k = 0; k < Pers.get(i).faceAtIndex(j).Edges.size(); k++) {
                        gc.setStroke(Pers.get(i).faceAtIndex(j).edgesColor);
                        gc.strokeLine(Pers.get(i).faceAtIndex(j).nodeCoordinatesA(k).x + (c.getWidth() / 2), Pers.get(i).faceAtIndex(j).nodeCoordinatesA(k).y + (c.getHeight() / 2),
                                Pers.get(i).faceAtIndex(j).nodeCoordinatesB(k).x + (c.getWidth() / 2), Pers.get(i).faceAtIndex(j).nodeCoordinatesB(k).y + (c.getHeight() / 2));
                    }
                }

            }

            gc.setLineWidth(1);
            if (!ocultacaoFaces) {
                for (int j = 0; j < Pers.get(i).numFaces; j++) {
                    for (int k = 0; k < Pers.get(i).faceAtIndex(j).Edges.size(); k++) {
                        gc.setStroke(Pers.get(i).faceAtIndex(j).edgesColor);
                        gc.strokeLine(Pers.get(i).faceAtIndex(j).nodeCoordinatesA(k).x + (c.getWidth() / 2), Pers.get(i).faceAtIndex(j).nodeCoordinatesA(k).y + (c.getHeight() / 2),
                                Pers.get(i).faceAtIndex(j).nodeCoordinatesB(k).x + (c.getWidth() / 2), Pers.get(i).faceAtIndex(j).nodeCoordinatesB(k).y + (c.getHeight() / 2));
                    }
                }
            }

            gc.setLineDashes(15d, 7d);
            gc.setLineWidth(2);
            if (PolyList.get(i).selected) {
                gc.setStroke(Color.GREENYELLOW);
                gc.strokeLine(Pers.get(i).minX + (c.getWidth() / 2), Pers.get(i).minY + (c.getHeight() / 2), Pers.get(i).maxX + (c.getWidth() / 2), Pers.get(i).minY + (c.getHeight() / 2));
                gc.strokeLine(Pers.get(i).maxX + (c.getWidth() / 2), Pers.get(i).minY + (c.getHeight() / 2), Pers.get(i).maxX + (c.getWidth() / 2), Pers.get(i).maxY + (c.getHeight() / 2));
                gc.strokeLine(Pers.get(i).maxX + (c.getWidth() / 2), Pers.get(i).maxY + (c.getHeight() / 2), Pers.get(i).minX + (c.getWidth() / 2), Pers.get(i).maxY + (c.getHeight() / 2));
                gc.strokeLine(Pers.get(i).minX + (c.getWidth() / 2), Pers.get(i).maxY + (c.getHeight() / 2), Pers.get(i).minX + (c.getWidth() / 2), Pers.get(i).minY + (c.getHeight() / 2));
            }
        }
    }

}
