"use client";

import { useEffect, useState } from "react";
import { useLeaveTypes, LeaveType } from "../hooks/useTimeTracking";

export const LeaveTypeManagement: React.FC = () => {
  const { leaveTypes, loading, fetchAll, create, update, remove } = useLeaveTypes();
  const [isCreating, setIsCreating] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [formData, setFormData] = useState<Partial<LeaveType>>({
    leaveTypeName: "",
    leaveTypeCode: "",
    maxDaysPerYear: 0,
    requiresApproval: true,
    isPaid: true,
  });

  useEffect(() => {
    fetchAll();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await create(formData);
      setIsCreating(false);
      setFormData({
        leaveTypeName: "",
        leaveTypeCode: "",
        maxDaysPerYear: 0,
        requiresApproval: true,
        isPaid: true,
      });
    } catch (error) {
      // Error handled by hook
    }
  };

  const handleEdit = (leaveType: LeaveType) => {
    setEditingId(leaveType.leaveTypeId);
    setFormData({
      leaveTypeName: leaveType.leaveTypeName,
      leaveTypeCode: leaveType.leaveTypeCode,
      maxDaysPerYear: leaveType.maxDaysPerYear,
      requiresApproval: leaveType.requiresApproval,
      isPaid: leaveType.isPaid,
    });
  };

  const handleUpdate = async (id: number) => {
    try {
      await update(id, formData);
      setEditingId(null);
    } catch (error) {
      // Error handled by hook
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm("Are you sure you want to delete this leave type?")) {
      try {
        await remove(id);
      } catch (error) {
        // Error handled by hook
      }
    }
  };

  return (
    <div className="bg-white rounded-2xl shadow-xl border border-gray-200 overflow-hidden">
      <div className="px-8 py-6 border-b border-gray-200 flex justify-between items-center">
        <div>
          <h2 className="text-2xl font-bold text-gray-900">Leave Type Management</h2>
          <p className="text-gray-600 mt-1">Manage leave types in the system</p>
        </div>
        <button
          onClick={() => setIsCreating(true)}
          className="px-4 py-2 bg-sky-600 text-white rounded-lg hover:bg-sky-700 transition-colors"
        >
          + Add Leave Type
        </button>
      </div>

      {isCreating && (
        <div className="px-8 py-6 border-b border-gray-200 bg-gray-50">
          <form onSubmit={handleCreate} className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Name *</label>
              <input
                type="text"
                value={formData.leaveTypeName}
                onChange={(e) => setFormData({ ...formData, leaveTypeName: e.target.value })}
                required
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Code *</label>
              <input
                type="text"
                value={formData.leaveTypeCode}
                onChange={(e) => setFormData({ ...formData, leaveTypeCode: e.target.value })}
                required
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Max Days Per Year *</label>
              <input
                type="number"
                value={formData.maxDaysPerYear}
                onChange={(e) => setFormData({ ...formData, maxDaysPerYear: Number(e.target.value) })}
                required
                min="0"
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
              />
            </div>
            <div className="space-y-4">
              <div className="flex items-center">
                <input
                  type="checkbox"
                  checked={formData.requiresApproval}
                  onChange={(e) => setFormData({ ...formData, requiresApproval: e.target.checked })}
                  className="mr-2"
                />
                <label className="text-sm font-medium text-gray-700">Requires Approval</label>
              </div>
              <div className="flex items-center">
                <input
                  type="checkbox"
                  checked={formData.isPaid}
                  onChange={(e) => setFormData({ ...formData, isPaid: e.target.checked })}
                  className="mr-2"
                />
                <label className="text-sm font-medium text-gray-700">Is Paid</label>
              </div>
            </div>
            <div className="col-span-2 flex justify-end space-x-4">
              <button
                type="button"
                onClick={() => setIsCreating(false)}
                className="px-4 py-2 border border-gray-300 rounded-lg"
              >
                Cancel
              </button>
              <button type="submit" className="px-4 py-2 bg-sky-600 text-white rounded-lg">
                Create
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="overflow-x-auto">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Name</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Code</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Max Days/Year</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Requires Approval</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Is Paid</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {leaveTypes.map((leaveType) => (
              <tr key={leaveType.leaveTypeId} className="hover:bg-gray-50">
                {editingId === leaveType.leaveTypeId ? (
                  <>
                    <td className="px-6 py-4">
                      <input
                        type="text"
                        value={formData.leaveTypeName}
                        onChange={(e) => setFormData({ ...formData, leaveTypeName: e.target.value })}
                        className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                      />
                    </td>
                    <td className="px-6 py-4">
                      <input
                        type="text"
                        value={formData.leaveTypeCode}
                        onChange={(e) => setFormData({ ...formData, leaveTypeCode: e.target.value })}
                        className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                      />
                    </td>
                    <td className="px-6 py-4">
                      <input
                        type="number"
                        value={formData.maxDaysPerYear}
                        onChange={(e) => setFormData({ ...formData, maxDaysPerYear: Number(e.target.value) })}
                        className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                      />
                    </td>
                    <td className="px-6 py-4">
                      <input
                        type="checkbox"
                        checked={formData.requiresApproval}
                        onChange={(e) => setFormData({ ...formData, requiresApproval: e.target.checked })}
                      />
                    </td>
                    <td className="px-6 py-4">
                      <input
                        type="checkbox"
                        checked={formData.isPaid}
                        onChange={(e) => setFormData({ ...formData, isPaid: e.target.checked })}
                      />
                    </td>
                    <td className="px-6 py-4 text-right">
                      <button
                        onClick={() => handleUpdate(leaveType.leaveTypeId)}
                        className="text-green-600 hover:text-green-900 mr-4"
                      >
                        Save
                      </button>
                      <button
                        onClick={() => setEditingId(null)}
                        className="text-gray-600 hover:text-gray-900"
                      >
                        Cancel
                      </button>
                    </td>
                  </>
                ) : (
                  <>
                    <td className="px-6 py-4 text-sm font-medium text-gray-900">{leaveType.leaveTypeName}</td>
                    <td className="px-6 py-4 text-sm text-gray-500">{leaveType.leaveTypeCode}</td>
                    <td className="px-6 py-4 text-sm text-gray-500">{leaveType.maxDaysPerYear}</td>
                    <td className="px-6 py-4">
                      <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                        leaveType.requiresApproval ? "bg-blue-100 text-blue-800" : "bg-gray-100 text-gray-800"
                      }`}>
                        {leaveType.requiresApproval ? "Yes" : "No"}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                        leaveType.isPaid ? "bg-green-100 text-green-800" : "bg-gray-100 text-gray-800"
                      }`}>
                        {leaveType.isPaid ? "Yes" : "No"}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-right text-sm font-medium">
                      <button
                        onClick={() => handleEdit(leaveType)}
                        className="text-sky-600 hover:text-sky-900 mr-4"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => handleDelete(leaveType.leaveTypeId)}
                        className="text-red-600 hover:text-red-900"
                      >
                        Delete
                      </button>
                    </td>
                  </>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {loading && leaveTypes.length === 0 && (
        <div className="text-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-sky-600 mx-auto"></div>
        </div>
      )}

      {!loading && leaveTypes.length === 0 && (
        <div className="text-center py-12 text-gray-500">No leave types found</div>
      )}
    </div>
  );
};






