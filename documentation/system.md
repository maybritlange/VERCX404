# Einführung und Ziele
<div class="formalpara-title">

## Was ist VERCX404?

</div>

VERCX404 soll als prototypisches Chatbot-Basissystem dienen, das als flexible Laufzeitumgebung für verschiedene Chatbots dient. Zugrunde liegt das Bestreben der Unternehmensleitung, das Produktportfolio durch ein modulares Chatsystem zu erweitern. Die Umsetzung erfolgt als Minimum Viable Product (MVP) mit einem Terminal User Interface (TUI). 

<div class="formalpara-title">

## Wesentliche Features

</div>

Funktionale Hauptanforderungen sind:
- die Unterstützung mehrerer Benutzer mit Login
- die Speicherung und Anzeige von Chatverläufen
- das Aktivieren und Deaktivieren von Chatbots, um Ressourcen zu sparen.

Nicht-funktionale Ziele umfassen:
- Modularität
- Skalierbarkeit
- Trennung von System und Bedienoberfläche

# Kontextabgrenzung
## Fachlicher Kontext

| Kommunikationsbeziehung | Eingabe                                   | Ausgabe                                 |
|------------------------|-------------------------------------------|-----------------------------------------|
| Benutzer               | Login-Daten, Chat-Nachrichten, Kommandos   | Chatbot-Antworten, Statusmeldungen      |
| Chatbots (WeatherBot, WikipediaBot, ...) | Benutzeranfrage (z.B. Ort, Suchbegriff) | Antwort des Bots (z.B. Wetter, Wiki-Text) |
| Datenbank (H2)         | Chatverlauf, Benutzername, Kommando        | Gespeicherte/vergangene Chatverläufe    |

**Erläuterung:**  
Das System nimmt von Benutzern Kommandos und Chat-Nachrichten entgegen, verarbeitet diese ggf. mit aktivierten Chatbots und speichert die Interaktionen in einer lokalen Datenbank. Die Chatbots liefern jeweils spezifische Antworten (z.B. Wetterdaten, Wikipedia-Zusammenfassungen).

---

## Technischer Kontext

| Nachbarsystem/Komponente | Kanal/Protokoll         | Zuordnung fachlicher Ein-/Ausgaben |
|--------------------------|-------------------------|------------------------------------|
| Terminal (TUI)           | Standard-IO (Konsole)   | Benutzer-Kommandos, Ausgaben       |
| H2-Datenbank             | JDBC                    | Speicherung und Abruf von Chats    |
| Wikipedia API            | HTTP/REST (über jwiki)  | WikipediaBot: Textabfragen         |
| Wetter-API (optional)    | HTTP/REST (Platzhalter) | WeatherBot: Wetterdaten            |

**Mapping:**  
- Benutzer interagiert über das Terminal (TUI) mit dem System.
- Die Datenbank wird über JDBC angesprochen.
- Bots kommunizieren ggf. über HTTP mit externen APIs.

---

**Kontextdiagramm (Textuell):**

```
[Benutzer] <---> [TUI/ChatApp] <---> [BotController] <---> [Chatbots]
         |                                   |
         v                                   v
   [H2-Datenbank]                    [Externe APIs]
```

---

**Verantwortlichkeiten:**  
- Das System ist verantwortlich für die Benutzerinteraktion, die Verwaltung und Aktivierung/Deaktivierung von Chatbots sowie die Speicherung der Chatverläufe.
- Externe APIs und die Datenbank sind für die Bereitstellung von Informationen bzw. die Persistenz zuständig.

# Lösungsstrategie

## Technologieentscheidungen

- Das System wird in Java entwickelt, um Plattformunabhängigkeit und gute Erweiterbarkeit zu gewährleisten.
- Für die Persistenz der Chatverläufe wird die H2-Datenbank verwendet (lokal, eingebettet, leichtgewichtig).
- Die Benutzeroberfläche wird als Terminal User Interface (TUI) umgesetzt, um eine schnelle und einfache Bedienung zu ermöglichen.
- Die Kommunikation mit externen Diensten (z.B. Wikipedia, Wetter) erfolgt über HTTP/REST-APIs.

## Architektur- und Entwurfsmuster

