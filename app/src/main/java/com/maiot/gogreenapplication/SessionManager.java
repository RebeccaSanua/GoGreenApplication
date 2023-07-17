package com.maiot.gogreenapplication;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SessionManager {
    private static boolean isSessionSet = false;
    private static LinearLayout tendinaCar;
    private static Button buttonShowCar;

    private static Button prenota;
    private static Button openScanner;
    private static Button TerminaNoleggio;

    public static void setTendinaCar(LinearLayout layout) {
        tendinaCar = layout;
    }

    public static void setButtonShowCar(Button button) {
        buttonShowCar = button;
    }
    public static void setPrenotaCar(Button button) {
        prenota = button;
    }
    public static void setScannerCar(Button button) {
        openScanner = button;
    }
    public static void setTerminaNoleggio(Button button) {
        TerminaNoleggio = button;
    }
    public static void startSession() {
        isSessionSet = true;
        openScanner.setVisibility(View.GONE);
        prenota.setVisibility(View.GONE);
        TerminaNoleggio.setVisibility(View.VISIBLE);

    }

    public static void endSession() {
        isSessionSet = false;
        openScanner.setVisibility(View.VISIBLE);
        prenota.setVisibility(View.VISIBLE);
        TerminaNoleggio.setVisibility(View.GONE);

    }

    public static boolean isSessionSet() {
        return isSessionSet;
    }
    // Altri metodi per gestire la sessione, se necessario
}
