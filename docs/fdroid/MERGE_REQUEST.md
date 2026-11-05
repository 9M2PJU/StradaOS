## Abstract for new app inclusion

**StradaOS** is a free (GPL-3.0) home screen launcher for Android car head units: the cheap "Android 6/10" screens sold for cars, many of which really run Android 4.x and ship with slow, ugly factory launchers.

It shows a live OpenStreetMap map, weather (Open-Meteo), the track playing over Bluetooth or Android Auto (via Open Headunit) with big media controls, and large shortcuts that are easy to tap while driving (radio, Bluetooth music, camera, files, navigation app). A setup wizard lets the user pick their car, a photo of it and which app each button opens.

**Why I wrote it / what is unique:** I wanted to revive the head unit of my own car (Kia Picanto 2019, Android 4.2.2, Autochips AC8227). I couldn't find a launcher that still works on Android 4.1+ (minSdk 16) and integrates with the head unit itself. StradaOS reads the now-playing track from the factory Bluetooth app (Clinkway/Autochips broadcasts and service), from the system remote control display on Android 4.x and from MediaSession on 5+, handles Android Auto through Open Headunit, and keeps working on very old, slow screens. It has no dependencies besides the Android SDK, no ads, no tracking, and the APK is about 0.5 MB.

**AI disclosure:** I want to be transparent: most of the code was written with an AI assistant (Claude) under my direction. I review every change, test each release on my own head unit, and maintain the project and answer users myself. IzzyOnDroid declined the app for this reason (their AI policy); their APK scan and metadata checks passed: https://codeberg.org/IzzyOnDroid/repodata/issues/832

Screenshots (real photos of my head unit) are attached below.

## Checklist

### Policy

- [x] The app complies with the inclusion policy. Pay special attention to Quality point 6.
- [x] The original app author has been notified (and does not oppose the inclusion). *I am the author.*
- [x] The upstream app source code repo contains the app metadata in a Fastlane or Triple-T folder structure. The summary and description must be included and images, icon, and changelog should also be provided for better user experience. The `en-US` locale must be included.

### Docs

- [x] Please read the guide first if this is your first contribution.
- [x] Please make sure your metadata follows the best practice in our templates.
- [x] Please read the Build Metadata Reference and make sure your metadata is valid.
- [x] Please read the Quick Start Guide.

### Merge Request Setup

- [x] The title of this merge request should follow "New app: app name" format.
- [x] Please make sure your fdroiddata fork is public and your branch is not protected.
- [x] Please read our Git guide if you don't know how to rebase your branch. Don't rebase your branch if there is no conflict.
- [x] All related fdroiddata and RFP issues have been referenced in this merge request. *(There are none.)*
- [x] Please only submit one app in one MR.

### Metadata

- [x] Metadata must be put in `metadata/<applicationId>.yml`.
- [x] Metadata must be a valid YAML file.
- [x] Metadata must use LF as line ending.
- [x] Don't add summary/description/changelog/images or anything that should be provided in upstream repo.
- [x] Releases are tagged and auto update is enabled unless there is a special reason.
- [x] There is an issue tracker and contact info of the author so that we can report bugs and contact the author.
- [x] An AuthorName must be added.
- [x] External repos are added as git submodules instead of srclibs. *(No external repos.)*
- [ ] Enable Reproducible Builds. *Not enabled for now: this is the first submission and I haven't verified that my CI build is byte-for-byte reproducible on your buildserver yet. Signing with the F-Droid key is fine; I may ask to enable it later.*
- [x] Setup abi split if the APK is large and the splitted ones can be much smaller. *(Pure Java, ~0.5 MB, no native code.)*
- [x] Only the latest versions should be kept in the metadata before it's merged.
- [x] Don't add any disabled versions in the metadata.
- [x] The `commit` field should be the full hash. Please don't use tag or branch in commit.

### Pipeline

- [ ] All pipelines should pass.
- [ ] All warnings and errors in the Reports tab should be fixed or explained.
- [ ] F-Droid CI runners are under GitLab's FOSS program, so there's no need for you to pay for any CI time. If Gitlab starts asking for phone numbers or credit cards don't submit anything, just leave a note in the MR so we know we need to trigger the CI.

### Notes

- Anti-feature declared: NonFreeNet (Esri map tiles and BigDataCloud city name, used only as a fallback when the free servers fail).
- The in-app GitHub update checker is off by default when the app is installed from an F-Droid store; the user must opt in, and the app explains where updates come from before enabling it.

/label ~"New App"
