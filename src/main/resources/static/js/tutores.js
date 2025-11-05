// URL base da API (ajuste se a porta for diferente)
const API_URL = 'http://localhost:8080/api/tutores';
 
// Referências aos elementos
const form = document.getElementById('tutorForm');
const nomeInput = document.getElementById('nome');
const telefoneInput = document.getElementById('telefone');
const emailInput = document.getElementById('email');
const tabela = document.getElementById('tutor-table-body');
const btnLimpar = document.getElementById('btnLimpar');
 
// Função para buscar e renderizar tutores
async function carregarTutores() {
    try {
        const resposta = await fetch(API_URL);
        if (!resposta.ok) throw new Error('Erro ao buscar tutores');
        const tutores = await resposta.json();
        atualizarTabela(tutores);
    } catch (erro) {
        console.error(erro);
        alert('Erro ao carregar tutores.');
    }
}
 
// Renderiza a tabela
function atualizarTabela(tutores) {
    tabela.innerHTML = '';
    tutores.forEach(tutor => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
<td>${tutor.id}</td>
<td>${tutor.nome}</td>
<td>${tutor.telefone}</td>
<td>${tutor.email || '-'}</td>
        `;
        tabela.appendChild(tr);
    });
}
 
// Envio do formulário (POST)
form.addEventListener('submit', async (e) => {
    e.preventDefault();
 
    const nome = nomeInput.value.trim();
    const telefone = telefoneInput.value.trim();
    const email = emailInput.value.trim();
 
    if (!nome || !telefone) {
        alert('Por favor, preencha os campos obrigatórios (Nome e Telefone).');
        return;
    }
 
    const novoTutor = { nome, telefone, email };
 
    try {
        const resposta = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoTutor)
        });
 
        if (!resposta.ok) throw new Error('Erro ao cadastrar tutor');
        form.reset();
        carregarTutores(); // Atualiza lista
    } catch (erro) {
        console.error(erro);
        alert('Erro ao cadastrar tutor.');
    }
});
 
// Botão "Limpar"
btnLimpar.addEventListener('click', () => form.reset());
 
// Carregar tutores ao abrir a página
carregarTutores();