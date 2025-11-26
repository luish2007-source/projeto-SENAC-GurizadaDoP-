const apiBaseUrl = "http://localhost:8080/api";

async function listarServicos() {
    const response = await fetch(`${apiBaseUrl}/servicos?page=0&size=50`);
    const data = await response.json();

    const tbody = document.querySelector("#tblServicos tbody");
    tbody.innerHTML = "";

    data.content.forEach(servico => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${servico.id}</td>
            <td>${servico.nome}</td>
            <td>R$ ${servico.valor.toFixed(2)}</td>
            <td>${servico.duracao || "—"} min</td>
            <td class="text-end">
                <button class="btn btn-warning btn-sm" onclick="editarServico(${servico.id})">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="excluirServico(${servico.id})">Excluir</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

async function salvarServico() {
    const id = document.getElementById("serviceId").value;
    const nome = document.getElementById("nome").value;
    const valor = Number(document.getElementById("valor").value);
    const duracao = document.getElementById("duracao").value;

    const servico = { nome, valor, duracao };

    const metodo = id ? "PUT" : "POST";
    const url = id
        ? `${apiBaseUrl}/servicos/${id}`
        : `${apiBaseUrl}/servicos`;

    const response = await fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(servico)
    });

    if (!response.ok) {
        alert("Erro ao salvar serviço");
        return;
    }

    limparFormulario();
    listarServicos();
}

async function editarServico(id) {
    const response = await fetch(`${apiBaseUrl}/servicos/${id}`);
    const servico = await response.json();

    document.getElementById("serviceId").value = servico.id;
    document.getElementById("nome").value = servico.nome;
    document.getElementById("valor").value = servico.valor;
    document.getElementById("duracao").value = servico.duracao;
}

async function excluirServico(id) {
    if (!confirm("Excluir este serviço?")) return;

    await fetch(`${apiBaseUrl}/servicos/${id}`, { method: "DELETE" });

    listarServicos();
}

function limparFormulario() {
    document.getElementById("serviceId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("valor").value = "";
    document.getElementById("duracao").value = "";
}

document.getElementById("serviceForm").addEventListener("submit", e => {
    e.preventDefault();
    salvarServico();
});

document.getElementById("btnLimpar").addEventListener("click", limparFormulario);

window.onload = () => listarServicos();
