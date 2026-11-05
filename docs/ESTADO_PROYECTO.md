# Estado del proyecto StradaOS

Última actualización: 2026-11-04 · Versión publicada: **2.9.0** (versionCode 29)

## Publicado
- Release v2.8.0 en GitHub, firmado con la llave de publicación (secretos de GitHub). La firma coincide con las versiones instaladas a mano, así que se instala encima sin perder la configuración.
- `update.json` en `main`: lo actualiza el bot de `release.yml`. La app lo usa para el aviso de actualización.
- Página web: `site/`, publicada con `pages.yml` en https://lordnordic-debug.github.io/StradaOS/. El botón de descarga toma solo el último release desde la API de GitHub.
- Donaciones: Ko-fi activo (`ko-fi.com/stradaos`) en el README, `.github/FUNDING.yml` y el botón «Donar» de la app.

## Pendientes, en orden
1. **GitHub Sponsors:** el perfil está listo y espera que soporte de GitHub corrija el nombre de facturación (ticket #4952223). Cuando lo aprueben y Andrés publique, activar `github: lordnordic-debug` en `.github/FUNDING.yml` y en la sección «Apoya el proyecto» del README.
2. **Confirmar en la pantalla de Andrés el audio Bluetooth automático (2.9.0):** que el celular suene sin abrir «Audio Bluetooth», que la radio no se interrumpa sola, y revisar *Música → Diagnóstico del reproductor* (eventos Bluetooth).
3. **Bluetooth en otras marcas:** la 2.9.0 trae una opción en prueba (abrir la app BT al conectar y volver). Para soporte nativo de cada marca, pedir `StradaDiag` exportado **con el celular sonando** (incluye `bluetooth.txt` con los servicios activos) y su app BT (apk/odex).
4. **Capturas reales:** reemplazar los mockups de `fastlane/.../phoneScreenshots` y `site/img` por fotos de la pantalla de Andrés.
5. **Publicar en IzzyOnDroid** y luego en F-Droid (plantilla en `docs/fdroid/`).

## Ideas sin compromiso
- Mapa con vista inclinada (3D) como opción en Ajustes. Antes hay que medir el rendimiento en pantallas lentas.

## Notas técnicas
- Clinkway (`com.clw.btmusic`): el audio A2DP solo sale cuando el servicio pide foco de audio en su canal propio (stream 20) con `setBTMusicMode()` (transacción 16). Su pantalla lo llama al abrirse. StradaOS mantiene el servicio vivo (`BIND_AUTO_CREATE`) y llama a la transacción 16 en el flanco «pausa → sonando» (`BtBridge.poll`, cada 2 s, también con StradaOS en segundo plano). Si el servicio se reinicia 3 veces en 90 s, deja de mantenerlo vivo 15 min.
- Página: en navegadores internos (Facebook, Instagram, Messenger, TikTok…) muestra una barra y, al tocar Descargar, instrucciones con «Abrir en Chrome» (intent de Android) y copiar enlace. Para probarla en un navegador normal se agrega `?iab=1` a la URL.

## Historial
- 2026-11-04: v2.9.0 (16c468a) — audio Bluetooth automático (Clinkway) y opción para otras marcas; aviso en la página para navegadores internos.
- 2026-11-04: v2.8.0 publicada; Ko-fi activado; página web en GitHub Pages; ajuste de espacios de la página.
