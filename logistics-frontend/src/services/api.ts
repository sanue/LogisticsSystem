import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 响应拦截器
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalPages: number;
  totalElements: number;
  hasNext: boolean;
  hasPrevious: boolean;
  isFirst: boolean;
  isLast: boolean;
}

export interface Product {
  productId: string;
  productName: string;
  specification?: string;
  unit: string;
  safetyStock: number;
  createdAt: string;
  updatedAt: string;
}

export interface Customer {
  customerId: string;
  customerName: string;
  address: string;
  phone: string;
  createdAt: string;
  updatedAt: string;
}

export interface Location {
  locationId: string;
  warehouseCode: string;
  zone: string;
  rack: string;
  levelNo: string;
  position: string;
  maxCapacity: number;
  createdAt: string;
}

// 商品API
export const productApi = {
  getAll: (): Promise<ApiResponse<Product[]>> => api.get('/products').then(res => res.data),
  getWithPagination: (page: number = 0, size: number = 10, sortBy: string = 'createdAt', sortDir: string = 'DESC', keyword?: string): Promise<ApiResponse<PageResponse<Product>>> => {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
      sortBy,
      sortDir
    });
    if (keyword) {
      params.append('keyword', keyword);
    }
    return api.get(`/products?${params.toString()}`).then(res => res.data);
  },
  getById: (id: string): Promise<ApiResponse<Product>> => api.get(`/products/${id}`).then(res => res.data),
  search: (name: string): Promise<ApiResponse<Product[]>> => api.get(`/products/search?name=${encodeURIComponent(name)}`).then(res => res.data),
  create: (product: Omit<Product, 'createdAt' | 'updatedAt'>): Promise<ApiResponse<Product>> => api.post('/products', product).then(res => res.data),
  update: (id: string, product: Omit<Product, 'productId' | 'createdAt' | 'updatedAt'>): Promise<ApiResponse<Product>> => api.put(`/products/${id}`, product).then(res => res.data),
  delete: (id: string): Promise<ApiResponse<null>> => api.delete(`/products/${id}`).then(res => res.data),
};

// 客户API
export const customerApi = {
  getAll: (): Promise<ApiResponse<Customer[]>> => api.get('/customers').then(res => res.data),
  getById: (id: string): Promise<ApiResponse<Customer>> => api.get(`/customers/${id}`).then(res => res.data),
  search: (name: string): Promise<ApiResponse<Customer[]>> => api.get(`/customers/search?name=${encodeURIComponent(name)}`).then(res => res.data),
  create: (customer: Omit<Customer, 'createdAt' | 'updatedAt'>): Promise<ApiResponse<Customer>> => api.post('/customers', customer).then(res => res.data),
  update: (id: string, customer: Omit<Customer, 'customerId' | 'createdAt' | 'updatedAt'>): Promise<ApiResponse<Customer>> => api.put(`/customers/${id}`, customer).then(res => res.data),
  delete: (id: string): Promise<ApiResponse<null>> => api.delete(`/customers/${id}`).then(res => res.data),
};

// 位置API
export const locationApi = {
  getAll: (): Promise<ApiResponse<Location[]>> => api.get('/locations').then(res => res.data),
  getById: (id: string): Promise<ApiResponse<Location>> => api.get(`/locations/${id}`).then(res => res.data),
  search: (warehouseCode: string): Promise<ApiResponse<Location[]>> => api.get(`/locations/search?warehouseCode=${encodeURIComponent(warehouseCode)}`).then(res => res.data),
  create: (location: Omit<Location, 'createdAt'>): Promise<ApiResponse<Location>> => api.post('/locations', location).then(res => res.data),
  update: (id: string, location: Omit<Location, 'locationId' | 'createdAt'>): Promise<ApiResponse<Location>> => api.put(`/locations/${id}`, location).then(res => res.data),
  delete: (id: string): Promise<ApiResponse<null>> => api.delete(`/locations/${id}`).then(res => res.data),
};

export default api;
