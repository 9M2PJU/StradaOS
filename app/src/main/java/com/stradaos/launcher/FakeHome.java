package com.stradaos.launcher;

import android.app.Activity;
import android.os.Bundle;

/**
 * Actividad vacía y desactivada. Se activa un instante para que Android olvide qué pantalla de inicio estaba
 * fijada y muestre la lista «¿Qué app usar?», donde el usuario elige StradaOS → Siempre (Android 4 a 9).
 */
public class FakeHome extends Activity {
    @Override protected void onCreate(Bundle b) { super.onCreate(b); finish(); }
}
