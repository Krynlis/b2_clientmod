# TESTME b2_clientmod 1.0.0.0

Voraussetzung: Minecraft 26.3 mit Fabric Loader, Fabric API und b2_clientmod. Server mit b2_core, das den Kanal `b2:client` unterstützt.

| Nr. | Test | Erwartung |
| --- | --- | --- |
| 1 | Minecraft starten | Startet ohne Fehler, Mod steht in der Mod-Liste |
| 2 | Einzelspielerwelt öffnen, `E` drücken | Normales Inventar öffnet sich |
| 3 | Fremden Server ohne b2_core betreten, `E` drücken | Normales Inventar öffnet sich, keine Fehlermeldung |
| 4 | b2-Server betreten | Server erkennt die Mod (Hinweis oder Log auf dem Server) |
| 5 | Auf dem b2-Server `E` drücken (Überleben) | Server-Inventar öffnet sich statt des normalen Inventars |
| 6 | Im Server-Inventar den Knopf für das normale Inventar nutzen | Normales Inventar mit 2x2-Handwerk öffnet sich |
| 7 | Kreativmodus, `E` drücken | Kreativ-Inventar öffnet sich wie gewohnt |
| 8 | Auf einem Pferd `E` drücken | Pferde-Inventar öffnet sich wie gewohnt |
| 9 | `R`, `G`, `V` drücken | Server meldet Aktion 1, 2, 3 (nur wenn ein Plugin sie nutzt) |
| 10 | Tasten unter Steuerung → b2 Server ändern | Neue Tasten senden die Aktionen |
| 11 | Server verlassen und neu betreten | Alles funktioniert weiter wie in 4 bis 9 |
