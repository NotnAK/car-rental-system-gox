# GoX 

## Popis projektu  

GoX je systém na prenájom automobilov, ktorý umožňuje používateľom vyhľadávať dostupné vozidlá, rezervovať ich na určité obdobie a spravovať svoje prenájmy. Používatelia si môžu vybrať vozidlo podľa rôznych parametrov, ako je značka, model, cena alebo typ paliva. Používatelia si môžu ukladať obľúbené vozidlá do zoznamu želaní, aby si ich mohli rýchlo rezervovať v budúcnosti. V rámci vernostného programu GoX môžu pravidelní zákazníci získavať zľavy za časté prenájmy. Administrátor systému spravuje databázu automobilov, schvaľuje rezervácie a monitoruje ich stav. Po dokončení prenájmu môže zákazník hodnotiť vozidlo a zanechať recenziu, čím pomáha ostatným používateľom pri výbere. 

## Zber požiadaviek

- **RQ01** Systém umožní používateľom vyhľadávať dostupné vozidlá podľa rôznych kritérií (značka, model, cena, rok, typ paliva).
- **RQ02** Systém umožní rezerváciu vozidiel na definované obdobie (od-do) s overením dostupnosti.
- **RQ03** Systém bude uchovávať históriu prenájmov (rezervácií) pre každého používateľa.
- **RQ04** Systém umožní správu databázy vozidiel (pridanie, úprava, odstránenie) a správu stavu (AVAILABLE, RENTED, UNAVAILABLE).
- **RQ05** Používatelia môžu dostávať notifikácie (email) a v prílohe PDF s detailmi rezervácie.
- **RQ06** Systém umožní hodnotiť prenajaté vozidlá (rating) a písať recenzie (review).
- **RQ07** V rámci vernostného programu získavajú pravidelní zákazníci zľavy podľa dosiahnutého levelu (ktorý je určený počtom prenajatých vozidiel).
- **RQ08** Systém umožní vypočítať pokutu (penalty) za oneskorené vrátenie vozidla.
- **RQ09** Systém zobrazí cenu prenájmu, pričom reálna platba prebehne na pobočke. Administrátor v systéme potvrdí, že platba bola vykonaná.
- **RQ10** Systém umožní integráciu s mapami: používateľ si môže vybrať miesto vyzdvihnutia a miesto odovzdania (pri inej lokalite sa pripočíta extra poplatok).
- **RQ11** Systém automaticky generuje PDF potvrdenie a posiela ho používateľovi e-mailom. Súčasne ho používateľ môže stiahnuť zo svojho profilu.
- **RQ12** Systém poskytne jednoduchú analytiku pre administrátora (prehľad prenájmov, najviac hodnotené autá, príjmy).
- **RQ13** Používateľ si môže uložiť obľúbené vozidlá do „wishlistu“ a rýchlo ich rezervovať v budúcnosti.

---

## Slovník pojmov

| **Pojem**                | **Anglický názov** | **Definícia**                                                                                                                         |
|--------------------------|--------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Používateľ (User)**    | User               | Osoba využívajúca GoX na rezerváciu auta. Môže prezerať vozidlá, vytvárať rezervácie, hodnotiť autá a dostávať notifikácie.            |
| **Administrátor**        | Administrator      | Používateľ s rozšírenými právami: správa databázy vozidiel (CRUD), schvaľovanie/rušenie rezervácií, prezeranie štatistík.              |
| **Vozidlo (Car)**        | Car                | Automobil dostupný na prenájom. Obsahuje informácie o značke, modeli, roku, cene za deň, stave (AVAILABLE, RENTED, UNAVAILABLE).       |
| **Rezervácia (Booking)** | Booking            | Záznam o prenájme vozidla na určené obdobie (od-do). Uchováva dátumy, cenu, stav a referenciu na používateľa a vozidlo.                |
| **Recenzia (Review)**    | Review             | Hodnotenie (rating) a textový komentár, ktorý používateľ pridá po skončení prenájmu.                                                  |
| **Notifikácia**          | Notification       | Správa zasielaná používateľovi (napr. email s PDF) pri udalostiach ako vytvorenie rezervácie, schválenie, oneskorenie vrátenia auta.  |
| **Wishlist**             | Wishlist           | Zoznam obľúbených vozidiel, ktoré si používateľ ukladá pre neskoršiu rýchlu rezerváciu.                                               |
| **Penalta (Penalty)**    | Penalty            | Pokuta, ktorá sa pripočíta k cene prenájmu, ak používateľ vráti vozidlo oneskorene. Systém vypočíta výšku pokuty a zašle email.        |
| **Program vernosti**     | Loyalty Program    | Mechanizmus, v ktorom používateľ získava body za každú rezerváciu. Body ho posúvajú na vyššiu úroveň (napr. SILVER, GOLD) a znižujú cenu budúcich prenájmov. |
| **Miesto vyzdvihnutia** (PickUp) | Pickup Location  | Adresa, kde si používateľ vyzdvihne auto. Môže to byť oficiálna pobočka alebo iná lokalita na mape.                                     |
| **Miesto odovzdania** (DropOff) | DropOff Location | Adresa, kde môže používateľ auto vrátiť. V prípade inej lokality než pobočka môže byť účtovaný poplatok navyše.                         |
| **PDF potvrdenie**       | PDF Receipt        | Dokument generovaný systémom s detailmi rezervácie (vozidlo, dátumy, cena). Posiela sa emailom a je dostupný na stiahnutie.           |
| **Analytika**            | Analytics          | Prehľad pre administrátora: počet prenájmov, obľúbené vozidlá, priemerné hodnotenie, príjmy.                                          |
