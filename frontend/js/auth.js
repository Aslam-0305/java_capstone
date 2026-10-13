let currentRole = null;
let currentUser = null;

function goToLogin(role) {
    currentRole = role;
    document.getElementById('roleSelection').style.display = 'none';
    document.getElementById('loginContainer').style.display = 'flex';
}

function goToRoleSelection() {
    currentRole = null;
    document.getElementById('loginContainer').style.display = 'none';
    document.getElementById('roleSelection').style.display = 'flex';
    document.getElementById('loginForm').reset();
    document.getElementById('loginError').textContent = '';
}

document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await api.login(username, password);
        if (response.token) {
            localStorage.setItem('token', response.token);
            localStorage.setItem('role', response.role);
            localStorage.setItem('userId', response.userId);
            localStorage.setItem('username', response.username);
            
            currentUser = response;
            
            document.getElementById('loginContainer').style.display = 'none';
            document.getElementById('dashboard').style.display = 'flex';
            document.getElementById('logoutBtn').style.display = 'block';
            
            loadDashboard(response.role);
        } else {
            document.getElementById('loginError').textContent = response.message || 'Login failed';
        }
    } catch (error) {
        document.getElementById('loginError').textContent = 'Login error: ' + error.message;
    }
});

document.getElementById('logoutBtn')?.addEventListener('click', () => {
    localStorage.clear();
    currentUser = null;
    currentRole = null;
    document.getElementById('dashboard').style.display = 'none';
    document.getElementById('logoutBtn').style.display = 'none';
    document.getElementById('loginForm').reset();
    document.getElementById('loginContainer').style.display = 'none';
    document.getElementById('roleSelection').style.display = 'flex';
});

function isAuthenticated() {
    return !!localStorage.getItem('token');
}

function getAuthToken() {
    return localStorage.getItem('token');
}

function getUserRole() {
    return localStorage.getItem('role');
}

function getUserId() {
    return parseInt(localStorage.getItem('userId'));
}

window.addEventListener('load', () => {
    if (isAuthenticated()) {
        document.getElementById('roleSelection').style.display = 'none';
        document.getElementById('dashboard').style.display = 'flex';
        document.getElementById('logoutBtn').style.display = 'block';
        loadDashboard(getUserRole());
    } else {
        document.getElementById('roleSelection').style.display = 'flex';
    }
});