- Das System folgt dem Prinzip der Modularität: Chatbots sind als eigenständige Komponenten implementiert und können zur Laufzeit aktiviert oder deaktiviert werden.
- Die Trennung von Kernlogik (ChatApp, BotController) und Benutzeroberfläche (GUI, TUI) ermöglicht eine spätere Erweiterung um andere Interfaces (z.B. Web, GUI).
- Die Datenbankanbindung erfolgt über ein Interface (DatabaseInterface), um die Austauschbarkeit der Persistenzlösung zu gewährleisten.

## Qualitätsanforderungen

- Skalierbarkeit: Neue Bots können einfach hinzugefügt werden, ohne bestehende Komponenten zu verändern.
- Erweiterbarkeit: Die Architektur erlaubt die Integration weiterer externer APIs und Bots.
- Wartbarkeit: Klare Trennung der Verantwortlichkeiten und Verwendung von Schnittstellen erleichtern die Pflege und Weiterentwicklung.

## Organisatorische Entscheidungen

- Die Entwicklung erfolgt iterativ als Minimum Viable Product (MVP), um früh Feedback von Stakeholdern zu erhalten.
- Die Benutzerverwaltung ist zunächst einfach gehalten (vordefinierte Nutzer), kann aber später erweitert werden.

**Motivation:**  
Die gewählten Technologien und Muster ermöglichen eine schnelle Entwicklung eines funktionsfähigen Prototyps, der modular und erweiterbar ist. Die klare Trennung der Komponenten und Schnittstellen unterstützt die langfristige Wartbarkeit und Anpassbarkeit des Systems.

# Bausteinsicht

## Whitebox Gesamtsystem

Das System VERCX404 ist in mehrere Hauptbausteine unterteilt, die jeweils klar abgegrenzte Verantwortlichkeiten besitzen. Die folgende Übersicht zeigt die wichtigsten Komponenten und deren Beziehungen.

### Übersichtsdiagramm (Textuell)

```
+-------------------+
|    ChatApp        |
+-------------------+
        |
        v
+-------------------+      +-------------------+
|   BotController   |<---->|      IBot         |
+-------------------+      +-------------------+
        |
        v
+-------------------+
|      GUI/TUI      |
+-------------------+
        |
        v
+-------------------+
| H2DatabaseConnector|
+-------------------+
```

### Begründung der Zerlegung

Die Zerlegung folgt dem Prinzip der Modularität und Trennung von Verantwortlichkeiten:
- **ChatApp** steuert den Ablauf und die Interaktion mit dem Benutzer.
- **BotController** verwaltet die Chatbots und deren Status.
- **IBot** ist das Interface für alle Bot-Implementierungen (z.B. WikipediaBot, WeatherBot).
- **GUI/TUI** stellt die Benutzeroberfläche bereit.
- **H2DatabaseConnector** übernimmt die Persistenz der Chatverläufe.

### Enthaltene Bausteine

| **Name**              | **Verantwortung**                                                                 |
|-----------------------|-----------------------------------------------------------------------------------|
| ChatApp               | Hauptsteuerung, Benutzerinteraktion, Kommandoverarbeitung                        |
| BotController         | Verwaltung und Aktivierung/Deaktivierung von Bots                                 |
| IBot                  | Schnittstelle für alle Chatbots                                                   |
| WikipediaBot          | Liefert Wikipedia-Zusammenfassungen                                               |
| WeatherBot            | Liefert Wetterinformationen                                                       |
| GUI/TUI               | Terminal-basierte Benutzeroberfläche                                              |
| H2DatabaseConnector   | Speicherung und Abruf von Chatverläufen                                           |
| Users                 | Verwaltung und Prüfung von Benutzerdaten                                          |

### Wichtige Schnittstellen

- **DatabaseInterface**: Schnittstelle zur Datenbankanbindung, ermöglicht Austauschbarkeit der Persistenzlösung.
- **GUI**: Interface für verschiedene Benutzeroberflächen (TUI, ggf. später GUI/Web).

---

## Beispiel Blackbox-Beschreibung: BotController

**Zweck/Verantwortung:**  
Verwaltet die Instanzen der Chatbots, ermöglicht deren Aktivierung und Deaktivierung, leitet Nachrichten an die Bots weiter.

