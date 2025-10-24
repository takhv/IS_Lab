import React, { useState, useEffect } from 'react';
import { Table, Button, Spinner } from 'react-bootstrap';
import { humanBeingApi } from '../services/api';
import { HumanBeing } from '../types/types';

interface HumanBeingTableProps {
  onEdit: (hero: HumanBeing) => void;
}

const HumanBeingTable: React.FC<HumanBeingTableProps> = ({ onEdit }) => {
  const [heroes, setHeroes] = useState<HumanBeing[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchHeroes();
  }, []);

  const fetchHeroes = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const response = await humanBeingApi.getAll();
      
      if (Array.isArray(response.data.content)) {
        setHeroes(response.data.content);
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

  const handleDelete = async (id: number) => {
    if (window.confirm('Удалить героя?')) {
      try {
        await humanBeingApi.delete(id);
        fetchHeroes();
      } catch (error) {
        console.error('Error deleting hero:', error);
        alert('Failed to delete hero');
      }
    }
  };

  if (loading) {
    return <Spinner animation="border" />;
  }

  if (error) {
    return <div className="alert alert-danger">Error: {error}</div>;
  }

  if (!Array.isArray(heroes)) {
    return <div>Heroes is not an array</div>;
  }

  return (
    <Table striped bordered hover>
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
              <Button variant="warning" size="sm" onClick={() => onEdit(hero)}>Изменить</Button>{' '}
              <Button variant="danger" size="sm" onClick={() => handleDelete(hero.id)}>Удалить</Button>
            </td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
};

export default HumanBeingTable;