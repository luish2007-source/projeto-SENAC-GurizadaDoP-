'use strict'

async function loadGallery() {
  const gallery = document.getElementById('gallery')
  const url = 'https://api.thecatapi.com/v1/images/search?limit=9'

  try {
    const response = await fetch(url)
    const cats = await response.json()

    gallery.innerHTML = ''

    cats.forEach(cat => {
      const img = document.createElement('img')
      img.src = cat.url
      img.alt = 'Gato fofo'
      gallery.appendChild(img)
    })
  } catch (error) {
    console.error('Erro ao carregar imagens:', error)
  }
}

loadGallery()