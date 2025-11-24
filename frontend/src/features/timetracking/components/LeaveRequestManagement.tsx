"use client";

import { useEffect, useState } from "react";
import { useLeaveRequests, LeaveRequest } from "../hooks/useTimeTracking";
import { useAuth } from "@/context/AuthContext";

export const LeaveRequestManagement: React.FC = () => {
  const { leaveRequests, loading, fetchAll, approve, reject } = useLeaveRequests();
  const { user } = useAuth();

  useEffect(() => {
    fetchAll();
  }, []);

  const handleApprove = async (id: number) => {
    if (confirm("Are you sure you want to approve this leave request?")) {
      try {
        const approvedBy = user?.email || "admin";
        await approve(id, approvedBy);
      } catch (error) {
        // Error handled by hook
      }
    }
  };

  const handleReject = async (id: number) => {
    if (confirm("Are you sure you want to reject this leave request?")) {
      try {
        const approvedBy = user?.email || "admin";
        await reject(id, approvedBy);
      } catch (error) {
        // Error handled by hook
      }
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case "APPROVED":
        return "bg-green-100 text-green-800";
      case "REJECTED":
        return "bg-red-100 text-red-800";
      case "PENDING":
        return "bg-yellow-100 text-yellow-800";
      default:
        return "bg-gray-100 text-gray-800";
    }
  };

  return (
    <div className="bg-white rounded-2xl shadow-xl border border-gray-200 overflow-hidden">
      <div className="px-8 py-6 border-b border-gray-200">
        <h2 className="text-2xl font-bold text-gray-900">Leave Request Management</h2>
        <p className="text-gray-600 mt-1">Review and manage leave requests</p>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Employee ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Leave Type</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Start Date</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">End Date</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Total Days</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Approved By</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {leaveRequests.map((request) => (
              <tr key={request.leaveRequestId} className="hover:bg-gray-50">
                <td className="px-6 py-4 text-sm font-medium text-gray-900">{request.employeeId}</td>
                <td className="px-6 py-4 text-sm text-gray-500">{request.leaveType.leaveTypeName}</td>
                <td className="px-6 py-4 text-sm text-gray-500">
                  {new Date(request.startDate).toLocaleDateString()}
                </td>
                <td className="px-6 py-4 text-sm text-gray-500">
                  {new Date(request.endDate).toLocaleDateString()}
                </td>
                <td className="px-6 py-4 text-sm text-gray-500">{request.totalDays}</td>
                <td className="px-6 py-4">
                  <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(request.status)}`}>
                    {request.status}
                  </span>
                </td>
                <td className="px-6 py-4 text-sm text-gray-500">{request.approvedBy || "N/A"}</td>
                <td className="px-6 py-4 text-right text-sm font-medium">
                  {request.status === "PENDING" && (
                    <>
                      <button
                        onClick={() => handleApprove(request.leaveRequestId)}
                        className="text-green-600 hover:text-green-900 mr-4"
                      >
                        Approve
                      </button>
                      <button
                        onClick={() => handleReject(request.leaveRequestId)}
                        className="text-red-600 hover:text-red-900"
                      >
                        Reject
                      </button>
                    </>
                  )}
                  {request.status !== "PENDING" && (
                    <span className="text-gray-400">No actions</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {loading && leaveRequests.length === 0 && (
        <div className="text-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-sky-600 mx-auto"></div>
        </div>
      )}

      {!loading && leaveRequests.length === 0 && (
        <div className="text-center py-12 text-gray-500">No leave requests found</div>
      )}
    </div>
  );
};






