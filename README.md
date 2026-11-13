# NuvioMobile-MT

[🇭🇺 Magyar nyelvű leírás (README in Hungarian)](README_HU.md)

---

**An independent phone client fork of [NuvioMobile](https://github.com/NuvioMedia/NuvioMobile) with MediaTrail Now Watching reporting, smart ExoPlayer/libmpv switching, its own package id, and in-app updates.**

[![Release](https://img.shields.io/badge/release-latest-blue.svg)](https://github.com/aicon/NuvioMobile-MT/releases)
[![License: GPL v3](https://img.shields.io/badge/License-GPL_v3-green.svg)](LICENSE)
[![Companion of](https://img.shields.io/badge/companion-MediaTrail-orange.svg)](https://github.com/aicon/MediaTrail)
[![Upstream](https://img.shields.io/badge/upstream-NuvioMobile-purple.svg)](https://github.com/NuvioMedia/NuvioMobile)
[![Support on Ko-fi](https://img.shields.io/badge/Ko--fi-Support-ff5e5b?logo=ko-fi)](https://ko-fi.com/aiconwg)

---

## Overview

NuvioMobile-MT is an independent community build of NuvioMobile for phones and tablets. It keeps the upstream catalog and sources, installs beside the official Nuvio app, and adds the same MediaTrail playback reporting and player-engine choice as [NuvioTV-MT](https://github.com/aicon/NuvioTV-MT).

---

## ✨ Key Additions & Differences

### 1. Side-by-Side Coexistence
- **Package Identifier:** `com.aicon.nuviomobile` (debug: `com.aicon.nuviomobile.debug`).
- Installs beside the official Nuvio mobile app (`com.nuvio.app`) and does not replace it.
- **Launcher Name:** **Nuvio**, on English and Hungarian systems alike.

### 2. MediaTrail Playback Events
- Dispatches playback events (`start`, `pause`, `progress`, `stop`) to installed addons that declare the `player` resource.
- Install the **[MediaTrail](https://github.com/aicon/MediaTrail)** addon address (`…/nuvio/manifest.json`) under Settings → Addons. No separate scrobbler container.
- Every install sends a stable `deviceId` plus an automatic label such as `telefon · Pixel 8`, so phone and TV strips stay separate on the MediaTrail home page.
- Seek also emits `progress` so the strip stays in sync. Finished watches are saved in MediaTrail at **90%**.
- Sends progress about every 30 seconds while playing, and not while paused.
- Video stays on the phone. MediaTrail receives the playback event only.
- The switch is on by default under playback settings.

### 3. Smart Playback Engine Switching
The default engine is Auto:

| Media / Format Condition | Selected Player Engine |
| :--- | :---: |
| **Standard Movies & TV Shows** | **ExoPlayer** |
| **Anime** *(Kitsu, MAL, AniList, or an anime genre)* | **libmpv** |
| **AVI and XviD** | **libmpv**, even when the saved engine is ExoPlayer |
| **HDR & Dolby Vision** | **ExoPlayer** |

A manual engine choice for the current playback overrides this.

### 4. Custom In-App Updater
The built-in updater reads public releases from `aicon/NuvioMobile-MT`:
- **Stable Channel:** the latest release that is not a pre-release, published from `main`.
- **Beta Channel:** the highest version among the releases from `dev`, including pre-releases.
- It picks the APK whose name matches the phone CPU. If none matches, it uses a universal APK when one is attached, otherwise the only APK.
- An update needs the same package id and the same signing key.

### 3. Branches
- **`dev`:** upstream beta tags are merged here. The current tag is `0.6.11-beta`. The fork's package id and update channel stay in the merge.
- **`main`:** the default branch. Upstream has not tagged a stable release on this line since `0.4.21`, so main is not moved back onto that old tag. The next stable tag merges here.
- The `cmp-rewrite` branch itself is not merged. It can sit ahead of the beta tag.

---

## 📥 Download & Installation

1. Open the **[latest release](https://github.com/aicon/NuvioMobile-MT/releases/latest)** and download the APK for the phone.

   <a href="https://github.com/aicon/NuvioMobile-MT/releases/latest"><img src="badges/get-it-on-github.png" alt="Get it on GitHub" height="80"></a>
2. Download the APK for the phone's CPU. Current phones are usually `arm64-v8a`.
3. Install it with the method you already use for sideloaded apps.

---

## 🛠️ Building from Source

The shared app is Kotlin Multiplatform and Compose Multiplatform. Android builds need Android Studio, a JDK, and the Android SDK.

```bash
git clone https://github.com/aicon/NuvioMobile-MT.git
cd NuvioMobile-MT
./gradlew :androidApp:assembleFullDebug
```

---

## ☕ Support

If you enjoy NuvioMobile-MT and want to support its maintenance and ongoing development:

[![Buy Me a Coffee](https://img.shields.io/badge/Ko--fi-Buy%20me%20a%20coffee-ff5e5b?style=for-the-badge&logo=ko-fi)](https://ko-fi.com/aiconwg)

---

## 📜 License & Acknowledgments

This software is licensed under the **[GNU General Public License v3.0 (GPL-3.0)](LICENSE)**.  
All upstream rights and authorship belong to [NuvioMedia/NuvioMobile](https://github.com/NuvioMedia/NuvioMobile).
