# VERCX404 – Chatbot-Basissystem

## Überblick

VERCX404 ist ein prototypisches, modulares Chatbot-Basissystem, das als flexible Laufzeitumgebung für verschiedene Chatbots dient. Die Anwendung wird als Minimum Viable Product (MVP) mit einem Terminal User Interface (TUI) bereitgestellt und ermöglicht die einfache Integration und Verwaltung von Chatbots.

## Hauptfunktionen

Das System erzwingt das Login als einer von mehreren Benutzern mit vorgefertigten Login-Eingaben. Danach kann über vordefinierte Befehle mit verschiedenen Chatbots interagiert werden. Chatverläufe werden in einer H2-Datenbank gespeichert und können ausgegeben werden. Die Ein- und Ausgabe erfolgt über das Terminal. 

Das System ist modular aufgebaut: Bots sollen einfach eingefügt werden, die GUI ist einfach austauschbar

- Mehrbenutzer-Unterstützung mit Login
- Speicherung und Anzeige von Chatverläufen
- Aktivieren und Deaktivieren von Chatbots
- Modularer Aufbau für einfache Erweiterbarkeit
- Trennung von Systemlogik und Bedienoberfläche

## Architektur

Das System besteht aus folgenden Hauptkomponenten:

- **ChatController**: Zentrale Steuerung und Benutzerinteraktion
- **BotController**: Verwaltung und Steuerung der Chatbots
- **BotFactory**: Erzeugung von Bot-Instanzen
- **WikipediaBot, WeatherBot**: Beispielhafte Bot-Implementierungen
- **GUI**: Interface für GUI
- **TUI**: Terminal-basierte Implementierung der GUI
- **H2DatabaseConnector**: Persistenz der Chatverläufe über eine H2-Datenbank
- **Users**: Vorgefertigte Benutzer

Darüber hinaus gibt es die folgenden Interfaces:
- **IBot**: Interface für alle Chatbots
- **GUI**: Interface für GUI-Implementationen, z.B. die TUI
- **DatabaseInterface**: Interface für die Anbindung an eine Datenbank zur Persistierung der Chats

Weitere Details zur Architektur findest du in der Datei `documentation/system.md`.

## Installation und Start

1. **Voraussetzungen:**  
   - Java 17 oder höher  
   - Internetverbindung für externe APIs (Wikipedia, Wetter)
   - Installation einer H2-Datenbank

2. **Projekt bauen:**  
   Kompiliere das Projekt mit deinem bevorzugten Build-Tool oder direkt in Visual Studio Code.

3. **Starten:**  
   Führe die Anwendung über die Main-Klasse aus:
   ```
   java -cp . Main
   ```
   oder nutze die Run-Funktion in Visual Studio Code.

## Bedienung

- Melde dich mit Benutzername und Passwort an (siehe vordefinierte Nutzer in `Users.java`).
- Gib Kommandos im Terminal ein, z.B.:
  - `list bots`
  - `activate bot <Botname>`
  - `deactivate bot <Botname>`
  - `call bot <Botname> <Nachricht>`
  - `load chat data`
  - `exit` zum Beenden

## Erweiterung

Neue Chatbots können einfach durch Implementierung des `IBot`-Interfaces und Registrierung in der `BotFactory` hinzugefügt werden.

## Dokumentation

Die ausführliche Systemdokumentation findest du in  
`documentation/system.md`  
Sie enthält Informationen zu Kontext, Architektur, Qualitätsanforderungen, Risiken und Glossar. Eine Dokumentation des Wikibots ist unter `documentation\wikibot.md` auffindbar.

## Lizenz

Dieses Projekt ist ein Prototyp und dient ausschließlich zu Demonstrationszwecken.  

## Kontakt

Für Fragen und Feedback wende dich bitte an das Entwicklungsteam.
