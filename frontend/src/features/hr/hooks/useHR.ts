import { useState } from 'react';
import axios from 'axios';
import toast from 'react-hot-toast';

const HR_API_BASE_URL = process.env.NEXT_PUBLIC_HR_SERVICE_URL || 'http://localhost:8091/hr';

const hrApiClient = axios.create({
  baseURL: HR_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
hrApiClient.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('accessToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export interface Company {
  companyId: number;
  name: string;
  address?: string;
  phone?: string;
  email?: string;
  taxCode?: string;
  status?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface Department {
  departmentId: number;
  companyId: number;
  name: string;
  description?: string;
  status?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface Position {
  positionId: number;
  departmentId: number;
  name: string;
  description?: string;
  status?: string;
  createdAt?: string;
  updatedAt?: string;
}

export const useCompanies = () => {
  const [companies, setCompanies] = useState<Company[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchAll = async () => {
    setLoading(true);
    try {
      console.log('Fetching companies from:', `${HR_API_BASE_URL}/companies`);
      const response = await hrApiClient.get('/companies');
      console.log('Companies API response:', response.data);
      
      // Handle different response structures
      let companiesData = [];
      if (response.data) {
        if (Array.isArray(response.data)) {
          companiesData = response.data;
        } else if (response.data.result && Array.isArray(response.data.result)) {
          companiesData = response.data.result;
        } else if (response.data.data && Array.isArray(response.data.data)) {
          companiesData = response.data.data;
        }
      }
      
      console.log('Parsed companies:', companiesData);
      setCompanies(companiesData);
    } catch (err: any) {
      console.error('Error fetching companies:', err);
      console.error('Error response:', err.response?.data);
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch companies';
      toast.error(errorMessage);
      setCompanies([]); // Set empty array on error
    } finally {
      setLoading(false);
    }
  };

  const create = async (data: Partial<Company>) => {
    setLoading(true);
    try {
      const response = await hrApiClient.post('/companies', data);
      const newCompany = response.data.result || response.data;
      setCompanies(prev => [...prev, newCompany]);
      toast.success(response.data.message || 'Company created successfully');
      return newCompany;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to create company');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const update = async (id: number, data: Partial<Company>) => {
    setLoading(true);
    try {
      const response = await hrApiClient.put(`/companies/${id}`, data);
      const updated = response.data.result || response.data;
      setCompanies(prev => prev.map(c => c.companyId === id ? updated : c));
      toast.success(response.data.message || 'Company updated successfully');
      return updated;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to update company');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const remove = async (id: number) => {
    setLoading(true);
    try {
      await hrApiClient.delete(`/companies/${id}`);
      setCompanies(prev => prev.filter(c => c.companyId !== id));
      toast.success('Company deleted successfully');
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to delete company');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { companies, loading, fetchAll, create, update, remove };
};

export const useDepartments = () => {
  const [departments, setDepartments] = useState<Department[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchAll = async () => {
    setLoading(true);
    try {
      console.log('Fetching departments from:', `${HR_API_BASE_URL}/departments`);
      const response = await hrApiClient.get('/departments');
      console.log('Departments API response:', response.data);
      
      // Handle different response structures
      let departmentsData = [];
      if (response.data) {
        if (Array.isArray(response.data)) {
          departmentsData = response.data;
        } else if (response.data.result && Array.isArray(response.data.result)) {
          departmentsData = response.data.result;
        } else if (response.data.data && Array.isArray(response.data.data)) {
          departmentsData = response.data.data;
        }
      }
      
      console.log('Parsed departments:', departmentsData);
      setDepartments(departmentsData);
    } catch (err: any) {
      console.error('Error fetching departments:', err);
      console.error('Error response:', err.response?.data);
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch departments';
      toast.error(errorMessage);
      setDepartments([]); // Set empty array on error
    } finally {
      setLoading(false);
    }
  };

  const create = async (data: Partial<Department>) => {
    setLoading(true);
    try {
      const response = await hrApiClient.post('/departments', data);
      const newDept = response.data.result || response.data;
      setDepartments(prev => [...prev, newDept]);
      toast.success(response.data.message || 'Department created successfully');
      return newDept;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to create department');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const update = async (id: number, data: Partial<Department>) => {
    setLoading(true);
    try {
      const response = await hrApiClient.put(`/departments/${id}`, data);
      const updated = response.data.result || response.data;
      setDepartments(prev => prev.map(d => d.departmentId === id ? updated : d));
      toast.success(response.data.message || 'Department updated successfully');
      return updated;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to update department');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const remove = async (id: number) => {
    setLoading(true);
    try {
      await hrApiClient.delete(`/departments/${id}`);
      setDepartments(prev => prev.filter(d => d.departmentId !== id));
      toast.success('Department deleted successfully');
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to delete department');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { departments, loading, fetchAll, create, update, remove };
};

export const usePositions = () => {
  const [positions, setPositions] = useState<Position[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchAll = async () => {
    setLoading(true);
    try {
      console.log('Fetching positions from:', `${HR_API_BASE_URL}/positions`);
      const response = await hrApiClient.get('/positions');
      console.log('Positions API response:', response.data);
      
      // Handle different response structures
      let positionsData = [];
      if (response.data) {
        if (Array.isArray(response.data)) {
          positionsData = response.data;
        } else if (response.data.result && Array.isArray(response.data.result)) {
          positionsData = response.data.result;
        } else if (response.data.data && Array.isArray(response.data.data)) {
          positionsData = response.data.data;
        }
      }
      
      console.log('Parsed positions:', positionsData);
      setPositions(positionsData);
    } catch (err: any) {
      console.error('Error fetching positions:', err);
      console.error('Error response:', err.response?.data);
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch positions';
      toast.error(errorMessage);
      setPositions([]); // Set empty array on error
    } finally {
      setLoading(false);
    }
  };

  const create = async (data: Partial<Position>) => {
    setLoading(true);
    try {
      const response = await hrApiClient.post('/positions', data);
      const newPosition = response.data.result || response.data;
      setPositions(prev => [...prev, newPosition]);
      toast.success(response.data.message || 'Position created successfully');
      return newPosition;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to create position');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const update = async (id: number, data: Partial<Position>) => {
    setLoading(true);
    try {
      const response = await hrApiClient.put(`/positions/${id}`, data);
      const updated = response.data.result || response.data;
      setPositions(prev => prev.map(p => p.positionId === id ? updated : p));
      toast.success(response.data.message || 'Position updated successfully');
      return updated;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to update position');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const remove = async (id: number) => {
    setLoading(true);
    try {
      await hrApiClient.delete(`/positions/${id}`);
      setPositions(prev => prev.filter(p => p.positionId !== id));
      toast.success('Position deleted successfully');
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to delete position');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { positions, loading, fetchAll, create, update, remove };
};






