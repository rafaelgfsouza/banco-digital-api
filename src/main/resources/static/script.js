const API_URL = window.location.origin;

function showSection(id) {
    document.getElementById('login-section').style.display = 'none';
    document.getElementById('cadastro-section').style.display = 'none';
    document.getElementById('dashboard-section').style.display = 'none';
    document.getElementById(id).style.display = 'block';
}

async function login() {
    const numeroConta = document.getElementById('login-conta').value;
    const senha = document.getElementById('login-senha').value;

    console.log("Tentando login com conta:", numeroConta);

    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ numeroConta, senha })
    });

    if (response.ok) {
        const token = await response.text();
        localStorage.setItem('token', token);
        localStorage.setItem('minhaConta', numeroConta);

        await carregarDados(); // Espera carregar os dados antes de mostrar a tela
        showSection('dashboard-section');
    } else {
        const erroMsg = await response.text();
        alert("Erro no login: " + erroMsg);
    }
}

async function carregarDados() {
    const token = localStorage.getItem('token');
    const minhaConta = localStorage.getItem('minhaConta');

    if (!token) return;

    const response = await fetch(`${API_URL}/contas`, {
        headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
        const contas = await response.json();
        // Procura a conta na lista que o Java retornou
        const minhaContaDados = contas.find(c => c.numeroConta === minhaConta);

        if (minhaContaDados) {
            // Ajustado para os nomes do seu ContaExibicaoDTO (nomeTitular, numeroConta, saldo)
            document.getElementById('user-name').innerText = minhaContaDados.nomeTitular;
            document.getElementById('user-account').innerText = minhaContaDados.numeroConta;
            document.getElementById('user-balance').innerText = `R$ ${minhaContaDados.saldo.toFixed(2)}`;
        }
    } else {
        console.error("Erro ao carregar dados do dashboard");
    }
}

async function transferir() {
    const token = localStorage.getItem('token');
    const destino = document.getElementById('trans-destino').value;
    const valor = document.getElementById('trans-valor').value;

    // Passando os parâmetros via URL como o seu ContaController espera (@RequestParam)
    const response = await fetch(`${API_URL}/contas/transferir?numeroDestino=${destino}&valor=${valor}`, {
        method: 'POST',
        headers: { 'Authorization': `Bearer ${token}` }
    });

    if (response.ok) {
        alert("Transferência realizada com sucesso!");
        document.getElementById('trans-destino').value = '';
        document.getElementById('trans-valor').value = '';
        carregarDados();
    } else {
        const erroTxt = await response.text();
        alert("Erro na transferência: " + erroTxt);
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

        // --- AQUI ESTAVA O ERRO ---
        // No Java, você retorna o Cliente, e o numeroConta está dentro da conta do cliente.
        const numeroContaGeral = dados.conta.numeroConta;

        alert(`Sucesso! Sua conta criada é: ${numeroContaGeral}`);

        // Preenche automaticamente o campo de login para facilitar
        document.getElementById('login-conta').value = numeroContaGeral;
        document.getElementById('login-senha').value = senha;

        // Tenta logar automaticamente
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

// Verifica se já está logado ao abrir a página
window.onload = () => {
    if (localStorage.getItem('token')) {
        carregarDados();
        showSection('dashboard-section');
    }
};