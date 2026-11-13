# NuvioMobile-MT

[🇺🇸 English description (README in English)](README.md)

---

**Önálló telefonos kliens, a [NuvioMobile](https://github.com/NuvioMedia/NuvioMobile) forkja MediaTrail „Most nézem” jelentéssel, intelligens ExoPlayer/libmpv választással, saját csomagnévvel és saját frissítővel.**

[![Verzió](https://img.shields.io/badge/verzió-legfrissebb-blue.svg)](https://github.com/aicon/NuvioMobile-MT/releases)
[![Licenc: GPL v3](https://img.shields.io/badge/Licenc-GPL_v3-green.svg)](LICENSE)
[![MediaTrail kiegészítő](https://img.shields.io/badge/kiegészítő-MediaTrail-orange.svg)](https://github.com/aicon/MediaTrail)
[![Upstream](https://img.shields.io/badge/upstream-NuvioMobile-purple.svg)](https://github.com/NuvioMedia/NuvioMobile)
[![Támogatás Ko-fi-n](https://img.shields.io/badge/Ko--fi-Támogatás-ff5e5b?logo=ko-fi)](https://ko-fi.com/aiconwg)

---

## Áttekintés

A NuvioMobile-MT a NuvioMobile független, közösségi kiadása telefonra és táblagépre. Megtartja az eredeti katalógust és forrásokat, a hivatalos Nuvio mellé települ, és ugyanazt a MediaTrail-jelentést és lejátszómotor-választást adja, mint a [NuvioTV-MT](https://github.com/aicon/NuvioTV-MT).

---

## ✨ Főbb funkciók és eltérések

### 1. Párhuzamos telepíthetőség
- **Csomagnév:** `com.aicon.nuviomobile` (debug változat: `com.aicon.nuviomobile.debug`).
- A hivatalos mobil Nuvio (`com.nuvio.app`) mellé települ, nem írja felül azt.
- **Indítóikon neve:** **Nuvio**, angol és magyar rendszeren egyaránt.

### 2. MediaTrail lejátszás-szinkronizáció
- Lejátszás közben továbbítja az eseményeket (`start`, `pause`, `progress`, `stop`) a `player` erőforrást hirdető addonoknak.
- A **[MediaTrail](https://github.com/aicon/MediaTrail)** addon címét (`…/nuvio/manifest.json`) add hozzá a Beállítások → Bővítmények alatt. Külön scrobbler konténer nem kell.
- Minden telepítés stabil `deviceId`-t és automatikus nevet küld (pl. `telefon · Pixel 8`), így a telefon és a tévé sávjai a MediaTrail kezdőlapon külön maradnak.
- Seek után is megy `progress`, hogy a sáv ne maradjon le. A befejezett nézés a MediaTrailben **90%-nál** mentődik.
- Aktív lejátszás alatt kb. 30 másodpercenként küld állapotot, szünet alatt nem.
- A kép a telefonon marad. A MediaTrail csak a lejátszási eseményt kapja.
- A kapcsoló a lejátszási beállításokban van, alapból bekapcsolva.

### 3. Intelligens lejátszómotor-választás
Az alapértelmezett motor az Auto:

| Tartalom / formátum | Választott lejátszómotor |
| :--- | :---: |
| **Film és sorozat** | **ExoPlayer** |
| **Anime** *(Kitsu, MAL, AniList, vagy anime műfaj)* | **libmpv** |
| **AVI és XviD** | **libmpv**, akkor is, ha a mentett motor ExoPlayer |
| **HDR és Dolby Vision** | **ExoPlayer** |

Az adott lejátszásra kézzel választott motor ezt felülírja.

### 4. Saját alkalmazáson belüli frissítő
A beépített frissítő az `aicon/NuvioMobile-MT` nyilvános kiadásait nézi:
- A legutóbbi stabil kiadást használja (`releases/latest` a `main` ágról).
- A telefon processzorához illő APK-t választja. Ha ilyen nincs, az univerzális csomagot használja, ha van csatolva, különben az egyetlen APK-t.
- A frissítéshez ugyanaz a csomagnév és ugyanaz az aláíró kulcs kell.

### 3. Ágak
- **`main`:** az egyetlen ág, amin ez a termékfork dolgozik. Az aláírt stabil kiadások innen jelennek meg.
- Az upstream címkék a `main`-be olvadnak be (nem reset), így a fork csomagneve, aláírása és a MediaTrail funkciók megmaradnak.

---

## 📥 Letöltés és telepítés

1. Nyisd meg a **[legfrissebb kiadást](https://github.com/aicon/NuvioMobile-MT/releases/latest)**, és töltsd le a telefonhoz illő APK-t.

   <a href="https://github.com/aicon/NuvioMobile-MT/releases/latest"><img src="badges/get-it-on-github.png" alt="Get it on GitHub" height="80"></a>
2. Töltsd le a telefon processzorához illő APK-t. A mostani telefonoknál ez általában `arm64-v8a`.
3. Telepítsd úgy, ahogy a más forrásból származó alkalmazásokat szoktad.

---

## 🛠️ Fordítás forrásból

A közös alkalmazás Kotlin Multiplatform és Compose Multiplatform. Androidhoz Android Studio, JDK és Android SDK kell.

```bash
git clone https://github.com/aicon/NuvioMobile-MT.git
cd NuvioMobile-MT
./gradlew :androidApp:assembleFullDebug
```

---

## ☕ Támogatás

Amennyiben hasznosnak találod a NuvioMobile-MT-t és szeretnéd támogatni a fejlesztést és a karbantartást:

[![Ko-fi támogatás](https://img.shields.io/badge/Ko--fi-Támogatás-ff5e5b?style=for-the-badge&logo=ko-fi)](https://ko-fi.com/aiconwg)

---

## 📜 Licenc és szerzői jogok

A projekt a **[GNU General Public License v3.0 (GPL-3.0)](LICENSE)** feltételei szerint használható és módosítható.  
Az eredeti alkalmazás szerzői joga a [NuvioMedia/NuvioMobile](https://github.com/NuvioMedia/NuvioMobile) csapatát illeti.
