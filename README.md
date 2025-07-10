# 🐢 TurtleEggHatcher Plugin (v2.0)

## ✅ Overview
**TurtleEggHatcher** is a lightweight, performance-friendly Paper plugin for **Minecraft 1.21.4** that automatically progresses the hatching of **Turtle Eggs** without requiring players to be nearby.

It’s ideal for survival servers or automated worlds where you want turtle eggs to hatch consistently without manual effort.

---

## 🔥 Features
- ✅ **Automatic Turtle Egg Hatching** (always progresses, even with no players nearby)
- ✅ **Overworld-Only Scanning** (only scans in the Overworld, not Nether/End)
- ✅ **Configurable Scan Radius** (choose how far around each chunk it searches)
- ✅ **Configurable Scan Interval** (control how often scanning happens in seconds)
- ✅ **Performance Optimized** (scans only loaded chunks for minimal lag)

---

## 📂 Configuration
After the plugin runs once, it generates a config file here:
plugins/TurtleEggHatcher/config.yml
### Example config:
```yaml
scan-radius: 5
scan-interval-seconds: 30
Setting	Description	Default
scan-radius	Number of blocks around each chunk to search for eggs	5
scan-interval-seconds	Interval (in seconds) for scanning turtle eggs	30

Tip: Adjust these values based on your server's performance needs. Larger intervals and smaller radius = less CPU usage.

⚙️ How to Use
Place the plugin .jar file into your server’s plugins folder:

bash
Copy
Edit
/plugins/TurtleEggHatcher-2.0.jar
Start or reload your server:

bash
Copy
Edit
/reload confirm
The plugin will automatically start scanning and hatching turtle eggs according to your config settings.

To modify its behavior:

Edit config.yml

Reload the server again.

🌍 How It Works
Scans only the Overworld (World.Environment.NORMAL).

Every scan-interval-seconds, it scans all loaded chunks.

Finds turtle eggs that haven’t reached maximum hatch level.

Progresses their hatch level gradually and safely.

Once fully hatched, eggs behave like in vanilla Minecraft.

🚀 Example Use Case
You want Turtle Eggs to always hatch naturally on your server, even without nearby players.

Useful for survival, semi-vanilla, or automation-friendly servers.

💡 Notes
Works only on Paper 1.21.4.

No commands or permissions needed — plugin runs automatically.

Lightweight and optimized for performance.


