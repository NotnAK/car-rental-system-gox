# GoX 

## Popis projektu  

GoX je systém na prenájom automobilov, ktorý umožňuje používateľom vyhľadávať dostupné vozidlá, rezervovať ich na určité obdobie a spravovať svoje prenájmy. Používatelia si môžu vybrať vozidlo podľa rôznych parametrov, ako je značka, model, cena alebo typ paliva. Používatelia si môžu ukladať obľúbené vozidlá do zoznamu želaní, aby si ich mohli rýchlo rezervovať v budúcnosti. V rámci vernostného programu GoX môžu pravidelní zákazníci získavať zľavy za časté prenájmy. Administrátor systému spravuje databázu automobilov, schvaľuje rezervácie a monitoruje ich stav. Po dokončení prenájmu môže zákazník hodnotiť vozidlo a zanechať recenziu, čím pomáha ostatným používateľom pri výbere. 


## Zber požiadaviek

- **RQ01** Systém umožní používateľom vyhľadávať dostupné vozidlá podľa rôznych kritérií (značka, model, cena, rok, typ paliva atď.).
- **RQ02** Systém umožní rezerváciu vozidiel na definované obdobie (od-do).
- **RQ03** Systém bude uchovávať históriu prenájmov (rezervácií) pre každého používateľa.
- **RQ04** System umožní administrátorovi spravovať používateľov, vozidlá, recenzie, lokality pobočiek a rezervácie.  
- **RQ05** System definuje pre každé vozidlo tieto stavy: AVAILABLE, RENTED, UNAVAILABLE, IN_TRANSIT; ak je stav UNAVAILABLE, rezervácia tohto vozidla nie je možná.
- **RQ06** Systém umožní hodnotiť prenajaté vozidlá (rating) a písať recenzie (review).
- **RQ07** V rámci vernostného programu získavajú pravidelní zákazníci zľavy podľa dosiahnutého levelu (STANDARD, SILVER, GOLD):
    - PRECHOD NA SILVER po 5 úspešných rezerváciách  
    - PRECHOD NA GOLD po 10 úspešných rezerváciách .
- **RQ08** Používateľ si môže uložiť obľúbené vozidlá do „wishlistu“ a rýchlo ich rezervovať v budúcnosti.
- **RQ09** Systém zobrazí cenu prenájmu, pričom reálna platba prebehne na pobočke.
- **RQ10** Systém umožňuje používateľovi určiť miesto vyzdvihnutia (pickup) a miesto odovzdania (dropoff).
- **RQ11** Systém podporuje tieto stavy rezervácie:  
   - PENDING – čaká na schválenie administrátorom  
   - APPROVED – potvrdené administrátorom (po telefonickom kontakte)  
   - CANCELLED – zrušené administrátorom alebo používateľom pred začiatkom rezervácie  
   - COMPLETED – manuálne uzavreté administrátorom po skutočnom vrátení vozidla
- **RQ12** Systém vypočíta pokutu za meškanie nad 30 minút tak, že sa spočíta počet celých hodín meškania (po prekročení 30 minút) a za každú hodinu sa pripočíta 40 % z dennej ceny vozidla.
- **RQ13** Systém musí povoliť rezerváciu minimálne na jeden deň, pričom platba sa účtuje vždy za celé dni.
- **RQ14** Systém musí zabezpečiť, aby medzi ukončením jednej rezervácie a začiatkom ďalšej rezervácie toho istého vozidla uplynulo aspoň 24 hodín.
- **RQ15** Systém umožní „skoré“ rezervácie v ten istý deň len vtedy, ak medzi časom rezervácie a plánovaným časom odchodu uplynuli aspoň 4 hodiny. Ak sa vozidlo nachádza na inej pobočke a rozdiel medzi skutočným časom rezervácie a časom odchodu je menej ako 10 hodín, k cene sa pripočíta poplatok za prevod vozidla.
- **RQ16** Systém umožní bezplatné zrušenie rezervácie kedykoľvek pred oficiálnym začiatkom rezervácie. Ak rezervácia nie je využitá po začatí, používateľ uhradí plnú cenu a administrátor situáciu telefonicky vyrieši.
- **RQ17** System umožní priradiť ku každému vozidlu viacero fotografií, pričom presne jedna z nich bude označená ako „preview“ a ostatné budú doplnkové.


## Slovník pojmov

| **Pojem**                | **Anglický názov** | **Definícia**                                                                                                                         |
|--------------------------|--------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Používateľ**    | User               | Osoba využívajúca GoX na rezerváciu auta (CUSTOMER). Môže prezerať vozidlá, vytvárať rezervácie, hodnotiť autá.          |
| **Administrátor**        | Administrator      | Používateľ s rozšírenými právami (ADMIN): správa databázy (CRUD), schvaľovanie/rušenie rezervácií.           |
| **Vozidlo**        | Car                | Automobil dostupný na prenájom. Obsahuje informácie o značke, modeli, roku, cene za deň, stave (AVAILABLE, RENTED, UNAVAILABLE, IN_TRANSIT).       |
| **Rezervácia** | Booking            | Záznam o prenájme vozidla na určené obdobie (od-do). Uchováva dátumy, cenu, stav a referenciu na používateľa a vozidlo.                |
| **Recenzia**    | Review             | Hodnotenie (rating) a textový komentár, ktorý používateľ pridá po skončení prenájmu.                                                  |
| **Wishlist**             | Wishlist           | Zoznam obľúbených vozidiel, ktoré si používateľ ukladá pre neskoršiu rýchlu rezerváciu.                                               |
| **Pokuta**    | Penalty            | Pokuta, ktorá sa pripočíta k cene prenájmu, ak používateľ vráti vozidlo oneskorene.        |
| **Program vernosti**     | Loyalty Program    | Mechanizmus, v ktorom používateľ získava body za každú rezerváciu. Body ho posúvajú na vyššiu úroveň (napr. SILVER, GOLD) a znižujú cenu budúcich prenájmov. |
| **Miesto vyzdvihnutia** | Pickup Location  | Adresa, kde si používateľ vyzdvihne auto.  Zvyčajne ide o adresu pobočky.                                     |
| **Miesto odovzdania** | DropOff Location | Adresa, kde môže používateľ auto vrátiť. Za inú lokalitu než pôvodnú môže byť účtovaný extra poplatok.                         |
| **Transfer**            | Transfer          | Presun auta z jedného filiálu do iného                 |
| **Fotografia**   | Photo              | Obrázok priradený k vozidlu. Každé vozidlo musí mať práve jednu fotografiu označenú ako „preview“ a voliteľne ďalšie doplnkové fotografie. |
