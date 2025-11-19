/* js/login.js */
import { postJSON } from './api.js';

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  if (!form) return;

  form.addEventListener('submit', async (ev) => {
    ev.preventDefault();
    const email = form.querySelector('[name="email"]').value.trim();
    const senha = form.querySelector('[name="senha"]').value.trim();

    try {
      // Ajuste a rota conforme seu backend (por exemplo: /api/login ou /login)
      const res = await postJSON('/login', { email, senha });

      // Espera-se que o backend retorne algo como:
      // { token: "...", nome: "Nome do Admin" }
      if (!res.token) throw new Error('Resposta inválida do servidor');

      localStorage.setItem('admin', JSON.stringify({
        nome: res.nome || email,
        token: res.token
      }));

      // redireciona para index
      location.href = '/index.html';
    } catch (err) {
      alert('Erro no login: ' + (err.message || err));
      console.error(err);
    }
  });
});
