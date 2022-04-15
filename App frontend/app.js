const menu = document.querySelector('#mobilemenu')
const menuLinks = document.querySelector('.navbarmenu')
let loggedInUser;
let nav = document.getElementsByTagName('nav')[0];
let logInBtn;
// call checklogin on the other JS files so that we can make sure other things happen first

async function checkLogin() {
    let userId = sessionStorage.getItem('Auth-Token');
    loggedOutNavBar();
    let httpResp = await fetch('http://localhost:8080/users/' + userId);
    if (httpResp && httpResp.status === 200) {
        loggedInUser = await httpResp.json();
        loggedInNavBar();
    } else {
        loggedOutNavBar();
    }
}


menu.addEventListener('Click', function() {
    menu.classList.toggle('is-active');
    menuLinks.classList.toggle('active');
});

async function logIn() {
    let credentials = {
        username:document.getElementById('usernameLogin').value,
        password:document.getElementById('passwordLogin').value
    };
    let credentialJSON = JSON.stringify(credentials);

    let httpResp = await fetch('http://localhost:1010/auth',
        {method:'POST', body:credentialJSON});
    if (httpResp && httpResp.status === 200) {
        loggedInUser = await httpResp.json();
        sessionStorage.setItem('Auth-Token', loggedInUser.id);
        await checkLogin();
        loggedInNavBar();
    }
}
