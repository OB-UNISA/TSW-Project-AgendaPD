var time = new Date();

var timeStr = time.getDate() + "-" + (time.getMonth() + 1) + "-" + time.getFullYear() + " -- " + time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds() + ":" + time.getMilliseconds();

setGlobal("Time", timeStr);
setGlobal("TimeMs", time.getTime().toString());