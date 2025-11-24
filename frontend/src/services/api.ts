import axios from 'axios';
import { HumanBeing, HumanBeingCreate, HumanBeingUpdate } from '../types/types';

const API_BASE_URL = 'http://localhost:3835/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

export const humanBeingApi = {
  getAll: (page: number = 0, size: number = 10) => 
    api.get<{ content: HumanBeing[]; totalElements: number; totalPages: number; number: number }>(
      `/human-being?page=${page}&size=${size}`),
  getById: (id: number) => api.get<HumanBeing>(`/human-being/${id}`),
  create: (data: HumanBeingCreate) => api.post<HumanBeing>('/human-being', data),
  update: (data: HumanBeingUpdate) => api.put<HumanBeing>('/human-being', data),
  delete: (id: number) => api.delete(`/human-being/${id}`),
  countWeaponTypeGreaterThan: (weaponType: string) => api.get<number>(`/human-being/weaponType?weaponType=${weaponType}`),
  soundtrackNameLessThan: (soundtrackName: string) => api.get<HumanBeing[]>(`/human-being/soundtrack?soundtrackName=${soundtrackName}`),
  uniqWeapons: () => api.get<string[]>(`/human-being/uniqWeapons`),
  sadHuman: () => api.put<void>(`/human-being/sad`),
  ladaKalina: () => api.put<void>(`/human-being/lada`),
};
