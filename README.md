# PartyKal
- projekt obsahuje soubor APK v hlavním adresáři
## Využité zdroje:
- QR Code reader: https://easyonecoder.com/android/basic/QRCodeScanner
- Ikona: https://www.flaticon.com/free-icons/playing-card
- Inspirace a nauka: ChatGPT a Pan učitel Jan Vais
- Většina kódu byla psána ručně
## Funkce:
- celá aplikace funguje v angličtině i v češtině
- body a nastavený časový limit se ukládá v Shared Preferences
- karty se ukládají v SQLite databázi
- Všechny chybné využití jsou ošetřeny pomocí chybových hlášek (Toast)
### Hlavní view
- Obsahuje:
  - tlačítko pro začátek hry
  - nastavení časového limitu (pokud zůstane prázdný, využije se defaultních 20 min)
  - správa karet
- Na vrcholu obrazovky se zobrazuje poslední skóre
### Hra
- v herním view se zobrazuje karta s úkolem
- karta se za každých x (default 20) minut změní
  - je zobrazen odpočet
  - pokud je karta označená za hotovou, přičtou se hráči body
- pro označení karty se klikne kdekoliv na kartu
- body se zobrazují na horní straně obrazovky
### Správa karet
- Možnost přidat kartu
- pomocí horního textového pole se dá vyhledávat karta
- karty se dají importovat a exportovat qr kódem (má svůj limit)
- všechny karty se dají vymazat pomocí tlačítka, které vyžaduje zadání specifického kódu do vyhledávání
- obsahuje tři TextView které ukazují: nejpodobnější název podle vyhledávání, počet podobných karet podle vyhledávání, celkový počet karet
## Třídy:
### MainActivity
- hlavní aktivita
- při spuštění hry se resetují body
### CardTestActivity
- aktivita pro správu karet
- většina funkcí na konci automaticky obnoví zobrazené hodnoty
- většina funkcí využívá další třídu DBM
- bohužel import karet využívá deprecated třídu, ale dokud funguje, funguje
- data se přes qr kód posílá ve formátu JSON
### CardActivity
- aktivita pro hraní hry
- na začátku nastaví time limit, který dostane z SharedPreferences pomocí statické třídy PM
### Card
- jednoduchá třída pro data v kartě
### PM (Preferences Manager)
- statická třída pro správu ukládání dat do preferences
- spravuje body a časový limit
### DBM (DataBase Manager)
- spravuje uložené karty v databázi SQLite
### QRExportActivity
- Zobrazuje vygenerováný qr kód
- pokud generace qr kódu selhala, vrátí se zpět do CardTestActivity a hodí Toast error