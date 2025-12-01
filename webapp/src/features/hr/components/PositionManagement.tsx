"use client";

import { useEffect, useState } from "react";
import { usePositions, Position, useDepartments } from "../hooks/useHR";

export const PositionManagement: React.FC = () => {
  const { positions, loading, fetchAll, create, update, remove } = usePositions();
  const { departments, fetchAll: fetchDepartments } = useDepartments();
  const [isCreating, setIsCreating] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [formData, setFormData] = useState<Partial<Position>>({
    departmentId: 0,
    name: "",
    description: "",
    status: "ACTIVE",
  });

  useEffect(() => {
    fetchAll();
    fetchDepartments();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await create(formData);
      setIsCreating(false);
      setFormData({ departmentId: 0, name: "", description: "", status: "ACTIVE" });
    } catch (error) {
      // Error handled by hook
    }
  };

  const handleEdit = (position: Position) => {
    setEditingId(position.positionId);
    setFormData({
      departmentId: position.departmentId,
      name: position.name,
      description: position.description || "",
      status: position.status || "ACTIVE",
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
    if (confirm("Are you sure you want to delete this position?")) {
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
          <h2 className="text-2xl font-bold text-gray-900">Position Management</h2>
          <p className="text-gray-600 mt-1">Manage positions in the system</p>
        </div>
        <button
          onClick={() => setIsCreating(true)}
          className="px-4 py-2 bg-sky-600 text-white rounded-lg hover:bg-sky-700 transition-colors"
        >
          + Add Position
        </button>
      </div>

      {isCreating && (
        <div className="px-8 py-6 border-b border-gray-200 bg-gray-50">
          <form onSubmit={handleCreate} className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Department *</label>
              <select
                value={formData.departmentId}
                onChange={(e) => setFormData({ ...formData, departmentId: Number(e.target.value) })}
                required
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
              >
                <option value={0}>Select Department</option>
                {departments.map((dept) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Name *</label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
              />
            </div>
            <div className="col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-2">Description</label>
              <textarea
                value={formData.description}
                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
                rows={2}
              />
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
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Department</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {positions.map((position) => {
              const dept = departments.find(d => d.departmentId === position.departmentId);
              return (
                <tr key={position.positionId} className="hover:bg-gray-50">
                  {editingId === position.positionId ? (
                    <>
                      <td className="px-6 py-4">
                        <input
                          type="text"
                          value={formData.name}
                          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                          className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                        />
                      </td>
                      <td className="px-6 py-4">
                        <select
                          value={formData.departmentId}
                          onChange={(e) => setFormData({ ...formData, departmentId: Number(e.target.value) })}
                          className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                        >
                          {departments.map((d) => (
                            <option key={d.departmentId} value={d.departmentId}>
                              {d.name}
                            </option>
                          ))}
                        </select>
                      </td>
                      <td className="px-6 py-4">
                        <select
                          value={formData.status || "ACTIVE"}
                          onChange={(e) => setFormData({ ...formData, status: e.target.value })}
                          className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                        >
                          <option value="ACTIVE">ACTIVE</option>
                          <option value="INACTIVE">INACTIVE</option>
                        </select>
                      </td>
                      <td className="px-6 py-4 text-right">
                        <button
                          onClick={() => handleUpdate(position.positionId)}
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
                      <td className="px-6 py-4 text-sm font-medium text-gray-900">{position.name}</td>
                      <td className="px-6 py-4 text-sm text-gray-500">{dept?.name || "N/A"}</td>
                      <td className="px-6 py-4">
                        <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                          position.status === "ACTIVE" ? "bg-green-100 text-green-800" : "bg-gray-100 text-gray-800"
                        }`}>
                          {position.status}
                        </span>
                      </td>
                      <td className="px-6 py-4 text-right text-sm font-medium">
                        <button
                          onClick={() => handleEdit(position)}
                          className="text-sky-600 hover:text-sky-900 mr-4"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDelete(position.positionId)}
                          className="text-red-600 hover:text-red-900"
                        >
                          Delete
                        </button>
                      </td>
                    </>
                  )}
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      {loading && positions.length === 0 && (
        <div className="text-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-sky-600 mx-auto"></div>
        </div>
      )}

      {!loading && positions.length === 0 && (
        <div className="text-center py-12 text-gray-500">No positions found</div>
      )}
    </div>
  );
};






