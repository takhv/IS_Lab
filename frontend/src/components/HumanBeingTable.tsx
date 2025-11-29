import React, { useState, useEffect } from 'react';
import { Table, Button, Spinner, Container, Pagination } from 'react-bootstrap';
import { humanBeingApi } from '../services/api';
import { HumanBeing } from '../types/types';
import HumanBeingModal from './HumanBeingModal';

interface HumanBeingTableProps {
  onEdit: (hero: HumanBeing) => void;
  onAdd: () => void;
}

const HumanBeingTable: React.FC<HumanBeingTableProps> = () => {
  const [heroes, setHeroes] = useState<HumanBeing[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [showModal, setShowModal] = useState(false);
  const [editingHero, setEditingHero] = useState<HumanBeing | null>(null);

  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [pageSize, setPageSize] = useState(10);

  useEffect(() => {
    fetchHeroes();
  }, [currentPage, pageSize]);

  const fetchHeroes = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const response = await humanBeingApi.getAll(currentPage, pageSize);
      
      if (Array.isArray(response.data.content)) {
        setHeroes(response.data.content);
        setTotalPages(response.data.totalPages);
      } else {
        console.error('API returned non-array data:', response.data);
        setError('API returned invalid data format');
        setHeroes([]);
      }
    } catch (error) {
      console.error('Error fetching heroes:', error);
      setError('Failed to load heroes');
      setHeroes([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setEditingHero(null);
    setShowModal(true);
  };

  const handleEdit = (hero: HumanBeing) => {
    setEditingHero(hero);
    setShowModal(true);
  }

  const handleDelete = async (id: number) => {
    try {
      await humanBeingApi.delete(id);
      fetchHeroes();
    } catch (error) {
      console.error('Error deleting hero:', error);
      alert('Failed to delete hero');
    }
  };

  const handleSave = async (formData: any) => {
    try {
      if (editingHero) {
        await humanBeingApi.update(formData);
      } else {
        await humanBeingApi.create(formData);
      }
      fetchHeroes();
    } catch (error) {
      console.error('Error saving hero:', error);
      alert('Failed to save hero');
    }
  };

  const handlePageChange = (pageNumber: number) => {
    if (pageNumber >= 0 && pageNumber < totalPages) {
      setCurrentPage(pageNumber);
    }
  };

  const renderPagination = () => {
    if (totalPages <= 1) return null;

    const items = [];
    const maxVisiblePages = 5;
    let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(0, endPage - maxVisiblePages + 1);
    }

    items.push(
      <Pagination.Prev
        key="prev"
        onClick={() => handlePageChange(currentPage - 1)}
        disabled={currentPage === 0}
      />
    );

    for (let number = startPage; number <= endPage; number++) {
      items.push(
        <Pagination.Item
          key={number}
          active={number === currentPage}
          onClick={() => handlePageChange(number)}
        >
          {number + 1}
        </Pagination.Item>
      );
    }

    items.push(
      <Pagination.Next
        key="next"
        onClick={() => handlePageChange(currentPage + 1)}
        disabled={currentPage === totalPages - 1}
      />
    );

    return <Pagination>{items}</Pagination>;
  };

  if (loading) {
    return <Spinner animation="border" />;
  }

  if (error) {
    return <div className="alert alert-danger">Error: {error}</div>;
  }

  return (
    <Container>
      <Table striped bordered hover responsive>
        <thead>
          <tr>
              <th>id</th>
              <th>name</th>
              <th>coords</th>
              <th>creation_date</th>
              <th>real hero</th>
              <th>tooth pick</th>
              <th>impact speed</th>
              <th>car cool</th>
              <th>car type</th>
              <th>mood</th>
              <th>soundtrack</th>
              <th>weapon type</th>
              <th>deystviya</th>
          </tr>
        </thead>
        <tbody>
          {heroes.map(hero => (
            <tr key={hero.id}>
              <td>{hero.id}</td>
              <td>{hero.name}</td>
              <td>{hero.x}, {hero.y}</td>
              <td>{hero.creationDate}</td>
              <td>{hero.realHero ? 'Да' : 'Нет'}</td>
              <td>{hero.hasToothpick ? 'Да' : 'Нет'}</td>
              <td>{hero.impactSpeed}</td>
              <td>{hero.carCool ? 'Да' : 'Нет'}</td>
              <td>{hero.carType != null ? hero.carType : '-'}</td>
              <td>{hero.mood}</td>
              <td>{hero.soundtrackName}</td>
              <td>{hero.weaponType}</td>
              <td>
                <Button variant="warning" size="sm" onClick={() => handleEdit(hero)}>Изменить</Button>{' '}
                <Button variant="danger" size="sm" onClick={() => handleDelete(hero.id)}>Удалить</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Button variant="primary" onClick={handleAdd}>
        Добавить персонажа
      </Button>
      {renderPagination()}
      <HumanBeingModal
        show={showModal}
        onHide={() => setShowModal(false)}
        hero={editingHero}
        onSave={handleSave}
        mode='create'
      />
    </Container>
  );
};

export default HumanBeingTable;