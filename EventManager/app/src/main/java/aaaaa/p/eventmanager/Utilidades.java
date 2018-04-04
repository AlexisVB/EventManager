package aaaaa.p.eventmanager;

import android.content.SharedPreferences;
import android.widget.TextView;

public class Utilidades {

    public static String leerPreferenciaUsuario(SharedPreferences preferencias) {
        return preferencias.getString("usuario", "");
    }

    public static String leerPreferenciaPassword(SharedPreferences preferencias) {
        return preferencias.getString("password", "");
    }

    public static String leerPreferenciaNombre(SharedPreferences preferencias) {
        return preferencias.getString("name", "");
    }

    public static String leerPreferenciaLast1(SharedPreferences preferencias) {
        return preferencias.getString("last1", "");
    }

    public static String leerPreferenciaLast2(SharedPreferences preferencias) {
        return preferencias.getString("last2", "");
    }

    public static String leerPreferenciaEmail(SharedPreferences preferencias) {
        return preferencias.getString("email", "");
    }

    public static int leerPreferenciaDia(SharedPreferences preferencias) {
        return preferencias.getInt("dia", 0);
    }

    public static int leerPreferenciaMes(SharedPreferences preferencias) {
        return preferencias.getInt("mes", 0);
    }

    public static int leerPreferenciaAnio(SharedPreferences preferencias) {
        return preferencias.getInt("anio", 0);
    }

    public static int leerPreferenciaId(SharedPreferences preferencias) {
        return preferencias.getInt("id", 0);
    }

    public static Boolean leerPreferenciaSession(SharedPreferences preferencias) {
        return preferencias.getBoolean("session", false);
    }

    public static void setPass(SharedPreferences preferencias, String pass) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("password", pass);
        editor.apply();
    }

    public static void clear(SharedPreferences preferencias) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();
    }

    public static void guardarPreferencias(SharedPreferences preferencias, int id, String usuario, String password, String name,
                                           String last2, String last1, String email) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id", id);
        editor.putString("usuario", usuario);
        editor.putString("password", password);
        editor.putString("name", name);
        editor.putString("last1", last1);
        editor.putString("last2", last2);
        editor.putString("email", email);
        editor.apply();
    }

    public static void guardarFecha(SharedPreferences preferencias, int dia, int mes, int anio) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("dia", dia);
        editor.putInt("mes", mes);
        editor.putInt("anio", anio);
        editor.apply();
    }

    public static void session(SharedPreferences preferencias, Boolean session) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putBoolean("session", session);
        editor.apply();
    }

    public static void textConfirm(TextView textView, String pass1, String pass2) {
        switch (passwords(pass1, pass2)) {
            case 0:
                textView.setText(null);
                break;
            case 1:
                textView.setText("Las contraseñas coinciden");
                break;
            case 2:
                textView.setText("Las contraseñas no coinciden");
                break;

            case 3:
                textView.setText("Contraseña demasiado corta");
                break;
        }
    }

    public static int passwords(String et1, String et2) {

        if (et1.equals("") && et2.equals("")) return 0;
        else if (et1.equals(""))
            if (et2.length() >= 5) return 0;
            else return 3;
        else if (et2.equals(""))
            if (et1.length() >= 5) return 0;
            else return 3;
        else if (et1.equals(et2)) {
            if (et1.length() >= 5) return 1;
            else return 3;
        } else {
            return 2;
        }
    }

}