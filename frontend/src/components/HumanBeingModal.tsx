import React, { useState, useEffect } from 'react';
import { Modal, Form, Button } from 'react-bootstrap';
import { HumanBeing, HumanBeingCreate, HumanBeingUpdate } from '../types/types';

interface HumanBeingModalProps {
  show: boolean;
  onHide: () => void;
  hero?: HumanBeing | null;
  onSave: (hero: HumanBeingCreate | HumanBeingUpdate) => void;
  mode: 'create' | 'edit';
}

const HumanBeingModal: React.FC<HumanBeingModalProps> = ({ show, onHide, hero, onSave }) => {
  const isEditing = !!hero;

  const [formData, setFormData] = useState<HumanBeingCreate | HumanBeingUpdate>({
    id: hero?.id || 0,
    name: hero?.name || '',
    x: hero?.x || 0,
    y: hero?.y || 0,
    realHero: hero?.realHero || false,
    hasToothpick: hero?.hasToothpick ?? false,  
    carCool: hero?.carCool ?? false,
    carType: hero?.carType || '',
    mood: hero?.mood || '',
    impactSpeed: hero?.impactSpeed || 0,
    soundtrackName: hero?.soundtrackName || '',
    weaponType: hero?.weaponType || ''
  });

  useEffect(() => {
    if (isEditing && hero) {
      setFormData({
        id: hero.id,
        name: hero.name,
        x: hero.x,
        y: hero.y,
        realHero: hero.realHero,
        hasToothpick: hero.hasToothpick ?? false,
        carCool: hero.carCool ?? false,
        carType: hero.carType || '',
        mood: hero.mood || '',
        impactSpeed: hero.impactSpeed,
        soundtrackName: hero.soundtrackName,
        weaponType: hero.weaponType
      });
    } else if (!isEditing) {
      setFormData({
        id: 0,
        name: '',
        x: 0,
        y: 0,
        realHero: false,
        hasToothpick: undefined,
        carCool: false,
        carType: '',
        mood: '',
        impactSpeed: 0,
        soundtrackName: '',
        weaponType: ''
      });
    }
  }, [isEditing, hero]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value, type } = e.target;
    const val = type === 'checkbox' ? (e.target as HTMLInputElement).checked : value;

    setFormData(prev => ({
      ...prev,
      [name]: type === 'number' ? Number(val) : val
    }));
  };

  const handleSave = () => {
    onSave(formData);
    onHide();
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>{isEditing ? 'изменить персонажа' : 'создать персонажа'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="name">
            <Form.Label>имя *</Form.Label>
            <Form.Control
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="x">
            <Form.Label>координата X *</Form.Label>
            <Form.Control
              type="number"
              name="x"
              value={formData.x}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="y">
            <Form.Label>координата Y *</Form.Label>
            <Form.Control
              type="number"
              name="y"
              value={formData.y}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="realHero">
            <Form.Check
              type="checkbox"
              name="realHero"
              label="реальный герой"
              checked={formData.realHero}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="hasToothpick">
            <Form.Check
              type="checkbox"
              name="hasToothpick"
              label="зубочистка"
              checked={formData.hasToothpick === true}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="carCool">
            <Form.Check
              type="checkbox"
              name="carCool"
              label="машина крутая"
              checked={formData.carCool === true}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="carType">
            <Form.Label>машина</Form.Label>
            <Form.Control
              type="text"
              name="carType"
              value={formData.carType}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="mood">
            <Form.Label>настроение *</Form.Label>
            <Form.Select
              name="mood"
              value={formData.mood}
              onChange={handleChange}
              required
            >
              <option value="">выберите настроение</option>
              <option value="LONGING">LONGING</option>
              <option value="APATHY">APATHY</option>
              <option value="CALM">CALM</option>
            </Form.Select>
          </Form.Group>

          <Form.Group controlId="impactSpeed">
            <Form.Label>скорость *</Form.Label>
            <Form.Control
              type="number"
              name="impactSpeed"
              value={formData.impactSpeed}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="soundtrackName">
            <Form.Label>название трека *</Form.Label>
            <Form.Control
              type="text"
              name="soundtrackName"
              value={formData.soundtrackName}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="weaponType">
            <Form.Label>Тип оружия *</Form.Label>
            <Form.Select
              name="weaponType"
              value={formData.weaponType}
              onChange={handleChange}
              required
            >
              <option value="">Выберите тип оружия</option>
              <option value="HAMMER">HAMMER</option>
              <option value="AXE">AXE</option>
              <option value="PISTOL">PISTOL</option>
              <option value="RIFLE">RIFLE</option>
              <option value="BAT">BAT</option>
            </Form.Select>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>закрыть</Button>
        <Button variant="primary" onClick={handleSave}>сохранить</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default HumanBeingModal;