# 🛠️ Minecraft Paper Plugin: Player Authentication (1.21.8)

## ✅ Goal
Create a Minecraft plugin that:
- Requires **registration** (`/register <password>`) and **login** (`/login <password>`)
- Blocks all actions (movement, chat, etc.) until authenticated
- Stores password **securely** (hashed)
- Requires **login every time** a player joins

---

## 📚 Learning Roadmap

### 1. 🧠 Learn Java Basics
- Variables, types, methods
- Classes & objects (OOP)
- File I/O (read/write files)
- Collections (Map, Set)
- Hashing (e.g. SHA-256)

Resources:
- [W3Schools Java](https://www.w3schools.com/java/)
- [Codecademy Java](https://www.codecademy.com/learn/learn-java)
- YouTube: “Java crash course”

---

### 2. ⚙️ Setup Development Environment
- Install:
  - JDK 17+
  - IntelliJ IDEA or Eclipse
  - Apache Maven
- Learn to:
  - Create Maven project
  - Add Paper API dependency in `pom.xml`
  - Build `.jar` and move to `plugins/` folder

---

### 3. 📦 Paper Plugin Basics
- Create main class that extends `JavaPlugin`
- Implement:
  - `onEnable()`, `onDisable()`
  - `plugin.yml` with name, main, version, commands

---

## 🔨 Plugin Features (Development Plan)

### 1. Register/Login System
- Commands:
  - `/register <password>`
  - `/login <password>`
- Store hashed password (e.g. SHA-256)
- Save data in JSON or YAML

Example storage:
```json
{
  "player-uuid": {
    "hashedPassword": "abcdef123...",
    "registered": true
  }
}
```

## Directory structure
```css
src/
└── main/
    └── java/
        └── com/yourname/authplugin/
            ├── AuthPlugin.java
            ├── commands/
            │   ├── RegisterCommand.java
            │   └── LoginCommand.java
            ├── listeners/
            │   └── PlayerJoinListener.java
            └── utils/
                └── PasswordUtil.java
resources/
└── plugin.yml
```
## Useful tools
| Tool             | Purpose               |
| ---------------- | --------------------- |
| Paper API        | Core server events    |
| Java File IO     | Save/load player data |
| Gson / Jackson   | JSON handling         |
| SHA-256 / bcrypt | Password hashing      |
| Vault (optional) | Permissions, economy  |