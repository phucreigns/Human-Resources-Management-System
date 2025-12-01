"use client";

import { useEffect, useState } from "react";
import { useDepartments, Department, useCompanies } from "../hooks/useHR";

export const DepartmentManagement: React.FC = () => {
  const { departments, loading, fetchAll, create, update, remove } = useDepartments();
  const { companies, fetchAll: fetchCompanies } = useCompanies();
  const [isCreating, setIsCreating] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [formData, setFormData] = useState<Partial<Department>>({
    companyId: 0,
    name: "",
    description: "",
    status: "ACTIVE",
  });

  useEffect(() => {
    fetchAll();
    fetchCompanies();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await create(formData);
      setIsCreating(false);
      setFormData({ companyId: 0, name: "", description: "", status: "ACTIVE" });
    } catch (error) {
      // Error handled by hook
    }
  };

  const handleEdit = (dept: Department) => {
    setEditingId(dept.departmentId);
    setFormData({
      companyId: dept.companyId,
      name: dept.name,
      description: dept.description || "",
      status: dept.status || "ACTIVE",
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
    if (confirm("Are you sure you want to delete this department?")) {
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
          <h2 className="text-2xl font-bold text-gray-900">Department Management</h2>
          <p className="text-gray-600 mt-1">Manage departments in the system</p>
        </div>
        <button
          onClick={() => setIsCreating(true)}
          className="px-4 py-2 bg-sky-600 text-white rounded-lg hover:bg-sky-700 transition-colors"
        >
          + Add Department
        </button>
      </div>

      {isCreating && (
        <div className="px-8 py-6 border-b border-gray-200 bg-gray-50">
          <form onSubmit={handleCreate} className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Company *</label>
              <select
                value={formData.companyId}
                onChange={(e) => setFormData({ ...formData, companyId: Number(e.target.value) })}
                required
                className="w-full border border-gray-300 rounded-lg px-4 py-2"
              >
                <option value={0}>Select Company</option>
                {companies.map((company) => (
                  <option key={company.companyId} value={company.companyId}>
                    {company.name}
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
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Company</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {departments.map((dept) => {
              const company = companies.find(c => c.companyId === dept.companyId);
              return (
                <tr key={dept.departmentId} className="hover:bg-gray-50">
                  {editingId === dept.departmentId ? (
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
                          value={formData.companyId}
                          onChange={(e) => setFormData({ ...formData, companyId: Number(e.target.value) })}
                          className="w-full border border-gray-300 rounded px-3 py-2 text-sm"
                        >
                          {companies.map((c) => (
                            <option key={c.companyId} value={c.companyId}>
                              {c.name}
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
                          onClick={() => handleUpdate(dept.departmentId)}
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
                      <td className="px-6 py-4 text-sm font-medium text-gray-900">{dept.name}</td>
                      <td className="px-6 py-4 text-sm text-gray-500">{company?.name || "N/A"}</td>
                      <td className="px-6 py-4">
                        <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                          dept.status === "ACTIVE" ? "bg-green-100 text-green-800" : "bg-gray-100 text-gray-800"
                        }`}>
                          {dept.status}
                        </span>
                      </td>
                      <td className="px-6 py-4 text-right text-sm font-medium">
                        <button
                          onClick={() => handleEdit(dept)}
                          className="text-sky-600 hover:text-sky-900 mr-4"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDelete(dept.departmentId)}
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

      {loading && departments.length === 0 && (
        <div className="text-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-sky-600 mx-auto"></div>
        </div>
      )}

      {!loading && departments.length === 0 && (
        <div className="text-center py-12 text-gray-500">No departments found</div>
      )}
    </div>
  );
};






