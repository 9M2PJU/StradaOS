# Estado del proyecto StradaOS

Última actualización: 2026-11-04 · Versión publicada: **2.9.2** (versionCode 31)

## Publicado
- Release v2.8.0 en GitHub, firmado con la llave de publicación (secretos de GitHub). La firma coincide con las versiones instaladas a mano, así que se instala encima sin perder la configuración.
- `update.json` en `main`: lo actualiza el bot de `release.yml`. La app lo usa para el aviso de actualización.
- Página web: `site/`, publicada con `pages.yml` en https://lordnordic-debug.github.io/StradaOS/. El botón de descarga toma solo el último release desde la API de GitHub.
- Donaciones: Ko-fi activo (`ko-fi.com/stradaos`) en el README, `.github/FUNDING.yml` y el botón «Donar» de la app.

## Pendientes, en orden
1. **GitHub Sponsors:** el perfil está listo y espera que soporte de GitHub corrija el nombre de facturación (ticket #4952223). Cuando lo aprueben y Andrés publique, activar `github: lordnordic-debug` en `.github/FUNDING.yml` y en la sección «Apoya el proyecto» del README.
2. **Confirmar en la pantalla de Andrés el audio Bluetooth automático (2.9.2; la 2.9.1 no le llegó por el aviso atrasado):** de radio → play en el celular → debe abrirse «Audio Bluetooth» un instante, volver y sonar; play/pausa desde el inicio sin problemas. Revisar *Música → Diagnóstico del reproductor*.
3. **Bluetooth en otras marcas:** la 2.9.0 trae una opción en prueba (abrir la app BT al conectar y volver). Para soporte nativo de cada marca, pedir `StradaDiag` exportado **con el celular sonando** (incluye `bluetooth.txt` con los servicios activos) y su app BT (apk/odex).
4. **Capturas reales:** reemplazar los mockups de `fastlane/.../phoneScreenshots` y `site/img` por fotos de la pantalla de Andrés.
5. **Publicar en IzzyOnDroid** y luego en F-Droid (plantilla en `docs/fdroid/`).

## Ideas sin compromiso
- Mapa con vista inclinada (3D) como opción en Ajustes. Antes hay que medir el rendimiento en pantallas lentas.

## Notas técnicas
- Actualizaciones: en Android 4.2 la API de GitHub y raw.githubusercontent suelen fallar (TLS); jsDelivr `@main` cachea horas y daba versiones viejas. Desde 2.9.2 la app consulta todas las fuentes y toma la mayor; `release.yml` purga jsDelivr tras actualizar `update.json`.
- Hallazgo con 2.9.0 en la pantalla de Andrés: `setBTMusicMode()` con el celular en pausa (botón ▶ del inicio) sí pasó el sonido de radio a Bluetooth; con el celular ya sonando no. `isAudioTmpLose()` es true mientras la radio tiene el sonido, así que no sirve para descartar.
- Compatibilidad: `showCompat()` en Main lista funciones, requisito (versión de Android o marca) y estado en la pantalla. Al agregar una función nueva, sumarla ahí.
- Clinkway (`com.clw.btmusic`): **probado en 2.9.0 que llamar a `setBTMusicMode()` (transacción 16) desde fuera no pasa el sonido** y además dañaba play/pausa (con el celular en pausa esa llamada no envía play). El sistema cambia la fuente de audio según la app de fuente que pasó al frente por última vez. Solución 2.9.1: StradaOS mantiene vivo el servicio (`BIND_AUTO_CREATE`) solo para detectar el flanco «pausa → sonando» (`BtBridge.poll`, cada 2 s, también en segundo plano); si la última fuente al frente no fue `com.clw.btmusic` (`getRunningTasks`, permisos GET_TASKS/REORDER_TASKS), abre su pantalla 2 s y vuelve con `moveTaskToFront` a la tarea anterior. `btRouted` se pone en falso cuando otra app de fuente (radio, AUX, video…) pasa al frente o cuando el servicio de fábrica se cierra. Si el servicio se reinicia 3 veces en 90 s, deja de mantenerlo vivo 15 min.
- Idea a futuro: el sistema tiene `android.mode.AppManager` (modo de fuente). Con sus métodos se podría cambiar la fuente sin abrir la pantalla; hay que listarlos por reflexión en el diagnóstico.
- Página: en navegadores internos (Facebook, Instagram, Messenger, TikTok…) muestra una barra y, al tocar Descargar, instrucciones con «Abrir en Chrome» (intent de Android) y copiar enlace. Para probarla en un navegador normal se agrega `?iab=1` a la URL.

## Historial
- 2026-11-04: v2.9.2 — actualizaciones confiables (todas las fuentes + purga de jsDelivr), BT desde la radio, tabla de compatibilidad.
- 2026-11-04: v2.9.1 — corrige play tras pausa y cambia el método: abre «Audio Bluetooth» un instante y vuelve.
- 2026-11-04: v2.9.0 (16c468a) — audio Bluetooth automático (Clinkway) y opción para otras marcas; aviso en la página para navegadores internos.
- 2026-11-04: v2.8.0 publicada; Ko-fi activado; página web en GitHub Pages; ajuste de espacios de la página.
