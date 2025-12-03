import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Nav, Navbar } from 'react-bootstrap';
import HumanBeingTable from './components/HumanBeingTable';
import SpecialOperations from './components/SpecialOperations';
import HumanBeingModal from './components/HumanBeingModal';
import ImportPage from './components/ImportPage';
import ImportHistoryPage from './components/ImportHistoryPage';
import { HumanBeing, HumanBeingCreate, HumanBeingUpdate } from './types/types';

function App() {
  const [activeTab, setActiveTab] = useState<'table' | 'special' | 'import' | 'import-history'>('table');
  const [showModal, setShowModal] = useState(false);
  const [editingHero, setEditingHero] = useState<HumanBeing | null>(null);
  const [modalMode, setModalMode] = useState<'create' | 'edit'>('create');

  const handleAdd = () => {
    setEditingHero(null);
    setModalMode('create');
    setShowModal(true);
  };

  const handleEdit = (hero: HumanBeing) => {
    setEditingHero(hero);
    setModalMode('edit');
    setShowModal(true);
  };

  const handleSave = async (formData: HumanBeingCreate | HumanBeingUpdate) => {
    try {
      if (modalMode === 'edit' && 'id' in formData) {
        console.log('Updating hero:', formData);
      } else {
        console.log('Creating hero:', formData);
      }
      setShowModal(false);
    } catch (error) {
      console.error('Error saving hero:', error);
      alert('Failed to save hero');
    }
  };

  return (
    <div className="App">
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="#home">Human Being System</Navbar.Brand>
          <Nav activeKey={activeTab} onSelect={(key) => setActiveTab(key as 'table' | 'special')}>
            <Nav.Link eventKey="table">Таблица героев</Nav.Link>
            <Nav.Link eventKey="special">Специальные операции</Nav.Link>
            <Nav.Link eventKey="import">Импорт</Nav.Link>
            <Nav.Link eventKey="import-history">История импорта</Nav.Link>
          </Nav>
        </Container>
      </Navbar>

      <Container fluid>
        {activeTab === 'table' && (
          <HumanBeingTable onEdit={handleEdit} onAdd={handleAdd} />
        )}
        {activeTab === 'special' && <SpecialOperations />}
        {activeTab === 'import' && <ImportPage />}
        {activeTab === 'import-history' && <ImportHistoryPage />} 

        <HumanBeingModal
          show={showModal}
          onHide={() => setShowModal(false)}
          hero={editingHero}
          onSave={handleSave}
          mode={modalMode}
        />
      </Container>
    </div>
  );
}

export default App;