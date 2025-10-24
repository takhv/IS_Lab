let currentPage = 0;

document.addEventListener('DOMContentLoaded', function() {
    loadHeroes(0);
    
    document.getElementById('createHeroForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const heroData = {
            name: document.getElementById('name').value,
            x: parseInt(document.getElementById('coordinateX').value),
            y: parseFloat(document.getElementById('coordinateY').value),
            realHero: document.getElementById('realHero').checked,
            hasToothpick: document.getElementById('hasToothpick').checked,
            carCool: document.getElementById('carCool').checked,
            carType: document.getElementById('carType').value,
            mood: document.getElementById('mood').value,
            impactSpeed: parseInt(document.getElementById('impactSpeed').value),
            soundtrackName: document.getElementById('soundtrackName').value,
            weaponType: document.getElementById('weaponType').value
        };
        
        fetch('/api/human-being', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(heroData)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Герой создан:', data);
            loadHeroes(currentPage);
            document.getElementById('createHeroForm').reset();
        })
        .catch(error => console.error('Ошибка:', error));
    });
});

function loadHeroes(page) {
    fetch(`/api/human-being?page=${page}`)
        .then(response => response.json())
        .then(heroes => {
            const tbody = document.querySelector('#heroesTable tbody');
            tbody.innerHTML = '';
            renderPagination(heroes);

            if (heroes.empty == true){
                tbody.innerHTML = `
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                    <td>-</td>
                `;
            } else {
                heroes.content.forEach(hero => {
                    const row = tbody.insertRow();
                    row.innerHTML = `
                        <td>${hero.id}</td>
                        <td>${hero.name}</td>
                        <td>${hero.x}, ${hero.y}</td>
                        <td>${hero.creationDate}</td>
                        <td>${hero.realHero ? 'Да' : 'Нет'}</td>
                        <td>${hero.hasToothpick}</td>
                        <td>${hero.impactSpeed}</td>
                        <td>${hero.carCool}</td>
                        <td>${hero.carType}</td>
                        <td>${hero.mood}</td>
                        <td>${hero.soundtrackName}</td>
                        <td>${hero.weaponType}</td>
                    `;
                });
            }
        })
        .catch(error => console.error('Ошибка загрузки:', error));
}

function renderPagination(pageData) {
    const paginationDiv = document.getElementById('pagination');
    paginationDiv.innerHTML = '';

    currentPage = pageData.number;
    const totalPages = pageData.totalPages;

  // Кнопка "Назад"
    if (currentPage > 0) {
        const prevBtn = document.createElement('button');
        prevBtn.textContent = '← Назад';
        prevBtn.addEventListener('click', () => loadHeroes(currentPage - 1));
        paginationDiv.appendChild(prevBtn);
    }

  // Кнопки страниц
    const maxVisible = Math.min(totalPages, 10);
    for (let i = 0; i < maxVisible; i++) {
        const btn = document.createElement('button');
        btn.textContent = i + 1;
        if (i === currentPage) btn.style.fontWeight = 'bold';
        btn.addEventListener('click', () => loadHeroes(i));
        paginationDiv.appendChild(btn);
    }

  // Кнопка "Вперёд"
    if (currentPage < totalPages - 1) {
        const nextBtn = document.createElement('button');
        nextBtn.textContent = 'Вперёд →';
        nextBtn.addEventListener('click', () => loadHeroes(currentPage + 1));
        paginationDiv.appendChild(nextBtn);
    }
}

function deleteHero(id) {
    if (confirm('Удалить героя?')) {
        fetch(`/api/human-being/${id}`, {
            method: 'DELETE'
        })
        .then(() => loadHeroes(currentPage))
        .catch(error => console.error('Ошибка:', error));
    }
}

function editHero(id) {
    alert('Редактирование героя с ID: ' + id);
}
