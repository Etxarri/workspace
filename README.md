# Devcontainer: Java + MySQL

[eu](README.md) | [es](README.es.md) | [en](README.en.md)

Oraingo honetan, kontenedore bakar bat izan beharrean, bi kontenedore izango ditugu:
1. **DevContainer**: Hemen garatuko dugu gure Java aplikazioa (klasean bezala).
2. **MySQL**: Hemen exekutatuko dugu gure MySQL datu-basea.

Ikusi daiteke `.devcontainer/compose.yml` fitxategian bi zerbitzu ditugula: `java-app` eta `mysql`.

## Probatu

* Ireki proiektua devcontainer moduan.

### MySQL

Behin kontenedorea sortuta, probatu MySQL konexioa:

```bash
mysql -h mysql -u root -p
```

* Sartu `pasahitza` pasahitz gisa.
* Exekutatu komandoak **pbl** datu-basea existitzen dela egiaztatzeko:

```sql
show databases;
```
* Irteera hau ikusi beharko zenuke:

```sql
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| pbl                |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.01 sec)
```

### SpringBoot

Orain [Spring Initializr](https://start.spring.io/)era joan zaitezke, zure SpringBoot aplikazioa sortzeko eta devcontainerean exekutatzeko.

![Spring Initializr](screenshots/spring-initializr.png)

* Deskargatu edukia worspaceko karpetan.

#### Dependentziak deskargatu

```bash
mvn clean install
```

#### Exekutatu aplikazioa
* Ireki `src/**` karpetako java fitxategia, gehitu *breakpoint* bat `main` metodoaren barruan eta exekutatu aplikazioa **F5** sakatuz.
* *breakpoint*ean gelditu beharko litzateke.

> `launch.json` fitxategia sor dezakezu aplikazioa exekutatzeko, java klase nagusia irekita eduki beharrik ez izateko. Joan **Run and Debug** fitxara, egin klik **launch.json fitxategi bat sortu** eta hautatu Java.

## Ondorioa

Devcontainer bat sortu duzu Java eta MySQL-rekin. Devcontainer hau Java aplikazioa garatzeko eta exekutatzeko erabil dezakezu. MySQL ere erabili dezakezu zure datuak gordetzeko.

Orain aplikazioa garatzen hasi zaitezke. Etorkizunean, funtzionalitate aurreratuak gehitzeko, dependentziak gehitu beharko dituzu.

> **OHARRA**: proiektu hau hasteko erabili baduzu, ziurtatu `.git` karpeta eta `.gitignore` fitxategia ezabatzen dituzula zure kodea zure errepositorio propioan igo aurretik. Bestela, ez duzu zure kodea igoko.


## Handitu abiadura Windows-en

* Ireki zure terminala eta exekutatu hurrengo komandoa:

```bash
wsl --install
```

or

```bash
wsl --install --distribution Ubuntu-24.04
```

* Joan Docker Desktop-en konfiguraziora eta aktibatu WSL integrazioa Ubuntu distribuzio berrian.

![Docker Desktop](./screenshots/docker-desktop-wsl-integration.png)

* Birabiarazi ordenagailua.
* Ireki zure terminala, egin klik geziaren gainean eta hautatu **Ubuntu**.
* Erabiltzaile berri bat sortuko da. Jarraitu argibideak erabiltzaile berri bat sortzeko eta gogoratu pasahitza.
* Ubuntu terminalean zaudenean
  * Sortu `git` izeneko karpeta berri bat
  * Klonatu errepo hau bertan.
  * Ireki karpeta VSCode-n.

```bash
cd ~
mkdir git
cd git
git clone https://gitlab.com/mgep-web-ingeniaritza-1/pbl/spring-devcontainer-kaixo-mundua.git
cd spring-devcontainer-kaixo-mundua
code .
```

* Honek proiektua zure VSCode-n irekiko du.
* Orain, aurreko adibideetan bezala, ireki proiektua devcontainer moduan.