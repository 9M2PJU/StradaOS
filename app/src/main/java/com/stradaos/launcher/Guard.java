package com.stradaos.launcher;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Registro de fallos: guarda los cierres inesperados y los bloqueos de la pantalla (hilo principal sin responder)
 * para poder diagnosticar pantallas que no tenemos. Se ve en Ajustes → Acerca de → Registro de fallos y también
 * se copia a StradaDiag/fallos.txt cuando el equipo lo permite.
 */
public class Guard {
    static final String FILE = "fallos.txt";
    static final long MAX = 64 * 1024;
    private static Context app;
    private static volatile long pong;
    private static volatile boolean running;
    private static Thread watchdog;

    /** Instala el registro de cierres inesperados (una vez por proceso). */
    static void install(Context c) {
        if (app != null) return;
        app = c.getApplicationContext();
        final Thread.UncaughtExceptionHandler prev = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    log("CIERRE INESPERADO en el hilo «" + t.getName() + "»\n" + cut(sw.toString(), 6000));
                } catch (Throwable ignored) { }
                if (prev != null) prev.uncaughtException(t, e);
            }
        });
    }

    /** Vigila el hilo principal: si no responde en 8 s, guarda dónde estaba trabado (una vez por bloqueo). */
    static synchronized void startWatchdog() {
        if (running) return;
        running = true;
        final Handler main = new Handler(Looper.getMainLooper());
        final Runnable ping = new Runnable() { public void run() { pong = SystemClock.uptimeMillis(); } };
        pong = SystemClock.uptimeMillis();
        watchdog = new Thread(new Runnable() {
            public void run() {
                boolean reported = false;
                while (running) {
                    main.post(ping);
                    try { Thread.sleep(2000); } catch (InterruptedException e) { return; }
                    long late = SystemClock.uptimeMillis() - pong;
                    if (late > 8000 && !reported) {
                        reported = true;
                        StringBuilder sb = new StringBuilder("BLOQUEO: la pantalla de StradaOS no responde hace ")
                                .append(late / 1000).append(" s. Estaba en:\n");
                        for (StackTraceElement el : Looper.getMainLooper().getThread().getStackTrace())
                            sb.append("    at ").append(el).append('\n');
                        log(cut(sb.toString(), 6000));
                    } else if (late < 4000 && reported) {
                        reported = false;
                        log("La pantalla de StradaOS volvió a responder.");
                    }
                }
            }
        }, "StradaOS-vigia");
        watchdog.setDaemon(true);
        watchdog.setPriority(Thread.MIN_PRIORITY);
        watchdog.start();
    }

    static synchronized void stopWatchdog() {
        running = false;
        if (watchdog != null) watchdog.interrupt();
        watchdog = null;
    }

    /** Agrega una línea al registro (archivo interno y, si se puede, StradaDiag/fallos.txt). */
    static synchronized void log(String msg) {
        if (app == null) return;
        String head = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date())
                + " · v" + Updates.currentVersion(app) + " · Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")"
                + " · " + Build.MANUFACTURER + " " + Build.MODEL + "\n";
        String entry = head + msg + "\n\n";
        append(new File(app.getFilesDir(), FILE), entry);
        try {
            File d = new File(Environment.getExternalStorageDirectory(), "StradaDiag");
            if (d.isDirectory() || d.mkdirs()) append(new File(d, FILE), entry);
        } catch (Throwable ignored) { }
    }

    /** Texto del registro (lo más reciente al final), o null si está vacío. */
    static String read(Context c) {
        File f = new File(c.getFilesDir(), FILE);
        if (!f.exists() || f.length() == 0) return null;
        try {
            FileInputStream in = new FileInputStream(f);
            byte[] b = new byte[(int) Math.min(f.length(), MAX)];
            int n = 0, r;
            while (n < b.length && (r = in.read(b, n, b.length - n)) > 0) n += r;
            in.close();
            return new String(b, 0, n, "UTF-8");
        } catch (Throwable e) {
            return null;
        }
    }

    static void clear(Context c) {
        try { new File(c.getFilesDir(), FILE).delete(); } catch (Throwable ignored) { }
    }

    private static void append(File f, String s) {
        try {
            if (f.length() > MAX) {   // se queda con la mitad más reciente
                String old = read(app);
                FileOutputStream o = new FileOutputStream(f, false);
                if (old != null && f.getParentFile().equals(app.getFilesDir()))
                    o.write(old.substring(old.length() / 2).getBytes("UTF-8"));
                o.close();
            }
            FileOutputStream o = new FileOutputStream(f, true);
            o.write(s.getBytes("UTF-8"));
            o.close();
        } catch (Throwable ignored) { }
    }

    private static String cut(String s, int max) { return s.length() <= max ? s : s.substring(0, max) + "\n…"; }
}
