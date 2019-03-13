# PV168_Transylvanie

### Téma:
Evidence hostů v hotelu 

### Specifikace:
Aplikace PV168_Transylvanie je určena na evidenci hostu v hotelu, prihlaseni hostu do hotelu (pokoje) a odhlaseni hostu z hotelu (pokoje). 
Evidenci hostu lze provadet pomoci seznamu hostu, kdy po zvolení hosta dostaneme informace o nem. Dalsi moznost evidence hosta lze podle vyhledani hosta podle jmena. 
Evidence pokojů lze delat pomoci seznamu pokoju a nasledne zvolenim pokoje.
Prihlasit hosta do pokoje lze pomoci vyplneni udaju a nasledne dalsiho hosta. maximalni pocet hostu v jednom pokoji je 5.
Odhlaseni hosta z hotelu lze pomoci zadani cisla pokoje. Automaticky tak odhlasi vechny hosty v danem pokoji a vypise se cena za stravene noci.


### Use case diagram:

![use](Images/Use_Case_Transylvanie.jpg "use case")

### Class diagram:

![class](Images/Class_Diagram_Transylvanie.jpg "use case")

### Uzivatelske prostredi:

Vstupni obrazovka:

![alt text](Images/uvodni_strana.png)

Kdyz uzivatel klikne na tlacitko "Hosti", objevi se seznam hostu. Pri kliknuti na jmeno hosta se vypisou informace o nem. Nebo si uzivatel muze vyhledat hosta podle jmena.

![alt text](Images/hosti.png)

Kdyz uzivatel klikne na tlacitko "Pokoje", objevi se seznam pokoju. Pri kliknuti na pokoj se vypisou informace o nem.
![alt text](Images/pokoje.png)

Uzivatel muze pri kliknuti na tlacitko "Přihlásit pokoj", prihlasit urcity pocet hostu do pokoje. Pocet je omezen kapacitou (max 5 hostu v jednom pokoji).
![alt text](Images/prihlasit.png)

Obrazovka, ktera se objevi pri pridavani posledniho hosta v pokoji.
![alt text](Images/prihlasit_5.png)

Uzivatel muze odhlasit pokoj. Po napsani cisla pokoje do kolonky, se uzivatli objevi cena za stravene noci.
![alt text](Images/odhlasit.png)



