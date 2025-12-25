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

  const handleDownload = async (id: number) => { // или number, в зависимости от типа ID
    try {
      // humanBeingApi.downloadImportFile возвращает AxiosResponse с blob
      const response = await humanBeingApi.downloadFile(id);
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      // Попробуйте использовать оригинальное имя файла из Content-Disposition, если возможно
      // или сгенерировать имя на основе ID
      link.setAttribute('download', `import_${id}_file.json`);
      document.body.appendChild(link);
      link.click();
      link.remove(); // Удалить элемент после клика
      window.URL.revokeObjectURL(url); // Освободить память
    } catch (error) {
      console.error('Error downloading file:', error);
      alert('Ошибка при скачивании файла.');
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Вы уверены, что хотите удалить этот файл?')) {
      try {
        await humanBeingApi.deleteFile(id); // Нужно будет добавить в api.ts
        // Обновить историю после удаления
        fetchHistory();
      } catch (error) {
        console.error('Error deleting file:', error);
        alert('Ошибка при удалении файла.');
      }
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
            <th>Файл</th>
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
                <td>
                {op.fileName ? (
                  <button
                    className="btn btn-primary btn-sm me-2"
                    onClick={() => handleDownload(op.id)}
                  >
                    Скачать
                  </button>
                ) : (
                  <span>Нет файла</span>
                )}
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDelete(op.id)}
                >
                  Удалить
                </button>
              </td>
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