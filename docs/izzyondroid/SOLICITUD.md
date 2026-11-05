# Solicitud de inclusión en IzzyOnDroid

Dónde: https://codeberg.org/IzzyOnDroid/repodata/issues → **New issue** → plantilla de solicitud de app («App request»).
Requisito: una cuenta de Codeberg (gratis).

Llena los campos de la plantilla con lo de abajo. Están en inglés porque así trabaja el equipo de IzzyOnDroid.

---

**Title:** `[AppRequest] StradaOS`

**Repository URL:** https://github.com/lordnordic-debug/StradaOS

**License:** GPL-3.0-only

**Package name:** `com.stradaos.launcher`

**Checklist (marcar todo):**
- [x] The app complies with the App Inclusion Policy.
- [x] The app is not already listed in the repo or issue tracker.
- [x] The Fastlane folder is available in the app's repo.
- [x] I am the developer of this app.

**Description / additional info:**

```
StradaOS is a free, open-source home screen (launcher) for Android car head units.
It targets the cheap Chinese units that claim "Android 6+" but actually run Android 4.x
(minSdk 16), where Play Store no longer works, and it also runs on modern units.
Features: live OpenStreetMap map, speedometer (GPS), weather (Open-Meteo), now-playing
card with cover art for Bluetooth and Android Auto (Open Headunit), Android Auto /
CarPlay launch buttons, light/dark themes, factory shortcuts (radio, reverse camera…).

- Plain Java, no third-party libraries, no trackers, no ads. APK ~0.5 MB.
- Release APKs are attached to GitHub releases (tags vX.Y.Z, file StradaOS-X.Y.Z.apk),
  signed with my own key (v1 + v2). The Dependency Info block is disabled.
- Fastlane metadata (es-ES, en-US) is in fastlane/metadata/android.

Self-updater: the app can check GitHub releases. When it detects it was installed by an
F-Droid style client (installer package contains fdroid/droidify/neostore/foxydroid), the
GitHub update check is OFF by default (opt-in), and before enabling it the app explains
that those downloads come from GitHub and skip the repo's screening.

Permissions:
- INTERNET, ACCESS_NETWORK_STATE: map tiles, weather, optional update check.
- ACCESS_FINE/COARSE_LOCATION: map position, speedometer, local weather. Only used on-device.
- READ_EXTERNAL_STORAGE / WRITE_EXTERNAL_STORAGE (maxSdk 28) / READ_MEDIA_IMAGES:
  pick the user's car photo; export a diagnostics folder the user can attach to bug reports.
- BLUETOOTH: show the Bluetooth music state on the home screen.
- KILL_BACKGROUND_PROCESSES: optional — stops the factory "EasyConnection" mirroring app that
  grabs the USB port, only if the user chose another connection app (e.g. Open Headunit).
- GET_TASKS / REORDER_TASKS: on Android 4.x head units, know which factory source (radio,
  Bluetooth audio) is in front and return to the previous app after switching audio source.
- REQUEST_INSTALL_PACKAGES: install Open Headunit from a USB stick on units without a store,
  and the opt-in GitHub updater described above.
- AccessibilityService (opt-in, user enables it): reads the Open Headunit notification to
  show Android Auto cover art on Android 4.x, where NotificationListener does not exist.
- NotificationListenerService (opt-in, Android 5+): now-playing info from any music app.

Network services: OpenStreetMap tile servers (and Esri as fallback), Open-Meteo, Android
Geocoder with BigDataCloud as fallback for the city name, GitHub (opt-in updates).
```

---

Después de enviarla:
- Izzy revisa la app con su escáner y puede hacer preguntas en el mismo issue. Respóndelas o pásamelas.
- Cuando la acepte, agrega la insignia de IzzyOnDroid al README; la URL te la da él.
