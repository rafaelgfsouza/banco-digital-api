const API_URL = "http://localhost:8080";

function showSection(id) {
    document.getElementById('login-section').style.display = 'none';
    document.getElementById('cadastro-section').style.display = 'none';
    document.getElementById('dashboard-section').style.display = 'none';
    document.getElementById(id).style.display = 'block';
}

async function login() {
    const numeroConta = document.getElementById('login-conta').value;
    const senha = document.getElementById('login-senha').value;

    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ numeroConta, senha })
    });

    if (response.ok) {
        const token = await response.text();
        localStorage.setItem('token', token); // Guarda a string longa!
        localStorage.setItem('minhaConta', numeroConta);
        carregarDados();
        showSection('dashboard-section');
    } else {
        alert("Erro no login! Verifique os dados.");
    }
}

async function carregarDados() {
    const token = localStorage.getItem('token');
    const minhaConta = localStorage.getItem('minhaConta');

    const response = await fetch(`${API_URL}/contas`, {
        headers: { 'Authorization': `Bearer ${token}` }
    });

    const contas = await response.json();
    const minhaContaDados = contas.find(c => c.numeroConta === minhaConta);

    if (minhaContaDados) {
        document.getElementById('user-name').innerText = minhaContaDados.nomeTitular;
        document.getElementById('user-account').innerText = minhaContaDados.numeroConta;
        document.getElementById('user-balance').innerText = `R$ ${minhaContaDados.saldo.toFixed(2)}`;
    }
}

async function transferir() {
    const token = localStorage.getItem('token');
    const destino = document.getElementById('trans-destino').value;
    const valor = document.getElementById('trans-valor').value;

    const response = await fetch(`${API_URL}/contas/transferir?numeroDestino=${destino}&valor=${valor}`, {
        method: 'POST',
        headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
        alert("Transferência realizada!");
        carregarDados(); // Atualiza o saldo na tela
    } else {
        alert("Erro na transferência.");
    }
}

async function cadastrar() {
    const nome = document.getElementById('cad-nome').value;
    const cpf = document.getElementById('cad-cpf').value;
    const email = document.getElementById('cad-email').value;
    const senha = document.getElementById('cad-senha').value;

    const response = await fetch(`${API_URL}/clientes`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome, cpf, email, senha })
    });

    if (response.ok) {
        const dados = await response.json(); 
        
        // Se no Java você retornar um Map ou um DTO simples, 
        // o numeroConta virá direto na raiz:
        const numeroContaGeral = dados.numeroConta;

        alert(`Sucesso! Sua conta é: ${numeroContaGeral}. Entrando...`);

        document.getElementById('login-conta').value = numeroContaGeral;
        document.getElementById('login-senha').value = senha;

        await login(); 
        
    } else {
        const erroTexto = await response.text();
        alert("Erro ao cadastrar: " + erroTexto);
    }
}

function logout() {
    localStorage.clear();
    location.reload();
}