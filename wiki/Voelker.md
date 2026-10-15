# Völker

Jedes Volk hat eine eigene Werkbank mit exklusiven Rezepten, die nur dieses Volk nutzen kann. Nach der Volkswahl bekommst du deine Werkbank einmal geschenkt, weitere stellst du über ein Rezept her. Die Werkbänke sehen aus wie normale Blöcke; normale Komposter, Zaubertische usw. bleiben normal.

| Volk | Werkbank | Was sie kann |
|---|---|---|
| Menschen | Köderkasten (Komposter) | 4 Kammern, in denen alle 20 Köder fürs [Angeln](Angeln) entstehen. Lebende Köder vermehren sich aus einem Ansatz mit Futter, auch wenn niemand hineinschaut. |
| Lunarei | Schmuckwerkbank (Bognertisch) | Stellt alle 40 Ringe, Gürtel und Halsketten her. Kostet Zutaten und Erfahrungsstufen, ab selten oft mit Gem. |
| Dirasau | Runenaltar (Zaubertisch) | 10 eigene Waffen-Verzauberungen mit je 3 Stufen für viele Erfahrungsstufen. |
| Mirna | Edelsteinschmiede (Hochofen) | 5x5-Raster mit Brennstoff und Zeit: zwei Gems einer Stufe plus Zutaten ergeben ein Gem der nächsten Stufe (bis 10). Mirna finden beim Erzabbau Gems der Stufe 1. |

Köderkasten und Edelsteinschmiede behalten ihren Inhalt im Block. Wer sie abbaut, bekommt Werkbank und Inhalt zurück.

`/volk` öffnet die Volkswahl oder zeigt die Völker, `/volk info` dein Volk.

## Die Völker

- **Menschen:** vielseitige Siedler, Meister der Köderzucht.
- **Lunarei:** elfenhaftes Volk, geschickte Schmuckmacher.
- **Dirasau:** Echsenmenschen, Hüter alter Runen.
- **Mirna:** Bergvolk aus Zwergen und Gnomen, finden beim Erzabbau Gems.

## Werkbank herstellen (normale Werkbank, nur fürs eigene Volk)

| Werkbank | Muster | Zutaten |
|---|---|---|
| Köderkasten | `PWP / DCD / PPP` | P = Bretter, W = Weizen, D = Erde, C = Komposter |
| Schmuckwerkbank | `AGA / QFQ / PPP` | A = Amethystscherbe, G = Goldbarren, Q = Quarz, F = Bognertisch, P = Bretter |
| Runenaltar | `BDB / OTO / OCO` | B = Buch, D = Diamant, O = Obsidian, T = Zaubertisch, C = Weinender Obsidian |
| Edelsteinschmiede | `IGI / DBD / SSS` | I = Eisenblock, G = Goldbarren, D = Diamant, B = Hochofen, S = Polierter Tiefenschiefer |

## Runen der Dirasau

Lege eine Waffe in den Runenaltar. Eine Waffe trägt höchstens 3 verschiedene Runen. Bogen, Armbrust und Dreizack geben ihre Runen an das Geschoss weiter. Die Wirkung gilt für jeden, der die Waffe führt.

| Rune | Waffen | Stufe 1 / 2 / 3 | Kosten (Erfahrungsstufen) |
|---|---|---|---|
| Lebensraub | Schwert, Axt, Speer, Streitkolben, Dreizack | Heilt dich um 5 % des Schadens. / Heilt dich um 10 % des Schadens. / Heilt dich um 15 % des Schadens. | 30 / 50 / 80 |
| Blutung | Schwert, Axt, Speer, Bogen, Armbrust | 15 %: Ziel blutet 3 s lang (1 Schaden/s). / 20 %: Ziel blutet 4 s lang (1.5 Schaden/s). / 25 %: Ziel blutet 5 s lang (2 Schaden/s). | 30 / 50 / 80 |
| Giftzahn | Schwert, Speer, Dreizack, Bogen, Armbrust | 15 %: Gift 1 für 3 s. / 20 %: Gift 1 für 5 s. / 25 %: Gift 2 für 5 s. | 30 / 50 / 80 |
| Frostbiss | Schwert, Axt, Speer, Streitkolben, Dreizack, Bogen, Armbrust | 20 %: Langsamkeit 1 für 2 s. / 25 %: Langsamkeit 2 für 3 s. / 30 %: Langsamkeit 2 für 4 s. | 25 / 45 / 70 |
| Donnerschlag | Axt, Streitkolben, Dreizack, Armbrust | 4 %: Blitz mit 4 Schaden. / 6 %: Blitz mit 6 Schaden. / 9 %: Blitz mit 8 Schaden. | 40 / 65 / 100 |
| Hinrichtung | Schwert, Axt, Speer, Streitkolben, Bogen, Armbrust | +20 % Schaden gegen Ziele unter 30 % Leben. / +40 % Schaden gegen Ziele unter 30 % Leben. / +60 % Schaden gegen Ziele unter 35 % Leben. | 35 / 60 / 90 |
| Echsenwut | Schwert, Axt, Speer, Streitkolben, Dreizack | +15 % Schaden, wenn du unter 40 % Leben hast. / +30 % Schaden, wenn du unter 40 % Leben hast. / +45 % Schaden, wenn du unter 50 % Leben hast. | 30 / 55 / 85 |
| Spaltung | Axt, Streitkolben | 20 % des Schadens an Gegner im Umkreis von 2.5 Blöcken. / 35 % des Schadens an Gegner im Umkreis von 3 Blöcken. / 50 % des Schadens an Gegner im Umkreis von 3.5 Blöcken. | 35 / 60 / 90 |
| Seelenernte | Schwert, Axt, Speer, Streitkolben, Dreizack | +50 % Erfahrung beim Töten. / +100 % Erfahrung beim Töten. / +150 % Erfahrung beim Töten. | 40 / 70 / 110 |
| Panzerbrecher | Axt, Streitkolben, Speer, Armbrust | +1 % Schaden je Rüstungspunkt des Ziels. / +2 % Schaden je Rüstungspunkt des Ziels. / +3 % Schaden je Rüstungspunkt des Ziels. | 35 / 60 / 90 |

## Gems der Mirna

Mirna finden beim Abbau von Erzen manchmal einen Gem der Stufe 1 (selbst platzierte Erze und Behutsamkeit zählen nicht, Tiefenschiefer-Erze zählen normal).

| Erz | Chance | Gem |
|---|---|---|
| Kohle | 1 % | Onyx |
| Kupfer | 1,5 % | Bernstein |
| Eisen | 2 % | Topas, Onyx |
| Gold | 3 % | Topas, Bernstein |
| Redstone | 2,5 % | Rubin |
| Lapislazuli | 3 % | Saphir |
| Diamant | 12 % | Diamant, Saphir, Rubin |
| Smaragd | 12 % | Smaragd |
| Netherquarz | 1,5 % | Mondstein |

In der Edelsteinschmiede werden zwei Gems einer Stufe mit Zutaten und Brennstoff (Kohle, Holzkohle, Lohenrute, Kohleblock, Magmablock) zu einem Gem der nächsten Stufe. Für Stufe 10 braucht man 512 Gems der Stufe 1. Alle Gems findest du unter [Schmuck und Gems](Schmuck-und-Gems).

[Zurück zur Übersicht](Startseite)
