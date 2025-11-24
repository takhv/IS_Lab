import React, { useState } from 'react';
import { Card, Button, Form, Alert, Container, Row, Col } from 'react-bootstrap';
import { humanBeingApi } from '../services/api';

const SpecialOperations: React.FC = () => {
  const [weaponTypeInput, setWeaponTypeInput] = useState<string>('');
  const [soundtrackInput, setSoundtrackInput] = useState<string>('');

  const [resultMessage, setResultMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const clearMessages = () => {
    setError(null);
    setResultMessage(null);
  };

  const handleCountByWeaponType = async () => {
    clearMessages();
    if (!weaponTypeInput) {
      setError('Пожалуйста, введите тип оружия');
      return;
    }
    try {
      const response = await humanBeingApi.countWeaponTypeGreaterThan(weaponTypeInput);
      setResultMessage(`Количество героев с типом оружия больше "${weaponTypeInput}": ${response.data}`);
    } catch (err) {
      console.error('Error counting by weapon type:', err);
      setError('Ошибка при подсчете героев по типу оружия');
    }
  };

  const handleFindBySoundtrack = async () => {
    clearMessages();
    if (!soundtrackInput) {
      setError('Пожалуйста, введите название саундтрека');
      return;
    }
    try {
      const response = await humanBeingApi.soundtrackNameLessThan(soundtrackInput);
      setResultMessage(`Найдено ${response.data.length} героев с саундтреком меньше "${soundtrackInput}".`);
      console.log('Герои:', response.data);
    } catch (err) {
      console.error('Error finding by soundtrack:', err);
      setError('Ошибка при поиске героев по саундтреку');
    }
  };

  const handleGetUniqueWeapons = async () => {
    clearMessages();
    try {
      const response = await humanBeingApi.uniqWeapons();
      setResultMessage(`Уникальные типы оружия: ${response.data.join(', ')}`);
    } catch (err) {
      console.error('Error getting unique weapons:', err);
      setError('Ошибка при получении уникальных типов оружия');
    }
  };

  const handleMakeAllSad = async () => {
    clearMessages();
    try {
      await humanBeingApi.sadHuman();
      setResultMessage('Всем героям установлено максимально печальное настроение.');
    } catch (err) {
      console.error('Error making all sad:', err);
      setError('Ошибка при установке настроения всем героям');
    }
  };

  const handleGiveLadaKalina = async () => {
    clearMessages();
    try {
      await humanBeingApi.ladaKalina();
      setResultMessage('Все герои без машины пересажены на красные "Lada Kalina".');
    } catch (err) {
      console.error('Error giving Lada Kalina:', err);
      setError('Ошибка при пересадке героев на Lada Kalina');
    }
  };

  return (
    <Container fluid className="mt-4">
      <h2>Специальные операции</h2>

      {error && <Alert variant="danger">{error}</Alert>}
      {resultMessage && <Alert variant="info">{resultMessage}</Alert>}

      <Row>
        <Col md={6} className="mb-4">
          <Card>
            <Card.Body>
              <Card.Title>Количество героев с типом оружия больше...</Card.Title>
              <Form>
                <Form.Group controlId="weaponTypeInput">
                  <Form.Label>Тип оружия:</Form.Label>
                  <Form.Control
                    type="text"
                    value={weaponTypeInput}
                    onChange={(e) => setWeaponTypeInput(e.target.value)}
                    placeholder="Введите тип оружия (например, AXE)"
                  />
                </Form.Group>
                <Button variant="primary" onClick={handleCountByWeaponType} className="mt-2">
                  Подсчитать
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>

        <Col md={6} className="mb-4">
          <Card>
            <Card.Body>
              <Card.Title>Найти героев с саундтреком меньше...</Card.Title>
              <Form>
                <Form.Group controlId="soundtrackInput">
                  <Form.Label>Название саундтрека:</Form.Label>
                  <Form.Control
                    type="text"
                    value={soundtrackInput}
                    onChange={(e) => setSoundtrackInput(e.target.value)}
                    placeholder="Введите название саундтрека"
                  />
                </Form.Group>
                <Button variant="primary" onClick={handleFindBySoundtrack} className="mt-2">
                  Найти
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>

        <Col md={4} className="mb-4">
          <Card>
            <Card.Body>
              <Card.Title>Получить уникальные типы оружия</Card.Title>
              <Button variant="secondary" onClick={handleGetUniqueWeapons}>
                Получить
              </Button>
            </Card.Body>
          </Card>
        </Col>

        <Col md={4} className="mb-4">
          <Card>
            <Card.Body>
              <Card.Title>Сделать всех максимально печальными</Card.Title>
              <Button variant="warning" onClick={handleMakeAllSad}>
                Сделать печальными
              </Button>
            </Card.Body>
          </Card>
        </Col>

        <Col md={4} className="mb-4">
          <Card>
            <Card.Body>
              <Card.Title>Пересадить всех без машины на Lada Kalina</Card.Title>
              <Button variant="success" onClick={handleGiveLadaKalina}>
                Пересадить
              </Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default SpecialOperations;
