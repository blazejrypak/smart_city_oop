# Blažej Rypák

# Smart city
Projekt Smart City je určený na efektívnejšiu komunikáciu medzi občanom a jeho
samosprávou v oblasti podávania rôznych podnetov zo strany občanov a opätovnom
získavani notifikácií po ich úspešnom vyriešení.

### Občan:
- Prostredníctvom aplikácie Smart City občan jednoduchšie prispieva na kvalite života
v jeho meste, a to tak že dáva podnety na rôzne nedostatky, ktoré je potrebné
odstrániť.
- Napríklad nefunkčné osvetlenie na ulici, poškodená lavička v parku, nevysypaný kôš
alebo výtlk na ceste.
- Následne keď sa problém vyrieši občan je notifikovaný správou.
- Občan v aplikácii zvolí konkrétny nedostatok, napíše k nemu potrebný popis, pridá
lokalizáciu miesta a odošle svojej samospráve.

### Mesto:
- Mesto v aplikácii vidí všetky podnety zvoleného typu podnetu.
- Podnet vo forme správy sa dostane k príslušnému uradnikovi, ktorý je zodpovedný
za danú oblasť.
- Po jeho vyriešení sa automaticky pošle notifikácia každému občanovi, ktorý podnet
podal alebo sa oň zaujímal.

## Funkcionalita: 
**Občan** po prihlásení môže pridať nový podnet podľa zvolenej kategórie, pozrieť si notifikácie o podnetoch,
 ktorým sa zmenil stav.

**Úradnik** si po prihlásení môže pozrieť všetky podnety podľa zvolenej kategórie, zmeniť stav daného podnetu,
 pozrieť všetky notifikácie alebo pridať novú kategóriu.

- agregácia: trieda Address je agregovaná v triedach: CategoryEvent a ContactDetails

- dedenie: trieda AdminUser dedí triedu User

- polymorfizmus: metóda update() z interfacu NotificationListeners v triedach ClientUser a OfficeUser 
je rôzne implementovaná. (Observer pattern)

# Getting Started
- change absolute path in helpers/DataStorage.java to:
    - private String USERS = "/**yourAbsolutePath**/src/helpers/users";
    - private String CATEGORIES = "/**yourAbsolutePath**/src/helpers/categories";
    - example: private String USERS = "/home/bubo/IdeaProjects/SmartCityFinal/src/helpers/users";

- install libraries in prerequisites below

# Prerequisites:
- Java 1.8.0
- JFoenix 8.x.x from https://github.com/jfoenixadmin/JFoenix
- json-simple-1.1.1.jar from https://code.google.com/archive/p/json-simple/downloads

## Accounts for testing
- Admin
    - username: **root**
    - password: **root**
    
- Officer
    - username: **officer**
    - password: **officer**
    
- Citizen
    - username: **client**
    - password: **client**