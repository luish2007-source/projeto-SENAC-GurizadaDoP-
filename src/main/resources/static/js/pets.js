const apiBaseUrl = "http://localhost:8080/api";

window.carregarTutores = carregarTutores;
window.listarPets = listarPets;
window.editarPet = editarPet;
window.excluirPet = excluirPet;

// ------------------------------
// CARREGAR TUTORES
// ------------------------------
async function carregarTutores() {
    const response = await fetch(`${apiBaseUrl}/tutores?page=0&size=50`);
    const data = await response.json();

    const select = document.getElementById("tutorId");
    select.innerHTML = "<option value=''>Selecione</option>";

    data.content.forEach(tutor => {
        const option = document.createElement("option");
        option.value = tutor.id;
        option.textContent = tutor.nome;
        select.appendChild(option);
    });
}

// ------------------------------
// LISTAR PETS
// ------------------------------
async function listarPets() {
    const response = await fetch(`${apiBaseUrl}/pets?page=0&size=50`);
    const data = await response.json();

    const tbody = document.querySelector("#tblPets tbody");
    tbody.innerHTML = "";

    data.content.forEach(pet => {
        const tr = document.createElement("tr");
        const nomeTutor = pet.tutor ? pet.tutor.nome : "—";

        tr.innerHTML = `
            <td>${pet.id}</td>
            <td>${pet.nome}</td>
            <td>${pet.especie}</td>
            <td>${pet.raca || ""}</td>
            <td>${nomeTutor}</td>
            <td class="text-end">
                <button class="btn btn-sm btn-warning" onclick="editarPet(${pet.id})">Editar</button>
                <button class="btn btn-sm btn-danger" onclick="excluirPet(${pet.id})">Excluir</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

// ------------------------------
// SALVAR PET
// ------------------------------
async function salvarPet(e) {
    e.preventDefault(); // impedir reload do form

    const id = document.getElementById("petId").value;
    const nome = document.getElementById("nome").value;
    const especie = document.getElementById("especie").value;
    const raca = document.getElementById("raca").value;
    const dataNascimento = document.getElementById("dataNascimento").value;
    const tutorId = document.getElementById("tutorId").value;

    const pet = {
        nome,
        especie,
        raca,
        dataNascimento,
        tutorId: Number(tutorId)
    };

    const metodo = id ? "PUT" : "POST";
    const url = id ? `${apiBaseUrl}/pets/${id}` : `${apiBaseUrl}/pets`;

    const response = await fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pet)
    });

    if (!response.ok) {
        alert("Erro ao salvar pet");
        return;
    }

    limparFormulario();
    listarPets();
}

// ------------------------------
// EDITAR
// ------------------------------
async function editarPet(id) {
    const response = await fetch(`${apiBaseUrl}/pets/${id}`);
    const pet = await response.json();

    document.getElementById("petId").value = pet.id;
    document.getElementById("nome").value = pet.nome;
    document.getElementById("especie").value = pet.especie;
    document.getElementById("raca").value = pet.raca;
    document.getElementById("dataNascimento").value = pet.dataNascimento;
    document.getElementById("tutorId").value = pet.tutor.id;
}

// ------------------------------
// EXCLUIR
// ------------------------------
async function excluirPet(id) {
    if (!confirm("Tem certeza que deseja excluir?")) return;

    await fetch(`${apiBaseUrl}/pets/${id}`, { method: "DELETE" });

    listarPets();
}

// ------------------------------
// LIMPAR FORM
// ------------------------------
function limparFormulario() {
    document.getElementById("petId").value = "";
    document.getElementById("nome").value = "";
    document.getElementById("especie").value = "";
    document.getElementById("raca").value = "";
    document.getElementById("dataNascimento").value = "";
    document.getElementById("tutorId").value = "";
}

// ------------------------------
// INICIAR PÁGINA
// ------------------------------
window.onload = () => {
    document.getElementById("petForm").addEventListener("submit", salvarPet);
    document.getElementById("btnLimpar").addEventListener("click", limparFormulario);

    carregarTutores();
    listarPets();
};
