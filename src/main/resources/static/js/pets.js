/* js/pets.js */
import { getJSON, postJSON, putJSON, del } from './api.js';

const form = document.getElementById('petForm');
const tbl = document.querySelector('#tblPets tbody');
const btnLimpar = document.getElementById('btnLimpar');

loadPets();

/* ===== Carregar lista ===== */
async function loadPets(page = 0) {
    const data = await getJSON(`/api/pets?page=${page}`);

    const pets = data.content; // <<--- AQUI ESTÁ O SEGREDO!!!
    tbl.innerHTML = '';

    pets.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${p.id}</td>
            <td>${p.nome}</td>
            <td>${p.especie}</td>
            <td>${p.raca ?? ''}</td>
            <td>${p.tutorNome ?? ''}</td>

            <td class="text-end">
                <button class="btn btn-sm btn-warning me-2" data-edit="${p.id}">Editar</button>
                <button class="btn btn-sm btn-danger" data-del="${p.id}">Excluir</button>
            </td>
        `;
        tbl.appendChild(tr);
    });

    bindActions();
}

/* ===== Eventos de cada botão ===== */
function bindActions() {
    document.querySelectorAll('[data-edit]').forEach(btn => {
        btn.onclick = () => editPet(btn.dataset.edit);
    });

    document.querySelectorAll('[data-del]').forEach(btn => {
        btn.onclick = () => removePet(btn.dataset.del);
    });
}

/* ===== Enviar formulário ===== */
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const data = {
        nome: nome.value.trim(),
        especie: especie.value.trim(),
        raca: raca.value.trim() || null,
        dataNascimento: dataNascimento.value || null
    };

    const id = petId.value;

    if (!id) {
        await postJSON('/api/pets', data);
    } else {
        await putJSON(`/api/pets/${id}`, data);
    }

    form.reset();
    petId.value = '';
    await loadPets();
});

/* ===== Editar ===== */
async function editPet(id) {
    const p = await getJSON(`/api/pets/${id}`);

    petId.value = p.id;
    nome.value = p.nome;
    especie.value = p.especie;
    raca.value = p.raca ?? '';
    dataNascimento.value = p.dataNascimento ?? '';
}

/* ===== Excluir ===== */
async function removePet(id) {
    if (!confirm('Excluir este pet?')) return;
    await del(`/api/pets/${id}`);
    loadPets();
}

/* ===== Limpar ===== */
btnLimpar.addEventListener('click', () => {
    form.reset();
    petId.value = '';
});
