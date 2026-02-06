# Guida alla Configurazione HTTPS per Frontend e Backend

## Prerequisiti

Prima di avviare il progetto, è necessario completare i seguenti passaggi.

---

## 1. Installazione di mkcert

Se `mkcert` è già installato sulla tua macchina, passa direttamente al punto 2.

### 1.1 Su Linux

```bash
apt install mkcert
# oppure
dnf install mkcert
```

### 1.2 Su Windows

```bash
choco install mkcert
```

### 1.3 Su MacOS

```bash
brew install mkcert
```

---

## 2. Creazione dei Certificati per il Frontend

### 2.1 Installazione della CA locale

```bash
mkcert -install
```

### 2.2 Generazione dei certificati

```bash
mkcert <ip_macchina_dev> localhost 127.0.0.1
```

Questo comando genererà due file:

- `<ip_macchina_dev>+2.pem`
- `<ip_macchina_dev>+2-key.pem`

### 2.3 Posizionamento dei certificati

Inserire i due file `.pem` all'interno della directory `certs` del progetto frontend.

### 2.4 Configurazione di Vite

Nel file `vite.config.ts`, inserire la seguente configurazione:

```typescript
export default defineConfig({
  // ... altre configurazioni
  server: {
    host: "0.0.0.0",
    port: 5173,
    https: {
      key: fs.readFileSync("./certs/<ip_macchina_dev>+2-key.pem"),
      cert: fs.readFileSync("./certs/<ip_macchina_dev>+2.pem"),
    },
  },
});
```

### 2.5 Verifica dell'avvio

Avviare il progetto con:

```bash
npm run dev
```

Assicurarsi che venga mostrato il seguente messaggio a console:

```
VITE v7.2.7  ready in 607 ms

  ➜  Local:   https://localhost:5173/
  ➜  Network: https://169.254.83.107:5173/
  ➜  Network: https://192.168.1.60:5173/
  ➜  Network: https://10.8.10.10:5173/
  ➜  press h + enter to show help
```

---

## 3. Creazione dei Certificati per il Backend

### 3.1 Installazione della CA locale

```bash
mkcert -install
```

### 3.2 Generazione dei certificati

```bash
mkcert <ip_macchina_dev> localhost 127.0.0.1
```

Questo comando genererà due file:

- `<ip_macchina_dev>+2.pem`
- `<ip_macchina_dev>+2-key.pem`

### 3.3 Generazione del keystore per Spring

Generare un keystore in formato PKCS12 compatibile con Spring Boot:

```bash
openssl pkcs12 -export \
  -in <ip_macchina_dev>+2.pem \
  -inkey <ip_macchina_dev>+2-key.pem \
  -out backend-keystore.p12 \
  -name <keystore_alias> \
  -passout pass:<keystore_password>
```

Dove:

- `<keystore_alias>` è il nome dell'alias del keystore
- `<keystore_password>` è la password del keystore

Entrambi dovranno essere specificati nelle properties del progetto Spring.

### 3.4 Posizionamento del keystore

Il comando precedente genererà un file chiamato `backend-keystore.p12`. Inserirlo nella directory:

```
src/main/resources/certs/
```

### 3.5 Configurazione di Spring Boot

Nel file `application.properties` o `application.yaml`, inserire:

```yaml
server:
  port: 8080
  ssl:
    enabled: true
    key-store: classpath:certs/backend-keystore.p12
    key-store-password: <keystore_password>
    key-store-type: PKCS12
    key-alias: <keystore_alias>
  address: 0.0.0.0
```

Specificare il nome e la password del keystore precedentemente impostati.

### 3.6 Verifica dell'avvio

Avviare il progetto e assicurarsi che a console venga scritto:

```
Tomcat initialized with port 8080 (https)
```

---

## 4. Completamento

Una volta completati questi passaggi, frontend e backend comunicheranno tramite HTTPS.

---

## 5. Configurazione dell'Accesso da Browser Mobile

### 5.1 Android

1. Copiare il file `mkcert-rootCA.crt` sul telefono
2. Aprire **Impostazioni → Sicurezza → Crittografia e credenziali**
3. Selezionare **Installa certificato → CA**
4. Riavviare Chrome

### 5.2 iOS

#### 5.2.1 Importazione del certificato

Importare nel dispositivo il file `<ip_macchina_dev>+2.pem` generato al passo 2.2.

#### 5.2.2 Download del profilo

Aprire il file. Comparirà un messaggio:

```
Profilo scaricato
Se vuoi installarlo, rivedi il profilo nell'app Impostazioni
```

#### 5.2.3 Installazione del profilo

1. Aprire **Impostazioni → Generali → VPN e gestione dispositivi** (oppure **Profili/Gestione profili**, a seconda della versione iOS)
2. Selezionare **Profilo scaricato**
3. Toccare **Installa**
4. Inserire il codice di sblocco
5. Confermare (anche se indica "non firmato")

**Nota:** A questo punto il profilo è installato, ma NON ancora attendibile.

#### 5.2.4 Abilitazione della fiducia

1. Aprire **Impostazioni → Generali → Info → Impostazioni certificati** (in fondo alla schermata "Info")
2. Nella sezione **Abilita piena fiducia per i certificati root**, attivare l'interruttore accanto a **mkcert Root CA**
3. Confermare il popup di sicurezza

#### 5.2.5 Riavvio

Riavviare il browser.

---

## Note Finali

Al termine di questa configurazione, il progetto sarà accessibile in HTTPS sia da desktop che da dispositivi mobili sulla rete locale.
