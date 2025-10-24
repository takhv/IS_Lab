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
};
