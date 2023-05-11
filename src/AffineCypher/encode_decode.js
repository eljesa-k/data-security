function generateRandomKeys(){
    let a = document.getElementById('a-key')
    let b = document.getElementById('b-key')
    let a_key_possibilities = [1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25];
    let a_index = Math.random() * 12;
    a.value = a_key_possibilities[parseInt(a_index)];

    b.value = parseInt(Math.random() * 25) + 1;

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
        if(p === ' ')
            output += p;
        else {
            output += String.fromCharCode((a * (input.charCodeAt(i) - 97) + b) % 26 + 97)
        }
    }

    document.getElementById('output').value = output
}

function encrypt1(){
    if(!validate1())
        return;
    let a = parseInt(document.getElementById('a-key-dec').value)
    let b = parseInt(document.getElementById('b-key-dec').value)

    let inversi = 1
        for(let i = 1; i<26; i++)
          if((a*i)%26==1)
          inversi=i


    let input = document.getElementById('input-dec').value
    input = input.toLowerCase()

    let output = ''
    for(let i = 0; i< input.length; i++){
        let p = input.charAt(i)
        if(p === ' ')
            output += p;
        else {
            output += String.fromCharCode(((input.charCodeAt(i) - 97) - b + 26) * inversi % 26 + 97)
        }
    }

    document.getElementById('output-dec').value = output
}

function validate(){
    let a_key_possibilities = [1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25];

    let a = document.getElementById('a-key')
    let b = document.getElementById('b-key')

    document.getElementById('invalid-keys-message').style.display = 'none'
    a.style.borderColor = '#aafcb8'
    b.style.borderColor = '#aafcb8'
    let res = true

    let reg = new RegExp('^[0-9]+$');

    if(!reg.test(a.value) || !a_key_possibilities.find(k => k === parseInt(a.value))){
        a.style.borderColor = 'red'
        res = false
    }
    if(!reg.test(b.value) || !(parseInt(b.value) > 0) || !(parseInt(b.value) < 26)){
        b.style.borderColor = 'red'
        res = false
    }

    if(!res)
        document.getElementById('invalid-keys-message').style.display = 'block'
    return res;
}

function validate1(){
    let a_key_possibilities = [1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25];

    let a = document.getElementById('a-key-dec')
    let b = document.getElementById('b-key-dec')

    document.getElementById('invalid-keys-message-dec').style.display = 'none'
    a.style.borderColor = '#aafcb8'
    b.style.borderColor = '#aafcb8'

    let res = true
    let reg = new RegExp('^[0-9]+$');

    if(!reg.test(a.value) || !a_key_possibilities.find(k => k === parseInt(a.value))){
        a.style.borderColor = 'red'
        res = false
    }
    if(!reg.test(b.value) || !(parseInt(b.value) > 0) || !(parseInt(b.value) < 26)){
        b.style.borderColor = 'red'
        res = false
    }
    if(!res)
        document.getElementById('invalid-keys-message-dec').style.display = 'block'
    return res;
}


function clearEncInput(){
    document.getElementById('input').value = null
    document.getElementById('output').value = null
}
function copyEncOutput(){
    let text = document.getElementById('output').value
    navigator.clipboard.writeText(text)
}

function clearEncInput(){
    document.getElementById('input').value = null
    document.getElementById('output').value = null
}
function copyEncOutput(){
    let text = document.getElementById('output').value
    navigator.clipboard.writeText(text)
}
