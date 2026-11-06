package com.stradaos.launcher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/** AlertDialog.Builder que traduce títulos, mensajes, botones y listas al idioma elegido (ver I18n). */
public class TB extends AlertDialog.Builder {
    public TB(Context c, int theme) { super(c, theme); }

    @Override public AlertDialog.Builder setTitle(CharSequence t) { return super.setTitle(I18n.t(t)); }
    @Override public AlertDialog.Builder setMessage(CharSequence m) { return super.setMessage(I18n.t(m)); }
    @Override public AlertDialog.Builder setPositiveButton(CharSequence t, DialogInterface.OnClickListener l) { return super.setPositiveButton(I18n.t(t), l); }
    @Override public AlertDialog.Builder setNegativeButton(CharSequence t, DialogInterface.OnClickListener l) { return super.setNegativeButton(I18n.t(t), l); }
    @Override public AlertDialog.Builder setNeutralButton(CharSequence t, DialogInterface.OnClickListener l) { return super.setNeutralButton(I18n.t(t), l); }
    @Override public AlertDialog.Builder setItems(CharSequence[] items, DialogInterface.OnClickListener l) {
        CharSequence[] out = new CharSequence[items.length];
        for (int i = 0; i < items.length; i++) out[i] = I18n.t(items[i]);
        return super.setItems(out, l);
    }
    @Override public AlertDialog.Builder setSingleChoiceItems(CharSequence[] items, int checked, DialogInterface.OnClickListener l) {
        CharSequence[] out = new CharSequence[items.length];
        for (int i = 0; i < items.length; i++) out[i] = I18n.t(items[i]);
        return super.setSingleChoiceItems(out, checked, l);
    }
}
