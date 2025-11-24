import { useState, useEffect } from 'react';
import apiClient from '@/lib/api';
import toast from 'react-hot-toast';

export interface User {
  userId: number;
  companyId?: number;
  departmentId?: number;
  positionId?: number;
  managerId?: number;
  employeeCode?: string;
  fullName: string;
  email: string;
  auth0Id?: string;
  phoneNumber?: string;
  avatarUrl?: string;
  address?: string;
  hireDate?: string;
  status?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface UserUpdateRequest {
  fullName: string;
  email?: string;
  phoneNumber?: string;
  avatarUrl?: string;
  address?: string;
  hireDate?: string;
  companyId?: number;
  departmentId?: number;
  positionId?: number;
  managerId?: number;
  status?: string;
}

export const useUsers = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchAllUsers = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await apiClient.get('/all');
      setUsers(response.data.result || response.data || []);
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch users';
      setError(errorMessage);
      toast.error(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const updateUser = async (userId: number, data: UserUpdateRequest) => {
    setLoading(true);
    setError(null);
    try {
      const response = await apiClient.put(`/update/${userId}`, data);
      const updatedUser = response.data.result || response.data;
      setUsers(prev => prev.map(u => u.userId === userId ? updatedUser : u));
      toast.success(response.data.message || 'User updated successfully');
      return updatedUser;
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Failed to update user';
      setError(errorMessage);
      toast.error(errorMessage);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const deleteUser = async (userId: number) => {
    setLoading(true);
    setError(null);
    try {
      await apiClient.delete(`/${userId}`);
      setUsers(prev => prev.filter(u => u.userId !== userId));
      toast.success('User deleted successfully');
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Failed to delete user';
      setError(errorMessage);
      toast.error(errorMessage);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return {
    users,
    loading,
    error,
    fetchAllUsers,
    updateUser,
    deleteUser,
  };
};

export const useMyProfile = () => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchMyProfile = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await apiClient.get('/me');
      setUser(response.data.result || response.data);
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch profile';
      setError(errorMessage);
      toast.error(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const updateMyProfile = async (data: UserUpdateRequest) => {
    setLoading(true);
    setError(null);
    try {
      const response = await apiClient.put('/me', data);
      const updatedUser = response.data.result || response.data;
      setUser(updatedUser);
      toast.success(response.data.message || 'Profile updated successfully');
      return updatedUser;
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.message || 'Failed to update profile';
      setError(errorMessage);
      toast.error(errorMessage);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return {
    user,
    loading,
    error,
    fetchMyProfile,
    updateMyProfile,
  };
};






