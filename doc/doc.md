# Documentazione progetto Trackit (backend)

## Server embedded Tomcat (dev)
Il server embedded Tomcat, fornito con il framework Spring Boot, è stato configurato per partire usando il protocollo HTTPS invece che il solito HTTP. Questa scelta, dovuta all'uso dell'autenticazione JWT tra backend e frontend, permette lo scambio dei cookie tra le parti, cosa che usando il protocollo HTTP non è possibile.
Talvolta, però, può capitare che Spring dia errore in fare di avvio del server con una Exception del tipo:
`Caused by: java.io.IOException: DerInputStream.getLength(): lengthTag=111, too big.`

Per quanto strano questo errore si risolve facilmente seguendo questa procedura:
1) Assicurarsi che sotto `<dir_proj>/src/resources/certs` esista il file `keystore.p12`
2) Se non esiste bisogna crearlo (spiegazione in ...), viene sempre fornito assieme al progetto, quindi scaricando effettuando un clone da git, questo viene scaricato automaticamente
3) Questo errore significa che quasi sempre il file `keystore.p12` <u>non</u> è un file PKCS#12 valido, quindi eseguire questi passi
   1) eseguire il comando `file keystore.p12`, l'output atteso è `keystore.p12: data` in quanto è un file binario, se è diverso c'è un problema
   2) controlla che nel file application.yml si sia un codice del genere:
   <code>server:
      ssl:
      key-store: classpath:certs/keystore.p12
      key-store-type: PKCS12
      key-store-password: ********</code>
    e che la password sia coretta
   3) Soluzione sicura: rigenera il p12 usando Java: `keytool -importkeystore srckeystore <keystore_original_name>.p12 -srcstoretype PKCS12 -destkeystore <keystore_java_new_name>.p12 -deststoretype PKCS12`
4) Riavviare il server (ora dovrebbe funzionare)
