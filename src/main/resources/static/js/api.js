// Caminho: src/main/resources/static/js/api.js

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Lida com a resposta da fetch API.
 * @param {Response} response - O objeto de resposta da fetch.
 * @returns {Promise<any>} - O JSON da resposta.
 * @throws {Error} - Lança um erro se a resposta não for OK.
 */
async function handleResponse(response) {
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        const errorMessage = errorData.message || `Erro ${response.status}: ${response.statusText}`;
        throw new Error(errorMessage);
    }
    // Retorna JSON ou nada se for 204 No Content (ex: delete)
    if (response.status === 204) {
        return null;
    }
    return response.json();
}

/**
 * Função genérica para requisições 'fetch'.
 * @param {string} endpoint - O caminho da API (ex: '/pets')
 * @param {RequestInit} options - As opções da fetch (method, headers, body)
 * @returns {Promise<any>}
 */
async function request(endpoint, options) {
    const url = `${API_BASE_URL}${endpoint}`;
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const config = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options?.headers,
        },
    };

    try {
        const response = await fetch(url, config);
        return await handleResponse(response);
    } catch (error) {
        console.error(`Falha na requisição para ${endpoint}:`, error);
        throw error; // Re-lança o erro para o chamador (o .js da página)
    }
}

// Expõe o objeto API globalmente
window.API = {
    // --- Funções de Tutor ---
    listTutores: (pageable) => {
        const params = new URLSearchParams(pageable); // ex: page=0&size=10
        return request(`/tutores?${params}`, { method: 'GET' });
    },
    getTutor: (id) => request(`/tutores/${id}`, { method: 'GET' }),
    createTutor: (tutorData) => request('/tutores', { method: 'POST', body: JSON.stringify(tutorData) }),
    updateTutor: (id, tutorData) => request(`/tutores/${id}`, { method: 'PUT', body: JSON.stringify(tutorData) }),
    deleteTutor: (id) => request(`/tutores/${id}`, { method: 'DELETE' }),

    // --- Funções de Pet ---
    listPets: (pageable) => {
        const params = new URLSearchParams(pageable);
        return request(`/pets?${params}`, { method: 'GET' });
    },
    getPet: (id) => request(`/pets/${id}`, { method: 'GET' }),
    createPet: (petData) => request('/pets', { method: 'POST', body: JSON.stringify(petData) }),
    updatePet: (id, petData) => request(`/pets/${id}`, { method: 'PUT', body: JSON.stringify(petData) }),
    deletePet: (id) => request(`/pets/${id}`, { method: 'DELETE' }),

    // --- Funções de Serviço ---
    listServicos: (pageable) => {
        const params = new URLSearchParams(pageable);
        return request(`/servicos?${params}`, { method: 'GET' });
    },
    getServico: (id) => request(`/servicos/${id}`, { method: 'GET' }),
    createServico: (servicoData) => request('/servicos', { method: 'POST', body: JSON.stringify(servicoData) }),
    updateServico: (id, servicoData) => request(`/servicos/${id}`, { method: 'PUT', body: JSON.stringify(servicoData) }),
    deleteServico: (id) => request(`/servicos/${id}`, { method: 'DELETE' }),

    // --- Funções de Atendimento ---
    listAtendimentos: (pageable) => {
        const params = new URLSearchParams(pageable);
        return request(`/atendimentos?${params}`, { method: 'GET' });
    },
    getAtendimento: (id) => request(`/atendimentos/${id}`, { method: 'GET' }),
    createAtendimento: (atendimentoData) => request('/atendimentos', { method: 'POST', body: JSON.stringify(atendimentoData) }),
    updateAtendimento: (id, atendimentoData) => request(`/atendimentos/${id}`, { method: 'PUT', body: JSON.stringify(atendimentoData) }),
    deleteAtendimento: (id) => request(`/atendimentos/${id}`, { method: 'DELETE' }),
};