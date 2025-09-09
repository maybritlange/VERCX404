# VERCX404 – Chatbot-Basissystem

## Überblick

VERCX404 ist ein prototypisches, modulares Chatbot-Basissystem, das als flexible Laufzeitumgebung für verschiedene Chatbots dient. Die Anwendung wird als Minimum Viable Product (MVP) mit einem Terminal User Interface (TUI) bereitgestellt und ermöglicht die einfache Integration und Verwaltung von Chatbots.

## Hauptfunktionen

- Mehrbenutzer-Unterstützung mit Login
- Speicherung und Anzeige von Chatverläufen
- Aktivieren und Deaktivieren von Chatbots
- Modularer Aufbau für einfache Erweiterbarkeit
- Trennung von Systemlogik und Bedienoberfläche

## Architektur

Das System besteht aus folgenden Hauptkomponenten:

- **ChatApp**: Zentrale Steuerung und Benutzerinteraktion
- **BotController**: Verwaltung und Steuerung der Chatbots
- **BotFactory**: Erzeugung von Bot-Instanzen
- **IBot**: Interface für alle Chatbots
- **WikipediaBot, WeatherBot**: Beispielhafte Bot-Implementierungen
- **GUI/TUI**: Terminal-basierte Benutzeroberfläche
- **H2DatabaseConnector**: Persistenz der Chatverläufe
- **Users**: Verwaltung der Benutzer

Weitere Details zur Architektur findest du in der Datei `documentation/system.md`.

## Installation und Start

1. **Voraussetzungen:**  
   - Java 17 oder höher  
   - Internetverbindung für externe APIs (Wikipedia, Wetter)

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
Sie enthält Informationen zu Kontext, Architektur, Qualitätsanforderungen, Risiken und Glossar.

## Lizenz

Dieses Projekt ist ein Prototyp und dient ausschließlich zu Demonstrationszwecken.  
Bitte beachte die Lizenzhinweise im Projekt.

## Kontakt

Für Fragen und Feedback wende dich bitte an das Entwicklungsteam.