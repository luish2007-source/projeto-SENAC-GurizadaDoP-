/* js/api.js */
const API_BASE = '/api'; // ajuste se seu backend estiver em outro caminho

/* ===== Helpers REST ===== */
export async function getJSON(url) {
    const r = await fetch(joinUrl(url), {
        headers: authHeader()
    });
    if (!r.ok) throw new Error(`GET ${url} -> ${r.status}`);
    return r.json();
}
export async function postJSON(url, body) {
    const r = await fetch(joinUrl(url), {
        method: 'POST',
        headers: Object.assign({ 'Content-Type': 'application/json' }, authHeader()),
        body: JSON.stringify(body)
    });
    if (!r.ok) throw new Error(await r.text());
    return r.json();
}
export async function putJSON(url, body) {
    const r = await fetch(joinUrl(url), {
        method: 'PUT',
        headers: Object.assign({ 'Content-Type': 'application/json' }, authHeader()),
        body: JSON.stringify(body)
    });
    if (!r.ok) throw new Error(await r.text());
    return r.json();
}
export async function del(url) {
    const r = await fetch(joinUrl(url), {
        method: 'DELETE',
        headers: authHeader()
    });
    if (!r.ok) throw new Error(await r.text());
}

/* ===== util ===== */
function joinUrl(url) {
    // if absolute (starts with http or /) return as is, else join with API_BASE
    if (/^(https?:)?\/\//.test(url) || url.startsWith('/')) return url;
    return `${API_BASE}/${url}`;
}

function authHeader() {
    const raw = localStorage.getItem('admin');
    if (!raw) return {};
    try {
        const obj = JSON.parse(raw);
        if (obj && obj.token) return { 'Authorization': `Bearer ${obj.token}` };
    } catch { /* ignore */ }
    return {};
}

/* ===== Auth simples ===== */
export function getUser() {
    try { return JSON.parse(localStorage.getItem('admin') || 'null'); }
    catch { return null; }
}

export function requireAuth(redirectTo = '/login.html') {
    if (!getUser()) {
        location.href = redirectTo;
    }
}

export function logout() {
    localStorage.removeItem('admin');
}

/* ===== Navbar parcial ===== */
export async function loadNavbar() {
    const host = document.getElementById('navbar');
    if (!host) return;

    // fetch partial (caminho: /partials/navbar.html)
    const html = await fetch('/partials/navbar.html').then(r => r.text());
    host.innerHTML = html;

    // ativa link atual (baseado em data-route)
    const file = location.pathname.split('/').pop() || 'index.html';
    const route = file.replace('.html', '');
    const active = host.querySelector(`a.nav-link[data-route="${route}"]`);
    if (active) {
        // remove active de outros (caso parcial reutilizada)
        host.querySelectorAll('a.nav-link.active').forEach(a => a.classList.remove('active'));
        active.classList.add('active');
    }

    // Info usuário + logout
    const u = getUser();
    const userInfo = host.querySelector('#userInfo');
    if (u && userInfo) userInfo.textContent = u.nome || '';

    const btnLogout = host.querySelector('#btnLogout');
    if (btnLogout) {
        btnLogout.addEventListener('click', () => {
            if (confirm('Deseja realmente sair?')) {
                logout();
                location.href = '/login.html';
            }
        });
    }
}

/* ===== Boot padrão páginas internas ===== */
/*
  Observação: você disse que não quer forçar proteção nas páginas.
  Portanto bootPage NÃO chama requireAuth automaticamente. Se quiser proteger
  alguma página, chame requireAuth() no script dessa página.
*/
export async function bootPage() {
    await loadNavbar();
}
