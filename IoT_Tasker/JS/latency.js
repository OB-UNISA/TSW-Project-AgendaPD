var time = new Date();
var timePast = global("TimeMs");
var latency = time.getTime() - timePast;

setGlobal("Latency", latency);