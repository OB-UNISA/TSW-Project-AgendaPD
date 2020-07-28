<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="IoT">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/iotdoc.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<title>IoT Doc</title>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<h1>IoT Documentazione</h1>
	<header>Introduzione</header>
	<article>
		<p>AgendaPD mette a disposizione dei dottori la possibilità di poter
			collegare dispositivi IoT per far sapere ai propri pazienti il numero di
			posti liberi nello studio e il numero di persone entrate, in modo tale da
			evitare code o assemblamenti negli studi.</p>
	</article>
	<header>Cosa devo fare?</header>
	<article>
		<p>Il meccanismo di funzionamento è semplice, il dispositivo IoT dovrà
			creare un oggetto JavaScript con le seguenti proprietà:</p>
		<ul>
			<li>id</li>
			<li>pass</li>
			<li>entered <i>oppure</i> freeSeats
			</li>
		</ul>
		<p>
			L'id, in questa fase iniziale di sperimentazione, è il nome utente del
			profilo.<br> <i>Pass</i> indica la password, al momento della
			registrazione del profilo da dottore, verrà assegnata la stessa password del
			profilo al dispositivo IoT, tuttavia questa potrà essere cambiata
			successivamente.<br>L'ultima proprietà può essere denominata <i>entered</i>
			oppure <i>freeSeats</i>. <i>Entered</i> indica il numero di persone entrate,
			l'altro indica il numero di posti a sedere liberi.<br> Durante questa
			prima fase, non sarà possibile aggiornare in una sola request entrambi i
			dati.<br> <br>Una volta creato l'oggetto, esso dovrà essere
			trasformato in una stringa usando il metodo <i>JSON.stringify(oggetto)</i> e
			la stringa dovrà essere inviata nel body della request. Dovrà essere inoltre
			inserito un parametro nella request che indichi la tipologia di operazione
			che si desidera eseguire. Il parametro dovrà chiamarsi <i>mode</i>, i valori
			ammessi sono:
		</p>
		<ul>
			<li>entered - per aggiornare il numero di persone entrate</li>
			<li>seats - per aggiornare il numero di posti a sedere liberi</li>
		</ul>
		<p>
			<b>Il metodo della request dovrà essere POST, altrimenti la risposta del
				server sarà un semplice testo con la scritta <i>NOT_ALLOWED</i>.
			</b><br> Dal profilo si può accedere alla sezione IoT e modificare
			manualmente i dati e i permessi.
		</p>
	</article>
	<header>Cosa fa il server?</header>
	<article>
		<p>
			La risposta del server sarà sempre una stringa, la quale dovrà essere
			trasformata in un oggetto JavaScript usando il metodo <i>JSON.parse(stringa)</i>.
			Per ottenere informazioni sulla risposta del server si dovrà accedere alla
			proprietà dell'oggetto denominata <i>info</i>.<br>Valori della proprietà
			<i>info</i>:
		</p>
		<ul>
			<li>OK - tutto è andato bene e il dato è stato aggiornato</li>
			<li>UNAUTHORIZED - l'id e/o la pass passati sono sbagliati</li>
			<li>NOT_SECURE - non si sta utilizzando il protocollo <i>https</i></li>
			<li>ERROR
				<ul>
					<li>ERROR + stato di risposta 422 indica un errore dei dati passati</li>
					<li>ERROR + stato di risposta 500 indica un errore lato server</li>
				</ul>
			</li>
		</ul>
	</article>
</body>
</html>