**Schnittstellen:**  
- Methoden zur Aktivierung/Deaktivierung von Bots
- Methode zum Aufruf eines Bots mit einer Nachricht

**Qualitäts-/Leistungsmerkmale:**  
- Erweiterbar für neue Bots
- Schnelle Umschaltung des Bot-Status

---

## Beispiel Blackbox-Beschreibung: H2DatabaseConnector

**Zweck/Verantwortung:**  
Speichert und lädt Chatverläufe aus einer lokalen H2-Datenbank.

**Schnittstellen:**  
- Methoden zum Speichern und Laden von Chats
- Verbindung über JDBC

**Qualitäts-/Leistungsmerkmale:**  
- Persistenz der Daten
- Austauschbar durch Implementierung von DatabaseInterface

---

## Beispiel Blackbox-Beschreibung: IBot

**Zweck/Verantwortung:**  
Definiert die Schnittstelle für alle Chatbot-Implementierungen.

**Schnittstellen:**  
- Methoden für Name, Status, Verarbeitung von Nachrichten, API-Zugang

**Qualitäts-/Leistungsmerkmale:**  
- Einheitliche Anbindung neuer Bots
- Trennung von Bot-Logik und Systemlogik

# Laufzeitsicht

## Szenario 1: Benutzer-Login und Start

1. Benutzer startet die Anwendung (Main → ChatApp).
2. ChatApp fordert über die GUI/TUI die Eingabe von Benutzername und Passwort.
3. Die Eingaben werden an die Users-Komponente weitergegeben.
4. Users prüft die Daten und gibt das Ergebnis zurück.
5. Bei Erfolg zeigt die GUI/TUI eine Begrüßung an, andernfalls eine Fehlermeldung.

## Szenario 2: Bot aktivieren und verwenden

1. Benutzer gibt den Befehl `activate bot weatherbot` ein.
2. ChatApp ruft `activateBot("weatherbot")` beim BotController auf.
3. BotController erstellt eine Instanz von WeatherBot über die BotFactory und setzt den Status auf aktiv.
4. ChatApp bestätigt die Aktivierung über die GUI/TUI.
5. Benutzer gibt den Befehl `call bot weatherbot Berlin` ein.
6. ChatApp ruft `callBot("weatherbot", "Berlin")` beim BotController auf.
7. BotController leitet die Anfrage an die WeatherBot-Instanz weiter.
8. WeatherBot verarbeitet die Nachricht und liefert eine Wetterantwort zurück.
9. ChatApp zeigt die Antwort über die GUI/TUI an und speichert den Chat in der Datenbank.

## Szenario 3: Chatverlauf laden

1. Benutzer gibt den Befehl `load chat data` ein.
2. ChatApp ruft `loadChatData(user)` beim H2DatabaseConnector auf.
3. H2DatabaseConnector liest die letzten 100 Chatverläufe aus der Datenbank.
4. ChatApp zeigt die Ergebnisse über die GUI/TUI an.

# Verteilungssicht

## Infrastruktur Ebene 1

Das System VERCX404 wird als Einzelplatzanwendung auf einem Arbeitsplatzrechner ausgeführt.  
Die technische Infrastruktur besteht aus:

| Infrastrukturkomponente | Beschreibung                       |
|------------------------|-------------------------------------|
| Arbeitsplatzrechner    | Windows-PC mit Java-Laufzeitumgebung|
| Dateisystem            | Speicherung der H2-Datenbank        |
| Internetverbindung     | Zugriff auf externe APIs (Wikipedia, Wetter) |

**Verbindungskanäle:**
- JDBC für die lokale Datenbank
- Standard-IO (Konsole) für die Benutzerinteraktion
- HTTP/REST für externe API-Abfragen

**Zuordnung der Software-Bausteine:**

| Software-Baustein        | Infrastrukturkomponente      |
|--------------------------|-----------------------------|
| ChatApp, BotController   | Arbeitsplatzrechner (JVM)   |
| GUI/TUI                  | Arbeitsplatzrechner (Konsole)|
| H2DatabaseConnector      | Arbeitsplatzrechner (Dateisystem)|
| WikipediaBot, WeatherBot | Arbeitsplatzrechner (JVM, Internetverbindung)|

