package at.spengergasse.posprojekt;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private Button[][] felder = new Button[3][3];
    private Label statusLabel = new Label("Spieler X ist am Zug");
    private boolean spielerX = true; // true für X, false für O
    private boolean spielEnde = false;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Statusanzeige am oberen Rand
        statusLabel.setFont(Font.font(16));
        HBox statusBox = new HBox(statusLabel);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(statusBox);

        // Spielfeld in der Mitte
        GridPane spielfeld = new GridPane();
        spielfeld.setAlignment(Pos.CENTER);
        spielfeld.setHgap(10);
        spielfeld.setVgap(10);

        // Initialisiere die Spielfelder
        for (int zeile = 0; zeile < 3; zeile++) {
            for (int spalte = 0; spalte < 3; spalte++) {
                Button feld = new Button();
                feld.setMinSize(100, 100);
                feld.setFont(Font.font(40));

                // Setze die Position für die spätere Auswertung
                final int r = zeile;
                final int c = spalte;

                feld.setOnAction(e -> feldKlick(r, c));

                felder[zeile][spalte] = feld;
                spielfeld.add(feld, spalte, zeile);
            }
        }

        root.setCenter(spielfeld);

        // Reset-Button am unteren Rand
        Button resetButton = new Button("Neues Spiel");
        resetButton.setOnAction(e -> spielZuruecksetzen());
        HBox buttonBox = new HBox(resetButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        root.setBottom(buttonBox);

        // Szene erstellen und anzeigen
        Scene scene = new Scene(root);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void feldKlick(int zeile, int spalte) {
        // Prüfen, ob das Feld bereits belegt ist oder das Spiel bereits beendet ist
        if (!felder[zeile][spalte].getText().isEmpty() || spielEnde) {
            return;
        }

        // Setze X oder O je nach aktuellem Spieler
        felder[zeile][spalte].setText(spielerX ? "X" : "O");

        // Prüfe, ob jemand gewonnen hat
        if (pruefeGewinner()) {
            statusLabel.setText("Spieler " + (spielerX ? "X" : "O") + " hat gewonnen!");
            spielEnde = true;
            zeigeGewinnDialog(spielerX ? "X" : "O");
            return;
        }

        // Prüfe auf Unentschieden
        if (pruefeUnentschieden()) {
            statusLabel.setText("Unentschieden!");
            spielEnde = true;
            zeigeUnentschiedenDialog();
            return;
        }

        // Wechsle den Spieler
        spielerX = !spielerX;
        statusLabel.setText("Spieler " + (spielerX ? "X" : "O") + " ist am Zug");
    }

    private boolean pruefeGewinner() {
        // Prüfe Zeilen
        for (int i = 0; i < 3; i++) {
            if (!felder[i][0].getText().isEmpty() &&
                    felder[i][0].getText().equals(felder[i][1].getText()) &&
                    felder[i][1].getText().equals(felder[i][2].getText())) {
                return true;
            }
        }

        // Prüfe Spalten
        for (int i = 0; i < 3; i++) {
            if (!felder[0][i].getText().isEmpty() &&
                    felder[0][i].getText().equals(felder[1][i].getText()) &&
                    felder[1][i].getText().equals(felder[2][i].getText())) {
                return true;
            }
        }

        // Prüfe Diagonalen
        if (!felder[0][0].getText().isEmpty() &&
                felder[0][0].getText().equals(felder[1][1].getText()) &&
                felder[1][1].getText().equals(felder[2][2].getText())) {
            return true;
        }

        if (!felder[0][2].getText().isEmpty() &&
                felder[0][2].getText().equals(felder[1][1].getText()) &&
                felder[1][1].getText().equals(felder[2][0].getText())) {
            return true;
        }

        return false;
    }

    private boolean pruefeUnentschieden() {
        // Prüfe, ob alle Felder belegt sind
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (felder[i][j].getText().isEmpty()) {
                    return false; // Es gibt noch freie Felder
                }
            }
        }
        return true; // Alle Felder sind belegt, kein Gewinner = Unentschieden
    }

    private void spielZuruecksetzen() {
        // Setze alle Felder zurück
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                felder[i][j].setText("");
            }
        }

        // Setze Spielvariablen zurück
        spielerX = true;
        spielEnde = false;
        statusLabel.setText("Spieler X ist am Zug");
    }

    private void zeigeGewinnDialog(String spieler) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spielende");
        alert.setHeaderText("Herzlichen Glückwunsch!");
        alert.setContentText("Spieler " + spieler + " hat gewonnen!");
        alert.showAndWait();
    }

    private void zeigeUnentschiedenDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spielende");
        alert.setHeaderText("Unentschieden!");
        alert.setContentText("Das Spiel endet unentschieden.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}