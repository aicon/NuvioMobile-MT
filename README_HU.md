# NuvioMobile-MT

[🇺🇸 English description (README in English)](README.md)

---

**Önálló telefonos kliens, a [NuvioMobile](https://github.com/NuvioMedia/NuvioMobile) forkja saját csomagnévvel, saját alkalmazáson belüli frissítővel, a hivatalos alkalmazás mellé telepítve.**

[![Verzió](https://img.shields.io/badge/verzió-legfrissebb-blue.svg)](https://github.com/aicon/NuvioMobile-MT/releases)
[![Licenc: GPL v3](https://img.shields.io/badge/Licenc-GPL_v3-green.svg)](LICENSE)
[![MediaTrail kiegészítő](https://img.shields.io/badge/kiegészítő-MediaTrail-orange.svg)](https://github.com/aicon/MediaTrail)
[![Upstream](https://img.shields.io/badge/upstream-NuvioMobile-purple.svg)](https://github.com/NuvioMedia/NuvioMobile)
[![Támogatás Ko-fi-n](https://img.shields.io/badge/Ko--fi-Támogatás-ff5e5b?logo=ko-fi)](https://ko-fi.com/aiconwg)

---

## Áttekintés

A NuvioMobile-MT a NuvioMobile független, közösségi kiadása telefonra és táblagépre. Megtartja az eredeti katalógust, forrásokat és lejátszást, és a hivatalos Nuvio mellé települ. A frissítések ebből a tárolóból jönnek.

Az Android TV párja a [NuvioTV-MT](https://github.com/aicon/NuvioTV-MT). Az a kiadás küldi a lejátszási eseményeket a [MediaTrail-Scrobblernek](https://github.com/aicon/MediaTrail-Scrobbler). Ez a telefonos kiadás ezeket az eseményeket még nem küldi.

---

## ✨ Főbb funkciók és eltérések

### 1. Párhuzamos telepíthetőség
- **Csomagnév:** `com.aicon.nuviomobile` (debug változat: `com.aicon.nuviomobile.debug`).
- A hivatalos mobil Nuvio (`com.nuvio.app`) mellé települ, nem írja felül azt.
- **Indítóikon neve:** **Nuvio**, angol és magyar rendszeren egyaránt.

### 2. Saját alkalmazáson belüli frissítő
A beépített frissítő az `aicon/NuvioMobile-MT` nyilvános kiadásait nézi:
- **Stabil csatorna:** a legutóbbi, nem előzetes kiadás, a `main` ágról.
- **Béta csatorna:** a `dev` ág kiadásai közül a legmagasabb verzió, az előzeteseket is beleértve.
- A telefon processzorához illő APK-t választja. Ha ilyen nincs, az univerzális csomagot használja, ha van csatolva, különben az egyetlen APK-t.
- A frissítéshez ugyanaz a csomagnév és ugyanaz az aláíró kulcs kell.

### 3. Ágak
- **`dev`:** ide kerülnek az upstream béta címkéi. A mostani címke a `0.6.11-beta`. A beolvasztás megtartja a fork csomagnevét és frissítési csatornáját.
- **`main`:** az alapértelmezett ág. Ezen a vonalon az upstream a `0.4.21` óta nem adott ki stabil címkét, ezért a main nem megy vissza arra a régi címkére. A következő stabil címke ide olvad be.
- A `cmp-rewrite` ág maga nem kerül át. A béta címke előtt járhat.

---

## 📥 Letöltés és telepítés

1. Nyisd meg a **[kiadások](https://github.com/aicon/NuvioMobile-MT/releases)** oldalát.
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
