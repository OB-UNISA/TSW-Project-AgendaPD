DROP database IF EXISTS agendapd;
CREATE database agendapd;
USE agendapd;

CREATE TABLE utente (
    nomeUtente VARCHAR(255),
    pass VARCHAR(512),
    ruolo TINYINT,
    email VARCHAR(50),
    nome VARCHAR(35),
    cognome VARCHAR(35),
    cf VARCHAR(16),
    dataDiNascita DATE,
    via VARCHAR(40),
    citta VARCHAR(30),
    cap INT,
    PRIMARY KEY (nomeUtente)
);

CREATE TABLE dottore (
    nomeUtenteDottore VARCHAR(255),
    prenotazioneVisita BOOLEAN,
    richiestaRicette BOOLEAN,
    codiceDottore VARCHAR(16),
    orarioVisite VARCHAR(300),
    PRIMARY KEY (nomeUtenteDottore),
    FOREIGN KEY (nomeUtenteDottore)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE segretaria (
    nomeUtenteSegretaria VARCHAR(255),
    nomeUtenteDottore VARCHAR(255),
    accessoPazienti BOOLEAN,
    accessoCartelle BOOLEAN,
    accessoRicette BOOLEAN,
    accessoPrenotazioni BOOLEAN,
    modificaRicette BOOLEAN,
    aggiuntaPazienti BOOLEAN,
    eliminazionePazienti BOOLEAN,
    aggiuntaNews BOOLEAN,
    eliminazioneNews BOOLEAN,
    PRIMARY KEY (nomeUtenteDottore),
    FOREIGN KEY (nomeUtenteSegretaria)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (nomeUtenteDottore)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE paziente (
    nomeUtentePaziente VARCHAR(255),
    nomeUtenteDottore VARCHAR(255),
    accessoCartelle BOOLEAN,
    PRIMARY KEY (nomeUtentePaziente),
    FOREIGN KEY (nomeUtentePaziente)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (nomeUtenteDottore)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE iot (
    id VARCHAR(255),
    pass VARCHAR(512),
    permessi INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE studio (
    nomeUtenteDottore VARCHAR(255),
    numeroPostiLiberi INT,
    pazientiEntrati INT,
    PRIMARY KEY (nomeUtenteDottore),
    FOREIGN KEY (nomeUtenteDottore)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE cartellaClinica (
    nomeUtentePaziente VARCHAR(255),
    dataCartella DATE,
    tipologia VARCHAR(100),
    luogo VARCHAR(60),
    esito VARCHAR(150),
    cartella MEDIUMBLOB,
    PRIMARY KEY (nomeUtentePaziente , dataCartella , tipologia),
    FOREIGN KEY (nomeUtentePaziente)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ricetta (
    nomeUtentePaziente VARCHAR(255),
    nomeUtenteDottore VARCHAR(255),
    dataOrarioRicetta TIMESTAMP,
    ricetta MEDIUMBLOB,
    PRIMARY KEY (nomeUtentePaziente , nomeUtenteDottore , dataOrarioRicetta),
    FOREIGN KEY (nomeUtentePaziente)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (nomeUtenteDottore)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE communicazione (
    nomeUtenteDottore VARCHAR(255),
    descrizione VARCHAR(1500),
    dataOrarioCommunicazione TIMESTAMP,
    PRIMARY KEY (nomeUtenteDottore , dataOrarioCommunicazione),
    FOREIGN KEY (nomeUtenteDottore)
        REFERENCES utente (nomeUtente)
        ON UPDATE CASCADE ON DELETE CASCADE
);