var idIoT = global("IdIoT");
var passIoT = global("PassIoT");
var enteredIoT = global("EnteredIoT");
var freeSeatsIoT = global("FreeSeatsIoT");

var login = { 
 id: idIoT, 
 pass: passIoT, 
 entered: enteredIoT,
 freeSeats: freeSeatsIoT
};

var jsonLogin = JSON.stringify(login);
setGlobal("JsonLogin", jsonLogin);