#!/bin/bash
# Abre cada APK de ./apks con el instalador del sistema desde /sdcard/Download y muestra qué diálogo aparece.
adb wait-for-device
for i in $(seq 1 60); do [ "$(adb shell getprop sys.boot_completed | tr -d '\r')" = "1" ] && break; sleep 3; done
sleep 10
adb shell input keyevent 82 || true
echo "API: $(adb shell getprop ro.build.version.sdk | tr -d '\r')"
# Orígenes desconocidos activados (si no, sale «Instalación bloqueada» en vez del análisis)
adb shell settings put secure install_non_market_apps 1 || true
adb shell settings put global install_non_market_apps 1 || true

texto() {  # textos visibles en pantalla
  adb shell uiautomator dump /data/local/tmp/ui.xml >/dev/null 2>&1
  adb shell cat /data/local/tmp/ui.xml 2>/dev/null | grep -o 'text="[^"]\+"' | sed 's/text=//' | tr '\n' ' '
  echo
}
abrir() {  # $1 = uri
  adb logcat -c
  adb shell am start -a android.intent.action.VIEW -d "$1" -t application/vnd.android.package-archive
  sleep 8
  echo "   Pantalla: $(texto)"
  echo "   logcat:"; adb logcat -d | grep -iE "PackageInstaller|PackageParser|parse|StrictJar|JarVerifier" | tail -15 | sed 's/^/     /'
  adb shell input keyevent 4; sleep 1; adb shell input keyevent 3; sleep 2
}

for f in apks/*.apk; do
  n=$(basename "$f")
  echo "=================== $n ==================="
  adb push "$f" "/sdcard/Download/$n" >/dev/null
  echo "-- file:// (como el explorador de archivos)"
  abrir "file:///sdcard/Download/$n"
  echo "-- content:// (MediaStore)"
  adb shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d "file:///sdcard/Download/$n" >/dev/null; sleep 4
  id=$(adb shell content query --uri content://media/external/file --projection _id --where "_data\ LIKE\ \'%$n\'" 2>/dev/null | grep -o '_id=[0-9]\+' | head -1 | cut -d= -f2)
  if [ -n "$id" ]; then abrir "content://media/external/file/$id"; else echo "   (no está en MediaStore)"; fi
  echo "-- pm install desde /sdcard/Download"
  adb shell pm install -r "/sdcard/Download/$n" 2>&1 | tail -2 | sed 's/^/   /'
  adb shell pm uninstall com.stradaos.launcher >/dev/null 2>&1
  adb shell pm uninstall com.stradaos.launcher.debug >/dev/null 2>&1
done
