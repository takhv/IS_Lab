import React, { useState } from 'react';
import './App.css';
import HumanBeingTable from './components/HumanBeingTable';
import { HumanBeing } from './types/types'; // Убедитесь, что интерфейс импортирован

function App() {
  const [editingHero, setEditingHero] = useState<HumanBeing | null>(null);

  const handleEdit = (hero: HumanBeing) => {
    setEditingHero(hero);
    // Здесь вы можете, например, переключить видимость формы редактирования
    console.log('Editing hero:', hero);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Human Being System</h1>
      </header>
      <main>
        {/* Передайте функцию handleEdit как пропс onEdit */}
        <HumanBeingTable onEdit={handleEdit} />
        
        {/* Пример: показать форму редактирования, если выбран герой */}
        {editingHero && (
          <div>
            <h3>Editing: {editingHero.name}</h3>
            {/* Ваша форма редактирования здесь */}
            <button onClick={() => setEditingHero(null)}>Cancel</button>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;