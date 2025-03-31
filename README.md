# AntiDraconicEvolutionGrinder

**AntiDraconicEvolutionGrinder** is a lightweight, server-side Minecraft Forge 1.7.10 mod that filters out spammy death messages caused by mob grinders using [Draconic Evolution].

When mobs die to Draconic Evolution machines, the server chat can become flooded with lines like:

```
Infested Zombie was slain by [Draconic-Evolution]
Spined Blaze was slain by [Draconic-Evolution]
```

This mod prevents those messages from ever reaching the client.

---

## Features

- Suppresses all server chat messages containing `was slain by [Draconic-Evolution]`
- Server-side only — no client installation required
- Zero config, works out of the box
- No coremod, mixins, or ASM

---

## Installation

1. Drop the compiled `.jar` into your server's `mods/` folder.
2. Start your Minecraft Forge 1.7.10 server.
3. That’s it — no config needed.

---

## How it Works

- Hooks into the Netty pipeline on player login (`FMLNetworkEvent.ServerConnectionFromClientEvent`)
- Intercepts outgoing `S02PacketChat` packets
- Uses reflection to locate the obfuscated chat field (`field_148919_a`)
- Filters and cancels any message matching `"was slain by [Draconic-Evolution]"`

---

## Source Structure

- `AntiDraconicEvolutionGrinder.java` — Mod entrypoint, sets up event bus and pipeline hook
- `DeathMessageFilter.java` — Main logic that scans and suppresses chat packets via Netty

---

## Author

Created by **TheLongIslander**  
MIT License, 2025

---

## Future Ideas

- Configurable regex filters or whitelist mode
- Optional logging or JSON export of filtered messages
- Toggle via in-game `/antigrinder` command

---

## Requirements

- Minecraft Forge 1.7.10
- Server environment only — has no effect on the client

---

## Example Output

When filtering is triggered, the server console will print:

```
[AntiGrinder] Blocked: Fiery Wither Skeleton was slain by [Draconic-Evolution]
```

But nothing will show up in the player’s chat.

---

## Clean server, clean chat. Enjoy your silence.