**Begründung:**  
Die Ausführung als Einzelplatzanwendung ermöglicht eine schnelle Entwicklung und einfache Installation.  
Externe APIs werden über das Internet angebunden, die Persistenz erfolgt lokal.

**Qualitäts-/Leistungsmerkmale:**  
- Schnelle Antwortzeiten durch lokale Ausführung
- Keine Abhängigkeit von Server-Infrastruktur
- Erweiterbar für spätere Verteilung auf mehrere Rechner

## Infrastruktur Ebene 2

Da das System aktuell nur auf einem Rechner läuft, ist eine weitere Detaillierung nicht notwendig.  
Für spätere Erweiterungen (z.B. Serverbetrieb, mehrere Clients) kann diese Sicht ergänzt werden.

# Querschnittliche Konzepte

## Fachliche Konzepte

- **Benutzerverwaltung:**  
  Die Authentifizierung erfolgt über vordefinierte Benutzer und Passwörter. Eine spätere Erweiterung um dynamische Benutzer ist vorgesehen.

- **Chatverlauf:**  
  Jeder Chatverlauf wird mit Benutzername, Botname, Eingabe und Antwort gespeichert. Die letzten 100 Chats können geladen werden.

## Architektur- und Entwurfsmuster

- **Modularität:**  
  Chatbots sind als eigenständige Komponenten implementiert und über das IBot-Interface angebunden. Neue Bots können einfach ergänzt werden.

- **Trennung von Logik und Oberfläche:**  
  Die Kernlogik (ChatApp, BotController) ist von der Benutzeroberfläche (GUI/TUI) getrennt, um spätere Erweiterungen (z.B. Web-UI) zu erleichtern.

## Implementierungsregeln

- **Namenskonventionen:**  
  Bots werden nach dem Muster `<name>Bot` benannt (z.B. WikipediaBot, WeatherBot).

- **Fehlerbehandlung:**  
  Fehler werden möglichst benutzerfreundlich über die TUI ausgegeben und in der Konsole protokolliert.

## Entwicklungskonzepte

- **Testbarkeit:**  
  Die wichtigsten Komponenten (z.B. BotController, H2DatabaseConnector) sind über Interfaces abstrahiert und können separat getestet werden.

- **Erweiterbarkeit:**  
  Neue Bots und Datenbanklösungen können durch Implementierung der jeweiligen Interfaces integriert werden.

## Betriebskonzepte

- **Persistenz:**  
  Die Datenbank wird lokal als Datei im Arbeitsverzeichnis gespeichert. Backups können durch Kopieren der Datei erstellt werden.

- **API-Zugriff:**  
  Externe APIs (Wikipedia, Wetter) werden über HTTP/REST angesprochen. API-Schlüssel werden zentral verwaltet.

# Architekturentscheidungen

## Entscheidung 1: Programmiersprache Java

**Alternativen:** Python, C#, Java  
**Entscheidung:** Java wird verwendet.  
**Begründung:**  
- Plattformunabhängigkeit  
- Gute Bibliotheken für Datenbank und API-Zugriff  
- Team-Erfahrung vorhanden

---

## Entscheidung 2: Persistenz mit H2-Datenbank

**Alternativen:** SQLite, MySQL, H2  
**Entscheidung:** H2 als eingebettete Datenbank  
**Begründung:**  
- Keine externe Installation notwendig  
- Einfache Integration in Java  
- Ausreichend für MVP und lokale Speicherung

---

## Entscheidung 3: Terminal User Interface (TUI)

**Alternativen:** Web-UI, Desktop-GUI, TUI  
**Entscheidung:** TUI für die erste Version  
**Begründung:**  
- Schnelle Entwicklung  
- Keine zusätzlichen Frameworks notwendig  
- Fokus auf Funktionalität statt Design

---

## Entscheidung 4: Modularer Aufbau mit Bot-Interface

**Alternativen:** Feste Bot-Implementierung, Modularer Aufbau  
**Entscheidung:** Verwendung eines IBot-Interfaces für alle Bots  
**Begründung:**  
- Einfache Erweiterbarkeit  
- Neue Bots können ohne Änderung der Kernlogik integriert werden  
- Klare Trennung von Bot-Logik und Systemlogik

