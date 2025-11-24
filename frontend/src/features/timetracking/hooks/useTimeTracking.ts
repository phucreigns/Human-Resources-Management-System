import { useState } from 'react';
import axios from 'axios';
import toast from 'react-hot-toast';

const TIME_TRACKING_API_BASE_URL = process.env.NEXT_PUBLIC_TIME_TRACKING_SERVICE_URL || 'http://localhost:8092/timetracking';

const timeTrackingApiClient = axios.create({
  baseURL: TIME_TRACKING_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
timeTrackingApiClient.interceptors.request.use(
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

export interface LeaveType {
  leaveTypeId: number;
  leaveTypeName: string;
  leaveTypeCode: string;
  maxDaysPerYear: number;
  requiresApproval: boolean;
  isPaid: boolean;
  createdAt?: string;
  updatedAt?: string;
}

export interface LeaveRequest {
  leaveRequestId: number;
  employeeId: string;
  leaveType: LeaveType;
  startDate: string;
  endDate: string;
  totalDays: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  approvedBy?: string;
  createdAt?: string;
  updatedAt?: string;
}

export const useLeaveTypes = () => {
  const [leaveTypes, setLeaveTypes] = useState<LeaveType[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchAll = async () => {
    setLoading(true);
    try {
      console.log('Fetching leave types from:', `${TIME_TRACKING_API_BASE_URL}/leave-types`);
      const response = await timeTrackingApiClient.get('/leave-types');
      console.log('Leave types API response:', response.data);
      
      // Handle different response structures
      let leaveTypesData = [];
      if (response.data) {
        if (Array.isArray(response.data)) {
          leaveTypesData = response.data;
        } else if (response.data.result && Array.isArray(response.data.result)) {
          leaveTypesData = response.data.result;
        } else if (response.data.data && Array.isArray(response.data.data)) {
          leaveTypesData = response.data.data;
        }
      }
      
      console.log('Parsed leave types:', leaveTypesData);
      setLeaveTypes(leaveTypesData);
    } catch (err: any) {
      console.error('Error fetching leave types:', err);
      console.error('Error response:', err.response?.data);
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch leave types';
      toast.error(errorMessage);
      setLeaveTypes([]); // Set empty array on error
    } finally {
      setLoading(false);
    }
  };

  const create = async (data: Partial<LeaveType>) => {
    setLoading(true);
    try {
      const response = await timeTrackingApiClient.post('/leave-types', data);
      const newLeaveType = response.data.result || response.data;
      setLeaveTypes(prev => [...prev, newLeaveType]);
      toast.success(response.data.message || 'Leave type created successfully');
      return newLeaveType;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to create leave type');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const update = async (id: number, data: Partial<LeaveType>) => {
    setLoading(true);
    try {
      const response = await timeTrackingApiClient.put(`/leave-types/${id}`, data);
      const updated = response.data.result || response.data;
      setLeaveTypes(prev => prev.map(lt => lt.leaveTypeId === id ? updated : lt));
      toast.success(response.data.message || 'Leave type updated successfully');
      return updated;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to update leave type');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const remove = async (id: number) => {
    setLoading(true);
    try {
      await timeTrackingApiClient.delete(`/leave-types/${id}`);
      setLeaveTypes(prev => prev.filter(lt => lt.leaveTypeId !== id));
      toast.success('Leave type deleted successfully');
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to delete leave type');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { leaveTypes, loading, fetchAll, create, update, remove };
};

export const useLeaveRequests = () => {
  const [leaveRequests, setLeaveRequests] = useState<LeaveRequest[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchAll = async () => {
    setLoading(true);
    try {
      console.log('Fetching leave requests from:', `${TIME_TRACKING_API_BASE_URL}/leave-requests`);
      const response = await timeTrackingApiClient.get('/leave-requests');
      console.log('Leave requests API response:', response.data);
      
      // Handle different response structures
      let leaveRequestsData = [];
      if (response.data) {
        if (Array.isArray(response.data)) {
          leaveRequestsData = response.data;
        } else if (response.data.result && Array.isArray(response.data.result)) {
          leaveRequestsData = response.data.result;
        } else if (response.data.data && Array.isArray(response.data.data)) {
          leaveRequestsData = response.data.data;
        }
      }
      
      console.log('Parsed leave requests:', leaveRequestsData);
      setLeaveRequests(leaveRequestsData);
    } catch (err: any) {
      console.error('Error fetching leave requests:', err);
      console.error('Error response:', err.response?.data);
      const errorMessage = err.response?.data?.message || err.message || 'Failed to fetch leave requests';
      toast.error(errorMessage);
      setLeaveRequests([]); // Set empty array on error
    } finally {
      setLoading(false);
    }
  };

  const approve = async (id: number, approvedBy: string) => {
    setLoading(true);
    try {
      const response = await timeTrackingApiClient.put(`/leave-requests/${id}/approve`, null, {
        params: { approvedBy },
      });
      const updated = response.data.result || response.data;
      setLeaveRequests(prev => prev.map(lr => lr.leaveRequestId === id ? updated : lr));
      toast.success(response.data.message || 'Leave request approved successfully');
      return updated;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to approve leave request');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const reject = async (id: number, approvedBy: string) => {
    setLoading(true);
    try {
      const response = await timeTrackingApiClient.put(`/leave-requests/${id}/reject`, null, {
        params: { approvedBy },
      });
      const updated = response.data.result || response.data;
      setLeaveRequests(prev => prev.map(lr => lr.leaveRequestId === id ? updated : lr));
      toast.success(response.data.message || 'Leave request rejected successfully');
      return updated;
    } catch (err: any) {
      toast.error(err.response?.data?.message || 'Failed to reject leave request');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return { leaveRequests, loading, fetchAll, approve, reject };
};






