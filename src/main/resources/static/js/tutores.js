let tutores = [];
let proximoId = 1; // ID gerado automaticamente

// Referências aos elementos
const form = document.getElementById('tutorForm');
const nomeInput = document.getElementById('nome');
const telefoneInput = document.getElementById('telefone');
const emailInput = document.getElementById('email');
const tabela = document.getElementById('tutor-table-body');
const btnLimpar = document.getElementById('btnLimpar');

// Função para renderizar a tabela
function atualizarTabela() {
    tabela.innerHTML = "";
    tutores.forEach(tutor => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${tutor.id}</td>
            <td>${tutor.nome}</td>
            <td>${tutor.telefone}</td>
            <td>${tutor.email || "-"}</td>
        `;
        tabela.appendChild(tr);
    });
}

// Submissão do formulário
form.addEventListener('submit', (e) => {
    e.preventDefault();

    const nome = nomeInput.value.trim();
    const telefone = telefoneInput.value.trim();
    const email = emailInput.value.trim();

    if (!nome || !telefone) {
        alert("Por favor, preencha os campos obrigatórios (Nome e Telefone).");
        return;
    }

    const novoTutor = {
        id: proximoId++,
        nome,
        telefone,
        email: email || ""
    };

    tutores.push(novoTutor);
    atualizarTabela();
    form.reset();
});

// Botão "Limpar"
btnLimpar.addEventListener('click', () => {
    form.reset();
});
