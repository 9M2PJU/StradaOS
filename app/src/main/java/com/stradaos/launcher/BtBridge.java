package com.stradaos.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;

/**
 * Consulta directa a la app Bluetooth de fábrica (com.clw.btmusic / IBTPlaybackService).
 * Se conecta solo si ese servicio ya está corriendo (no lo arranca, para no cambiar la fuente de audio).
 */
public class BtBridge {
    private static final String DESCRIPTOR = "com.clw.btmusic.IBTPlaybackService";
    private static final int T_TOGGLE = 1, T_PREV = 2, T_NEXT = 3, T_IS_PLAYING = 4, T_A2DP = 5,
            T_TITLE = 10, T_ARTIST = 11, T_ALBUM = 12, T_TOTAL = 13, T_CURRENT = 14, T_REFRESH = 15;

    public interface Listener { void onBtState(NowPlaying.Track t); }

    private final Context ctx;
    private final Listener listener;
    private final Handler ui = new Handler(Looper.getMainLooper());
    private IBinder binder;
    private boolean bound;

    public BtBridge(Context c, Listener l) {
        ctx = c.getApplicationContext();
        listener = l;
    }

    private final ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName n, IBinder b) {
            binder = b;
            refresh();
        }
        public void onServiceDisconnected(ComponentName n) {
            binder = null;
        }
    };

    /** Intenta conectarse (sin arrancar el servicio). */
    public void connect() {
        if (bound) return;
        try {
            Intent i = new Intent();
            i.setClassName("com.clw.btmusic", "com.clw.btmusic.BTPlaybackService");
            bound = ctx.bindService(i, conn, 0);
        } catch (Throwable e) {
            bound = false;
        }
    }

    public void disconnect() {
        if (!bound) return;
        try { ctx.unbindService(conn); } catch (Throwable ignored) { }
        bound = false;
        binder = null;
    }

    public boolean isConnected() { return binder != null && binder.isBinderAlive(); }

    /** Lee el estado actual en un hilo aparte y lo entrega en la UI. */
    public void refresh() {
        final IBinder b = binder;
        if (b == null) return;
        new Thread(new Runnable() {
            public void run() {
                try {
                    final NowPlaying.Track t = new NowPlaying.Track();
                    t.origin = "bt";
                    t.source = "Bluetooth";
                    if (!bool(b, T_A2DP)) {
                        t.clear = true;
                    } else {
                        call(b, T_REFRESH);                 // pide a la app que reenvíe la info
                        t.title = str(b, T_TITLE);
                        t.artist = str(b, T_ARTIST);
                        t.album = str(b, T_ALBUM);
                        long tot = lng(b, T_TOTAL), cur = lng(b, T_CURRENT);
                        if (tot > 0 && tot < 36000) { tot *= 1000; cur *= 1000; } // venía en segundos
                        if (tot > 0) t.durationMs = tot;
                        if (cur >= 0 && tot > 0) t.positionMs = cur;
                        t.playing = bool(b, T_IS_PLAYING);
                        if (t.title == null && t.artist == null && !t.playing) return;
                    }
                    ui.post(new Runnable() { public void run() { listener.onBtState(t); } });
                } catch (Throwable ignored) { }
            }
        }).start();
    }

    /** Controles directos. Devuelve false si no hay conexión. */
    public boolean command(int which) {
        final IBinder b = binder;
        if (b == null) return false;
        final int code = which == 1 ? T_NEXT : which == -1 ? T_PREV : T_TOGGLE;
        new Thread(new Runnable() { public void run() { try { call(b, code); } catch (Throwable ignored) { } } }).start();
        return true;
    }

    private static Parcel tx(IBinder b, int code) throws Exception {
        Parcel d = Parcel.obtain(), r = Parcel.obtain();
        try {
            d.writeInterfaceToken(DESCRIPTOR);
            b.transact(code, d, r, 0);
            r.readException();
            return r;
        } finally {
            d.recycle();
        }
    }

    private static void call(IBinder b, int code) throws Exception { tx(b, code).recycle(); }

    private static String str(IBinder b, int code) throws Exception {
        Parcel r = tx(b, code);
        try {
            String s = r.readString();
            return s == null || s.trim().length() == 0 || "Not Provided".equalsIgnoreCase(s.trim()) ? null : s.trim();
        } finally { r.recycle(); }
    }

    private static long lng(IBinder b, int code) throws Exception {
        Parcel r = tx(b, code);
        try { return r.readLong(); } finally { r.recycle(); }
    }

    private static boolean bool(IBinder b, int code) throws Exception {
        Parcel r = tx(b, code);
        try { return r.readInt() != 0; } finally { r.recycle(); }
    }
}
