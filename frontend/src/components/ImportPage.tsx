import React, { useState } from 'react';
import { Container, Form, Button, Alert, Spinner } from 'react-bootstrap';
import { humanBeingApi } from '../services/api';

const ImportPage: React.FC = () => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<{ success: boolean; message: string } | null>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setSelectedFile(e.target.files[0]);
      setResult(null);
    }
  };

  const handleImport = async () => {
    if (!selectedFile) {
      setResult({ success: false, message: 'Пожалуйста, выберите файл для импорта.' });
      return;
    }

    setLoading(true);
    setResult(null);

    try {
      const response = await humanBeingApi.importFromJson(selectedFile);
      setResult({
        success: true,
        message: `Импорт успешно завершён! ID операции: ${response.data.id}, статус: ${response.data.status}, объектов добавлено: ${response.data.objectsCount || 0}`
      });
    } catch (error) {
      console.error('Error importing file:', error);
      setResult({
        success: false,
        message: `Ошибка при импорте: ${(error as Error).message || 'Неизвестная ошибка'}`
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container>
      <h2>Импорт JSON</h2>
      <Form>
        <Form.Group controlId="importFile">
          <Form.Label>Выберите JSON файл:</Form.Label>
          <Form.Control
            type="file"
            accept=".json"
            onChange={handleFileChange}
          />
        </Form.Group>
        <Button
          variant="primary"
          onClick={handleImport}
          disabled={!selectedFile || loading}
          className="mt-3"
        >
          {loading ? (
            <>
              <Spinner as="span" animation="border" size="sm" role="status" />
              Загрузка...
            </>
          ) : (
            'Импортировать'
          )}
        </Button>
      </Form>

      {result && (
        <Alert variant={result.success ? "success" : "danger"} className="mt-3">
          {result.message}
        </Alert>
      )}
    </Container>
  );
};

export default ImportPage;