---

## Entscheidung 5: Vordefinierte Benutzer

**Alternativen:** Dynamische Benutzerverwaltung, Vordefinierte Nutzer  
**Entscheidung:** Vordefinierte Nutzer für MVP  
**Begründung:**  
- Reduziert Komplexität in der ersten Version  
- Ermöglicht schnellen Start und Test  
- Erweiterung für dynamische Nutzer später möglich

---

## Entscheidung 6: API-Zugriff über HTTP/REST

**Alternativen:** SOAP, HTTP/REST  
**Entscheidung:** HTTP/REST für externe Dienste  
**Begründung:**  
- Moderne APIs sind meist REST-basiert  
- Einfache Integration in Java  
- Gute Unterstützung durch Bibliotheken

---

## Entscheidung 7: Trennung von Logik und Oberfläche

**Alternativen:** Vermischung von Logik und UI, Trennung  
**Entscheidung:** Klare Trennung zwischen Kernlogik und UI  
**Begründung:**  
- Erleichtert spätere Erweiterungen (z.B. Web-UI)  
- Verbessert Testbarkeit und Wartbarkeit

---

# Qualitätsanforderungen

## Qualitätsziele

| Qualitätsmerkmal   | Zielbeschreibung                                                                 |
|--------------------|----------------------------------------------------------------------------------|
| Modularität        | Neue Chatbots können ohne Änderung der Kernlogik integriert werden.              |
| Erweiterbarkeit    | Schnittstellen erlauben die Integration weiterer Datenbanken und Benutzeroberflächen. |
| Wartbarkeit        | Klare Trennung der Verantwortlichkeiten und Verwendung von Interfaces.            |
| Skalierbarkeit     | Das System kann um weitere Bots und Benutzer erweitert werden.                   |
| Benutzbarkeit      | Die Bedienung über das Terminal ist intuitiv und schnell erlernbar.              |
| Zuverlässigkeit    | Chatverläufe werden zuverlässig gespeichert und können jederzeit abgerufen werden.|
| Sicherheit         | Benutzer müssen sich authentifizieren, um auf das System zuzugreifen.            |
| Performance        | Antworten der Bots erfolgen innerhalb von 1 Sekunde.                             |
| Portabilität       | Das System läuft auf allen gängigen Betriebssystemen mit Java-Unterstützung.     |

## Qualitätsbaum

```
Qualität
├── Funktionalität
│   ├── Modularität
│   ├── Erweiterbarkeit
│   └── Zuverlässigkeit
├── Usability
│   └── Benutzbarkeit
├── Sicherheit
│   └── Authentifizierung
├── Performance
│   └── Antwortzeit| Begriff           | Definition                                                                 |
|-------------------|----------------------------------------------------------------------------|
| Chatbot           | Software-Komponente, die automatisiert auf Benutzeranfragen reagiert.      |
| BotController     | Modul zur Verwaltung und Steuerung der Chatbots im System.                  |
| BotFactory        | Komponente zur Erzeugung von Bot-Instanzen anhand ihres Namens.            |
| IBot              | Interface, das die Schnittstelle für alle Chatbots definiert.               |
| TUI               | Terminal User Interface; textbasierte Benutzeroberfläche.                   |
| GUI               | Graphical User Interface; grafische Benutzeroberfläche (optional, geplant).|
| H2-Datenbank      | Eingebettete relationale Datenbank zur lokalen Speicherung von Chatverläufen.|
| Benutzer          | Person, die sich am System anmeldet und mit Chatbots interagiert.           |
| Chatverlauf       | Historie der geführten Chats zwischen Benutzer und Chatbots.                |
| MVP               | Minimum Viable Product; erste lauffähige Version mit Kernfunktionen.        |
| API               | Application Programming Interface; Schnittstelle zu externen Diensten.      |
| WikipediaBot      | Chatbot, der Wikipedia-Inhalte abfragt und zusammenfasst.                   |
| WeatherBot        | Chatbot, der Wetterdaten abfragt und bereitstellt.                          |
| CredentialsAPI    | Zugangsdaten für die Nutzung externer APIs.                                 |
| Benutzername      | Eindeutige Kennung eines Benutzers im System.                               |
| Passwort          | Geheimwort zur Authentifizierung eines Benutzers.                           |
| Kommando          | Textbasierte Eingabe zur Steuerung des Systems oder der Bots.               |
├── Wartbarkeit
│   └── Trennung von Verantwortlichkeiten
├── Portabilität
│   └── Plattformunabhängigkeit
```

