// Caminho: src/main/resources/static/js/atendimento.js

// URLs da API
const API_ATENDIMENTOS = 'http://localhost:8080/api/atendimentos';
const API_PETS = 'http://localhost:8080/api/pets';
const API_SERVICOS = 'http://localhost:8080/api/servicos';

// Referências aos elementos do formulário de atendimento
const form = document.getElementById('atendimentoForm');
const dataHoraInput = document.getElementById('dataHora');
const petSelect = document.getElementById('petId');
const servicoSelect = document.getElementById('servicoId');
const obsInput = document.getElementById('observacoes');
const tabela = document.getElementById('atendimento-table-body');
const btnLimpar = document.getElementById('btnLimpar');

// ID oculto para rastrear a edição
const editIdInput = document.getElementById('editAtendimentoId');

/**
 * Função 1: Carrega os menus <select> de Pets e Serviços
 */
async function carregarDropdowns() {
    try {
        // 1. Carregar Pets (pedir muitos 'size=1000' para garantir que todos venham)
        const respPets = await fetch(`${API_PETS}?size=1000&sort=nome`);
        const pagePets = await respPets.json();
        const pets = pagePets.content || [];

        petSelect.innerHTML = '<option value="">Selecione um pet</option>';
        pets.forEach(pet => {
            // Mostra o nome do tutor junto para diferenciar pets com mesmo nome
            const nomeTutor = pet.tutor ? pet.tutor.nome : 'Sem Tutor';
            const option = new Option(`${pet.nome} (Tutor: ${nomeTutor})`, pet.id);
            petSelect.appendChild(option);
        });

        // 2. Carregar Serviços
        const respServicos = await fetch(`${API_SERVICOS}?size=1000&sort=descricao`);
        const pageServicos = await respServicos.json();
        const servicos = pageServicos.content || [];

        servicoSelect.innerHTML = '<option value="">Selecione um serviço</option>';
        servicos.forEach(servico => {
            const option = new Option(`${servico.descricao} (R$ ${servico.valor})`, servico.id);
            servicoSelect.appendChild(option);
        });

    } catch (erro) {
        console.error("Erro ao carregar pets ou serviços:", erro);
        alert("Erro ao carregar opções do formulário. Verifique o console.");
    }
}

/**
 * Função 2: Carrega a tabela principal de atendimentos
 */
async function carregarAtendimentos() {
    try {
        // Pede os atendimentos ordenados por data (mais novos primeiro)
        const resposta = await fetch(`${API_ATENDIMENTOS}?sort=dataHora,desc`);
        const dados = await resposta.json();
        const atendimentos = dados.content || [];
        atualizarTabela(atendimentos);
    } catch (erro) {
        console.error("Erro ao carregar atendimentos:", erro);
        alert("Erro ao carregar atendimentos.");
    }
}

/**
 * Função 3: Atualiza o HTML da tabela
 */
function atualizarTabela(atendimentos) {
    tabela.innerHTML = ""; // Limpa a tabela

    if (atendimentos.length === 0) {
        tabela.innerHTML = '<tr><td colspan="6">Nenhum atendimento cadastrado.</td></tr>';
        return;
    }

    atendimentos.forEach(at => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${at.id}</td>
            <td>${formatarData(at.dataHora)}</td>
            <td>${at.pet?.nome || 'Pet excluído'}</td>
            <td>${at.servico?.descricao || 'Serviço excluído'}</td>
            <td>${at.observacoes || "-"}</td>
            <td>
                <button class="btn btn-edit" data-id="${at.id}">Editar</button>
                <button class="btn btn-delete" data-id="${at.id}">Excluir</button>
            </td>
        `;
        tabela.appendChild(tr);
    });
}

/**
 * Função 4: Lida com o envio do formulário (Criar e Atualizar)
 */
form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    // Pega o ID do campo oculto. Se existir, é uma edição.
    const id = editIdInput.value;

    // Monta o objeto de dados (Request DTO)
    const dadosAtendimento = {
        dataHora: dataHoraInput.value + ':00', // Adiciona segundos para o LocalDateTime
        petId: petSelect.value,
        servicoId: servicoSelect.value,
        observacoes: obsInput.value.trim() || null
    };

    // Validação simples
    if (!dadosAtendimento.dataHora || !dadosAtendimento.petId || !dadosAtendimento.servicoId) {
        alert('Por favor, preencha Data/Hora, Pet e Serviço.');
        return;
    }

    try {
        let url = API_ATENDIMENTOS;
        let method = 'POST';

        if (id) {
            // É ATUALIZAÇÃO (PUT)
            url = `${API_ATENDIMENTOS}/${id}`;
            method = 'PUT';
        }

        const resposta = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosAtendimento)
        });

        if (!resposta.ok) {
            const erro = await resposta.json();
            throw new Error(erro.message || 'Erro ao salvar atendimento');
        }

        limparFormulario();
        carregarAtendimentos(); // Atualiza a tabela
        alert(id ? 'Atendimento atualizado!' : 'Atendimento cadastrado!');

    } catch (erro) {
        console.error(erro);
        alert(`Erro ao salvar atendimento: ${erro.message}`);
    }
});

/**
 * Função 5: Lida com cliques na tabela (Editar e Excluir)
 */
tabela.addEventListener('click', async (e) => {
    const el = e.target;
    const id = el.dataset.id;

    if (el.classList.contains('btn-delete')) {
        // --- AÇÃO DE DELETAR ---
        if (confirm(`Tem certeza que deseja excluir o atendimento ID ${id}?`)) {
            try {
                const resposta = await fetch(`${API_ATENDIMENTOS}/${id}`, { method: 'DELETE' });
                
                if (!resposta.ok) {
                    // Tenta ler o erro do backend se houver
                    const erro = await resposta.json().catch(() => ({}));
                    throw new Error(erro.message || 'Erro ao excluir');
                }
                
                carregarAtendimentos();
                alert('Atendimento excluído com sucesso!');

            } catch (erro) {
                alert('Erro ao excluir: ' + erro.message);
            }
        }
    } else if (el.classList.contains('btn-edit')) {
        // --- AÇÃO DE EDITAR ---
        // 1. Busca os dados completos do atendimento na API
        try {
            const resposta = await fetch(`${API_ATENDIMENTOS}/${id}`);
            const at = await resposta.json();

            // 2. Preenche o formulário com os dados
            editIdInput.value = at.id;
            // O input 'datetime-local' espera o formato 'YYYY-MM-DDTHH:MM'
            // O backend retorna 'YYYY-MM-DDTHH:MM:SS'. Cortamos os segundos.
            dataHoraInput.value = at.dataHora.substring(0, 16); 
            
            petSelect.value = at.pet.id;
            servicoSelect.value = at.servico.id;
            obsInput.value = at.observacoes || '';

            // 3. Rola a página para o topo (para o formulário)
            window.scrollTo({ top: 0, behavior: 'smooth' });

        } catch (erro) {
            alert('Erro ao carregar dados para edição: ' + erro.message);
        }
    }
});

/**
 * Função 6: Limpar formulário
 */
function limparFormulario() {
    form.reset();
    editIdInput.value = ''; // Limpa o ID de edição
}
btnLimpar.addEventListener('click', limparFormulario);

/**
 * Helper: Formata a data para 'dd/mm/aaaa HH:MM'
 */
function formatarData(isoString) {
    if (!isoString) return '-';
    const data = new Date(isoString);
    return data.toLocaleString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// --- CARREGAMENTO INICIAL ---
// Carrega os dropdowns E a tabela quando a página abre
carregarDropdowns();
carregarAtendimentos();