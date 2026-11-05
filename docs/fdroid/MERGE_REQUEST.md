Texto para el merge request en gitlab.com/fdroid/fdroiddata (título y descripción). Copiar desde «Title».

---

**Title:** New App: com.stradaos.launcher

**Description:**

StradaOS is a free, open source (GPL-3.0) home screen launcher for Android car head units (the cheap "Android 6/10" screens sold for cars, many of which really run Android 4.x). It shows a live OpenStreetMap map, weather, now-playing info for Bluetooth and Android Auto (via Open Headunit) and big shortcuts that are easy to tap while driving. It supports Android 4.1+ (minSdk 16), has no dependencies besides the Android SDK, and no ads or tracking.

* Source code: https://github.com/lordnordic-debug/StradaOS
* Releases with version tags (vX.Y.Z) and fastlane metadata (en-US, es-ES) in the repo.
* Anti-feature declared: NonFreeNet (Esri tiles and BigDataCloud city name, only as fallback when free servers fail).
* The in-app GitHub update checker is off by default when the app is installed from an F-Droid store; the user must opt in.

**AI disclosure:** I want to be transparent: most of the code was written with an AI assistant (Claude) under my direction. I review every change, I test each release on my own head unit (Kia Picanto 2019, Android 4.2.2), and I maintain the project and answer issues myself. IzzyOnDroid declined the app for this reason (their AI policy); their APK scan and metadata checks passed: https://codeberg.org/IzzyOnDroid/repodata/issues/832

I am the author of the app.
