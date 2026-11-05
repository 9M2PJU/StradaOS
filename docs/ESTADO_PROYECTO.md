# Estado del proyecto StradaOS

Última actualización: 2026-11-04 · Versión publicada: **2.8.0** (versionCode 28)

## Publicado
- Release v2.8.0 en GitHub, firmado con la llave de publicación (secretos de GitHub). La firma coincide con las versiones instaladas a mano, así que se instala encima sin perder la configuración.
- `update.json` en `main`: lo actualiza el bot de `release.yml`. La app lo usa para el aviso de actualización.
- Página web: `site/`, publicada con `pages.yml` en https://lordnordic-debug.github.io/StradaOS/. El botón de descarga toma solo el último release desde la API de GitHub.
- Donaciones: Ko-fi activo (`ko-fi.com/stradaos`) en el README, `.github/FUNDING.yml` y el botón «Donar» de la app.

## Pendientes, en orden
1. **GitHub Sponsors:** el perfil está listo y espera que soporte de GitHub corrija el nombre de facturación (ticket #4952223). Cuando lo aprueben y Andrés publique, activar `github: lordnordic-debug` en `.github/FUNDING.yml` y en la sección «Apoya el proyecto» del README.
2. **Página:** mostrar un aviso cuando se abre desde el navegador interno de Facebook o Instagram, que bloquea la descarga de APK. Debe decir: «⋯ → Abrir en navegador» y tener un botón para copiar el enlace.
3. **Bluetooth en otras pantallas:** usuarios de Facebook reportan que la música Bluetooth solo suena desde la app de fábrica. Para cada marca, pedir el paquete de su app BT y los archivos de diagnóstico, y agregarlos a `BtBridge`/`NowPlaying`.
4. **Capturas reales:** reemplazar los mockups de `fastlane/.../phoneScreenshots` y `site/img` por fotos de la pantalla de Andrés.
5. **Publicar en IzzyOnDroid** y luego en F-Droid (plantilla en `docs/fdroid/`).

## Ideas sin compromiso
- Mapa con vista inclinada (3D) como opción en Ajustes. Antes hay que medir el rendimiento en pantallas lentas.

## Historial
- 2026-11-04: v2.8.0 publicada; Ko-fi activado; página web en GitHub Pages; ajuste de espacios de la página.
