
// Insecure direct object reference (potential SQL injection)
function getUser(username: string) {
    const sql = `SELECT * FROM users WHERE username = '${username}'`;
    // ... (execute SQL query)
}

// Unvalidated user input (potential XSS attack)
function displayMessage(message: string) {
    document.getElementById('message').innerHTML = message;
}
