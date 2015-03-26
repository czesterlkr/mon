# MOP
## Instalacja
Aby uruchomić aplikację należy zainstalować poniższe narzędzia:
 - Java
 - Maven
 - GIT
 - Node.js
 - Yeoman
 - Bower
 - Grunt
 - JHipster

Opis instalacji znajdziemy na oficjalnej [stronie JHipster'a.][1]. Pomocny może być również [ten filmik][2]. Baza danych jaką używamy w aplikacji to PostgreSQL.
Plik konfiguracyjny znajdziemy *src/main/resources/config/application-dev.yml*. Uzupełniamy go swoimi lokalnymi parametrami. Oczywiście należy wcześniej utworzyć nową bazę danych.
**Uwaga! Tego pliku nie commitujemy. Dodajemy go do ignore'a lokalnego**

## Uruchomienie

Po zakończeniu instalacji, aby uruchomić projekt należy w katalogu głównym projektu wywołać polecenie:
```sh
$ mvn spring-boot:run
```

Jeżeli dociągnięcie zależności maven'owych oraz uruchomienie aplikacji powiedzie się, możemy uruchomić naszą aplikację, wpisując w przeglądarce:
```sh
http://http://localhost:8080/
```
 
 [1]:https://jhipster.github.io/installation.html
 [2]:https://jhipster.github.io/video_tutorial.html
