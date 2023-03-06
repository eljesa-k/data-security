// Write JavaScript here 

function openCity(evt, cityName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
// document.getElementById("defaultOpen").click();
// console.log(document.getElementById('default-tab'))
// console.log('ok')

function generateRandomKeys(){
    let a = document.getElementById('a-key')
    let b = document.getElementById('b-key')
    let a_key_possibilities = [1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25];
    let a_index = Math.random() * 12;
    a.value = a_key_possibilities[parseInt(a_index)];

    b.value = parseInt(Math.random() * 26);

    // using validate to remove error in case it is shown
    validate()
}

function encrypt(){
    if(!validate())
        return;
    let a = parseInt(document.getElementById('a-key').value)
    let b = parseInt(document.getElementById('b-key').value)
    let input = document.getElementById('input').value
    input = input.toLowerCase()

    let output = ''
    for(let i = 0; i< input.length; i++){
        let p = input.charAt(i)
        if(p === '')
            output += p;
        else {
            console.log(input.charCodeAt(i)-97, (a * (input.charCodeAt(i) - 97) + b) )

            output += String.fromCharCode((a * (input.charCodeAt(i) - 97) + b) % 26 + 97)
        }
    }

    document.getElementById('output').value = output
}

function validate(){
    // TODO: validate decimal numbers
    let a = document.getElementById('a-key')
    let b = document.getElementById('b-key')
    let res = true
    if(!a.value || isNaN(a.value)){
        a.style.borderColor = 'red'
        res = false
    }
    if(!b.value || isNaN(b.value)){
        b.style.borderColor = 'red'
        res = false
    }
    if(!res)
        document.getElementById('invalid-keys-message').style.display = 'block'
    else {
        document.getElementById('invalid-keys-message').style.display = 'none'
        a.style.borderColor = '#aafcb8'
        b.style.borderColor = '#aafcb8'
    }

    return res;
}


