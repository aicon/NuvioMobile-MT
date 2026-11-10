# NuvioMobile-MT

[🇭🇺 Magyar nyelvű leírás (README in Hungarian)](README_HU.md)

---

**An independent phone client fork of [NuvioMobile](https://github.com/NuvioMedia/NuvioMobile) with its own package id, a dedicated in-app update channel, and a side-by-side install next to the official app.**

[![Release](https://img.shields.io/badge/release-latest-blue.svg)](https://github.com/aicon/NuvioMobile-MT/releases)
[![License: GPL v3](https://img.shields.io/badge/License-GPL_v3-green.svg)](LICENSE)
[![Companion of](https://img.shields.io/badge/companion-MediaTrail-orange.svg)](https://github.com/aicon/MediaTrail)
[![Upstream](https://img.shields.io/badge/upstream-NuvioMobile-purple.svg)](https://github.com/NuvioMedia/NuvioMobile)
[![Support on Ko-fi](https://img.shields.io/badge/Ko--fi-Support-ff5e5b?logo=ko-fi)](https://ko-fi.com/aiconwg)

---

## Overview

NuvioMobile-MT is an independent community build of NuvioMobile for phones and tablets. It keeps the upstream catalog, sources, and playback, and it installs beside the official Nuvio app. Updates come from this repository, not from upstream.

The Android TV counterpart is [NuvioTV-MT](https://github.com/aicon/NuvioTV-MT). That build sends playback events to [MediaTrail-Scrobbler](https://github.com/aicon/MediaTrail-Scrobbler). This phone build does not send those events yet.

---

## ✨ Key Additions & Differences

### 1. Side-by-Side Coexistence
- **Package Identifier:** `com.aicon.nuviomobile` (debug: `com.aicon.nuviomobile.debug`).
- Installs beside the official Nuvio mobile app (`com.nuvio.app`) and does not replace it.
- **Launcher Name:** **Nuvio**, on English and Hungarian systems alike.

### 2. Custom In-App Updater
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

1. Open the **[Releases](https://github.com/aicon/NuvioMobile-MT/releases)** page.
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
