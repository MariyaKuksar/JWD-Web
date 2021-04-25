var startDate = new Date();
function timer() {
var theDate = new Date();
var differ = theDate - startDate;
var hours, minutes, seconds;
hours = Math.floor(differ/(1000*60*60));
hours = (hours > 60) ? hours%60 : hours;
hours = (hours < 10) ? "0" + hours : hours;

minutes = Math.floor(differ/(1000*60));
minutes = (minutes > 60) ? minutes%60 : minutes;
minutes = (minutes < 10) ? "0" + minutes : minutes;

seconds = Math.round(differ/1000);
seconds = (seconds > 60) ? seconds%60 : seconds;
seconds = (seconds < 10) ? "0" + seconds : seconds;		

var strDate =  hours + ":" + minutes + ":" + seconds;
document.forms['timerForm'].timerBox.value=strDate;		
setTimeout("timer()", 1000);}