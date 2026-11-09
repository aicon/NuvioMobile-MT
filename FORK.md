# NuvioMobile-Scrobbler (product fork)

Personal product fork of [NuvioMedia/NuvioMobile](https://github.com/NuvioMedia/NuvioMobile).  
**Not an official Nuvio build.**

## Side-by-side install

| Build | `applicationId` |
|-------|-----------------|
| Official Nuvio Mobile | `com.nuvio.app` |
| This fork | `com.aicon.nuviomobile` |
| This fork debug | `com.aicon.nuviomobile.debug` |

Installs next to the official app; data stores are separate.

## Updates

In-app updater targets `aicon/NuvioMobile-Scrobbler` GitHub Releases. Do not publish public releases until signing and a local dry-run are verified.

## Contracts (shared with TV fork)

Same behaviour goals as `NuvioTV-Scrobbler`:

1. AUTO engine prefers libmpv for AVI (meta/mime/path), keep manual switch + failover.
2. Stremio `player` events to addons (start/pause/stop + progress interval, no ticks while paused).
3. MediaTrail via [MediaTrail-Scrobbler](https://github.com/aicon/MediaTrail-Scrobbler); percent UI not complete until MediaTrail shows position — see scrobbler `docs/MEDIATRAIL_PLAYBACK_PROGRESS.md`.

Mobile Compose player wiring for (1)(2) follows the TV implementation in a later pass; this repo already has own package id + release channel.

## Remotes

```
origin    https://github.com/aicon/NuvioMobile-Scrobbler.git
upstream  https://github.com/NuvioMedia/NuvioMobile.git
```

Upstream default branch at fork time: `cmp-rewrite`.