## Qualitätsszenarien

| Szenario-Typ      | Auslöser / Stimulus                | Erwartetes Verhalten / Messgröße                    |
|-------------------|------------------------------------|-----------------------------------------------------|
| Nutzungsszenario  | Benutzer ruft einen Bot auf        | Antwort innerhalb von 1 Sekunde                     |
| Nutzungsszenario  | Benutzer lädt Chatverlauf          | Die letzten 100 Chats werden korrekt angezeigt      |
| Änderungsszenario | Neuer Bot wird hinzugefügt         | Integration ohne Änderung bestehender Komponenten   |
| Änderungsszenario | Wechsel der Datenbank              | Austausch durch Implementierung von DatabaseInterface|
| Nutzungsszenario  | Falsche Login-Daten                | Zugriff wird verweigert, Fehlermeldung erscheint    |
| Nutzungsszenario  | Systemabsturz                      | Keine Datenverluste, Chatverläufe bleiben erhalten  |

# Risiken und technische Schulden

<div class="formalpara-title">

**Inhalt**

</div>

Eine nach Prioritäten geordnete Liste der erkannten Architekturrisiken
und/oder technischen Schulden.

> Risikomanagement ist Projektmanagement für Erwachsene.
>
> —  Tim Lister Atlantic Systems Guild

Unter diesem Motto sollten Sie Architekturrisiken und/oder technische
Schulden gezielt ermitteln, bewerten und Ihren Management-Stakeholdern
(z.B. Projektleitung, Product-Owner) transparent machen.

<div class="formalpara-title">

**Form**

</div>

Liste oder Tabelle von Risiken und/oder technischen Schulden, eventuell
mit vorgeschlagenen Maßnahmen zur Risikovermeidung, Risikominimierung
oder dem Abbau der technischen Schulden.

Siehe [Risiken und technische
Schulden](https://docs.arc42.org/section-11/) in der
online-Dokumentation (auf Englisch!).

# Glossar

| Begriff           | Definition                                                                 |
|-------------------|----------------------------------------------------------------------------|
| Chatbot           | Software-Komponente, die automatisiert auf Benutzeranfragen reagiert.      |
| BotController     | Modul zur Verwaltung und Steuerung der Chatbots im System.                  |
| BotFactory        | Komponente zur Erzeugung von Bot-Instanzen anhand ihres Namens.            |
| IBot              | Interface, das die Schnittstelle für alle Chatbots definiert.               |
| TUI               | Terminal User Interface; textbasierte Benutzeroberfläche.                   |
| GUI               | Graphical User Interface; grafische Benutzeroberfläche (optional, geplant).|
| H2-Datenbank      | Eingebettete relationale Datenbank zur lokalen Speicherung von Chatverläufen.|
| Benutzer          | Person, die sich am System anmeldet und mit Chatbots interagiert.           |
| Chatverlauf       | Historie der geführten Chats zwischen Benutzer und Chatbots.                |
| MVP               | Minimum Viable Product; erste lauffähige Version mit Kernfunktionen.        |
| API               | Application Programming Interface; Schnittstelle zu externen Diensten.      |
| WikipediaBot      | Chatbot, der Wikipedia-Inhalte abfragt und zusammenfasst.                   |
| WeatherBot        | Chatbot, der Wetterdaten abfragt und bereitstellt.                          |
| CredentialsAPI    | Zugangsdaten für die Nutzung externer APIs.                                 |
| Benutzername      | Eindeutige Kennung eines Benutzers im System.                               |
| Passwort          | Geheimwort zur Authentifizierung eines Benutzers.                           |
| Kommando          | Textbasierte Eingabe zur Steuerung des Systems oder der Bots.               |

# Quellen
arc42 TEMPLATE EINFÜGEN