function countdown(elementId) {
    // Fetch the display element
    var el = document.getElementById(elementId);
    console.log(elementId + "_min");
    var minutes =  document.getElementById(elementId + "_min").value;
    var seconds =  document.getElementById(elementId + "_sec").value;

    // Set the timer
    var interval = setInterval(function() {
        if(seconds == 0) {
            if(minutes == 0) {
                (el.innerHTML = "KOMT ERAAN!");

                clearInterval(interval);
                return;
            } else {
                minutes--;
                seconds = 60;
            }
        }

        if(minutes > 0) {
            var minute_text = minutes + (minutes > 1 ? ' minutes' : ' minute');
        } else {
            var minute_text = '';
        }

        var second_text = seconds > 1 ? '' : '';
        el.innerHTML = minute_text + ' ' + seconds + ' ' + second_text + '';
        seconds--;
    }, 1000);
}

//Start as many timers as you want

var start1 = document.getElementById("timer1");
var start2 = document.getElementById('timer2');
var start3 = document.getElementById('timer3');
var start4 = document.getElementById('timer4');
var start5 = document.getElementById('timer5');

