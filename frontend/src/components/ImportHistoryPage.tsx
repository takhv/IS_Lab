import React, { useState, useEffect } from 'react';
import { Container, Table, Spinner, Alert } from 'react-bootstrap';
import { humanBeingApi } from '../services/api';
import { ImportJSON } from '../types/types';

const ImportHistoryPage: React.FC = () => {
  const [history, setHistory] = useState<ImportJSON[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchHistory();
  }, []);

  const fetchHistory = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await humanBeingApi.getImportHistory();
      setHistory(response.data);
    } catch (error) {
      console.error('Error fetching import history:', error);
      setError('Ошибка при загрузке истории импорта.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center" style={{ height: '200px' }}>
        <Spinner animation="border" />
      </Container>
    );
  }

  if (error) {
    return (
      <Container>
        <Alert variant="danger">{error}</Alert>
      </Container>
    );
  }

  return (
    <Container>
      <h2>История импорта</h2>
      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>ID</th>
            <th>Статус</th>
            <th>Время импорта</th>
            <th>Объектов добавлено</th>
          </tr>
        </thead>
        <tbody>
          {history.length > 0 ? (
            history.map(op => (
              <tr key={op.id}>
                <td>{op.id}</td>
                <td>{op.status}</td>
                <td>{new Date(op.importTime).toLocaleString()}</td>
                <td>{op.objectsCount ?? '-'}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={5} className="text-center">История импорта пуста.</td>
            </tr>
          )}
        </tbody>
      </Table>
    </Container>
  );
};

export default ImportHistoryPage;